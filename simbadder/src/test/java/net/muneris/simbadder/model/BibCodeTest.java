package net.muneris.simbadder.model;

import static net.muneris.simbadder.testutils.TestConstants.STRING;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
public class BibCodeTest {

    BibCode bibCode;

    @Before
    public void setUp() throws Exception {
        bibCode = new BibCode();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetAuthors() {
        bibCode.setAuthors(STRING);
        assertEquals(STRING, bibCode.getAuthors());
    }

    @Test
    public void testGetBibCode() {
        bibCode.setBibCode(STRING);
        assertEquals(STRING, bibCode.getBibCode());
    }

    @Test
    public void testGetComments() {
        bibCode.setComments(STRING);
        assertEquals(STRING, bibCode.getComments());
    }

    @Test
    public void testGetCoordinates() {
        bibCode.setCoordinates(STRING);
        assertEquals(STRING, bibCode.getCoordinates());
    }

    @Test
    public void testGetErrata() {
        bibCode.setErrata(STRING);
        assertEquals(STRING, bibCode.getErrata());
    }

    @Test
    public void testGetFiles() {
        bibCode.setFiles(STRING);
        assertEquals(STRING, bibCode.getFiles());
    }

    @Test
    public void testGetFlags() {
        bibCode.setFlags(STRING);
        assertEquals(STRING, bibCode.getFlags());
    }

    @Test
    public void testGetLastPage() {
        bibCode.setLastPage("100");
        assertEquals(100, bibCode.getLastPage());
    }

    @Test
    public void testGetNomenclatureDictionary() {
        bibCode.setNomenclatureDictionary(STRING);
        assertEquals(STRING, bibCode.getNomenclatureDictionary());
    }

    @Test
    public void testGetNotes() {
        bibCode.setNotes(STRING);
        assertEquals(STRING, bibCode.getNotes());
    }

    @Test
    public void testGetStatus() {
        bibCode.setStatus(STRING);
        assertEquals(STRING, bibCode.getStatus());
    }

    @Test
    public void testGetTitle() {
        bibCode.setTitle(STRING);
        assertEquals(STRING, bibCode.getTitle());
    }

}
