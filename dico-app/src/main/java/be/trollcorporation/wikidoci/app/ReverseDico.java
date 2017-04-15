package be.trollcorporation.wikidoci.app;

import java.util.Scanner;
import java.util.stream.Collectors;

import be.trollcorporation.wikidoci.model.Definition;
import be.trollcorporation.wikidoci.model.Mot;
import be.trollcorporation.wikidoci.service.DictionaryService;
import be.trollcorporation.wikidoci.service.DictionaryServiceImpl;
import be.trollcorporation.wikidoci.utils.StringUtils;

public class ReverseDico {
	
	private static DictionaryService dico = new DictionaryServiceImpl();
	private static int FREQUENCE = 2500;
	
	public static void main(String[] args) {
		System.out.println("Bienvenu sur Dicogame");
		System.out.println("Voici 10 questions:");
		int score = 0;
		Scanner in = new Scanner(System.in);
		for (int i = 1; i < 11; i++) {
			Mot mot = dico.findRandom(FREQUENCE);
			System.out.println("Question " + i + " :");
			System.out.println("Nous cherchons un " 
					+ StringUtils.join(mot.getClasse()
							.stream()
							.map(e -> e.getValues().get(0))
							.collect(Collectors.toList()))
			);
			for (Definition definition : mot.getDefinitions()) {
				System.out.println(StringUtils.hideWord(definition.getContenu(), mot.getOrthographe()));
			}
			String response = "";
			int essai = 3;
			System.out.println("Vous avez 3 essais :");
			while (essai > 0) {
				if (in.hasNextLine()) {
					response = in.nextLine();
				}
				if (StringUtils.equalsWithTolerance(response, mot.getOrthographe(), 5)) {
					break;
				} else {
					essai--;
					if (essai > 0) {
						System.out.println("Il vous reste " + essai + " essai(s)");
					}
				}
				
			}
			if (essai == 0) {
				System.out.println("Vous n'avez pas trouvé le mot : " + mot.getOrthographe());
			} else {
				score++;
				System.out.println("Bien joué. On cherchait en effet le mot: " + mot.getOrthographe());
			}
		}
		in.close();
		System.out.println("Vous avez obtenu le score de " + score + "/10");
		
	}
}
