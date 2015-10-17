package net.muneris.simbadder.simbadapi.query.sample;

import net.muneris.simbadder.simbadapi.query.Query;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class SampleQuery implements Query {
    
    private final Map<String, Map<String, String>> query;
    
    public SampleQuery(Map<String, Map<String, String>> query) {
        SampleQueryValidator.validateQuery(query);
        this.query = query;
    }
    
    public Map<String, Map<String, String>> getQuery() {
        return query;
    }

    @Override
    public String getQueryString() {
        String criteriaStrings = query.entrySet().stream()
                .map(c -> getCriteriaString(c.getKey(), c.getValue()))
                .collect(Collectors.joining(" & "));
        return "query sample " + criteriaStrings;
    }
    
    private String getCriteriaString(String field, Map<String, String> criteria) {
        return criteria.entrySet().stream()
            .map(c -> getCriterionString(field, c))
            .collect(Collectors.joining(" & "));
    }
    
    private String getCriterionString(String field, Entry<String, String> criterion) {
        String operator = SampleQueryValidator.getSimbadOperator(criterion.getKey());
        return String.format("%s %s %s", field, operator, criterion.getValue());
    }
}
