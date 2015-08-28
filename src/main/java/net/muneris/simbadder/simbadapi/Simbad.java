package net.muneris.simbadder.simbadapi;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.query.Query;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

/**
 * This class is responsible for hitting the apis at simbad.u-strasbg.fr given
 * specific query and format parameters.
 *
 * SIMBAD features a number of different api endpoints that are chosen primarily
 * on the nature of query to be provided. Several of these endpoints provide
 * simplified access to a specific type of query. The most recommended endpoint
 * allows for queries to be submitted by script, and can fetch any and all data
 * that the other end points are able to obtain. Given that this project is
 * intended to provide a simplified interface to SIMBAD as well as to return
 * more useful data formats, only the scripting query is implemented here. The
 * specialized query endpoints offered by SIMBAD are not required.
 *
 * There are three distinct types of queries that may be submitted to the api:
 * <ul>
 * <li>ID queries search by object identifier</li>
 * <li>Coordinate queries search by and around coordinates</li>
 * <li>Sample queries accept a list of conditions, such as maximum magnitudes or
 * coordinate ranges. This provides the greatest flexibility, although it is
 * also the most challenging to configure.</li>
 * </ul>
 * The exact request made of the SIMBAD api is dictated by the content of the
 * query member of this class.
 *
 * The class includes a format member which provides a formatting instruction
 * string to the SIMBAD api, with the intent of ensuring the content returned
 * from SIMBAD will be properly formatted JSON.
 *
 * @author Adam Fitzptrick (adam@muneris.net)
 * @see net.muneris.simbadder.simbadapi.formatting.Format
 * @see net.muneris.simbadder.simbadapi.queries.Query
 *
 */

public class Simbad {
    /* TODO Implement Query class(es) */

    private static final Logger LOGGER = Logger.getLogger(Simbad.class);

    /**
     * The main api url that this microservice utilizes to request data from
     * SIMBAD.
     */
    private static final String QUERYURL =
            "http://simbad.u-strasbg.fr/simbad/sim-script?script=output"
                    + " console=off script=off\n{format}\n{query}";
    /**
     * Determines the formatting specifier that will be provided to SIMBAD. The
     * formatting specifier dictates exactly what information is returned by
     * simbad and how it is presented.
     *
     * @see net.muneris.simbadder.simbadapi.formatting
     */
    private Format format;
    /** The actual query submitted to SIMBAD. */
    private Query query;
    /** Spring class for consuming REST services. */
    private RestTemplate restTemplate;

    /* TODO JavaDoc */
    public Simbad() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new SimbadToJsonMessageConverter());
    }

    /* TODO JavaDoc */
    public Simbad(Query query, Format format) {
        this();
        this.setQuery(query);
        this.setFormat(format);
    }

    /**
     * Executes a query on the SIMBAD api if it can be verified as properly
     * formatted.
     *
     * @return response from the SIMBAD server as a list of JSON object.
     */
    public List<SimbadObject> execute() {
        if (verify()) {
            URI uri =
                    new UriTemplate(QUERYURL).expand(format.getFormatString(),
                            query.getQueryString());
            LOGGER.info(String.format("SIMBAD Request: %s", uri));
            SimbadObject[] objects =
                    restTemplate.getForObject(QUERYURL, SimbadObject[].class,
                            format.getFormatString(), query.getQueryString());
            return Arrays.asList(objects);
        }
        return null;
    }

    /** Retrieves the format object for this instance. */
    public Format getFormat() {
        return format;
    }

    /** Retrieves the query object for this instance. */
    public Query getQuery() {
        return query;
    }

    /** Sets the format object for this instance. */
    public void setFormat(Format format) {
        this.format = format;
    }

    /** Sets the query object for this instance. */
    public void setQuery(Query query) {
        this.query = query;
    }

    /**
     * Verifies that all components required for a well-formatted request to
     * SIMBAD are available.
     *
     * @return boolean indicating that the request is ready
     */
    private boolean verify() {
        if (query == null) {
            LOGGER.error("Missing query.");
            return false;
        }
        if (format == null) {
            LOGGER.error("Missing format.");
            return false;
        }
        return true;
    }
}
