package net.muneris.simbadder;

import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static net.muneris.simbadder.testUtils.TestConstants.*;
import net.muneris.simbadder.simbadapi.Simbad;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppInitializer.class})
@WebAppConfiguration
public class ControllerTest {

	private MockMvc mockMvc;
	private Controller controller;
	private Simbad simbad;
		
	@Before
	public void setUp() throws Exception {
		controller = new Controller();
		simbad = createMockBuilder(Simbad.class).addMockedMethod("execute").createMock();
		ReflectionTestUtils.setField(controller, "simbad", simbad);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testController() {
		assertNotNull(controller);
	}

	@Test
	public void testGetForCustomQuery() throws Exception {
		expect(simbad.execute()).andReturn(SIMBAD_OBJECTS);
		replay(simbad);
		mockMvc.perform(get("/custom-query/query coo 0 0 radius=10m"))
			.andExpect(status().isOk())
			.andExpect(content().string(SIMBADDER_RESPONSE_STRING));
	}
}


