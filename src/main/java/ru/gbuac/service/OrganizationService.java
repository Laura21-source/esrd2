package ru.gbuac.service;

import ru.gbuac.model.Organization;
import ru.gbuac.to.OrganizationTo;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface OrganizationService {
    Organization get(int id) throws NotFoundException;

    Organization getByInn(String inn) throws NotFoundException;

    List<OrganizationTo> getAll();

    List<Organization> getEGRULData(String query);

    Organization save(Organization organization);

    Organization update(Organization organization, int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;
}
