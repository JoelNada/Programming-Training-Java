package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.cognizant.assettracker.models.entity.EmployeeDocuments;
import com.cognizant.assettracker.repositories.EmployeeDetailRepository;
import com.cognizant.assettracker.repositories.EmployeeDocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PickupReportService {
    @Autowired
    EmployeeDocumentsRepository employeeDocumentsRepository;

    @Autowired
    EmployeeDetailRepository employeeDetailRepository;

    public List<EmployeeDetail> pickupReport() throws IOException {
        List<EmployeeDetail> empPickupList=new ArrayList<>();
        List<EmployeeDetail> employeeDetailList=employeeDetailRepository.findAll();
        for(int i=0;i<employeeDetailList.size();i++){
            Long assignmentId=employeeDetailList.get(i).getAssignmentId();
            List<EmployeeDocuments> employeeDocuments=new ArrayList<>();
            if(employeeDocumentsRepository.findAssignmentId(assignmentId)==null)
                continue;
            else{
                employeeDocuments=employeeDocumentsRepository.findAssignmentId(assignmentId);

                for(EmployeeDocuments employeeDocuments1:employeeDocuments){
                if(employeeDocuments1.isPickupReceiptPresent())
                    empPickupList.add(employeeDetailRepository.findByAssignmentId(assignmentId));
            }}
        }
        return empPickupList;

    }

}
