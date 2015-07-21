package net.muneris.simbadder.integrationtests;

import static net.muneris.simbadder.testUtils.TestConstants.NAME;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.formatting.FormatField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimbadResponseTestIT {

	Simbad simbad;
	Format format;
	ObjectMapper mapper;
	
	@Before
	public void setUp() throws Exception {
		simbad = new Simbad();
		format = new Format(NAME);
		format.setFields(FormatField.getAll());
		//format.addField(FormatField.MEASLIST);
		simbad.setFormat(format);
		mapper = new ObjectMapper();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws JsonProcessingException {
		List<SimbadObject> objects = simbad.runQuery();
		assertNotNull(objects);
		assertEquals(32, objects.size());
		String jsonString = mapper.writeValueAsString(objects.get(0));
		assertTrue(jsonString.length() > 0);
	}

}
