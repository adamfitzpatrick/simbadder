package net.muneris.simbadder.simbadapi.query;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class CooQueryTest {

    CooQuery query;
    
    @Before
    public void setUp() throws Exception {
        query = new CooQuery(1, 1, 1, RadiusUnit.DEGREES, "2000", "2000");
    }

    @Test
    public void testCooQuery() {
        assertNotNull(query);
    }

    @Test
    public void testGetQueryString() {
        String queryStr = query.getQueryString();
        assertThat(queryStr, is("query coo 1.0 1.0 radius=1.0d epoch=2000 equi=2000"));
    }
    
    @Test
    public void testGetQueryStringNullEpoch() {
        query.setEpoch(null);
        String queryStr = query.getQueryString();
        assertThat(queryStr, is("query coo 1.0 1.0 radius=1.0d equi=2000"));
    }
    
    @Test
    public void testGetQueryStringNullEqui() {
        query.setEqui(null);
        String queryStr = query.getQueryString();
        assertThat(queryStr, is("query coo 1.0 1.0 radius=1.0d epoch=2000"));
    }

    @Test
    public void testSetGetRa() {
        query.setRa(2);
        assertThat(query.getRa(), is(2.0));
    }

    @Test
    public void testSetGetDec() {
        query.setDec(2);
        assertThat(query.getDec(), is(2.0));
    }
    
    @Test
    public void testSetGetRadius() {
        query.setRadius(10);
        assertThat(query.getRadius(), is(10.0));
    }

    @Test
    public void testSetGetUnit() {
        query.setUnit(RadiusUnit.SECONDS);
        assertThat(query.getUnit(), is(RadiusUnit.SECONDS));
    }

    @Test
    public void testSetGetEpoch() {
        query.setEpoch("1950");
        assertThat(query.getEpoch(), is("1950"));
    }

    @Test
    public void testSetGetEqui() {
        query.setEqui("1900");
        assertThat(query.getEqui(), is("1900"));
    }
}
