package ru.gbuac.controller.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gbuac.model.Organization;
import ru.gbuac.service.OrganizationService;
import ru.gbuac.to.DocFieldsTo;
import ru.gbuac.to.DocTo;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = OrganizationRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationRestController extends AbstractOrganizationRestController {
    public static final String REST_URL = "/rest/profile/organizations";

    @Autowired
    ServletContext context;

    @Autowired
    OrganizationService organizationService;

    @Override
    @GetMapping(value = "/getAllOrganizations")
    public List<Organization> getAllOrganizations(@PathVariable("id") int id) {
        return super.getAllOrganizations(id);
    }

    @Override
    @GetMapping
    public Organization get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(int id) {
        super.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization updateOrCreate(@Valid @RequestBody Organization organization) {
        if (organization.isNew()) {
            return super.create(organization, context.getRealPath("/"));
        } else {
            return super.update(organization, organization.getId(), context.getRealPath("/"));
        }
    }
}
