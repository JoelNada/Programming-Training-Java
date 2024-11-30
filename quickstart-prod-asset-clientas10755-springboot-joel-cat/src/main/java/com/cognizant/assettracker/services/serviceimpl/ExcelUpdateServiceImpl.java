package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.controller.ExcelController;
import com.cognizant.assettracker.models.EmployeeCheck;
import com.cognizant.assettracker.models.dto.*;
import com.cognizant.assettracker.models.entity.*;
import com.cognizant.assettracker.models.enums.ExcelBadRequestType;
import com.cognizant.assettracker.repositories.*;
import com.cognizant.assettracker.services.*;
import com.poiji.bind.Poiji;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class ExcelUpdateServiceImpl implements ExcelUpdateService {
    private static final Logger log = LoggerFactory.getLogger(ExcelUpdateServiceImpl.class);

    @Autowired
    EmployeeDetailRepository associateRepository;

    @Autowired
    ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    MasterHistoryRepository masterHistoryRepository;

    @Autowired
    UserService userService;

    @Autowired
    MasterHistoryService masterHistoryService;

    @Autowired
    EPLRepository eplRepository;
    @Autowired
    ExcelService excelService;

    @Autowired
    ExcelRepository excelRepository;

    @Autowired
    MissingFieldsRepository missingFieldsRepository;

    @Autowired
    NotificationsService notificationsService;

    @Autowired
    ValidatorService validatorService;



    public ExcelBadRequestDTO empUpdate(File file, ExcelDetail excel) throws IOException {

        ExcelBadRequestDTO excelBadRequestDTO = new ExcelBadRequestDTO();
        List<ExcelBadRequestType> excelBadRequestTypes = new ArrayList<>();
        List<FinalExcelResponseDTO> finalExcelResponseDTOList=new ArrayList<>();
        EmployeeCheck employeeCheck = new EmployeeCheck();
        AtomicInteger empCount=new AtomicInteger(0);
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<String> headersCheck=new ArrayList<>();
        List<String> headers= List.of("SNo","Assignment ID","Associate ID", "Associate Name","Grade","C ID","AEXP ID","City", "Country","Billability Status","Business Unit","Extract Department","Project ID","Project Description","Project Manager ID", "Project Manager Name", "Project Start Date", "Project End Date","Percent Allocation", "EPL","Cognizant Asset","Amex Asset serial number/Amex Asset ID","Amex Asset Make","Amex Asset Model","Missing Fields","Invalid Data Fields");
        System.out.println("header list: "+headers);
        Iterator<Row> itr = sheet.iterator();
        Row row = itr.next();
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getStringCellValue() == null) {
                continue;
            }
            headersCheck.add(cell.getStringCellValue());
        }

        int flag=0;
        for(String head:headersCheck)
        {
            if(head.contains("Missing Fields")||(head.contains("Invalid Data Fields")))
                continue;
            if(!headers.contains(head)){
                flag=1;
                break;
            }
        }

        if (flag == 1) {
            excelBadRequestDTO.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            excelService.uploadProgressCalculation(100,100);
            excelService.sinkflux();
            return excelBadRequestDTO;
        }
        AtomicInteger check = new AtomicInteger();
        check.set(0);

        List<EmployeeUpdateDTO> employeeUpdateDTOList = new ArrayList<>();
        LocalDateTime current = LocalDateTime.now();
        String.valueOf(System.currentTimeMillis());
        Calendar date = Calendar.getInstance();
        date.getTimeInMillis();
        String fileName = file.getName();
        long startTime = System.currentTimeMillis();

        List<EmployeeDetail> employeeDetails = Poiji.fromExcel(file, EmployeeDetail.class);
        List<ProjectDetails> projectDetails = Poiji.fromExcel(file, ProjectDetails.class);
        List<AssetDetail> assetDetails = Poiji.fromExcel(file, AssetDetail.class);

        List<ProjectDetails> projectDetailsSaveList = new ArrayList<>();
        List<EmployeeDetail> employeeDetailSaveList = new ArrayList<>();
        List<MasterHistory> masterHistoriesSaveList = new ArrayList<>();
        List<String> multipleModelCheck=new ArrayList<>();
        List<String> multipleMakeCheck=new ArrayList<>();

        List<EmployeeDetail> employeeDetailCheck = new ArrayList<>();

        List<MissingFields> missingFields = new ArrayList<>();

        List<ExcelExistingFieldResponseDTO> excelExistingFieldResponseDTOS = new ArrayList<>();


        Double totalRecords = (double) employeeDetails.size();

        validatorService.clearDTO();

        for(int i=0;i < employeeDetails.size(); i++)
        {
            int assetCount=0;
            List<AssetDetail> assetDetailList = new ArrayList<>();

            EmployeeDetail employeeDetail = EmployeeDetail.builder()
                    .associateAmexContractorId(employeeDetails.get(i).getAssociateAmexContractorId())
                    .associateId(employeeDetails.get(i).getAssociateId())
                    .associateAmexContractorId(employeeDetails.get(i).getAssociateAmexContractorId())
                    .associateName(employeeDetails.get(i).getAssociateName())
                    .amexDirectorEmail(employeeDetails.get(i).getAssociateAmexContractorId() + "@aexp.com")
                    .onboardingStatus(employeeDetails.get(i).getAssociateAmexContractorId()!=null?"Onboarded":"NotOnboarded")
                    .city(employeeDetails.get(i).getCity())
                    .country(employeeDetails.get(i).getCountry())
                    .serviceLine(employeeDetails.get(i).getServiceLine())
                    .associateCTSEmailId(employeeDetails.get(i).getAssociateId() + "@cognizant.com")
                    .associateAmexEmailId(employeeDetails.get(i).getAssociateAmexEmailId())
                    .assignmentId(employeeDetails.get(i).getAssignmentId())
                    .billability(employeeDetails.get(i).getBillability())
                    .grade(employeeDetails.get(i).getGrade())
                    .businessUnit(employeeDetails.get(i).getBusinessUnit())
                    .percentAllocation(employeeDetails.get(i).getPercentAllocation())
                    .isDeleted(false)
                    .isEdited(false)
                    .assetCount(assetCount)
                    .build();

            ProjectDetails projectDetailCheck = ProjectDetails.builder()
                    .projectId(projectDetails.get(i).getProjectId())
                    .projectName(projectDetails.get(i).getProjectName())
                    .ctsEPLId(projectDetails.get(i).getCtsEPLId())
                    .ctsEPLName(projectDetails.get(i).getCtsEPLName())
                    .projectManagerEmpId(projectDetails.get(i).getProjectManagerEmpId())
                    .projectManagerName(projectDetails.get(i).getProjectManagerName())
                    .projectStartDate(projectDetails.get(i).getProjectStartDate())
                    .projectEndDate(projectDetails.get(i).getProjectEndDate())
                    .build();

            projectDetailCheck.setCtsEPLId(eplRepository.getEplIdFromName(projectDetailCheck.getCtsEPLName()));
            if (assetDetails.get(i).getSerialNumber() != null) {
                List<String> serialAsset=new ArrayList<>(List.of(assetDetails.get(i).getSerialNumber() != null ? assetDetails.get(i).getSerialNumber().split("&") : new String[]{""}));
                List<String> serialModel=new ArrayList<>(List.of(assetDetails.get(i).getAssetModel()!=null?assetDetails.get(i).getAssetModel().split("&"):new String[]{""}));
                List<String> serialMake=new ArrayList<>(List.of(assetDetails.get(i).getAssetMake()!=null?assetDetails.get(i).getAssetMake().split("&"):new String[]{""}));
                assetCount = serialAsset.size();
                if(serialModel.size()!=serialAsset.size()&& !Objects.equals(serialModel.get(0), "")){
                    int difference=serialAsset.size()-serialModel.size();
                    for(int j=0;j<difference;j++)
                        serialModel.add("");
                    multipleModelCheck.add("Multiple Asset Model missing");
                }
                else
                    multipleModelCheck.add("");
                if(serialMake.size()!=serialAsset.size()&& !Objects.equals(serialMake.get(0), "")){
                    int difference=serialAsset.size()-serialMake.size();
                    for(int j=0;j<difference;j++)
                        serialMake.add("");
                    multipleMakeCheck.add("Multiple Asset Make missing");
                }
                else
                    multipleMakeCheck.add("");
                System.out.println("Serial Model After: "+serialModel);

                String cognizantAsset=assetDetails.get(i).getCognizantAsset();
                for(int assetlist=0;assetlist<serialAsset.size();assetlist++){
                AssetDetail assetCheck = AssetDetail.builder()
                        .serialNumber(serialAsset.get(assetlist))
                        .status(assetDetails.get(i).getStatus())
                        .trackingNumber(assetDetails.get(i).getTrackingNumber())
                        .cognizantAsset(cognizantAsset)
                        .assetMake(Objects.equals(serialMake.get(0),"")?"": serialMake.get(assetlist))
                        .assetModel(Objects.equals(serialModel.get(0),"")?"": serialModel.get(assetlist))
                        .assetId(assetDetails.get(i).getAssetId())
                        .build();
                if(assetCheck.getAssetMake()!=null&&assetCheck.getAssetModel()!=null)
                    assetCheck.setStatus("Asset Assigned");
                else
                    assetCheck.setStatus("Asset details incomplete");
                assetDetailList.add(assetCheck);
                }
            } else{
                assetDetailList = null;
                multipleModelCheck.add("");
                multipleMakeCheck.add("");
            }
            if (assetCount !=0) {
                employeeDetail.setAssetCount(assetCount);
            }

            employeeDetail.setProjectDetails(projectDetailCheck);
            employeeDetail.setAssetsDetails(assetDetailList);

            employeeDetailCheck.add(employeeDetail);
        }

        employeeDetailCheck.stream()
                .forEach(employeeDetail -> {
                    empCount.getAndIncrement();
                    AtomicInteger check2 = new AtomicInteger();
                    check2.set(0);
                    MissingFields missingFieldsCheck = new MissingFields();
                    List<String> missingfields = new ArrayList<>();

                    if (employeeDetail.getAssociateId() == null) {
                        missingfields.add("Associate ID missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getAssociateName() == null) {
                        missingfields.add("Associate Name missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getAssignmentId() == null) {
                        missingfields.add("Assignment ID missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getCity() == null) {
                        missingfields.add("City name missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getCountry() == null) {
                        missingfields.add("Country name missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getGrade() == null) {
                        missingfields.add("Grade missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getBusinessUnit() == null) {
                        missingfields.add("Business Unit missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getPercentAllocation() == null) {
                        missingfields.add("Percent Allocation missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getServiceLine() == null) {
                        missingfields.add("Service Line missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getBillability() == null) {
                        missingfields.add("Billability missing");
                        check.set(1);
                        check2.set(1);
                    }

                    if (employeeDetail.getProjectDetails().getProjectManagerEmpId() == null) {
                        missingfields.add("Project Manager ID missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getProjectDetails().getProjectManagerName() == null) {
                        missingfields.add("Project Manager Name missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getProjectDetails().getCtsEPLName() == null) {
                        missingfields.add("EPL Name missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getProjectDetails().getCtsEPLId() == null) {
                        missingfields.add("EPL ID missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getProjectDetails().getProjectStartDate() == null) {
                        missingfields.add("Project Start Date missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getProjectDetails().getProjectEndDate() == null) {
                        missingfields.add("Project End Date missing");
                        check.set(1);
                        check2.set(1);
                    }

                    if (employeeDetail.getProjectDetails().getProjectName() == null) {
                        missingfields.add("Project name missing");
                        check.set(1);
                        check2.set(1);
                    }
                    if (employeeDetail.getProjectDetails().getProjectId() == null) {
                        missingfields.add("Project ID missing");
                        check.set(1);
                        check2.set(1);
                    }
                    System.out.println("Emp count get: "+empCount.get());
                    if(!Objects.equals(multipleModelCheck.get(empCount.get()-1), "")){
                        missingfields.add(multipleModelCheck.get(empCount.get()-1));
                        check.set(1);
                        check2.set(1);
                    }
                    if(!Objects.equals(multipleMakeCheck.get(empCount.get()-1), "")){
                        missingfields.add(multipleMakeCheck.get(empCount.get()-1));
                        check.set(1);
                        check2.set(1);
                    }

                    missingFieldsCheck.setId(employeeDetail.getAssignmentId());
                    EmployeeUpdateDTO employeeUpdateDto = employeeUpdateDTOBuilder(employeeDetail); //to build dto for employee details for excel download
                    employeeUpdateDTOList.add(employeeUpdateDto);
                    if (check2.get() == 1) { //there are missing fields
                        missingFieldsCheck.setFields(missingfields); //setting to missing fields local list

                        if (employeeDetail.getAssignmentId() == null) {
                            missingFieldsCheck.setId(null);
                        } else {
                            missingFieldsCheck.setId(employeeDetail.getAssignmentId());
                        }
                        missingFieldsCheck.setExcelName(excel.getName() + employeeDetail.getAssignmentId());
                        missingFields.add(missingFieldsCheck);
                        float progress=excelService.uploadProgressCalculation(empCount.get(),employeeDetailCheck.size());
                    }
                   DataValidatorResponseDTO dataValidatorResponseDTO=validatorService.emplyoeeDetailValidator(employeeDetail);
                    if (dataValidatorResponseDTO==null&&check2.get()==0) { // there are no missing fields and there is valid data
                            if (associateRepository.findByAssignmentId(employeeDetail.getAssignmentId()) == null) {
                                employeeDetail.getProjectDetails().setCreateTimestamp(current);
                                employeeDetail.getProjectDetails().setUpdatedTimestamp(current);
                                employeeDetail.getProjectDetails().setUpdatedBy(userService.getUser());
                                projectDetailsSaveList.add(employeeDetail.getProjectDetails());

                                if (employeeDetail.getAssetsDetails() != null) {
                                    for(int i=0;i<employeeDetail.getAssetsDetails().size();i++){
                                    employeeDetail.getAssetsDetails().get(i).setUpdatedBy(userService.getUser());
                                    employeeDetail.getAssetsDetails().get(i).setUpdatedTimestamp(current);
                                    employeeDetail.getAssetsDetails().get(i).setAmexEmployeeDetail(employeeDetail);
                                    }
                                }

                            employeeDetail.setIsActiveInAmex(true);
                            employeeDetail.setCreateTimestamp(current);
                            employeeDetail.setUpdatedTimestamp(current);
                            employeeDetail.setUpdatedBy(userService.getUser());
                            employeeDetail.setDeleted(false);
                            employeeDetail.setAssetCount(employeeDetail.getAssetCount());
                            employeeDetailSaveList.add(employeeDetail);
                            float progress = excelService.uploadProgressCalculation(empCount.get(), employeeDetailCheck.size());

                        } else {
//                            if (!excelExistingRecordCheck(employeeDetail, excelExistingFieldResponseDTOS)) {
                                savePreparation(employeeDetailSaveList, projectDetailsSaveList, masterHistoriesSaveList, employeeDetail);
                                float progress = excelService.uploadProgressCalculation(empCount.get(), employeeDetailCheck.size());
 //                           }
                        }


                    }

                    FinalExcelResponseDTO finalExcelResponseDTO= FinalExcelResponseDTO.builder()
                            .assignmentId(employeeDetail.getAssignmentId())
                            .associateId(employeeDetail.getAssociateId())
                            .associateName(employeeDetail.getAssociateName())
                            .employeeUpdateDTO(employeeUpdateDto)
                            .missingFields(!missingfields.isEmpty()?missingfields.toString():null)
                            .invalidDataFields(dataValidatorResponseDTO!=null?dataValidatorResponseDTO.getDataFields().toString():null)
                            .build();
                    if(finalExcelResponseDTO.getMissingFields()!=null||finalExcelResponseDTO.getInvalidDataFields()!=null)
                        finalExcelResponseDTOList.add(finalExcelResponseDTO);
                    float progress = excelService.uploadProgressCalculation(empCount.get(), employeeDetailCheck.size());
                    excelService.sinkflux();
                    System.out.println("Employee: "+employeeDetail.toString());
                });

        saveRecords(masterHistoriesSaveList,employeeDetailSaveList, projectDetailsSaveList); //save the records that do not have missing fields, field validation or existing record check

        employeeMissingFields(check, employeeCheck, employeeUpdateDTOList, missingFields); //assigning and saving to the missing fields repository

        List<DataValidatorResponseDTO> dataValidatorResponseDTOS = validatorService.dataValidatorResponses(); //collecting validation responses

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        long updatedRecords = employeeDetailSaveList.size();
        long missingFieldCount = missingFields.size();
        long dataInvalidCount = dataValidatorResponseDTOS.size();

        excel.setTotalRecords(String.valueOf(totalRecords));
        excel.setUpdatedRecords(updatedRecords);
        excel.setErrorRecords(missingFieldCount);
        excel.setTotalTimeTaken(String.valueOf(totalTime));
        excel.setAverageTimePerRecord(String.valueOf(totalRecords / totalTime));

        excelRepository.save(excel);

        if (check.get() == 1) //setting missing fields response
        {
            excelBadRequestDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            excelBadRequestTypes.add(ExcelBadRequestType.MISSING_FIELDS);
            excelBadRequestDTO.setFinalExcelResponseDTOList(finalExcelResponseDTOList);

        }
        if (!dataValidatorResponseDTOS.equals(new ArrayList<>())) {//setting data validation response
            excelBadRequestDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            excelBadRequestTypes.add(ExcelBadRequestType.DATA);
            excelBadRequestDTO.setFinalExcelResponseDTOList(finalExcelResponseDTOList);

        } else if (!excelExistingFieldResponseDTOS.equals(new ArrayList<>())) //setting existing fields response if missing fields response is null
        {
            excelBadRequestDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            excelBadRequestTypes.add(ExcelBadRequestType.EDITED_RECORD);
            excelBadRequestDTO.setExcelExistingFieldResponseDTOS(excelExistingFieldResponseDTOS);
        }

        excelBadRequestDTO.setExcelBadRequest(excelBadRequestTypes);

        if (excelBadRequestDTO.getExcelBadRequest().isEmpty())
            excelBadRequestDTO.setHttpStatus(HttpStatus.OK);
        notificationsService.createExcelUpdateNotification(excel, excelBadRequestDTO.getHttpStatus(), employeeCheck.getMissingFields(), dataInvalidCount, excelBadRequestDTO.getExcelBadRequest(),excelBadRequestDTO);

        return excelBadRequestDTO;
    }

    private void saveRecords(List<MasterHistory> masterHistoriesSaveList, List<EmployeeDetail> employeeDetailSaveList, List<ProjectDetails> projectDetailsSaveList) {

        masterHistoryRepository.saveAll(masterHistoriesSaveList);
        projectDetailsRepository.saveAll(projectDetailsSaveList);
        associateRepository.saveAll(employeeDetailSaveList);

    }

    private void employeeMissingFields(AtomicInteger check, EmployeeCheck employeeCheck, List<EmployeeUpdateDTO> employeeUpdateDTOList, List<MissingFields> missingFields) {
        if (check.get() == 1) {
            employeeCheck.setEmployeeDetailList(employeeUpdateDTOList);
            employeeCheck.setHttpStatus(HttpStatus.BAD_REQUEST);
            employeeCheck.setMissingFields(missingFields);
            missingFieldsRepository.saveAll(employeeCheck.getMissingFields());

        } else {
            employeeCheck.setEmployeeDetailList(employeeUpdateDTOList);
            employeeCheck.setHttpStatus(HttpStatus.OK);
        }

    }

    private boolean excelExistingRecordCheck(EmployeeDetail employeeDetail, List<ExcelExistingFieldResponseDTO> excelExistingFieldResponseDTOS) {
        EmployeeDetail EmployeeExists = associateRepository.findByAssignmentId(employeeDetail.getAssignmentId());

        if (EmployeeExists.isEdited()) {

            ExcelExistingFieldResponseDTO excelExistingFieldResponseDTO = ExcelExistingFieldResponseDTO.builder()
                    .assignmentId(EmployeeExists.getAssignmentId())
                    .databaseRecord(EmployeeExists)
                    .excelRecord(employeeDetail)
                    .build();
            if(compareDatabaseWithExcel(employeeDetail, EmployeeExists))
            {
                excelExistingFieldResponseDTOS.add(excelExistingFieldResponseDTO);
            }
            return true;
        } else
            return false;
    }

    private boolean compareDatabaseWithExcel(EmployeeDetail excel, EmployeeDetail database) {
        EmployeeUpdateDTO databaseRecord = employeeProjectDetailComparatorBuilder(database);
        EmployeeUpdateDTO excelRecord = employeeProjectDetailComparatorBuilder(excel);

        boolean addToResponse = false;

        if (databaseRecord.equals(excelRecord)) {
            try {
                if (!assetDetailEditableFieldComparator(database.getAssetsDetails().get(0), excel.getAssetsDetails().get(0))) {
                    addToResponse = true;
                }
            }
            catch (IndexOutOfBoundsException e)
            {
                addToResponse = true;

            }
            return addToResponse;

            }
        return addToResponse;
        }

    private EmployeeUpdateDTO employeeUpdateDTOBuilder(EmployeeDetail employeeDetail)
    {
        EmployeeUpdateDTO employeeUpdateDto = EmployeeUpdateDTO.builder()
                .assignmentId(employeeDetail.getAssignmentId())
                .associateId(employeeDetail.getAssociateId())
                .associateName(employeeDetail.getAssociateName())
                .associateAmexContractorId(employeeDetail.getAssociateAmexContractorId())
                .associateAmexEmailId(employeeDetail.getAssociateAmexEmailId())
                .city(employeeDetail.getCity())
                .country(employeeDetail.getCountry())
                .serviceLine(employeeDetail.getServiceLine())
                .grade(employeeDetail.getGrade())
                .businessUnit(employeeDetail.getBusinessUnit())
                .percentAllocation(employeeDetail.getPercentAllocation())
                .billability(employeeDetail.getBillability())
                .serviceLine(employeeDetail.getServiceLine())
                .projectId(employeeDetail.getProjectDetails().getProjectId())
                .projectName(employeeDetail.getProjectDetails().getProjectName())
                .ctsEPLId(employeeDetail.getProjectDetails().getCtsEPLId())
                .ctsEPLName(employeeDetail.getProjectDetails().getCtsEPLName())
                .projectManagerEmpId(employeeDetail.getProjectDetails().getProjectManagerEmpId())
                .projectManagerName(employeeDetail.getProjectDetails().getProjectManagerName())
                .projectStartDate(employeeDetail.getProjectDetails().getProjectStartDate())
                .projectEndDate(employeeDetail.getProjectDetails().getProjectEndDate())
                .build();

        if(employeeDetail.getAssetsDetails()!=null)
        {
            employeeUpdateDto.setAssetModel(employeeDetail.getAssetsDetails().get(0).getAssetModel());
            employeeUpdateDto.setAssetMake(employeeDetail.getAssetsDetails().get(0).getAssetMake());
            employeeUpdateDto.setSerialNumber(employeeDetail.getAssetsDetails().get(0).getSerialNumber());
            employeeUpdateDto.setCognizantAsset(employeeDetail.getAssetsDetails().get(0).getCognizantAsset());
        }

        return employeeUpdateDto;
    }

    public void saveEmployee(List<ExcelExistingFieldResponseDTO> excelExistingFieldResponseDTOS)
    {
        List<ProjectDetails> projectDetailsSaveList = new ArrayList<>();
        List<EmployeeDetail> employeeDetailSaveList = new ArrayList<>();
        List<MasterHistory> masterHistoriesSaveList = new ArrayList<>();



        excelExistingFieldResponseDTOS.stream().filter(ExcelExistingFieldResponseDTO::isReplaceRecord)
                .forEach(excelExistingFieldResponseDTO -> {

                    savePreparation(employeeDetailSaveList, projectDetailsSaveList, masterHistoriesSaveList, excelExistingFieldResponseDTO.getExcelRecord());
                });

        saveRecords(masterHistoriesSaveList,employeeDetailSaveList,projectDetailsSaveList);

    }

    public void savePreparation(List<EmployeeDetail> employeeDetailSaveList, List<ProjectDetails> projectDetailsSaveList, List<MasterHistory> masterHistoriesSaveList, EmployeeDetail employeeDetail) {

        LocalDateTime current = LocalDateTime.now();
        EmployeeDetail deletedData = associateRepository.findByAssignmentId(employeeDetail.getAssignmentId());
        if (deletedData != null) {
            associateRepository.softDeleteByName(employeeDetail.getAssignmentId());

            ProjectDetails deletedDataProject = deletedData.getProjectDetails();
            List<AssetDetail> deletedDataAssetDetail = deletedData.getAssetsDetails();

            deletedData.setProjectDetails(deletedDataProject);
            MasterHistory newMasterHistoryData = masterHistoryService.commonEmployeeDetailsBuilder(deletedData, ExcelController.fileName);

            if (deletedDataAssetDetail!=null) {
                for (AssetDetail assetDetail : deletedDataAssetDetail) {
                    newMasterHistoryData.setSerialNumber(assetDetail.getSerialNumber());
                    newMasterHistoryData.setIssueDate(assetDetail.getIssueDate());
                    newMasterHistoryData.setStatus(assetDetail.getStatus());
                    newMasterHistoryData.setReturnDate(assetDetail.getReturnDate());
                    newMasterHistoryData.setTrackingNumber(assetDetail.getTrackingNumber());
                    newMasterHistoryData.setAssetMake(assetDetail.getAssetMake());
                    newMasterHistoryData.setAssetModel(assetDetail.getAssetModel());
                    newMasterHistoryData.setAssetWarrantyEndDate(assetDetail.getAssetWarrantyEndDate());
                    newMasterHistoryData.setIsAssetUpToDate(assetDetail.getIsAssetUpToDate());

                    masterHistoriesSaveList.add(newMasterHistoryData);
                }
            }
            masterHistoriesSaveList.add(newMasterHistoryData);
        }
        employeeDetail.getProjectDetails().setCreateTimestamp(current);
        employeeDetail.getProjectDetails().setUpdatedTimestamp(current);
        employeeDetail.getProjectDetails().setUpdatedBy(userService.getUser());
        projectDetailsSaveList.add(employeeDetail.getProjectDetails());

        if (employeeDetail.getAssetsDetails() != null) {
            for(int i=0;i<employeeDetail.getAssetsDetails().size();i++){
            employeeDetail.getAssetsDetails().get(i).setUpdatedBy(userService.getUser());
            employeeDetail.getAssetsDetails().get(i).setUpdatedTimestamp(current);
            employeeDetail.getAssetsDetails().get(i).setAmexEmployeeDetail(employeeDetail);
                System.out.println("Asset before Saving: "+employeeDetail.getAssetsDetails().get(i).getSerialNumber());
            }
        }
        employeeDetail.setIsActiveInAmex(true);
        employeeDetail.setCreateTimestamp(current);
        employeeDetail.setUpdatedTimestamp(current);
        employeeDetail.setUpdatedBy(userService.getUser());
        employeeDetail.setDeleted(false);
        employeeDetailSaveList.add(employeeDetail);
    }

    private boolean assetDetailEditableFieldComparator(AssetDetail excelRecord, AssetDetail databaseRecord) {


        if (excelRecord != null && databaseRecord != null) {

            if (Objects.equals(excelRecord.getCognizantAsset(), databaseRecord.getCognizantAsset()) &&
                    Objects.equals(excelRecord.getAssetMake(), databaseRecord.getAssetMake()) &&
                    Objects.equals(excelRecord.getAssetModel(), databaseRecord.getAssetModel()) &&
                    Objects.equals(excelRecord.getSerialNumber(), databaseRecord.getSerialNumber())) {
                return true;
            }
        }
        return false;
    }


    private EmployeeUpdateDTO employeeProjectDetailComparatorBuilder(EmployeeDetail employeeDetail)
    {

        return EmployeeUpdateDTO.builder()
                .assignmentId(employeeDetail.getAssignmentId())
                .associateId(employeeDetail.getAssociateId())
                .associateName(employeeDetail.getAssociateName())
                .associateAmexContractorId(employeeDetail.getAssociateAmexContractorId())
                .associateAmexEmailId(employeeDetail.getAssociateAmexEmailId())
                .city(employeeDetail.getCity())
                .country(employeeDetail.getCountry())
                .serviceLine(employeeDetail.getServiceLine())
                .grade(employeeDetail.getGrade())
                .businessUnit(employeeDetail.getBusinessUnit())
                .percentAllocation(employeeDetail.getPercentAllocation())
                .billability(employeeDetail.getBillability())
                .serviceLine(employeeDetail.getServiceLine())
                .projectId(employeeDetail.getProjectDetails().getProjectId())
                .projectName(employeeDetail.getProjectDetails().getProjectName())
                .ctsEPLId(employeeDetail.getProjectDetails().getCtsEPLId())
                .ctsEPLName(employeeDetail.getProjectDetails().getCtsEPLName())
                .projectManagerEmpId(employeeDetail.getProjectDetails().getProjectManagerEmpId())
                .projectManagerName(employeeDetail.getProjectDetails().getProjectManagerName())
                .projectStartDate(employeeDetail.getProjectDetails().getProjectStartDate())
                .projectEndDate(employeeDetail.getProjectDetails().getProjectEndDate())
                .build();
    }
}