package net.muneris.simbadder.simbadapi;

import static net.muneris.simbadder.testUtils.TestConstants.NAME;
import static net.muneris.simbadder.testUtils.TestConstants.STRING;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.formatting.FormatField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

public class SimbadTest {

	private static final String QUERY_URL = "http://simbad.u-strasbg.fr/simbad/sim-script?script=output"
			+ " console=off script=off\n{format}\n{query}";
	private static final String query = "query coo 0 0 Radius=1 Radius.unit=deg";
	
	private Simbad simbad;
	private Format format = new Format(NAME);
	private RestTemplate restTemplate;
	
	@Before
	public void setUp() throws Exception {
		simbad = new Simbad();
		format.addField(FormatField.MAINOTYPE);
		restTemplate = createMock(RestTemplate.class);
		ReflectionTestUtils.setField(simbad, "restTemplate", restTemplate);
		expect(restTemplate.getForObject(QUERY_URL, String.class, format.getFormatString(), query)).andReturn(STRING);
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
	public void testRunQuery() {
		assertNull(simbad.runQuery());
		simbad.setFormat(format);
		assertEquals(STRING, simbad.runQuery());
	}
}
