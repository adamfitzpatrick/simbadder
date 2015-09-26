package net.muneris.simbadder.testutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class PatternMatcher extends TypeSafeMatcher<String> {

    private final Pattern pattern;
    private Matcher matcher;
    
    public static PatternMatcher matches(String pattern) {
        return new PatternMatcher(pattern);
    }
    
    public PatternMatcher(String patternStr) {
        this.pattern = Pattern.compile(patternStr);
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("Match to regular expression ").appendValue(pattern);
    }

    @Override
    protected boolean matchesSafely(String item) {
        matcher = pattern.matcher(item);
        return matcher.find(0);
    }
}
