package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.OrganizationRepository;
import ru.gbuac.model.Organization;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

import static ru.gbuac.util.ValidationUtil.checkNotFoundWithId;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;


    @Override
    public Organization get(int id) throws NotFoundException {
        return organizationRepository.get(id);
    }

    @Override
    public List<Organization> getAllOrganizations(int id) {
        return organizationRepository.getAllOrganizations(id);
    }


    @Override
    public Organization save(Organization organization, String rootPath) {
        Assert.notNull(organization, "organization must not be null");
        return organizationRepository.save(organization);
    }

    @Override
    public Organization update(Organization organization, int id) throws NotFoundException {
        Assert.notNull(organization, "organization must not be null");
        Organization savedOrganization = checkNotFoundWithId(organizationRepository.save(organization), id);
        return savedOrganization;
    }

    @Override
    public void delete(int id) throws NotFoundException {
        organizationRepository.delete(id);
    }
}
