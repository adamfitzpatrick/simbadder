package net.muneris.simbadder.simbadapi;

import static net.muneris.simbadder.testUtils.TestConstants.NAME;
import static net.muneris.simbadder.testUtils.TestConstants.SIMBAD_RESPONSE_STRING;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.formatting.FormatField;
import net.muneris.simbadder.simbadapi.query.CustomQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

public class SimbadTest {

	private static final String QUERY_URL = "http://simbad.u-strasbg.fr/simbad/sim-script?script=output"
			+ " console=off script=off\n{format}\n{query}";
	
	private Simbad simbad;
	private Format format = new Format(NAME);
	private CustomQuery query = new CustomQuery("query coo 0 0 radius=10m");
	private RestTemplate restTemplate;
	
	@Before
	public void setUp() throws Exception {
		format.addField(FormatField.MAINOTYPE);
		simbad = new Simbad(query, format);
		restTemplate = createMock(RestTemplate.class);
		ReflectionTestUtils.setField(simbad, "restTemplate", restTemplate);
		expect(restTemplate.getForObject(QUERY_URL, String.class, format.getFormatString(),
				query.getQueryString())).andReturn(SIMBAD_RESPONSE_STRING);
		replay(restTemplate);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimbad() {
		assertNotNull(simbad);
	}

	@Test
	public void testSetGetFormat() {
		simbad.setFormat(format);
		assertEquals(simbad.getFormat().getFormatString(), format.getFormatString());
	}
	
	@Test
	public void testSetGetQuery() {
		simbad.setQuery(query);
		assertEquals(query, simbad.getQuery());
	}
	
	@Test
	public void testGetMany() {
		Simbad simbadNull = new Simbad();
		assertNull(simbadNull.getMany());
		simbadNull.setQuery(query);
		assertNull(simbadNull.getMany());
		List<SimbadObject> objects  = simbad.getMany().getBody();
		assertEquals(1, objects.size());
		assertEquals(0.0, objects.get(0).getDistance(), 0e-6);
		assertEquals(3, objects.get(0).getOTypeList().size());
		assertEquals("Possible Quasar", objects.get(0).getOTypeList().get(0).getVerbose());
	}
}
