package ru.gbuac.service;

import org.springframework.web.multipart.MultipartFile;
import ru.gbuac.model.Doc;
import ru.gbuac.to.DocTo;
import ru.gbuac.to.FileTo;
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

    DocTo save(DocTo doc, String userName, String rootPath);

    DocTo update(DocTo docTo, int id, String userName, String rootPath) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    FileTo uploadFile(MultipartFile inputFile, String rootPath) throws FileUploadException;

    FileTo createDOCX(DocTo docTo, String rootPath) throws GenerateDocxException;

    FileTo createPDF(DocTo docTo, String rootPath) throws GeneratePdfException;
}
