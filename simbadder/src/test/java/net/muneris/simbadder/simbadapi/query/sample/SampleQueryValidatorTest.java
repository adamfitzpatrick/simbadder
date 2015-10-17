package net.muneris.simbadder.simbadapi.query.sample;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import net.muneris.simbadder.exception.SimbadderException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SampleQueryValidatorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testValidateFieldValid() {
        assertThat(SampleQueryValidator.validateField("ra"), is(true));
    }
    
    @Test
    public void testValidateFieldInvalid() {
        exception.expect(SimbadderException.class);
        exception.expectMessage("foo is not a valid field");
        SampleQueryValidator.validateField("foo");
    }

    @Test
    public void testValidateOperator() {
        assertThat(SampleQueryValidator.validateOperator("ra", "$gt"), is(true));
    }
    
    @Test
    public void testValidateOperatorInvalid() {
        exception.expect(SimbadderException.class);
        exception.expectMessage("foo is not valid for ra");
        SampleQueryValidator.validateOperator("ra", "foo");
    }

    @Test
    public void testValidateQuery() {
        Map<String, String> criterion = new HashMap<>();
        criterion.put("$gt", "foo");
        Map<String, Map<String, String>> query = new HashMap<>();
        query.put("ra", criterion);
        assertThat(SampleQueryValidator.validateQuery(query), is(true));
    }

    @Test
    public void testGetSimbadOperator() {
        assertThat(SampleQueryValidator.getSimbadOperator("$eq"), is("="));
        assertThat(SampleQueryValidator.getSimbadOperator("$ne"), is("!="));
        assertThat(SampleQueryValidator.getSimbadOperator("$gt"), is(">"));
        assertThat(SampleQueryValidator.getSimbadOperator("$gte"), is(">="));
        assertThat(SampleQueryValidator.getSimbadOperator("$lt"), is("<"));
        assertThat(SampleQueryValidator.getSimbadOperator("$lte"), is("<="));
        assertThat(SampleQueryValidator.getSimbadOperator("$in"), is("in"));
        assertThat(SampleQueryValidator.getSimbadOperator("$nin"), is("not in"));
        assertThat(SampleQueryValidator.getSimbadOperator("$like"), is("~"));
    }
}
