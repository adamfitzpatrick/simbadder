/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
package net.muneris.simbadder.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * @author adam.fitzpatrick
 *
 */
public class SimbadResponseWrapper extends ResourceSupport {
    public List<SimbadObject> objects;

    public SimbadResponseWrapper(List<SimbadObject> objects) {
        this.objects = objects;
    }
}
