package ru.gbuac.controller.organization;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gbuac.model.Organization;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = OrganizationRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationRestController extends AbstractOrganizationRestController {
    public static final String REST_URL = "/rest/profile/organizations";

    @Override
    @GetMapping
    public List<Organization> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/get/{id}")
    public Organization get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(int id) {
        super.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization updateOrCreate(@Valid @RequestBody Organization organization) {
        if (organization.isNew()) {
            return super.create(organization);
        } else {
            return super.update(organization, organization.getId());
        }
    }
}
