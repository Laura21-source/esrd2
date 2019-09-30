package ru.gbuac.service;

import ru.gbuac.model.Organization;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface OrganizationService {
    Organization get(int id) throws NotFoundException;

    List<Organization> getAllOrganizations(int id);

    Organization save(Organization organization, String rootPath);

    Organization update(Organization organization, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;
}
