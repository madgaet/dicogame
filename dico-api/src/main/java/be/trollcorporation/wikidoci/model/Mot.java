package be.trollcorporation.wikidoci.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a french Word.
 *
 * @author madgaet
 */
public class Mot {

    private int id;
    private String orthographe;
    private int frequency;
    private List<Definition> definitions;
    private List<EnumClasseMot> classes;

    public int getId() {
        return id;
    }

    public String getOrthographe() {
        return orthographe;
    }

    public int getFrequency() {
        return frequency;
    }

    public List<EnumClasseMot> getClasse() {
        return classes;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrthographe(String orthographe) {
        this.orthographe = orthographe;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void addDefinition(Definition definition) {
        if (definition == null) {
            this.definitions = null;
        } else {
            if (this.definitions == null) {
                this.definitions = new ArrayList<Definition>();
            }
            this.definitions.add(definition);
        }
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public void setClasses(List<EnumClasseMot> classes) {
        this.classes = classes;
    }

    public String toString() {
        return orthographe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mot mot = (Mot) o;
        if (orthographe != null ? !orthographe.equals(mot.orthographe) : mot.orthographe != null) {
            return false;
        }
        if (definitions != null) {
            if (mot.definitions == null) {
                return false;
            } else if (definitions.size() != mot.definitions.size()) {
                return false;
            }
            for (int i = 0; i < definitions.size(); i++) {
                if (!definitions.get(i).getContenu().equals(mot.definitions.get(i).getContenu())) {
                    return false;
                }
            }
        } else if (mot.definitions != null) {
            return false;
        }
        if (classes != null) {
            if (mot.classes == null) {
                return false;
            } else if (classes.size() != mot.classes.size()) {
                return false;
            }
            for (int i = 0; i < classes.size(); i++) {
                if (!classes.get(i).name().equals(mot.classes.get(i).name())) {
                    return false;
                }
            }
        } else if (mot.classes != null) {
            return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        int result = orthographe != null ? orthographe.hashCode() : 0;
        return result;
    }
}
