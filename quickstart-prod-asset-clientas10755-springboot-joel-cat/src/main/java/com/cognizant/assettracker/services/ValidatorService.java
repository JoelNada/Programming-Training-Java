package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.DataValidatorResponseDTO;
import com.cognizant.assettracker.models.entity.EmployeeDetail;

import java.util.List;

public interface ValidatorService {

    DataValidatorResponseDTO emplyoeeDetailValidator(EmployeeDetail employeeDetail);

    List<DataValidatorResponseDTO> dataValidatorResponses();

    void clearDTO();



}
