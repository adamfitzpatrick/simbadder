/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
package net.muneris.simbadder.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import net.muneris.simbadder.model.SimbadObject;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * @author adam.fitzpatrick
 *
 */
@Service
public class HypertextStateProvider {

    public SimbadObject addObjectSelfRel(SimbadObject object) {
        object.add(linkTo(methodOn(Controller.class).getForId(object.getMainId()))
                .withSelfRel());
        return object;
    }

    public List<SimbadObject> addObjectSelfRelForList(List<SimbadObject> objects) {
        return objects.stream().map(this::addObjectSelfRel)
                .collect(Collectors.toList());
    }
}
