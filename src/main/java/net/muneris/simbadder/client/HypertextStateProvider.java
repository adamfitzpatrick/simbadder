/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
package net.muneris.simbadder.client;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import net.muneris.simbadder.model.SimbadObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author adam.fitzpatrick
 *
 */
public class HypertextStateProvider {

    public static List<SimbadObject> addListSelfRel(List<SimbadObject> objects) {
        return objects.stream().map(HypertextStateProvider::addSingleSelfRel)
                .collect(Collectors.toList());
    }

    public static SimbadObject addSingleSelfRel(SimbadObject object) {
        object.add(linkTo(methodOn(Controller.class).getForId(object.getMainId())).withSelfRel());
        return object;
    }
}
