package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.models.dto.DataValidatorResponseDTO;
import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.cognizant.assettracker.services.ValidatorService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.DateValidator;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ValidatorServiceImpl implements ValidatorService {

    String dateValidatorMessage = "The format of the following date fields should be in dd-mm-yyy or dd/mm/yyyy :";

    List<DataValidatorResponseDTO> dataValidatorResponseDTOList = new ArrayList<>();

    public DataValidatorResponseDTO emplyoeeDetailValidator(EmployeeDetail employeeDetail)
    {
        HashMap<String, String> dateFields = dateExtractor(employeeDetail);
        DataValidatorResponseDTO dataValidatorResponseDTO = new DataValidatorResponseDTO();
        boolean isValidDate = dataValidator(dateFields, dataValidatorResponseDTO,employeeDetail);
        if (!isValidDate) {
            dataValidatorResponseDTO.setAssignmentId(employeeDetail.getAssignmentId());
            dataValidatorResponseDTO.setAssociateName(employeeDetail.getAssociateName());
            dataValidatorResponseDTOList.add(dataValidatorResponseDTO);
            System.out.println("data validator response: "+dataValidatorResponseDTO);
            return dataValidatorResponseDTO;
        }
        else{

            return null;}

    }

    public List<DataValidatorResponseDTO> dataValidatorResponses()
    {
        return dataValidatorResponseDTOList;
    }
    private boolean dataValidator(HashMap<String,String> date, DataValidatorResponseDTO dataValidatorResponseDTO, EmployeeDetail employeeDetail)
    {
        DateValidator validator = DateValidator.getInstance();
        List<String> dateFields = new ArrayList<>();
        if(employeeDetail.getAssignmentId()!=null&&employeeDetail.getAssignmentId()==0){
            dateFields.add("Assignment ID");
        }
        if(employeeDetail.getAssociateId()!=null&&employeeDetail.getAssociateId()==0){
            dateFields.add("Associate ID");
        }
        if(employeeDetail.getAssociateName()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getAssociateName().replaceAll("\\P{Print}", "")).matches("^[a-zA-Z,.-]*$")){
            dateFields.add("Associate Name");
        }
        if(employeeDetail.getGrade()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getGrade().replaceAll("\\P{Print}", "")).matches("^[A-Z/]*$"))
            dateFields.add("Grade");
        if(employeeDetail.getBillability()!=null&&Objects.equals(employeeDetail.getBillability(),"y")||employeeDetail.getBillability()!=null&&Objects.equals(employeeDetail.getBillability(),"Y")) {
            if (employeeDetail.getAssociateAmexContractorId() != null && !StringUtils.deleteWhitespace(employeeDetail.getAssociateAmexContractorId().replaceAll("\\P{Print}", "")).matches("^[a-zA-Z0-9]*$"))
                dateFields.add("Amex Contractor ID");
            if (employeeDetail.getAssociateAmexEmailId()!= null&&!StringUtils.deleteWhitespace(employeeDetail.getAssociateAmexEmailId().replaceAll("\\P{Print}", "")).matches("^[a-zA-Z0-9.-_]+@aexp.com$")) {
                dateFields.add("Amex Email ID");
            }
        }
        if(employeeDetail.getCity()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getCity().replaceAll("\\P{Print}", "")).matches("^[a-zA-Z-]*$"))
            dateFields.add("City");
        if(employeeDetail.getCountry()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getCountry().replaceAll("\\P{Print}", "")).matches("^[a-zA-Z]*$"))
            dateFields.add("Country");
        if(employeeDetail.getBillability()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getBillability().replaceAll("\\P{Print}", "")).matches("[YN]"))
            dateFields.add("Billability Status");
        if(employeeDetail.getBusinessUnit()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getBusinessUnit().replaceAll("\\P{Print}", "")).matches("^[a-zA-Z.-]*$")){
            dateFields.add("Business Unit");
        }
        if(employeeDetail.getServiceLine()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getServiceLine().replaceAll("\\P{Print}", "")).matches("^[a-zA-Z&-]*$")){
            dateFields.add("Service Line");
        }
        if(employeeDetail.getProjectDetails().getProjectId()!=null&&employeeDetail.getProjectDetails().getProjectId()==0){
            dateFields.add("Project ID");
        }
        if(employeeDetail.getProjectDetails().getProjectManagerEmpId()!=null&&employeeDetail.getProjectDetails().getProjectManagerEmpId()==0){
            dateFields.add("Project Manager ID");
        }
        if(employeeDetail.getProjectDetails().getProjectManagerName()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getProjectDetails().getProjectManagerName().replaceAll("\\P{Print}", "")).matches("^[a-zA-Z,]*$")){
            dateFields.add("Project Manager Name");
        }
        if(employeeDetail.getPercentAllocation()!=null&&employeeDetail.getPercentAllocation()==0){
            dateFields.add("Percent Allocation");
        }
        if(employeeDetail.getProjectDetails().getCtsEPLName()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getProjectDetails().getCtsEPLName().replaceAll("\\P{Print}", "")).matches("^[a-zA-Z,]*$")){
            dateFields.add("EPL Name");
        }
        if(employeeDetail.getAssetsDetails()!=null){
        if(employeeDetail.getAssetsDetails().get(0).getCognizantAsset()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getAssetsDetails().get(0).getCognizantAsset().replaceAll("\\P{Print}", "")).matches("^[a-zA-Z0-9:.]+$")){
            dateFields.add("Cognizant Asset");
        }
            if(employeeDetail.getAssetsDetails().get(0).getSerialNumber()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getAssetsDetails().get(0).getSerialNumber().replaceAll("\\P{Print}", "")).matches("^[a-zA-Z0-9,./#&-]*$")){
                dateFields.add("Amex Serial Number");
                }

            if(employeeDetail.getAssetsDetails().get(0).getAssetMake()!=null&&!StringUtils.deleteWhitespace(employeeDetail.getAssetsDetails().get(0).getAssetMake()).replaceAll("\\P{Print}", "").matches("^[[a-zA-Z&]*\s*]*$")){
                dateFields.add("Asset Make");
            }
        }
        for(Map.Entry<String, String> dates : date.entrySet()) {
            if(dates.getValue()!=null) {
                if (!(validator.isValid(dates.getValue(), "dd/mm/yyyy") || validator.isValid(dates.getValue(), "dd-mm-yyyy"))) {
                    dateFields.add(dates.getKey());
                }
            }
        }
        if (!dateFields.isEmpty()) {
            dataValidatorResponseDTO.setDataFields(dateFields);
            return false;
        }
        else{
            return true;
        }

    }

    private HashMap<String,String> dateExtractor (EmployeeDetail employeeDetail)
    {
        HashMap<String,String> dateFields = new HashMap<>();
        dateFields.put("Project Start Date",employeeDetail.getProjectDetails().getProjectStartDate());
        dateFields.put("Project End Date", employeeDetail.getProjectDetails().getProjectEndDate());
        return dateFields;

    }

    public void clearDTO()
    {
        dataValidatorResponseDTOList.clear();
    }


}

