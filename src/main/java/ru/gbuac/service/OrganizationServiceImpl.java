package ru.gbuac.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
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
        return checkNotFoundWithId(organizationRepository.findById(id).orElse(null), id);
    }

    @Override
    public List<Organization> getAll() {
        return organizationRepository.findAll();
    }

    @Override
    public Organization getEGRULData(String INN) {
        JSONObject query = new JSONObject();
        query.put("query", INN);
        query.put("branch_type", "MAIN");
        String JSONString = query.toJSONString();

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        try {
            HttpPost request = new HttpPost("https://suggestions.dadata.ru/suggestions/api/4_1/rs/findById/party");
            StringEntity params =new StringEntity("details=" + JSONString);
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept", "application/json");
            request.addHeader("Authorization", "Token 13c49f7cdb1ab14887f0329ff2bba40073a74c25");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            int f;
            f = 3;

        }   catch (Exception ex) {

        }

        Organization returned = new Organization();
        return null;
    };

    @Override
    public Organization save(Organization organization) {
        Assert.notNull(organization, "organization must not be null");
        return organizationRepository.save(organization);
    }

    @Override
    public Organization update(Organization organization, int id) throws NotFoundException {
        Assert.notNull(organization, "organization must not be null");
        return checkNotFoundWithId(organizationRepository.save(organization), id);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        organizationRepository.delete(id);
    }
}
