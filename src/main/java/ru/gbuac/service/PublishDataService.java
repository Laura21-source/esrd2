package ru.gbuac.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.gbuac.dao.PublishDataRepository;
import ru.gbuac.jaxws.basereg.CreateDocumentFile;
import ru.gbuac.jaxws.basereg.DocStatus;
import ru.gbuac.jaxws.basereg.ResponseStatus;
import ru.gbuac.model.Doc;
import ru.gbuac.model.Organization;
import ru.gbuac.model.PublishData;
import ru.gbuac.util.DateTimeUtil;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Service
public class PublishDataService {
    @Autowired
    public JavaMailSender emailSender;

    @Value("${email.sender}")
    private String sender;

    @Value("${email.login}")
    private String login;

    @Value("${email.publish.recipients}")
    private String publishRecipients;

    @Value("${uri.publish.mask}")
    private String publishUriMask;

    @Value("${service.basereg.uri}")
    private String baseregServiceUri;

    @Autowired
    private PublishDataRepository publishDataRepository;

    public void publish(String regNum, String regDate, String optionalDate, Doc doc, String publishNameMask, String publishClassifierParams,
                        String fileName, byte[] fileBytes, String signer, String signerPosition) {
        PublishData publishData = new PublishData(doc);
        if (publishClassifierParams != null) {
            String docName = optionalDate != null ? String.format(publishNameMask, regNum, optionalDate) :
                    String.format(publishNameMask, regNum, regDate);
            String[] topLevelClassifierParams = publishClassifierParams.split("\\|");


            String[] mosRuClassifierParams = topLevelClassifierParams[0].split(";");
            publishMosRu(docName, DateTimeUtil.toString(LocalDate.now()), mosRuClassifierParams[0], mosRuClassifierParams[1],
                    fileName, fileBytes, publishData);

            String[] baseRegClassifierParams = topLevelClassifierParams[1].split(";");
            String uri = publishBaseReg(docName, baseRegClassifierParams[0], fileName, fileBytes, regNum, regDate,
                    signer, signerPosition, publishData);



            String[] openDataClassifierParams = topLevelClassifierParams[2].split(";");
            //publishOpenData(openDataClassifierParams[0], regNum, regDate, docName, regDate, uri, );
            publishDataRepository.save(publishData);
        }
    }

    private void publishOpenData(String docType, String docNumber, String docDate, String docTitle,
                                 String dateAccept, String contentUrl, String meetingDatePlan, String meetingAddress,
                                 List<Organization> organizationList) {
        /*
        try {
            JSONObject query = new JSONObject();
            query.put("docType", docType);
            query.put("docNumber", docNumber);
            query.put("docDate", docDate);
            query.put("docTitle", docTitle);
            query.put("dateAccept", dateAccept);
            query.put("contentUrl", contentUrl);
            query.put("meetingDatePlan", meetingDatePlan);
            query.put("meetingAddress", meetingAddress);
            JSONArray jsonArray = new JSONArray();
            for (Organization organization : organizationList) {
                JSONObject organizationQuery = new JSONObject();
                organizationQuery.put("inn", organization.getInn());
                organizationQuery.put("ogrn", organization.getOgrn());
                jsonArray.add(organizationQuery);
            }
            query.put("organizations", jsonArray);

            String JSONString = query.toJSONString();


            HttpPost request = new HttpPost(uri);
            StringEntity params = new StringEntity(JSONString, "UTF-8");
            params.setContentEncoding("UTF-8");
            params.setContentType("application/json");
            request.addHeader("Authorization", "Token 13c49f7cdb1ab14887f0329ff2bba40073a74c25");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            String data = EntityUtils.toString(response.getEntity());
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(data).getAsJsonObject();
        } catch (Exception ex) {

        }
        return returned;
 */
    }

    private String publishBaseReg(String docName, String docType, String fileName, byte[] fileBytes,
                                  String regNum, String regDate, String signer, String signerPosition, PublishData publishData) {
        ru.gbuac.jaxws.basereg.BasicHttpBinding_IService1Stub binding;
        try {
            binding = (ru.gbuac.jaxws.basereg.BasicHttpBinding_IService1Stub)
                    new ru.gbuac.jaxws.basereg.Service1Locator(baseregServiceUri).getBasicHttpBinding_IService1();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }

        // Time out after a minute
        binding.setTimeout(60000);
        try {
            CreateDocumentFile[] files = {
                    new CreateDocumentFile(docName, fileBytes, fileName, null)
            };

            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            Calendar calendar  = Calendar.getInstance();
            calendar.setTime(df.parse(regDate));

            ru.gbuac.jaxws.basereg.CreateDocumentResponse response = binding.createDocument(new ru.gbuac.jaxws.basereg.
                    CreateDocumentRequest(calendar, null, files, null, null,
                    new DocStatus("Действует"), signerPosition, signer,
                    "Департамент экономической политики и развития города Москвы", "135d2a0b-dd6c-4d56-be6d-dc9e399c9621",
                    null, null, null, regNum, Integer.parseInt(docType)));
            if (response.getStatus().equals(ResponseStatus.ok)) {
                publishData.setBasereg(response.getFilesId()[0]);
                publishData.setBaseregDateTime(LocalDateTime.now());
                return publishUriMask + response.getFilesId()[0];
            }
        }
        catch (RemoteException | ParseException re) {
            System.out.println(re);
        }
        return "";
    }

    private void publishMosRu(String docName, String publishDate, String rubrname, String uri, String fileName,
                              byte[] fileBytes, PublishData publishData) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            message.setFrom(new InternetAddress(sender + "<" + login + ">"));

            boolean multipart = true;

            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

            String htmlMsg = "<p>\n" +
                    "    <strong>Дата:&nbsp " + publishDate + "</strong>\n" +
                    "</p>\n" +
                    "<table <table style=\"border-collapse: collapse;\" border=\"1\" width=\"97%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                    "    <tbody>\n" +
                    "        <tr>\n" +
                    "            <td width=\"35%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    <a name=\"l103\"></a>\n" +
                    "                    Наименование структурного подразделения\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"64%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    ИАС «Тариф»\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td width=\"35%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    Название документа/ информация\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"64%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    "+docName+"\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td width=\"35%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    Размещение на сайте\n" +
                    "                    <em>\n" +
                    "                        (прописать адрес страницы сайта, на которую необходимо\n" +
                    "                        установить информацию)\n" +
                    "                    </em>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"64%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    " + rubrname + ",\n" +
                    "                    " + uri + "\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td width=\"35%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    Срок размещения в течение\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"64%\" valign=\"top\">\n" +
                    "                <p align=\"center\">\n" +
                    "                    <strong><u>3-х часов</u></strong>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>\n";

            //message.setContent(htmlMsg, "text/html; charset=UTF-8");

            helper.setText(htmlMsg, true);

            helper.setTo(InternetAddress.parse(publishRecipients));

            helper.setSubject("Заявка на размещение информации на Интернет-сайте Департамента экономической политики и развития города Москвы");

            helper.addAttachment(fileName, new ByteArrayResource(fileBytes));

            this.emailSender.send(message);

            publishData.setMosRu(true);
            publishData.setMosRuDateTime(LocalDateTime.now());
        }
        catch (Exception e) {

        }
    }
}
