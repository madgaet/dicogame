package be.trollcorporation.wikidoci.utils;

import java.util.List;

import be.trollcorporation.wikidoci.model.EnumClasseMot;

/**
 * Created by madgaet on 19-06-16.
 */
public final class StringUtils {
	
	public static final String CROSS_REGEX = "^[a-zA-Z | \\*]*$";

    public static <T extends Object> String join(List<T> list) {
        return join(list, ",");
    }

    public static <T extends Object> String join(List<T> list, final String join) {
        if (list == null) {
            return null;
        } else if (list.isEmpty()) {
            return "";
        } else {
            String toJoin = list.get(0).toString();
            for (int i = 1; list.size() > i; i++) {
                if (list.get(i) instanceof EnumClasseMot) {
                    toJoin += join + " " + ((EnumClasseMot) list.get(i)).getValues().get(0);
                } else {
                    toJoin += join + " " + list.get(i).toString();
                }
            }
            return toJoin;
        }
    }
    
    private static int indexOfIgnoreCase(String s1, String s2) {
    	return s1.toLowerCase().indexOf(s2.toLowerCase());
    }
    
    public static boolean containsIgnoreCase(String s1, String s2) {
    	return s1.toLowerCase().contains(s2.toLowerCase());
    }
    
    public static boolean matchIgnoreCase(String s1, String s2) {
    	return s1.toLowerCase().matches(s2.toLowerCase());
    }
    
    /**
     * This method will hide a word from a text whatever it's case
     * @param text
     * 			the text from which we should hide the word.
     * @param word
     * 			the word to hide.
     * @return the text result.
     */
    public static String hideWord(final String text, final String word) {
    	if (word != null && word.length() <= 2) {
    		return text;
    	}
    	String result = text.replaceAll(word, "...");
    	int i = -1;
    	while ((i = indexOfIgnoreCase(result, word)) != -1) {
    		result = result.substring(0, i) + "..." + result.substring(i+1, result.length());
    	}
    	return result;
    }
    
    /**
     * This will compare the two text and will tell if they are equals (ignoring case).
     * and allowing a tolerance percentage of type error.
     * @param text1
     * 			the first text.
     * @param text2
     * 			the second text.
     * @param tolerancePercentage
     * 			the tolerance percentage (0 <= tolerance percentage <= 100).
     * @return true if equaly enough, false otherwise.
     */
    public static boolean equalsWithTolerance(final String text1, final String text2, final int tolerancePercentage) {
    	if (tolerancePercentage < 0 && tolerancePercentage > 100) {
    		throw new IllegalArgumentException("tolerance percentage should be between 0 and 100");
    	}
    	if (text1 == null || text2 == null) {
    		throw new IllegalArgumentException("text can't be null");
    	}
    	int tolerance = 100 - tolerancePercentage;
    	if (tolerance == 0 || text1.equals(text2)) {
    		return true;
    	}
    	double errorTolerance = Math.round(
    			((text1.length() + text2.length()) / 2.0) * (tolerancePercentage / 100.0));
    	return compare(text1, text2) <= errorTolerance;
    }
    
    /**
     * Verify if two texts are equals when replacing '*' by any letter.
     * @param text1
     * 			the first text
     * @param text2
     * 			the second text
     * @return true if the two texts matches.
     */
    public static boolean equalsWithCrossRegex(final String text1, final String text2) {
    	if ((text1 == null || text2 == null)
    			|| text1.length() != text2.length()) {
    		return false;
    	}
    	String t1 = text1.toLowerCase();
    	String t2 = text2.toLowerCase();
    	for (int i=0; i < t1.length(); i++) {
    		char c1 = t1.charAt(i);
    		char c2 = t2.charAt(i);
    		if ((c1 != '*' && c2 != '*') && c1 != c2) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static int compare(final String text1, final String text2) {
    	int error = 0;
    	int maxLength = Math.max(text1.length(), text2.length());
    	for (int i = 0; i < maxLength; i++) {
    		if (text1.length() <= i || text2.length() <= i) {
    			error += 1;
    		} else if (text1.charAt(i) != text2.charAt(i)) {
    			error += 1;
    		}
    	}
    	return error;
    }
    
}
