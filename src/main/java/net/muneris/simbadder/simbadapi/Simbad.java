package net.muneris.simbadder.simbadapi;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.query.Query;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Service
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
   
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private SimbadToJsonMessageConverter converter;
    
    @PostConstruct
    public void setup() {
        restTemplate.getMessageConverters().add(converter);
    }

    /**
     * Executes a query on the SIMBAD api if it can be verified as properly
     * formatted.
     *
     * @return response from the SIMBAD server as a list of JSON object.
     */
    public List<SimbadObject> execute(Query query, Format format) {
        URI uri = new UriTemplate(QUERYURL).expand(format.getFormatString(),
                            query.getQueryString());
        LOGGER.info(String.format("SIMBAD Request: %s", uri));
        SimbadObject[] objects =
                restTemplate.getForObject(QUERYURL, SimbadObject[].class,
                        format.getFormatString(), query.getQueryString());
        if (objects == null) {
            return null;
        }
        return Arrays.asList(objects);
    }
}
