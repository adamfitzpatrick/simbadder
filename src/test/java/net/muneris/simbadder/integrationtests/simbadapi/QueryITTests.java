package net.muneris.simbadder.integrationtests.simbadapi;

import static net.muneris.simbadder.testUtils.TestConstants.NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.formatting.FormatField;
import net.muneris.simbadder.simbadapi.query.CustomQuery;
import net.muneris.simbadder.simbadapi.query.IdQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QueryITTests {

	Simbad simbad;
	Format format;
	ObjectMapper mapper;
	
	@Before
	public void setUp() throws Exception {
		format = new Format(NAME);
		format.setFields(FormatField.getAllNotDist());
		mapper = new ObjectMapper();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIdListQuery() throws JsonProcessingException {
		IdQuery query = new IdQuery(Arrays.asList("hd1","hd2"));
		simbad = new Simbad(query, format);
		List<SimbadObject> objects = simbad.execute();
		assertEquals(2, objects.size());
		String jsonString = mapper.writeValueAsString(objects.get(0));
		assertTrue(jsonString.length() > 0);
		jsonString = mapper.writeValueAsString(objects.get(1));
		assertTrue(jsonString.length() > 0);
	}
	
	@Test
	public void testCustomQuery() throws JsonProcessingException {
		CustomQuery query = new CustomQuery("query coo 0 0 radius=10m");
		format.setFields(FormatField.getAll());
		simbad = new Simbad(query, format);
		List<SimbadObject> objects = simbad.execute();
		assertNotNull(objects);
		assertEquals(32, objects.size());
		String jsonString = mapper.writeValueAsString(objects.get(0));
		assertTrue(jsonString.length() > 0);
	}

}
