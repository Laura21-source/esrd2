package ru.gbuac.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
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

    public Organization getEGRULData(String inn) {
        //
//для запроса к api требуется сформировать запрос в виде json структуры, в java можно его сформировать с помощью
        //библиотеки com.googlecode.json-simple. для создания структуры в начале создается пустой экземпляр jsonObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", inn);//
        /*согласно примеру требемому формату запроса для данного апи jsonObject заполняется query --inn(для получения inn с контроллера),
        branch_type MAIN - остальное тут по шаблону запроса
        Головные организации и филиалы
Если у компании есть филиалы, запрос вернёт несколько объектов. Если нужна только головная организация, укажите дополнительный параметр branch_type:

{
    "query": "7707083893",
    "branch_type": "MAIN"
}
Чт
         */
        jsonObject.put("branch_type", "MAIN");
        jsonObject.toString();//для того, чтобы в составе POST запроса можно было передать структуру JSON приводится к строке. POST
        //может передавать только текстовые данныею
        HttpClient httpClient = HttpClientBuilder.create().build(); //HTTPClient создается для отправки этого запроса, поскольку там есть метод
        //execute(request)

        try {//тут формируется тело этого запроса

            HttpPost request = new HttpPost("https://suggestions.dadata.ru/suggestions/api/4_1/rs/findById/party");//HTTPPost класс существует чтобы отпраялять такие запросы
            StringEntity params = new StringEntity(jsonObject.toString());//Этот класс сущность для заполнения состава запроса
            request.addHeader("content-type", "application/json");//в заголовок запроса нужно записать согласно примеру запроса
            request.addHeader("Authorization", "Token 13c49f7cdb1ab14887f0329ff2bba40073a74c25");
            request.setEntity(params);//от состава запроса String Entity приходят параметры, которые записывается в него
            HttpResponse response = httpClient.execute(request);//ответ
            String result = EntityUtils.toString(response.getEntity());//EntityUtils тут нужен для того чтобы HTTPEntity, ответ преобразовать к строке
            int a = 3;
            a = 3;
        } catch (Exception ex) {

            //handle exception here

        }
        return null;
    }
}
