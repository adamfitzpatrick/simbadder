package net.muneris.simbadder.api;

import static net.muneris.simbadder.testutils.TestConstants.*;
import static net.muneris.simbadder.testutils.TestConstants.NAME;
import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_OBJECTS;
import static net.muneris.simbadder.testutils.TestConstants.STRING;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import net.muneris.simbadder.exception.SimbadderException;
import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.model.SimbadResponseWrapper;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.formatting.Format;

import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
@RunWith(EasyMockRunner.class)
public class ControllerTest extends EasyMockSupport {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @TestSubject
    private Controller controller = new Controller();
    
    @Mock
    private Simbad simbad;
    
    @Mock
    private HypertextStateProvider stateProvider;

    private CoordinateValidator validator = new CoordinateValidator();
    
    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(controller, "validator", validator);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetStartTime() {
        controller.setStartTime();
        assertNotNull(ReflectionTestUtils.getField(controller, "startTime"));
    }
    @Test
    public void testController() {
        assertNotNull(controller);
    }

    @Test
    public void testGetAroundId() {
        expectList();
        ResponseEntity<SimbadResponseWrapper> actual =
                controller.getAroundId(NAME, 1.0, "d");
        verifyList(actual);
    }
    
    @Test
    public void testGetAroundIdRadiusOutOfRange() {
        expectList();
        exception.expect(SimbadderException.class);
        exception.expectMessage("Invalid radius value");
        controller.getAroundId(NAME, 1000, null);
    }

    @Test()
    public void testGetAroundIdInvalidUnit() {
        expectList();
        exception.expect(SimbadderException.class);
        exception.expectMessage("Unable to parse radius unit");
        controller.getAroundId(NAME, 1.0, "q");
    }

    @Test
    public void testGetAroundIdNullUnit() {
        expectList();
        ResponseEntity<SimbadResponseWrapper> actual =
                controller.getAroundId(NAME, 1.0, null);
        verifyList(actual);
    }
    
    @Test
    public void testGetAroundIdNullResponse() {
        expectNull();
        ResponseEntity<SimbadResponseWrapper> actual =
                controller.getAroundId(NAME, 1.0, null);
        assertNull(actual.getBody().objects);
    }

    @Test
    public void testGetForCustomQuery() {
        expectList();
        ResponseEntity<SimbadResponseWrapper> actual = controller.getForCustomQuery(NAME);
        verifyList(actual);
    }
    
    @Test
    public void testGetForCustomQueryNullResponse() {
        expectNull();
        ResponseEntity<SimbadResponseWrapper> actual = controller.getForCustomQuery(NAME);
        assertNull(actual.getBody().objects);
    }

    @Test
    public void testGetForId() {
        expectSingles();
        ResponseEntity<SimbadObject> actual = controller.getForId(NAME);
        verifySingle(actual);
    }
    
    @Test
    public void testGetForIdMultiObjectResponse() {
        expect(simbad.execute(anyObject(), isA(Format.class))).andReturn(SIMBAD_OBJECTS);
        expect(stateProvider.addObjectSelfRel(anyObject())).andReturn(SIMBAD_OBJECTS.get(0));
        replay(simbad);
        replay(stateProvider);
        ResponseEntity<SimbadObject> actual = controller.getForId(NAME);
        verifySingle(actual);
    }
    
    @Test
    public void testGetForIdNullResponse() {
        expectNull();
        ResponseEntity<SimbadObject> actual = controller.getForId(NAME);
        assertNull(actual.getBody());
    }

    @Test
    public void testGetForIdListQuery() {
        expectList();
        String[] query = { "asdf", "qwer" };
        ResponseEntity<SimbadResponseWrapper> actual = controller.getForIdListQuery(query);
        verifyList(actual);
    }
    
    @Test
    public void testGetForIdListQueryNullResponse() {
        expectNull();
        String[] query = { "asdf", "qwer" };
        ResponseEntity<SimbadResponseWrapper> actual = controller.getForIdListQuery(query);
        assertNull(actual.getBody().objects);
    }
    
    @Test
    public void testGetForCooQuery() {
        expectList();
        ResponseEntity<SimbadResponseWrapper> actual =
                controller.getForCooQuery(1.0, 1.0, 1.0, "d", STRING, STRING2);
        verifyList(actual);
    }
    
    @Test
    public void testGetForCooQueryNullUnit() {
        expectList();
        ResponseEntity<SimbadResponseWrapper> actual =
                controller.getForCooQuery(1.0, 1.0, 1.0, null, STRING, STRING2);
        verifyList(actual);
    }
    
    @Test
    public void testGetForCooQueryInvalidUnit() {
        expectList();
        exception.expect(SimbadderException.class);
        exception.expectMessage("Unable to parse radius unit");
        controller.getForCooQuery(1.0, 1.0, 1.0, "q", STRING, STRING2);
    }
    
    @Test
    public void testGetForCooQueryInvalidRadius() {
        expectList();
        exception.expect(SimbadderException.class);
        exception.expectMessage("Invalid radius value");
        controller.getForCooQuery(1.0, 1.0, 1000, "d", STRING, STRING2);
    }
    
    @Test
    public void testGetForCooQueryInvalidRa() {
        expectList();
        exception.expect(SimbadderException.class);
        exception.expectMessage("Invalid right ascension value");
        controller.getForCooQuery(1000, 1.0, 1.0, "d", STRING, STRING2);
    }
    
    @Test
    public void testGetForCooQueryInvalidDec() {
        expectList();
        exception.expect(SimbadderException.class);
        exception.expectMessage("Invalid declination value");
        controller.getForCooQuery(1.0, 1000, 1.0, "d", STRING, STRING2);
    }
    
    @Test
    public void testGetForCooQueryNullResponse() {
        expectNull();
        ResponseEntity<SimbadResponseWrapper> actual =
                controller.getForCooQuery(1.0, 1.0, 1.0, "d", STRING, STRING2);
        assertNull(actual.getBody().objects);
    }

    private void expectList() {
        expect(simbad.execute(anyObject(), isA(Format.class))).andReturn(SIMBAD_OBJECTS);
        expect(stateProvider.addObjectSelfRelForList(anyObject())).andReturn(
                SIMBAD_OBJECTS);
        replay(simbad);
        replay(stateProvider);
    }

    private void expectSingles() {
        List<SimbadObject> objects = Arrays.asList(SIMBAD_OBJECTS.get(0));
        expect(simbad.execute(anyObject(), isA(Format.class))).andReturn(objects);
        expect(stateProvider.addObjectSelfRel(isA(SimbadObject.class))).andReturn(
                SIMBAD_OBJECTS.get(0));
        replay(simbad);
        replay(stateProvider);
    }
    
    private void expectNull() {
        expect(simbad.execute(anyObject(), isA(Format.class))).andReturn(null);
        replay(simbad);
    }

    private void verifyList(ResponseEntity<SimbadResponseWrapper> actual) {
        assertNotNull(actual.getBody());
        assertEquals(2, actual.getBody().objects.size());
        assertEquals(SIMBAD_OBJECTS.get(0).getDistance(), actual.getBody().objects.get(0)
                .getDistance(), 1e-10);
        assertEquals(SIMBAD_OBJECTS.get(1).getDistance(), actual.getBody().objects.get(1)
                .getDistance(), 1e-10);
        verify(simbad);
        verify(stateProvider);
    }

    private void verifySingle(ResponseEntity<SimbadObject> actual) {
        assertNotNull(actual.getBody());
        assertEquals(SIMBAD_OBJECTS.get(0).getDistance(), actual.getBody().getDistance(),
                1e-10);
        verify(simbad);
        verify(stateProvider);
    }
}