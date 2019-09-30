package ru.gbuac.service;

import ru.gbuac.model.Organization;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface OrganizationService {
    Organization get(int orgId) throws NotFoundException;

    List<Organization> getAllOrganizations(int orgId);

    Organization save(Organization organization);

    Organization update(Organization organization, int id) throws NotFoundException;

    void delete(int orgId) throws NotFoundException;
}
