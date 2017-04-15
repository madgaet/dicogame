package be.trollcorporation.wikidoci.service;

import java.util.List;

import be.trollcorporation.wikidoci.model.Mot;

/**
 * Created by madgaet on 19-06-16.
 */
public interface DictionaryService {

    /**
     * Search a word in the file dictionary by using it's alphabetic order.
     * Throws a illegal argument if the id is out of range.
     * @param id
     *          the alphabetic position of the word (1 <= id <= 83296)
     * @return the word corresponding to the order position.
     */
    Mot find(int id);
    
    /**
     * Search a word based on it's typo
     * @param orthographe
     * 			the search orthographe.
     * @return the list of word matching the orthographe
     */
    List<Mot> getWord(String orthographe);
    
    /**
     * Search a list of word matching the length and the letters given.
     * @param orthographe
     * 			a word with '*' where letters are not known.
     * @return the list of word matching.
     */
    List<Mot> getPossibleWords(String orthographe);
    
    /**
     * find a random word having at least the given frequence.
     * or the 100 word found. (to limit requests)
     * @param frequency
     * 			the minimum frequency of the searched word.
     * @return the word.
     */
    Mot findRandom(int frequency);
    
    /**
     * Method that will search in the dictionnary a word matching the pattern.
     * @param pattern
     * 			the word pattern.
     * @return the list of word found.
     */
    List<Mot> find(String pattern);

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
    
}
