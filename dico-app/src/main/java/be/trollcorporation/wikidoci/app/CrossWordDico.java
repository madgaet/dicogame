package be.trollcorporation.wikidoci.app;

import java.util.List;
import java.util.Scanner;

import be.trollcorporation.wikidoci.model.Mot;
import be.trollcorporation.wikidoci.service.DictionaryService;
import be.trollcorporation.wikidoci.service.DictionaryServiceImpl;

public class CrossWordDico {
	
private static DictionaryService dico = new DictionaryServiceImpl();
	
	public static void main(String[] args) {
		System.out.println("Bienvenu sur Dico");
		Scanner in = new Scanner(System.in);
		System.out.println("Entrez le mot recherché :");
		if (in.hasNextLine()) {
			String searchWord = in.nextLine();
			List<Mot> mots = dico.getPossibleWords(searchWord);
			if (mots != null && !mots.isEmpty()) {
				printList(mots);
			} else {
				System.out.println("Aucune possibilité n'a été trouvée.");
			}
		}
		in.close();
	}
	
	private static void printList(List<Mot> mots) {
		System.out.println("Vous cherchez peut-être : ");
		for (Mot mot : mots) {
			System.out.println(mot.getOrthographe());
		}
	}
}
