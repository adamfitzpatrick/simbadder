package net.muneris.simbadder.client;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.Simbad;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Provides static methods for assembling a response into a single object or a
 * list of objects. Primarily used to log a warning in cases where a single
 * object is expected, but SIMBAD returns multiple objects.
 *
 * @author adam.fitzpatrick
 *
 */
public class ResponseAssembler {

    private static final Logger LOGGER = Logger.getLogger(ResponseAssembler.class);

    /**
     * Simply calls the simbad.execute() method to obtain a list of objects.
     * Included to provide a logical balance to the
     * {@link #assembleSingle(Simbad)} method in this class.
     *
     * @param simbad
     * @return
     */
    public static List<SimbadObject> assembleList(Simbad simbad) {
        return simbad.execute();
    }

    /**
     * From a properly configured Simbad object, extracts the first object from
     * a list of SIMBAD objects. If the list contains more than one objects,
     * this method logs a warning message.
     *
     * @param simbad
     * @return a single astronomical object
     */
    public static SimbadObject assembleSingle(Simbad simbad) {
        List<SimbadObject> objects = assembleList(simbad);
        if (objects.size() > 1) {
            LOGGER.warn(String.format("Expected a single object response, but got %s objects.",
                    objects.size()));
        }
        return objects.get(0);
    }
}
