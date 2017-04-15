package be.trollcorporation.wikidoci.service;

import java.util.List;

import be.trollcorporation.wikidoci.model.Field;
import be.trollcorporation.wikidoci.model.Mot;
import be.trollcorporation.wikidoci.model.Operator;
import be.trollcorporation.wikidoci.persistence.DictionaryFileDao;
import be.trollcorporation.wikidoci.persistence.DictionaryFileDaoImpl;
import be.trollcorporation.wikidoci.utils.ConfigurationUtils;

/**
 * Created by madgaet on 19-06-16.
 */
public class DictionaryServiceImpl implements DictionaryService {

    DictionaryFileDao dictionaryDao;

    public DictionaryServiceImpl() {
        String daoImpl = ConfigurationUtils.getProperty("dictionary.dao.impl",
                DictionaryFileDaoImpl.class.getSimpleName());
        try {
            dictionaryDao = (DictionaryFileDao) Class.forName(daoImpl).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            dictionaryDao = new DictionaryFileDaoImpl();
        }
    }

    @Override
    public Mot find(int id) {
        return dictionaryDao.find(id);
    }
    
    @Override
    public List<Mot> getWord(String orthographe) {
    	return dictionaryDao
    			.getWordsWith(Field.SPELLING, orthographe);
    }
    
    @Override
    public List<Mot> getPossibleWords(String orthographe) {
    	try {
    		return dictionaryDao.getPossibleWords(orthographe);
    	} catch (IllegalArgumentException i) {
    		System.out.println(i.getMessage());
    		return null;
    	}
    }
    
    @Override
    public Mot findRandom(int frequency) {
    	return dictionaryDao
    			.getRandomWordWith(Field.FREQUENCY, frequency, Operator.BIGGER);
    }

	@Override
	public List<Mot> find(final String pattern) {
		return dictionaryDao.query(pattern);
	}

    @Override
    public List<Mot> load(int fromId, int size) {
        return dictionaryDao.load(fromId, size);
    }
}
