package be.trollcorporation.wikidoci.app;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import be.trollcorporation.wikidoci.model.Definition;
import be.trollcorporation.wikidoci.model.Mot;
import be.trollcorporation.wikidoci.service.DictionaryService;
import be.trollcorporation.wikidoci.service.DictionaryServiceImpl;
import be.trollcorporation.wikidoci.utils.StringUtils;

public class Dico {
	private static DictionaryService dico = new DictionaryServiceImpl();
	
	public static void main(String[] args) {
		System.out.println("Bienvenu sur Dico");
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("Entrez le mot recherché :");
			if (in.hasNextLine()) {
				String searchWord = in.nextLine();
				List<Mot> mots = dico.getWord(searchWord);
				if (mots != null && !mots.isEmpty()) {
					mots.sort(new Comparator<Mot>() {
						@Override
						public int compare(final Mot o1, final Mot o2) {
							return StringUtils.compare(o1.getOrthographe(), searchWord)
									- StringUtils.compare(o2.getOrthographe(), searchWord);
						}
					});
					if (mots.get(0).getOrthographe().equals(searchWord)) {
						printMot(mots.get(0));
						for (int i = 1; i < 15; i++) {
							if (i < mots.size() 
									&& mots.get(i).getOrthographe().equals(searchWord)) {
								System.out.print("ou bien ");
								printMot(mots.get(i));
							}
						}
					} else {
						System.out.println("Vous cherchiez peut-être : ");
						for (int i = 0; i < 15; i++) {
							if (i < mots.size()) {
								System.out.println(mots.get(i));
							}
						}
					}
				}
			}
			System.out.println("");
			System.out.println("Voulez-vous cherchez un autre mot (y | n) ?");
		} while (in.hasNextLine() && in.nextLine().matches("^[y|o|1]$"));
		in.close();
	}
	
	private static void printMot(Mot mot) {
		System.out.println("Vous cherchiez le mot");
		System.out.println(mot.toString());
		System.out.println("Classe(s) : " 
				+ StringUtils.join(mot.getClasse()
						.stream()
						.map(e -> e.getValues().get(0))
						.collect(Collectors.toList()))
		);
		for (Definition definition : mot.getDefinitions()) {
			System.out.println(definition.getContenu());
		}
	}
}
