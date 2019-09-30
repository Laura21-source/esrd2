package ru.gbuac.controller.organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gbuac.model.Organization;
import ru.gbuac.service.OrganizationService;

import java.util.List;

public abstract class AbstractOrganizationRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    OrganizationService organizationService;

    public List<Organization> getAllOrganizations(int orgId) {
        LOG.info("getAllOrganizations");
        return organizationService.getAllOrganizations(orgId);
    }

    public void delete(int orgId) {
        LOG.info("delete");
        organizationService.delete(orgId);
    }
}
