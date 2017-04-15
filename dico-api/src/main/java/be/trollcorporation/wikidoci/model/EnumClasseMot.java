package be.trollcorporation.wikidoci.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an enum representing the different classes of words.
 * @author madgaet
 */
public enum EnumClasseMot {
	
	NOMS("nom"),
	DETS("article", "article défini", "article indéfini"),
	ADJS("adjectif"),
	PRON("pronom"),
	VERB("verbe"),
	ADV("adverbe"),
	PREP("préposition"),
	CONJ("conjonction"),
	INTJ("interjection");
	
	private List<String> values = new ArrayList<String>();
	
	private EnumClasseMot(final String... values) {
		for (String value : values) {
			this.values.add(value);
		}
	}
	
	public List<String> getValues() {
		return values;
	}
	
	public static EnumClasseMot fromName(final String name) {
		if (name == null) {
			return null;
		}
		String nameLow = name.trim().toLowerCase();
		for (EnumClasseMot classe : values()) {
			if (classe.name().toLowerCase().equals(nameLow)) {
				return classe;
			}
		}
		return null;
	}
	
	public static EnumClasseMot fromValue(final String value) {
		if (value == null) {
			return null;
		}
		String valueLow = value.trim().toLowerCase();
		for (EnumClasseMot classe : values()) {
			for (String classeVal : classe.values) {
				if (classeVal.toLowerCase().equals(valueLow)) {
					return classe;
				}
			}
		}
		return null;
	}
	
	public boolean isVariable() {
		return (this == NOMS || this == DETS || this == ADJS || this == PRON || this == VERB);
	}
	
}
