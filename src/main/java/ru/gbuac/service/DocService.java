package ru.gbuac.service;

import org.springframework.web.multipart.MultipartFile;
import ru.gbuac.model.Doc;
import ru.gbuac.model.User;
import ru.gbuac.to.DocNumberTo;
import ru.gbuac.to.DocTo;
import ru.gbuac.to.FileTo;
import ru.gbuac.to.UserTo;
import ru.gbuac.util.exception.FileUploadException;
import ru.gbuac.util.exception.GenerateDocxException;
import ru.gbuac.util.exception.GeneratePdfException;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface DocService {
    Doc get(int id) throws NotFoundException;

    DocTo getFullByUserName(int id, String userName) throws NotFoundException;

    List<Doc> getAllAgreementByUsername(String userName);

    List<Doc> getAllAgreedByUsername(String userName);

    List<Doc> getAllRegisteredByUsername(String userName);

    List<Doc> getAll();

    List<Doc> getAllAgreement();

    List<Doc> getAllRegistered();

    List<Doc> getAllDistribution(String userName);

    List<Doc> getAllDistributed(String userName);

    List<DocNumberTo> getRegNumbers();

    DocTo save(DocTo doc, String userName, String rootPath);

    DocTo update(DocTo docTo, int id, String userName, String rootPath) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    DocTo returnDocAgreement(int id, String targetUserName, String userName, String comment) throws NotFoundException;

    DocTo rejectDocAgreement(int id, String userName, String comment) throws NotFoundException;

    List<User> saveExecutorUsersList(int id, List<User> executorUsers);

    FileTo uploadFile(MultipartFile inputFile, String rootPath) throws FileUploadException;

    FileTo createDOCX(DocTo docTo, String rootPath) throws GenerateDocxException;

    FileTo createPDF(DocTo docTo, String rootPath) throws GeneratePdfException;
}
