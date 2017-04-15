package be.trollcorporation.wikidoci.model;

/**
 * Class that represent the words that can change.
 * @author madgaet
 */
public class MotVariable extends Mot {
	
	public String getRadical() {
		//TODO routine de radicalisation d'un mot.
		return getOrthographe();
	}

}
