/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
package net.muneris.simbadder.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.model.SimbadResponseWrapper;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.query.IdQuery;

import java.util.Arrays;
import java.util.List;

import com.jayway.restassured.RestAssured;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author adam.fitzpatrick
 *
 */
public class ResponseAssemblerTestIT {

    @Before
    public void setUp() throws Exception {
        RestAssured.port = 7901;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAssembleObjectList() {
        IdQuery query = new IdQuery(Arrays.asList("hd1", "hd2"));
        Simbad simbad = new Simbad(query, Format.allNonDistance());
        SimbadResponseWrapper wrapper = ResponseAssembler.assembleObjectList(simbad);

        assertNotNull(wrapper.objects);
        List<SimbadObject> objects = wrapper.objects;
        assertEquals(2, objects.size());
        if (objects.get(0).getMainId().equals("HD      1")) {
            assertEquals("HD      1", objects.get(0).getMainId());
            assertEquals("HD      2", objects.get(1).getMainId());
        } else {
            assertEquals("HD      1", objects.get(1).getMainId());
            assertEquals("HD      2", objects.get(0).getMainId());
        }
    }

    @Test
    public void testAssembleSingleObject() {
        IdQuery query = new IdQuery("hd1");
        Simbad simbad = new Simbad(query, Format.allNonDistance());
        SimbadObject object = ResponseAssembler.assembleSingleObject(simbad);

        assertNotNull(object);
        assertEquals("HD      1", object.getMainId());
    }

}
