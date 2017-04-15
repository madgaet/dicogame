package be.trollcorporation.wikidoci.model;

/**
 * Class to represent the definition of a word.
 * @author madgaet
 */
public class Definition {
	
	private String contenu;
	//TODO type define the different kinds of definitions that are possible. 
	private String type;
	
	public Definition(final String contenu, final String type) {
		this.contenu = contenu;
		this.type = type;
	}
	
	public Definition(final String contenu) {
		this.contenu = contenu;
	}
	
	public String getContenu() {
		return contenu;
	}
	public String getType() {
		return type;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
