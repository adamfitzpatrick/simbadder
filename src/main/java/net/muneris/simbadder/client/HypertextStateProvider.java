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

    public static SimbadObject addObjectSelfRel(SimbadObject object) {
        object.add(linkTo(methodOn(Controller.class).getForId(object.getMainId()))
                .withSelfRel());
        return object;
    }

    public static List<SimbadObject> addObjectSelfRelForList(List<SimbadObject> objects) {
        return objects.stream().map(HypertextStateProvider::addObjectSelfRel)
                .collect(Collectors.toList());
    }
}
