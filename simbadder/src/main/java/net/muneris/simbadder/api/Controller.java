package net.muneris.simbadder.api;

import net.muneris.simbadder.exception.SimbadderException;
import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.model.SimbadResponseWrapper;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.query.CooQuery;
import net.muneris.simbadder.simbadapi.query.CustomQuery;
import net.muneris.simbadder.simbadapi.query.IdQuery;
import net.muneris.simbadder.simbadapi.query.Query;
import net.muneris.simbadder.simbadapi.query.RadiusUnit;
import net.muneris.simbadder.simbadapi.query.sample.SampleQuery;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contains all request mappings for this microservice.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
@RestController
public class Controller {

    private static final Logger LOGGER = Logger.getLogger(Controller.class);
    private Long startTime;
    
    @Autowired
    private Simbad simbad;

    @Autowired
    private HypertextStateProvider stateProvider;

    @Autowired
    private CoordinateValidator validator;

    @PostConstruct
    public void setStartTime() {
        this.startTime = System.currentTimeMillis();
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getApplicationStatus() {
        String response = String.format("{\"status\":\"ready\",\"uptime\":\"%s\"}",
                System.currentTimeMillis() - startTime);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
    
    /**
     * Query around a single identifier, using a radius value and specifying
     * units of degrees, minutes or seconds.
     *
     * @param id
     *            Single identifier string
     * @param radius
     *            Search radius around id object as double
     * @param unitStr
     *            String specifying radius units as degrees, minutes or seconds.
     */
    @RequestMapping(value = "/id/{id}/around", method = RequestMethod.GET)
    public ResponseEntity<SimbadResponseWrapper> getAroundId(
            @PathVariable(value = "id") String id, @RequestParam(value = "radius",
            required = true) double radius, @RequestParam(value = "unit",
            required = false) String unitStr) {
        String me = "Controller.getAroundId";
/*
        double radius;
        try {
            radius = Double.valueOf(radiusStr);
        } catch (NumberFormatException e) {
            throw unreadableRadius(radiusStr, me);
        }*/

        RadiusUnit unit;
        if (unitStr != null) {
            unit = RadiusUnit.parseString(unitStr);
            if (unit == null) {
                throw unreadableUnit(unitStr, me);
            }
        } else {
            unit = RadiusUnit.DEGREES;
        }
        validateCoordinates(null, null, radius, unit, me);

        IdQuery query = new IdQuery(id, radius, unit);
        SimbadResponseWrapper wrapper = assembleObjectList(query, Format.all());
        if (wrapper.objects != null) {
            wrapper.objects = stateProvider.addObjectSelfRelForList(wrapper.objects);
        }
        return new ResponseEntity<SimbadResponseWrapper>(wrapper, HttpStatus.OK);
    }

    @RequestMapping(value = "/coo", method = RequestMethod.GET)
    public ResponseEntity<SimbadResponseWrapper> getForCooQuery(
            @RequestParam(value = "ra", required = true) double ra,
            @RequestParam(value = "dec", required = true) double dec,
            @RequestParam(value = "radius", required = true) double radius,
            @RequestParam(value = "unit", required = false) String unitStr,
            @RequestParam(value = "epoch", required = false) String epoch,
            @RequestParam(value = "equi", required = false) String equi) {
        String me = "Controller.getForCooQuery";
        RadiusUnit unit;
        if (unitStr != null) {
            unit = RadiusUnit.parseString(unitStr);
            if (unit == null) {
                throw unreadableUnit(unitStr, me);
            }
        } else {
            unit = RadiusUnit.DEGREES;
        }

        validateCoordinates(ra, dec, radius, unit, me);

        CooQuery query = new CooQuery(ra, dec, radius, unit, epoch, equi);
        SimbadResponseWrapper wrapper = assembleObjectList(query, Format.all());
        if (wrapper.objects != null) {
            wrapper.objects = stateProvider.addObjectSelfRelForList(wrapper.objects);
        }
        return new ResponseEntity<SimbadResponseWrapper>(wrapper, HttpStatus.OK);
    }

    /**
     * Custom query endpoint.
     *
     * @param queryString
     *            custom query as a string
     * @return list of JSON formatted astronomical objects
     */
    @RequestMapping(value = "/custom-query/{queryString}", method = RequestMethod.GET)
    public ResponseEntity<SimbadResponseWrapper> getForCustomQuery(@PathVariable(
            value = "queryString") String queryString) {
        CustomQuery query = new CustomQuery(queryString);
        SimbadResponseWrapper wrapper = assembleObjectList(query, Format.allNonDistance());
        if (wrapper.objects != null) {
            wrapper.objects = stateProvider.addObjectSelfRelForList(wrapper.objects);
        }
        return new ResponseEntity<SimbadResponseWrapper>(wrapper, HttpStatus.OK);
    }

    /**
     * Identifier query endpoint for a single object query. Accepts only a
     * single identifier, and expects to provide only a single object.
     *
     * @param id
     *            Single identifier string
     * @return A single JSON formatted astronomical object.
     */
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<SimbadObject> getForId(@PathVariable(value = "id") String id) {
        IdQuery query = new IdQuery(id);
        SimbadObject object = assembleSingleObject(query, Format.allNonDistance());
        if (object != null) {
            object = stateProvider.addObjectSelfRel(object);
        }
        return new ResponseEntity<SimbadObject>(object, HttpStatus.OK);
    }

    /**
     * Identifier query endpoint for array responses.
     *
     * @param idListString
     *            Comma separated list of identifiers
     * @return list of JSON formatted astronomical objects
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<SimbadResponseWrapper> getForIdListQuery(@RequestParam(value = "in",
    required = true) String[] idListString) {
        IdQuery query = new IdQuery(Arrays.asList(idListString));
        SimbadResponseWrapper wrapper = assembleObjectList(query, Format.allNonDistance());
        if (wrapper.objects != null) {
            wrapper.objects = stateProvider.addObjectSelfRelForList(wrapper.objects);
        }
        return new ResponseEntity<SimbadResponseWrapper>(wrapper, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/sample", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimbadResponseWrapper> getForSampleQuery(
            @RequestBody Map<String, Map<String, String>> requestBody) {
        SampleQuery query = new SampleQuery(requestBody);
        SimbadResponseWrapper wrapper = assembleObjectList(query, Format.allNonDistance());
        if (wrapper.objects != null) {
            wrapper.objects = stateProvider.addObjectSelfRelForList(wrapper.objects);
        }
        return new ResponseEntity<SimbadResponseWrapper>(wrapper, HttpStatus.OK);
    }

    /**
     * Simply calls the simbad.execute() method to obtain a list of objects and
     * wraps them appropriately to incorporate link references.
     *
     * @param simbad
     * @return
     */
    private SimbadResponseWrapper assembleObjectList(Query query, Format format) {
        List<SimbadObject> objects = simbad.execute(query, format);
        return new SimbadResponseWrapper(objects);
    }

    /**
     * From a properly configured Simbad object, extracts the first object from
     * a list of SIMBAD objects. If the list contains more than one object, this
     * method logs a warning message.
     *
     * @param simbad
     * @return a single astronomical object
     */
    private SimbadObject assembleSingleObject(Query query, Format format) {
        List<SimbadObject> objects = simbad.execute(query, format);
        if (objects == null) {
            return null;
        }
        if (objects.size() > 1) {
            LOGGER.warn(String.format(
                    "Expected a single object response, but got %s objects.", objects.size()));
        }
        return objects.get(0);
    }

    private SimbadderException unreadableRadius(String value, String source) {
        String message = String.format("Badly formatted radius value: %s", value);
        return new SimbadderException(NumberFormatException.class.getSimpleName(), message,
                source, HttpStatus.BAD_REQUEST);
    }

    private SimbadderException unreadableUnit(String value, String source) {
        String message = String.format("Unable to parse radius unit: %s", value);
        return new SimbadderException("Convert string to RadiusUnit", message, source,
                HttpStatus.BAD_REQUEST);
    }

    private boolean validateCoordinates(Double ra, Double dec, Double radius, RadiusUnit unit,
            String source) {
        String message;
        String reason = "Coordinate out of range";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ra != null && !validator.validLongitude(ra)) {
            message = String.format("Invalid right ascension value: %s", ra);
            throw new SimbadderException(reason, message, source, status);
        }
        if (dec != null && !validator.validLatitude(dec)) {
            message = String.format("Invalid declination value: %s", dec);
            throw new SimbadderException(reason, message, source, status);
        }
        if (radius != null && !validator.validRadius(radius, unit)) {
            message = String.format("Invalid radius value: %s%s", radius, unit.toString());
            throw new SimbadderException(reason, message, source, status);
        }
        return true;
    }
}
