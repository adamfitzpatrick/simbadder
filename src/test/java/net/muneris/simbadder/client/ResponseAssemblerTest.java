package net.muneris.simbadder.client;

import static net.muneris.simbadder.testUtils.TestConstants.SIMBAD_OBJECTS;
import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import net.muneris.simbadder.client.ResponseAssembler;
import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.query.IdQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResponseAssemblerTest {

	ResponseAssembler assembler;
	Simbad simbad;
	
	@Before
	public void setUp() throws Exception {
		simbad = createMockBuilder(Simbad.class).addMockedMethod("execute").createMock();
		simbad.setQuery(new IdQuery("hd"));
		expect(simbad.execute()).andReturn(SIMBAD_OBJECTS)
			.andReturn(Collections.singletonList(SIMBAD_OBJECTS.get(0)));
		replay(simbad);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAssembleSingle() {
		SimbadObject object = ResponseAssembler.assembleSingle(simbad);
		assertEquals(SIMBAD_OBJECTS.get(0), object);
		object = ResponseAssembler.assembleSingle(simbad);
		assertEquals(SIMBAD_OBJECTS.get(0), object);
	}

	@Test()
	public void testAssembleList() {
		List<SimbadObject> objects = ResponseAssembler.assembleList(simbad);
		assertEquals(2, objects.size());
		assertEquals(SIMBAD_OBJECTS.get(0), objects.get(0));
		assertEquals(SIMBAD_OBJECTS.get(1), objects.get(1));
		objects = ResponseAssembler.assembleList(simbad);
		assertEquals(1, objects.size());
	}

}
