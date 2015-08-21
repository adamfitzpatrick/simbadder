package net.muneris.simbadder.simbadapi.formatting;

import static net.muneris.simbadder.testUtils.TestConstants.NAME;
import static net.muneris.simbadder.testUtils.TestConstants.SIMBAD_FORMATTING_STRING;
import static net.muneris.simbadder.testUtils.TestConstants.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.formatting.FormatField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FormatTest {

	private Format format;
	
	@Before
	public void setUp() throws Exception {
		format = new Format(NAME);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFormat() {
		assertNotNull(format);
		assertEquals(NAME, format.getName());
	}

	@Test
	public void testAddRemoveFieldFormatField() {
		format.addField(FormatField.MAINOTYPE);
		assertEquals(FormatField.MAINOTYPE.valueOf(), format.getFields().get(FormatField.MAINOTYPE.toString()));
		format.removeField(FormatField.MAINOTYPE);
		assertEquals(0, format.getFields().size());
		format.addField(NAME, STRING);
		assertTrue(format.getFields().containsKey(NAME));
		assertEquals(STRING, format.getFields().get(NAME));
		format.removeField(NAME);
		assertEquals(0, format.getFields().size());
	}

	@Test
	public void testSetGetFields() {
		format.setFields(Collections.singletonList(FormatField.MAINOTYPE));
		assertEquals(FormatField.MAINOTYPE.valueOf(), format.getFields().get(FormatField.MAINOTYPE.toString()));
		Map<String, String> fieldMap = new HashMap<>();
		fieldMap.put(NAME, STRING);
		fieldMap.put(STRING, NAME);
		format.setFields(fieldMap);
		assertEquals(2, format.getFields().size());
		assertTrue(format.getFields().containsKey(NAME));
		assertTrue(format.getFields().containsKey(STRING));
		assertEquals(STRING, format.getFields().get(NAME));
		assertEquals(NAME, format.getFields().get(STRING));
	}

	@Test
	public void testGetFormatString() {
		format.addField(FormatField.MAINOTYPE);
		assertEquals(SIMBAD_FORMATTING_STRING, format.getFormatString());
	}

}
