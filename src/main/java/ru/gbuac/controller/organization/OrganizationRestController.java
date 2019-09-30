package ru.gbuac.controller.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gbuac.model.Organization;
import ru.gbuac.service.OrganizationService;
import ru.gbuac.to.DocFieldsTo;

import java.util.List;

@RestController
@RequestMapping(value = OrganizationRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationRestController extends AbstractOrganizationRestController {
    public static final String REST_URL = "/rest/profile/organization";

    @Autowired
    OrganizationService organizationService;

    @Override
    @GetMapping
    public List<Organization> getAllOrganizations(@PathVariable("orgId") int orgId) {
        return super.getAllOrganizations(orgId);
    }

    @Override
    public void delete(int orgId) {
        super.delete(orgId);
    }


}
