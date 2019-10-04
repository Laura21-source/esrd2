package ru.gbuac.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gbuac.dao.OrganizationRepository;
import ru.gbuac.model.Organization;
import ru.gbuac.util.exception.NotFoundException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

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

    @Override
    public Organization getEGRULData(String INN) {
        JSONObject query = new JSONObject();
        query.put("query", INN);
        query.put("branch_type", "MAIN");
        String JSONString = query.toJSONString();
        Organization returned = new Organization();
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        try {
            HttpPost request = new HttpPost("https://suggestions.dadata.ru/suggestions/api/4_1/rs/findById/party");
            StringEntity params = new StringEntity(JSONString);
            params.setContentEncoding("application/json;charset=UTF-8");
            params.setContentType("application/json;charset=UTF-8");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept", "application/json");
            request.addHeader("Authorization", "Token 13c49f7cdb1ab14887f0329ff2bba40073a74c25");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            String data = EntityUtils.toString(response.getEntity());
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(data).getAsJsonObject();
            returned.setInn(INN);
            JsonObject jsonObjectSuggestions = root.get("suggestions").getAsJsonArray().get(0).getAsJsonObject();
            returned.setShortName(jsonObjectSuggestions.get("value").getAsString());
            JsonObject jsonObjectData = jsonObjectSuggestions.get("data").getAsJsonObject();
            returned.setKpp(jsonObjectData.get("kpp").getAsString());
            returned.setOgrn(jsonObjectData.get("ogrn").getAsString());
            returned.setFullName(jsonObjectData.get("name").getAsJsonObject().get("full_with_opf").getAsString());
            returned.setAddress(jsonObjectData.get("address").getAsJsonObject().get("value").getAsString());
            returned.setFioManager(jsonObjectData.get("management").getAsJsonObject().get("name").getAsString());
            returned.setPositionManager(jsonObjectData.get("management").getAsJsonObject().get("post").getAsString());
        }   catch (Exception ex) {
        }
        return returned;
    }

    @Override
    public Organization getOrgByInn(String inn) {
        return organizationRepository.getOrgByInn(inn);
    }

    ;


}
