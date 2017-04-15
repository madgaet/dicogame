package be.trollcorporation.wikidoci.service;

import java.util.List;

import be.trollcorporation.wikidoci.model.Mot;
import be.trollcorporation.wikidoci.utils.ConfigurationUtils;

public class WordSearchServiceImpl implements WordSearchService {

	private DictionaryService dicoService;
	
	public WordSearchServiceImpl() {
		String daoImpl = ConfigurationUtils.getProperty("dictionary.service.impl",
                DictionaryServiceImpl.class.getSimpleName());
        try {
        	dicoService = (DictionaryService) Class.forName(daoImpl).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        	dicoService = new DictionaryServiceImpl();
        }
	}
	
	
	@Override
	public List<Mot> searchWithPattern(final String pattern) {
		dicoService.find(1);
		return null;
	}

}
