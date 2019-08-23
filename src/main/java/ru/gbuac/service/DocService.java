package ru.gbuac.service;

import ru.gbuac.model.Doc;
import ru.gbuac.to.DocTo;
import ru.gbuac.to.PdfTo;
import ru.gbuac.util.exception.NotFoundException;

import java.util.List;

public interface DocService {
    Doc get(int id) throws NotFoundException;

    DocTo getFullByUserName(int id, String userName) throws NotFoundException;

    List<Doc> getAllAgreementByUsername(String userName);

    List<Doc> getAll();

    DocTo save(DocTo doc, String userName, String rootPath);

    DocTo update(DocTo docTo, int id, String userName, String rootPath) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    PdfTo getPdfPathByDocTo(DocTo docTo, String rootPath);
}
