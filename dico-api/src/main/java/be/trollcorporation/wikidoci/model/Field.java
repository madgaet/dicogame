package be.trollcorporation.wikidoci.model;

public enum Field {
	
	SPELLING ("\"orthographe\""),
	FREQUENCY ("\"frequency\"");
	
	private String name;
	private Field(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
