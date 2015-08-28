package net.muneris.simbadder.simbadapi.query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates properly formatted identifier queries for submission to SIMBAD.
 * Identifier querys are directed to the script query URL at simbad, and take
 * the following form:
 *
 * <pre>
 *     query id {identifier 1}
 *              {identifier 2}
 *              ...
 * </pre>
 *
 * For wildcard queries, the query string is:
 *
 * <pre>
 *     query id wildcard {identifier}
 * </pre>
 *
 * For queries around an identifier:
 *
 * <pre>
 *     query around {identifier} radius={radius}{unit}
 * </pre>
 *
 * where radius is a double and unit is one of d, m, or s.
 *
 * @author adam.fitzpatrick
 *
 */
public class IdQuery implements Query {

    private List<String> identifiers;
    private boolean wildcard = false;
    private boolean around = false;
    private double radius;
    private RadiusUnit unit;

    public IdQuery() {
        this.identifiers = new ArrayList<>();
    }

    public IdQuery(List<String> identifiers) {
        this();
        this.identifiers = identifiers;
    }

    public IdQuery(String identifier) {
        this();
        this.identifiers.add(identifier);
    }

    public IdQuery(String identifier, boolean wildcard) {
        this(identifier);
        this.wildcard = true;
    }

    public IdQuery(String identifier, double radius, RadiusUnit unit) {
        this.identifiers = new ArrayList<>();
        this.identifiers.add(identifier);
        this.radius = radius;
        this.unit = unit;
        this.around = true;
    }

    public void addIdentifier(String identifier) {
        this.identifiers.add(identifier);
    }

    public List<String> getIdentifiers() {
        return this.identifiers;
    }

    @Override
    public String getQueryString() {
        if (wildcard) {
            return "query id wildcard "
                    + identifiers.stream().collect(Collectors.joining("\n"));
        }
        if (around) {
            return String.format("query around %s radius=%s%s", identifiers.get(0), radius,
                    unit);
        }
        return "query id " + identifiers.stream().collect(Collectors.joining("\n"));
    }

    public double getRadius() {
        return radius;
    }

    public RadiusUnit getUnit() {
        return unit;
    }

    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setUnit(RadiusUnit unit) {
        this.unit = unit;
    }

}
