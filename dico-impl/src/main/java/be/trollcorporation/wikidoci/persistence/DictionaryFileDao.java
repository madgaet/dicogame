package be.trollcorporation.wikidoci.persistence;

import java.util.List;

import be.trollcorporation.wikidoci.model.Field;
import be.trollcorporation.wikidoci.model.Mot;
import be.trollcorporation.wikidoci.model.Operator;

/**
 * Created by madgaet on 12-06-16.
 */
public interface DictionaryFileDao {

    /**
     * Search a word in the file dictionary by using it's alphabetic order.
     * Throws a illegal argument if the id is out of range.
     * @param id
     *          the alphabetic position of the word (1 <= id <= 83296)
     * @return the word corresponding to the order position.
     */
    Mot find(int id);

    /**
     * load a specific amount of word, from one given id.
     * The fromId has to be greater or equal to 1 and smaller than 83297.
     * Depending on the fromId, the size will be limited if it would exceed the maximum id.
     * @param fromId
     *          the starting from which to load (1 <= fromId <= 83296).
     * @param size
     *          the amount of words to load.
     * @return the list of following words from the dictionary starting from fromId and going
     *      to fromId + size with a maximum of 83296.
     */
    List<Mot> load(int fromId, int size);

    /**
     * Search a word in the file dictionary using the spelling given.
     * @param orthographe
     *          the spelling of a word.
     * @return the first match of the word corresponding to the spelling or null if none.
     */
    Mot findFirst(String orthographe);

    /**
     * Search a list of words in the file dictionary using the spelling given.
     * @param orthographe
     *          the spelling of a word.
     * @return All the matches of the word corresponding to the spelling or empty list if none.
     */
    List<Mot> findAll(String orthographe);
    
    /**
     * Search the dico for a text pattern.
     * @param textPattern
     * 			the text pattern to search
     * @return the list of words matching.
     */
    List<Mot> query(String textPattern);
    
    /**
     * Get a random word corresponding to the criterias.
     * @param field
     * 			the field.
     * @param value
     * 			the value.
     * @param op
     * 			the operator.
     * @return a word if found or null.
     */
    Mot getRandomWordWith(Field field, Object value, Operator op);

    /**
     * Get the list of words with the specified value of the field or the closest ones.
     * @param field
     * 			the field
     * @param value
     * 			the field value.
     * @return the list of word matching.
     */
    List<Mot> getWordsWith(Field field, Object value);

    /**
     * Get the list of words with the spelling matching the String value format.
     * @param value
     * 			the string value format.
     * @return the list of words matching.
     * @throws IllegalArgumentException if value doesn't match the correct format
     * 
     * format is ^[a-z | \\*]$
     */
    List<Mot> getPossibleWords(String value) throws IllegalArgumentException;
}
