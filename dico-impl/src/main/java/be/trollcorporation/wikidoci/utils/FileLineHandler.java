package be.trollcorporation.wikidoci.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.trollcorporation.wikidoci.model.Operator;

/**
 * Created by madgaet on 12-06-16.
 * Handler on the line from a file.
 */
public class FileLineHandler {

    private Stream<String> stream;

    public FileLineHandler(Stream<String> stream) {
    	this.stream = stream;
    }

    public Optional<String> getLine(int id) {
        return stream
        		.filter(line -> line.contains("" + id))
        		.findFirst();
    }
    
    public List<String> getLinesWith(String field, String value, Operator op, int limit) {
    	Predicate<String> predicate = line -> {
    		List<String> splitLine = 
    				Arrays.asList(line.substring(1, line.length()-1).split(","));
    		String val = "0";
    		for (String attribut : splitLine) {
    			if (StringUtils.containsIgnoreCase(attribut, field)) {
    				val = attribut.split(":")[1].trim().replaceAll("\"", "");
    			}
    		}
    		switch(op) {
    		case ALMOST:
    			return StringUtils.equalsWithTolerance(val, value, 50);
    		case CROSSWORD:
    			return StringUtils.equalsWithCrossRegex(val, value);
    		case SMALLER:
    			return val.compareTo(value) < 0;
    		case EQUAL:
    			return val.compareTo(value) == 0;
    		case BIGGER:
    		default:
    			return val.compareTo(value) > 0;
    		}
    	};
        return stream
        		.filter(line -> line.contains(field))
        		.filter(predicate)
        		.unordered()
        		.limit(limit)
        		.collect(Collectors.toList());
    }

    public List<String> getLines(Predicate<String> predicate) {
    	return stream
    			.filter(predicate)
    			.collect(Collectors.toList());
    }

}
