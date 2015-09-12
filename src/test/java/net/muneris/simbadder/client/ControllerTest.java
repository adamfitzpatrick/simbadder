package net.muneris.simbadder.client;

import static net.muneris.simbadder.testutils.TestConstants.DOUBLE_STRING;
import static net.muneris.simbadder.testutils.TestConstants.NAME;
import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_OBJECTS;
import static net.muneris.simbadder.testutils.TestConstants.STRING;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import net.muneris.simbadder.exception.IdQueryException;
import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.Simbad;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ResponseAssembler.class, HypertextStateProvider.class })
public class ControllerTest {

    @Rule
    ExpectedException exception = ExpectedException.none();

    Controller controller;

    @Before
    public void setUp() throws Exception {
        mockStatic(ResponseAssembler.class);
        mockStatic(HypertextStateProvider.class);
        controller = new Controller();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testController() {
        assertNotNull(controller);
    }

    @Test
    public void testGetAroundId() {
        expectList();
        ResponseEntity<List<SimbadObject>> actual =
                controller.getAroundId(NAME, DOUBLE_STRING, "d");
        verifyList(actual);
    }

    @Test
    public void testGetAroundIdInvalidRadius() {
        expectList();
        exception.expect(IdQueryException.class);
        exception.expectMessage("Badly formatted radius value");
        controller.getAroundId(NAME, STRING, null);
    }

    @Test()
    public void testGetAroundIdInvalidUnit() {
        expectList();
        exception.expect(IdQueryException.class);
        exception.expectMessage("Unable to parse radius unit");
        controller.getAroundId(NAME, DOUBLE_STRING, "q");
    }

    @Test
    public void testGetAroundIdNullUnit() {
        expectList();
        ResponseEntity<List<SimbadObject>> actual =
                controller.getAroundId(NAME, DOUBLE_STRING, null);
        verifyList(actual);
    }

    @Test
    public void testGetForCustomQuery() {
        expectList();
        ResponseEntity<List<SimbadObject>> actual = controller.getForCustomQuery(NAME);
        verifyList(actual);
    }

    @Test
    public void testGetForId() {
        expectSingles();
        ResponseEntity<SimbadObject> actual = controller.getForId(NAME);
        verifySingle(actual);
    }

    @Test
    public void testGetForIdListQuery() {
        expectList();
        String[] query = { "asdf", "qwer" };
        ResponseEntity<List<SimbadObject>> actual = controller.getForIdListQuery(query);
        verifyList(actual);
    }

    private void expectList() {
        expect(ResponseAssembler.assembleList(isA(Simbad.class))).andReturn(SIMBAD_OBJECTS);
        expect(HypertextStateProvider.addListSelfRel(anyObject())).andReturn(SIMBAD_OBJECTS);
        replayAll();
    }

    private void expectSingles() {
        expect(ResponseAssembler.assembleSingle(isA(Simbad.class)))
                .andReturn(SIMBAD_OBJECTS.get(0));
        expect(HypertextStateProvider.addSingleSelfRel(isA(SimbadObject.class))).andReturn(
                SIMBAD_OBJECTS.get(0));
        replayAll();
    }

    private void verifyList(ResponseEntity<List<SimbadObject>> actual) {
        assertNotNull(actual.getBody());
        assertEquals(2, actual.getBody().size());
        assertEquals(SIMBAD_OBJECTS.get(0).getDistance(), actual.getBody().get(0).getDistance(),
                1e-10);
        assertEquals(SIMBAD_OBJECTS.get(1).getDistance(), actual.getBody().get(1).getDistance(),
                1e-10);
        verifyAll();
    }

    private void verifySingle(ResponseEntity<SimbadObject> actual) {
        assertNotNull(actual.getBody());
        assertEquals(SIMBAD_OBJECTS.get(0).getDistance(), actual.getBody().getDistance(), 1e-10);
        verifyAll();
    }
}
