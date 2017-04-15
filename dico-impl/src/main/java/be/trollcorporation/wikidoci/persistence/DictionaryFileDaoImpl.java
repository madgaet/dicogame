package be.trollcorporation.wikidoci.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import be.trollcorporation.wikidoci.model.Field;
import be.trollcorporation.wikidoci.model.Mot;
import be.trollcorporation.wikidoci.model.Operator;
import be.trollcorporation.wikidoci.utils.ConfigurationUtils;
import be.trollcorporation.wikidoci.utils.FileLineHandler;
import be.trollcorporation.wikidoci.utils.StringUtils;

/**
 * Created by madgaet on 12-06-16.
 */
public class DictionaryFileDaoImpl implements DictionaryFileDao {

	public static final String DEFAULT_PATH = "data/larousse/definitions.txt";
	private static final String RESOURCES_PATH = ClassLoader.getSystemResource(DEFAULT_PATH).toString().substring(5);
    private static final Gson gson = new GsonBuilder().create();

    //Alphabetic indexes
    private static final int[] indexes = {1, 7272, 11846, 21457, 27199, 32532, 35716, 38743, 41312,
        44607, 45269, 45748, 48270, 53660, 55327, 57148, 65371, 65809, 70334, 76271, 80411, 80844,
        82670, 82847, 82934, 83027};

    private Charset charset;
    private Path path;

    public DictionaryFileDaoImpl() {
        this(RESOURCES_PATH, Charset.forName("UTF-8"));
    }

    public DictionaryFileDaoImpl(final String path, final Charset charset) {
        this.path = new File(RESOURCES_PATH).toPath(); //Paths.get(path);
        this.charset = charset;
    }

    private int getMaximumDicoEntries() {
        String max = ConfigurationUtils.getProperty("maximum.definitions", "83296");
        return Integer.valueOf(max);
    }

    @Override
    public Mot find(int id) {
        if (id < 1 || id > getMaximumDicoEntries()) {
            throw new IllegalArgumentException("id " + id + " doesn't match requirements");
        }
        Mot mot = null;
        try {
            FileLineHandler fli = new FileLineHandler(Files.lines(path));
            Optional<String> line = fli.getLine(id);
            mot = line.isPresent() ? gson.fromJson(line.get(), Mot.class) : null;
        } catch (IOException e) {
            Mot m = new Mot();
            m.setId(id);
            m.setOrthographe("find (" + id + ") error : " + e.getMessage());
            return m;
        }
        return mot;
    }

    @Override
    public List<Mot> load(int fromId, int size) {
        if (fromId < 1 || fromId > getMaximumDicoEntries()) {
            throw new IllegalArgumentException("id " + fromId + " doesn't match requirements");
        }
        List<Mot> list = new ArrayList<Mot>();
        try {
            FileLineHandler fli = new FileLineHandler(Files.lines(path));
            //TODO modify
            List<Integer> ids = new ArrayList<Integer>();
            for (int i = 0; i < size; i++) {
            	ids.add(fromId + i);
            }
            List<String> lines = fli.getLines(line -> ids.contains(gson.fromJson(line, Mot.class).getId()));
            for (String line : lines) {
                list.add(gson.fromJson(line, Mot.class));
            }
        } catch (IOException e) {
            Mot m = new Mot();
            m.setId(1);
            m.setOrthographe("Load error : " + e.getMessage());
            list.add(m);
        }
        return list;
    }

    @Override
    public Mot findFirst(String orthographe) {
        if (orthographe == null || orthographe.isEmpty()) {
            return null;
        }
        List<Mot> list = findAll(orthographe);
        if (list != null && !list.isEmpty()) {
        	return list.get(0);
        }
        return null;
    }

    @Override
    public List<Mot> findAll(String orthographe) {
        if (orthographe == null || orthographe.isEmpty() || !orthographe.matches("\\p{ASCII}")) {
            return null;
        }
        try {
            List<Mot> mots = new ArrayList<Mot>();
            FileLineHandler fli = new FileLineHandler(Files.lines(path));
            List<String> lines = fli.getLines(string -> StringUtils.containsIgnoreCase(string, orthographe));
            for (String line : lines) {
                mots.add(gson.fromJson(line, Mot.class));
            }
            return mots;
        } catch (IOException e) {
            return null;
        }
    }

	@Override
	public List<Mot> query(String textPattern) {
		if (textPattern == null || textPattern.isEmpty()) {
            return null;
        }
      try {
            List<Mot> mots = new ArrayList<Mot>();
            FileLineHandler fli = new FileLineHandler(Files.lines(path));
            List<String> lines = fli.getLines(string -> StringUtils.matchIgnoreCase(string, textPattern));
            for (String line : lines) {
                mots.add(gson.fromJson(line, Mot.class));
            }
            return mots;
        } catch (IOException e) {
            return null;
        }
	}
	
	@Override
	public Mot getRandomWordWith(Field field, Object value, Operator op) {
		List<String> mots = getWordsWith(field, value, op);
		if (mots != null) {
			String mot = mots.get((int) Math.round(Math.random() * mots.size()) + 1);
			return gson.fromJson(mot, Mot.class);
		}
		return null;
	}
	
	@Override
	public List<Mot> getWordsWith(Field field, Object value) {
		List<String> list = getWordsWith(field, value, Operator.ALMOST, 15000);
		return list.stream()
				.map(line -> gson.fromJson(line, Mot.class))
				.collect(Collectors.toList());
	}
	
	private List<String> getWordsWith(Field field, Object value, Operator op) {
		return getWordsWith(field, value, op, 15000);
	}
	
	private List<String> getWordsWith(Field field, Object value, Operator op, int limit) {
		if (field == null || value == null) {
			return null;
		}
		if (op == null) {
			op = Operator.EQUAL;
		}
		try {
			FileLineHandler fli = new FileLineHandler(Files.lines(path));
			return fli.getLinesWith(field.getName(), value.toString(), op, limit);
		} catch (IOException e) {
			//LOG
			System.out.print(e.getMessage());
		}
		return null;
		
	}
	
	@Override
	public List<Mot> getPossibleWords(String value) throws IllegalArgumentException {
		if (!value.matches(StringUtils.CROSS_REGEX)) {
			throw new IllegalArgumentException("Not valid value");
		}
		try {
			FileLineHandler fli = new FileLineHandler(Files.lines(path));
			return fli.getLinesWith(Field.SPELLING.getName(), value.toString(),
					Operator.CROSSWORD, 15000)
					.stream()
					.map(line -> gson.fromJson(line, Mot.class))
					.collect(Collectors.toList());
		} catch (IOException e) {
			//LOG
			System.out.print(e.getMessage());
		}
		return null;
	}

}
