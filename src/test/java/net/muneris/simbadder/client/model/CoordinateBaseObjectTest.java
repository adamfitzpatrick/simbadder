package net.muneris.simbadder.client.model;

import static net.muneris.simbadder.client.testUtils.TestConstants.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.muneris.simbadder.model.Coo;
import net.muneris.simbadder.model.CoordinateBaseObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoordinateBaseObjectTest {

	CoordinateBaseObject object = new CoordinateBaseObject(Coo.class);
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCoordinateBaseObject() {
		assertNotNull(object);
	}

	@Test
	public void testGetRightAscension() {
		object.setRightAscension(STRING);
		assertEquals(0.0, object.getRightAscension(), 1e-10);
		object.setRightAscension("10.0");
		assertEquals(10.0, object.getRightAscension(), 1e-10);
	}

	@Test
	public void testGetDeclination() {
		object.setDeclination(STRING);
		assertEquals(0.0, object.getDeclination(), 1e-10);
		object.setDeclination("10.0");
		assertEquals(10.0, object.getDeclination(), 1e-10);
	}

	@Test
	public void testGetPrecisionCode() {
		object.setPrecisionCode(STRING);
		assertEquals(0, object.getPrecisionCode());
		object.setPrecisionCode("10");
		assertEquals(10, object.getPrecisionCode());
	}

	@Test
	public void testGetQualityCode() {
		object.setQualityCode(STRING);
		assertEquals(STRING, object.getQualityCode());
	}

	@Test
	public void testGetErrorEllipse() {
		object.setErrorEllipse(STRING);
		assertEquals(STRING, object.getErrorEllipse());
	}

	@Test
	public void testGetBibCode() {
		object.setBibCode(STRING);
		assertEquals(STRING, object.getBibCode());
	}

}
