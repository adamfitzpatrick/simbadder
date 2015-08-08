package net.muneris.simbadder.client;

import java.util.List;

import net.muneris.simbadder.exceptions.ObjectNotFoundException;
import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.Simbad;

import org.apache.log4j.Logger;

public class ResponseAssembler {

	private static final Logger log = Logger.getLogger(ResponseAssembler.class);
	
	public static SimbadObject assembleSingle(Simbad simbad) {
		List<SimbadObject> objects = assembleList(simbad);
		if (objects.size() > 1) {
			log.warn(String.format("Expected a single object response, but got %s objects.", objects.size()));
		}
		return objects.get(0);
	}
	
	public static List<SimbadObject> assembleList(Simbad simbad) {
		List<SimbadObject> objects = simbad.execute();
		if (objects.size() == 0) {
			throw new ObjectNotFoundException(simbad.getQuery());
		}
		return objects;
	}
}
