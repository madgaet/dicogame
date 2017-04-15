package be.trollcorporation.wikidoci.service;

import java.util.List;

import be.trollcorporation.wikidoci.model.Mot;

public interface WordSearchService {
	
	List<Mot> searchWithPattern(String pattern);
}
