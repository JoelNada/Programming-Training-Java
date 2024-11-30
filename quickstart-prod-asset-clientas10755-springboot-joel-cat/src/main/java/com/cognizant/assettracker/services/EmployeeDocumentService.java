package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.AmexEmployeeDocumentDTO;
import com.cognizant.assettracker.models.dto.EmployeeDocumentDTO;
import com.cognizant.assettracker.models.entity.EmployeeDocuments;

import java.io.IOException;
import java.util.List;

public interface EmployeeDocumentService {
    public EmployeeDocuments store(AmexEmployeeDocumentDTO amexEmpDoc, int i) throws IOException;
    public List<EmployeeDocumentDTO> getFile(String serialNumber, String docType);
    public String save(AmexEmployeeDocumentDTO amexEmpDoc);

    public void deleteById(long id);

    public byte[] downloadFileById(Long fileId);

    public String getFileName(Long fileId);
}
