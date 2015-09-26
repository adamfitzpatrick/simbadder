package net.muneris.simbadder.api;

import net.muneris.simbadder.exception.IdQueryException;
import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.model.SimbadResponseWrapper;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.query.CustomQuery;
import net.muneris.simbadder.simbadapi.query.IdQuery;
import net.muneris.simbadder.simbadapi.query.Query;
import net.muneris.simbadder.simbadapi.query.RadiusUnit;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    private Simbad simbad;
    
    @Autowired
    private HypertextStateProvider stateProvider;
    
    /**
     * Query around a single identifier, using a radius value and specifying
     * units of degrees, minutes or seconds.
     *
     * @param id
     *            Single identifier string
     * @param radiusStr
     *            Search radius around id object as double
     * @param unitStr
     *            String specifying radius units as degrees, minutes or seconds.
     */
    @RequestMapping(value = "/id/{id}/around", method = RequestMethod.GET)
    public ResponseEntity<SimbadResponseWrapper> getAroundId(
            @PathVariable(value = "id") String id, @RequestParam(value = "radius",
            required = true) String radiusStr, @RequestParam(value = "unit",
            required = false) String unitStr) {
        double radius;
        try {
            radius = Double.valueOf(radiusStr);
        } catch (NumberFormatException e) {
            throw new IdQueryException(String.format("Badly formatted radius value: %s",
                    radiusStr));
        }

        RadiusUnit unit;
        if (unitStr != null) {
            unit = RadiusUnit.parseString(unitStr);
            if (unit == null) {
                throw new IdQueryException(String.format("Unable to parse radius unit: %s",
                        unitStr));
            }
        } else {
            unit = RadiusUnit.DEGREES;
        }

        IdQuery query = new IdQuery(id, radius, unit);
        SimbadResponseWrapper wrapper =
                assembleObjectList(query, Format.all());
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
        SimbadResponseWrapper wrapper =
                assembleObjectList(query, Format.allNonDistance());
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
    
    /**
     * Simply calls the simbad.execute() method to obtain a list of objects and
     * wraps them appropriately to incorporate link references.
     *
     * @param simbad
     * @return
     */
    public SimbadResponseWrapper assembleObjectList(Query query, Format format) {
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
    public SimbadObject assembleSingleObject(Query query, Format format) {
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
}
