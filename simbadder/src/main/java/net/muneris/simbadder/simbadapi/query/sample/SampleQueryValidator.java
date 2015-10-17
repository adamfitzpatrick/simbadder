package net.muneris.simbadder.simbadapi.query.sample;

import net.muneris.simbadder.exception.SimbadderException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class SampleQueryValidator {

    private static final Map<String, String> operatorMap = new HashMap<>();
    static {
        operatorMap.put("$eq", "=");
        operatorMap.put("$ne", "!=");
        operatorMap.put("$gt", ">");
        operatorMap.put("$gte", ">=");
        operatorMap.put("$lt", "<");
        operatorMap.put("$lte", "<=");
        operatorMap.put("$in", "in");
        operatorMap.put("$nin", "not in");
        operatorMap.put("$like", "~");
    }
    
    private static final Map<String, Set<String>> criteriaFields = new HashMap<>();
    static {
        criteriaFields.put("ra", getSet(new String[]{"$gt", "$gte", "$lt", "$lte"}));
        criteriaFields.put("dec", getSet(new String[]{"$gt", "$gte", "$lt", "$lte"}));
    }
    
    private static final Set<String> getSet(String[] allowedOperators) {
        return new HashSet<String>(Arrays.asList(allowedOperators));
    }
    
    public static final boolean validateField(String field) {
        String me = "SampleQueryValidator.validateField";
        if (criteriaFields.get(field) == null) {
            throw new SimbadderException("Unable to parse sample query",
                    field + " is not a valid field", me, HttpStatus.BAD_REQUEST);
        }
        return true;
    }
    
    public static final boolean validateOperator(String field, String operator) {
        String me = "SampleQueryValidator.validateOperator";
        if (criteriaFields.get(field) != null) {
            if (!criteriaFields.get(field).contains(operator)) {
                throw new SimbadderException("Unable to parse sample query",
                        operator + " is not valid for " + field, me, HttpStatus.BAD_REQUEST);
            }
        }
        return true;
    }
    
    public static final boolean validateQuery(Map<String, Map<String, String>> query) {
        return query.entrySet().stream()
                .allMatch(p -> validateField(p.getKey()) &&
                        validateCriteria(p.getKey(), p.getValue()));
    }
    
    public static final String getSimbadOperator(String operator) {
        return operatorMap.get(operator);
    }
    
    private static final boolean validateCriteria(String field, Map<String, String> criteria) {
        return criteria.entrySet().stream()
                .allMatch(p -> validateOperator(field, p.getKey()));
    }
}
