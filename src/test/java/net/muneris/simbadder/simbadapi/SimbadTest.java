package net.muneris.simbadder.simbadapi;

import static net.muneris.simbadder.testutils.TestConstants.NAME;
import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_RESPONSE_STRING;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.formatting.FormatField;
import net.muneris.simbadder.simbadapi.query.CustomQuery;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
public class SimbadTest {

    private static final String QUERY_URL =
            "http://simbad.u-strasbg.fr/simbad/sim-script?script=output"
                    + " console=off script=off\n{format}\n{query}";

    private Simbad simbad;
    private Format format = new Format(NAME);
    private CustomQuery query = new CustomQuery("query coo 0 0 radius=10m");
    private RestTemplate restTemplate;
    private SimbadToJsonMessageConverter converter = new SimbadToJsonMessageConverter();

    @Before
    public void setUp() throws Exception {

        SimbadObject[] objects =
                converter.read(SimbadObject[].class, new MockHttpInputMessage(
                        SIMBAD_RESPONSE_STRING.getBytes()));

        format.addField(FormatField.MAINOTYPE);
        simbad = new Simbad(query, format);
        restTemplate = createMock(RestTemplate.class);
        ReflectionTestUtils.setField(simbad, "restTemplate", restTemplate);

        expect(
                restTemplate.getForObject(QUERY_URL, SimbadObject[].class,
                        format.getFormatString(), query.getQueryString())).andReturn(objects);
        replay(restTemplate);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testExecute() {
        Simbad simbadNull = new Simbad();
        assertNull(simbadNull.execute());
        simbadNull.setQuery(query);
        assertNull(simbadNull.execute());
        List<SimbadObject> objects = simbad.execute();
        assertEquals(1, objects.size());
        assertEquals(0.0, objects.get(0).getDistance(), 0e-6);
        assertEquals(3, objects.get(0).getOTypeList().size());
        assertEquals("Possible Quasar", objects.get(0).getOTypeList().get(0).getVerbose());
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
    public void testSimbad() {
        assertNotNull(simbad);
    }
}
