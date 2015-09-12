package net.muneris.simbadder.client;

import static net.muneris.simbadder.client.HypertextStateProvider.addListSelfRel;
import static net.muneris.simbadder.client.HypertextStateProvider.addSingleSelfRel;

import net.muneris.simbadder.exception.IdQueryException;
import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.query.CustomQuery;
import net.muneris.simbadder.simbadapi.query.IdQuery;
import net.muneris.simbadder.simbadapi.query.RadiusUnit;

import java.util.Arrays;
import java.util.List;

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
    public ResponseEntity<List<SimbadObject>> getAroundId(
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
        List<SimbadObject> objects =
                addListSelfRel(ResponseAssembler.assembleList(new Simbad(query, Format.all())));
        return new ResponseEntity<List<SimbadObject>>(objects, HttpStatus.OK);
    }

    /**
     * Custom query endpoint.
     *
     * @param queryString
     *            custom query as a string
     * @return list of JSON formatted astronomical objects
     */
    @RequestMapping(value = "/custom-query/{queryString}", method = RequestMethod.GET)
    public ResponseEntity<List<SimbadObject>> getForCustomQuery(@PathVariable(
            value = "queryString") String queryString) {
        CustomQuery query = new CustomQuery(queryString);
        List<SimbadObject> objects =
                addListSelfRel(ResponseAssembler.assembleList(new Simbad(query, Format
                        .allNonDistance())));
        return new ResponseEntity<List<SimbadObject>>(objects, HttpStatus.OK);
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
        SimbadObject object =
                addSingleSelfRel(ResponseAssembler.assembleSingle(new Simbad(query, Format
                        .allNonDistance())));
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
    public ResponseEntity<List<SimbadObject>> getForIdListQuery(@RequestParam(value = "in",
            required = true) String[] idListString) {
        IdQuery query = new IdQuery(Arrays.asList(idListString));

        List<SimbadObject> objects =
                addListSelfRel(ResponseAssembler.assembleList(new Simbad(query, Format
                        .allNonDistance())));
        return new ResponseEntity<List<SimbadObject>>(objects, HttpStatus.OK);
    }
}
