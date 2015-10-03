package net.muneris.simbadder.simbadapi.query;

/**
 * Allows the user to provide an arbitrary query statement to SIMBAD and obtain
 * a properly formatted JSON response. Note that there is no guarantee that
 * SIMBAD will understand the query.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class CustomQuery implements Query {

    private final String queryString;

    public CustomQuery(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

}
