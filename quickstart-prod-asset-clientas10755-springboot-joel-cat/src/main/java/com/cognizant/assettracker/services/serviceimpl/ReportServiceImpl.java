package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.controller.AuthController;
import com.cognizant.assettracker.models.ReportHeaders;
import com.cognizant.assettracker.models.ReportOptions;
import com.cognizant.assettracker.models.entity.AssetDetail;
import com.cognizant.assettracker.models.entity.AssetRelease;
import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.cognizant.assettracker.repositories.*;
import com.cognizant.assettracker.models.dto.ReportResponseDTO;
import com.cognizant.assettracker.services.ReportService;
import com.cognizant.assettracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    EmployeeDetailRepository employeeDetailRepository;
    @Autowired
    AssetsRepository assetsRepository;
    @Autowired
    AssetReleaseRepository assetReleaseRepository;
    @Autowired
    PDFReportRepository pdfReportRepository;
    @Autowired
    ExcelReportRepository excelReportRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    @Autowired
    AuthController authController;

    public List<EmployeeDetail> assignEmployeeList() throws RoleNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = null;
        if (authentication != null && authentication.getDetails() instanceof String) {

            role = (String) authentication.getDetails();
        }
        if ("ESA_PM".equals(role)) {
            Long employeeId= Long.valueOf(userService.getEmployeeId());
            return employeeDetailRepository.getEmpDetailsByESA(employeeId);
        } else if ("EPL".equals(role)) {
            String employeeId= userService.getEmployeeId();
            return employeeDetailRepository.getEmpDetailsByEPL(employeeId);
        } else if ("PMO".equals(role)) {
            return employeeDetailRepository.findAll();
        }
        else
            throw new RoleNotFoundException("Invalid Role");
    }
    public List<AssetDetail> assignAssetList() throws RoleNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = null;
        if (authentication != null && authentication.getDetails() instanceof String) {

            role = (String) authentication.getDetails();
        }
        if ("ESA_PM".equals(role)) {
            Long employeeId= Long.valueOf(userService.getEmployeeId());
            return assetsRepository.getAssetDetailsByESA(employeeId);
        } else if ("EPL".equals(role)) {
            String employeeId= userService.getEmployeeId();
            return assetsRepository.getAssetDetailsByEPL(employeeId);
        } else if ("PMO".equals(role)) {
            return assetsRepository.findAll();
        }
        else
            throw new RoleNotFoundException("Invalid Role");
    }
    public List<AssetRelease> assignAssetReleaseList() throws RoleNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = null;
        if (authentication != null && authentication.getDetails() instanceof String) {

            role = (String) authentication.getDetails();
        }
        if ("ESA_PM".equals(role)) {
            Long employeeId= Long.valueOf(userService.getEmployeeId());
            return assetReleaseRepository.getAssetDetailsByESA(employeeId);
        } else if ("EPL".equals(role)) {
            String employeeId= userService.getEmployeeId();
            return assetReleaseRepository.getAssetDetailsByEPL(employeeId);
        } else if ("PMO".equals(role)) {
            return assetReleaseRepository.findAll();
        }
        else
            throw new RoleNotFoundException("Invalid Role");
    }
    List<String> emptyCheckList=Arrays.asList("", null);
    public  List<ReportResponseDTO> onboardingReport() throws RoleNotFoundException {//asset release requested
        List<EmployeeDetail> employeeDetailList = assignEmployeeList();
        List<EmployeeDetail> onboardedList = new ArrayList<>();
        for (EmployeeDetail employee : employeeDetailList) {
            if(employee.getAssetsDetails().size()==0||emptyCheckList.contains(employee.getAssociateAmexContractorId())){
                continue;
            }
            AssetDetail assetDetail= employee.getAssetsDetails().get(0);
                   if(emptyCheckList.contains(assetDetail.getReturnDate()))
                       onboardedList.add(employee);

        }
        return generateReportList(onboardedList,null);
    }
    public List<ReportResponseDTO> ReleaseRequested() throws RoleNotFoundException { //release confirmed
        List<EmployeeDetail> releaseRequestedList=new ArrayList<>();
        List<AssetDetail> assetDetailList=assignAssetList();
        for(AssetDetail assetDetail:assetDetailList){
            AssetRelease asset = assetReleaseRepository.findBySerialNumber(assetDetail.getSerialNumber());
            if(asset==null){
                continue;
            }
            else{
                if(emptyCheckList.contains(asset.getRequestCreationTime())){
                    if(!emptyCheckList.contains(assetDetail.getReturnDate())){
                        releaseRequestedList.add(assetDetail.getAmexEmployeeDetail());
                    }
                }}

        }
        return generateStatusReportList(releaseRequestedList,null,"Asset release requested");
    }

    public  List<ReportResponseDTO> AssetReleaseRequested() throws RoleNotFoundException {//asset release requested
        List<EmployeeDetail> assetReleaseRequestList = new ArrayList<>();
        List<AssetDetail> assetDetailList=assignAssetList();
        for(AssetDetail assetDetail:assetDetailList){
            AssetRelease asset = assetReleaseRepository.findBySerialNumber(assetDetail.getSerialNumber());
            if(asset==null){
                continue;
            }
            else{
                if(!emptyCheckList.contains(asset.getRequestCreationTime())&&emptyCheckList.contains(asset.getPickupTimestamp())){
                        EmployeeDetail empAssetReleaseRequest=assetDetail.getAmexEmployeeDetail();
                    assetReleaseRequestList.add(empAssetReleaseRequest);
                    }
                }
        }
        return generateStatusReportList(assetReleaseRequestList,null,"Asset pickup requested");
    }

    public  List<ReportResponseDTO> AssetReturnCompleted() throws RoleNotFoundException { //asset return completed
        List<EmployeeDetail> AssetReturnCompletedList = new ArrayList<>();
        List<AssetDetail> assetDetailList=assignAssetList();
        for(AssetDetail assetDetail:assetDetailList) {
            AssetRelease asset = assetReleaseRepository.findBySerialNumber(assetDetail.getSerialNumber());
            if (asset == null) {
                continue;
            } else {
                if (!emptyCheckList.contains(asset.getRequestCreationTime())&&!emptyCheckList.contains(asset.getPickupTimestamp())) {
                    EmployeeDetail AssetReturnCompleted = assetDetail.getAmexEmployeeDetail();
                    AssetReturnCompletedList.add(AssetReturnCompleted);
                }
            }
        }
        return generateStatusReportList(AssetReturnCompletedList,null,"Asset pickup completed");
    }

    public List<ReportResponseDTO> amexCidFalse() throws RoleNotFoundException {
        List<EmployeeDetail> associateAmexContractorIdList = new ArrayList<>();
        List<EmployeeDetail> employeeDetailList = assignEmployeeList();
        for (EmployeeDetail employeeDetail : employeeDetailList) {
            if (emptyCheckList.contains(employeeDetail.getAssociateAmexContractorId())) {
                associateAmexContractorIdList.add(employeeDetail);
            }
        }
        return generateReportList(associateAmexContractorIdList,null);
        }

    public List<ReportResponseDTO> amexCidTrue() throws RoleNotFoundException {
        List<EmployeeDetail> associateAmexContractorIdList = new ArrayList<>();
        List<EmployeeDetail> employeeDetailList = assignEmployeeList();
            for (EmployeeDetail employeeDetail : employeeDetailList) {
                if (!emptyCheckList.contains(employeeDetail.getAssociateAmexContractorId())) {
                    associateAmexContractorIdList.add(employeeDetail);
                }
            }
        return generateReportList(associateAmexContractorIdList,null);
        }

    public  List<ReportResponseDTO> amexLaptopHolders() throws IOException, RoleNotFoundException {
        List<EmployeeDetail> amexLaptopHoldersList=new ArrayList<>();
        List<AssetDetail> employeeDetailList = assignAssetList();
        List<EmployeeDetail> employeeDetails=new ArrayList<>();
        for (AssetDetail assetDetail : employeeDetailList) {
            EmployeeDetail employeeDetail = assetDetail.getAmexEmployeeDetail();
            if(employeeDetails.contains(employeeDetail))
                continue;
            amexLaptopHoldersList.add(employeeDetail);
            employeeDetails.add(employeeDetail);
        }
        return generateReportList(amexLaptopHoldersList,null);
    }

    public  List<ReportResponseDTO> laptopReturnPerMonth(String month, String year ) throws IOException, RoleNotFoundException {
        List<AssetRelease> laptopReturnPerMonthList = assignAssetReleaseList();
        List<EmployeeDetail> employeeDetailList=new ArrayList<>();
        for (AssetRelease assetRelease : laptopReturnPerMonthList) {
            String returnDate = assetRelease.getPickupTimestamp();
            if(emptyCheckList.contains(returnDate)){
                continue;
            }
            String time="T09:00:00.000000000";
            String returnFinal=returnDate+time;
            if (returnDate != null) {
                LocalDateTime date = LocalDateTime.parse(returnFinal);
                Month returnMonth = date.getMonth();
                Integer returnYear = date.getYear();
                String stringReturnMonth = returnMonth.toString();
                if (Objects.equals(month, stringReturnMonth) && Objects.equals(year,returnYear.toString())) {
                    if(assetRelease.getAssetDetail()!=null) {
                        employeeDetailList.add(assetRelease.getAssetDetail().getAmexEmployeeDetail());
                    }

                }
            }
        }
        return generateStatusReportList(employeeDetailList,null,"Asset pickup completed");
    }
    public  List<ReportResponseDTO> incompleteAssetDetails() throws RoleNotFoundException {
        List<EmployeeDetail> IncompleteAssetDetailsList = new ArrayList<>();
        List<AssetDetail> assetDetailList=assignAssetList();
        List<EmployeeDetail> employeeDetails=new ArrayList<>();
        for(AssetDetail assetDetail:assetDetailList) {

                if (emptyCheckList.contains(assetDetail.getAssetMake())||
                        emptyCheckList.contains(assetDetail.getAssetModel())||
                        emptyCheckList.contains(assetDetail.getIssueDate()))  {
                    EmployeeDetail empAssetIncompleteDetails = assetDetail.getAmexEmployeeDetail();
                    if(employeeDetails.contains(empAssetIncompleteDetails))
                        continue;
                   IncompleteAssetDetailsList.add(empAssetIncompleteDetails);
                   employeeDetails.add(empAssetIncompleteDetails);
                }
            }
        return generateStatusReportList(IncompleteAssetDetailsList,null,"Asset details incomplete");
        }
    public  List<ReportResponseDTO> cidWithoutAsset() throws RoleNotFoundException {
        List<EmployeeDetail> cidWithoutAssetList = new ArrayList<>();
        List<EmployeeDetail> employeeDetailList=assignEmployeeList();
        for(EmployeeDetail employeeDetail:employeeDetailList) {
            if (!emptyCheckList.contains(employeeDetail.getAssociateAmexContractorId())&&
                    employeeDetail.getAssetsDetails().isEmpty())  {
                cidWithoutAssetList.add(employeeDetail);
            }
        }
        return generateReportList(cidWithoutAssetList,null);
    }

    public  List<ReportResponseDTO> laptopReturnPerYear(String year ) throws IOException, RoleNotFoundException {
        List<AssetRelease> employeeLaptopReturnDateList = assignAssetReleaseList();
        int totalCount=0;
        LinkedHashMap<String, Integer> monthCount = new LinkedHashMap<>();
        monthCount.put("JANUARY",0);
        monthCount.put("FEBRUARY",0);
        monthCount.put("MARCH",0);
        monthCount.put("APRIL",0);
        monthCount.put("MAY",0);
        monthCount.put("JUNE",0);
        monthCount.put("JULY",0);
        monthCount.put("AUGUST",0);
        monthCount.put("SEPTEMBER",0);
        monthCount.put("OCTOBER",0);
        monthCount.put("NOVEMBER",0);
        monthCount.put("DECEMBER",0);
        monthCount.put("YEAR", Integer.valueOf(year));
        monthCount.put("TOTAL COUNT",0);

        for (AssetRelease dat : employeeLaptopReturnDateList) {
            String returnDate = dat.getPickupTimestamp();
            if(emptyCheckList.contains(returnDate)){
                continue;
            }
            returnDate+="T09:00:00.000000000";
            LocalDateTime date = LocalDateTime.parse(returnDate);
            String userReturnMonth = String.valueOf(date.getMonth());
            String userReturnYear= String.valueOf(date.getYear());
            if (Objects.equals(year, userReturnYear)) {
                Integer count = monthCount.get(userReturnMonth);
                monthCount.put(userReturnMonth,count+1);
                totalCount++;
            }
        }
        monthCount.put("TOTAL COUNT",totalCount);
        return generateReportList(null,monthCount);
    }

        public  List<ReportOptions> reportList(){
            List<ReportOptions> reportOptionsList=new ArrayList<>();
            reportOptionsList.add(new ReportOptions(1,"Asset Issued-Onboarded", "List of Associates with AMEX asset but have not initiated asset return"));
            reportOptionsList.add(new ReportOptions(2,"Asset Release Requested", "List of Associates whose have requested for the asset release"));
            reportOptionsList.add(new ReportOptions(3,"Asset Pickup Requested", "List of Associates who have requested for asset pickup and have attached the evidence document"));
            reportOptionsList.add(new ReportOptions(4,"Asset Pickup Completed", "List of Associates whose asset pickup is completed and have attached the DW pickup receipt and Updated the Tracking number"));
            reportOptionsList.add(new ReportOptions(5,"Onboarded to Client Network", "List of Associates with/without AMEX Contractor ID"));
            reportOptionsList.add(new ReportOptions(6,"Client Asset Assignment List", "List of all the Client Assets"));
            reportOptionsList.add(new ReportOptions(7,"Assets Returned Per Month", "List of Associates who have returned their asset in a particular month"));
            reportOptionsList.add(new ReportOptions(8,"Incomplete Associate Asset Details", "List of Associates with incomplete asset details "));
            reportOptionsList.add(new ReportOptions(9,"Onboarded to Client Network without Client Asset", "List of Associates with AMEX Contractor ID but don't have AMEX asset"));
            reportOptionsList.add(new ReportOptions(10,"Laptops Returned Per Year", "List of Associates who have returned their asset in a particular year"));

            return reportOptionsList;
        }

    public List<ReportResponseDTO> generateReportList(List<EmployeeDetail> employeeDetails, LinkedHashMap<String,Integer> laptopReturnPerYear) {
        List<ReportResponseDTO> reportResponseDTOList =new ArrayList<>();
        if(laptopReturnPerYear!=null){
            ReportResponseDTO reportResponseDTO =new ReportResponseDTO();
            reportResponseDTO.setLaptopReturnPerYear(laptopReturnPerYear);
            return Collections.singletonList(reportResponseDTO);
        }
        else{
            employeeDetails.stream()
                    .filter(Objects::nonNull)
                    .forEach(employeeDetail -> {
                        ReportResponseDTO reportsDto= ReportResponseDTO.builder()
                                .associateId(employeeDetail.getAssociateId().toString())
                                .associateName(employeeDetail.getAssociateName())
                                .associateAmexContractorId(employeeDetail.getAssociateAmexContractorId())
                                .associateCTSEmailId(employeeDetail.getAssociateCTSEmailId())
                                .associateAmexEmailId(employeeDetail.getAssociateAmexEmailId())
                                .amexDirectorEmail(employeeDetail.getAmexDirectorEmail())
                                .city(employeeDetail.getCity())
                                .country(employeeDetail.getCountry())
                                .serviceLine(employeeDetail.getServiceLine())
                                .grade(employeeDetail.getGrade())
                                .businessUnit(employeeDetail.getBusinessUnit())
                                .percentAllocation(employeeDetail.getPercentAllocation().toString())
                                .billability(employeeDetail.getBillability())
                                .onboardingStatus(employeeDetail.getOnboardingStatus())
                                .projectId(employeeDetail.getProjectDetails().getProjectId().toString())
                                .projectName(employeeDetail.getProjectDetails().getProjectName())
                                .projectManagerEmpId(employeeDetail.getProjectDetails().getProjectManagerEmpId().toString())
                                .projectManagerName(employeeDetail.getProjectDetails().getProjectManagerName())
                                .projectStartDate(employeeDetail.getProjectDetails().getProjectStartDate())
                                .projectEndDate(employeeDetail.getProjectDetails().getProjectEndDate())
                                .ctsEPLName(employeeDetail.getProjectDetails().getCtsEPLName())
                                .ctsEPLId(employeeDetail.getProjectDetails().getCtsEPLId())
                                .assetCount(employeeDetail.getAssetCount())
                                .build();
                        if(employeeDetail.getAssetsDetails()!=null)
                        {
                            if(employeeDetail.getAssetsDetails().isEmpty()){
                                reportResponseDTOList.add(reportsDto);
                            }
                            for(AssetDetail assetDetail: employeeDetail.getAssetsDetails()) {
                                ReportResponseDTO addAsset= new ReportResponseDTO();
                                try {
                                    addAsset = (ReportResponseDTO) reportsDto.clone();
                                } catch (CloneNotSupportedException e) {
                                    throw new RuntimeException("Unable to clone object for adding multiple asset");
                                }

                                System.out.println("asset details: "+addAsset.getSerialNumber());
                                addAsset.setSerialNumber(assetDetail.getSerialNumber());
                                addAsset.setIssueDate(assetDetail.getIssueDate());
                                addAsset.setReleaseRequestedDate(assetDetail.getReturnDate());
                                addAsset.setStatus(assetDetail.getStatus());
                                addAsset.setTrackingNumber(assetDetail.getTrackingNumber());
                                addAsset.setCognizantAsset(assetDetail.getCognizantAsset());
                                addAsset.setAssetModel(assetDetail.getAssetMake());
                                addAsset.setAssetMake(assetDetail.getAssetMake());
                                if (assetReleaseRepository.findBySerialNumber(assetDetail.getSerialNumber()) != null) {
                                    AssetRelease assetRelease = assetReleaseRepository.findBySerialNumber(assetDetail.getSerialNumber());
                                    addAsset.setDWPickupRequestedDate(assetRelease.getRequestCreationTime());
                                    addAsset.setDWPickupDate(assetRelease.getPickupTimestamp());
                                }

                                reportResponseDTOList.add(addAsset);
                            }
                        }
                        else{
                        reportResponseDTOList.add(reportsDto);}
                    });
        return reportResponseDTOList;
        }
    }

    public List<ReportResponseDTO> generateStatusReportList(List<EmployeeDetail> employeeDetails, LinkedHashMap<String,Integer> laptopReturnPerYear, String assetStatus) {
        List<ReportResponseDTO> reportResponseDTOList = new ArrayList<>();

        if (laptopReturnPerYear != null) {
            ReportResponseDTO reportResponseDTO = new ReportResponseDTO();
            reportResponseDTO.setLaptopReturnPerYear(laptopReturnPerYear);
            return Collections.singletonList(reportResponseDTO);
        } else {
            employeeDetails.stream()
                    .filter(Objects::nonNull)
                    .forEach(employeeDetail -> {
                        ReportResponseDTO reportsDto = ReportResponseDTO.builder()
                                .associateId(employeeDetail.getAssociateId().toString())
                                .associateName(employeeDetail.getAssociateName())
                                .associateAmexContractorId(employeeDetail.getAssociateAmexContractorId())
                                .associateCTSEmailId(employeeDetail.getAssociateCTSEmailId())
                                .associateAmexEmailId(employeeDetail.getAssociateAmexEmailId())
                                .amexDirectorEmail(employeeDetail.getAmexDirectorEmail())
                                .city(employeeDetail.getCity())
                                .country(employeeDetail.getCountry())
                                .serviceLine(employeeDetail.getServiceLine())
                                .grade(employeeDetail.getGrade())
                                .businessUnit(employeeDetail.getBusinessUnit())
                                .percentAllocation(employeeDetail.getPercentAllocation().toString())
                                .billability(employeeDetail.getBillability())
                                .onboardingStatus(employeeDetail.getOnboardingStatus())
                                .projectId(employeeDetail.getProjectDetails().getProjectId().toString())
                                .projectName(employeeDetail.getProjectDetails().getProjectName())
                                .projectManagerEmpId(employeeDetail.getProjectDetails().getProjectManagerEmpId().toString())
                                .projectManagerName(employeeDetail.getProjectDetails().getProjectManagerName())
                                .projectStartDate(employeeDetail.getProjectDetails().getProjectStartDate())
                                .projectEndDate(employeeDetail.getProjectDetails().getProjectEndDate())
                                .ctsEPLName(employeeDetail.getProjectDetails().getCtsEPLName())
                                .ctsEPLId(employeeDetail.getProjectDetails().getCtsEPLId())
                                .assetCount(employeeDetail.getAssetCount())
                                .build();

                        if (employeeDetail.getAssetsDetails() != null) {
                            if (employeeDetail.getAssetsDetails().isEmpty()) {
                                reportResponseDTOList.add(reportsDto);
                            }

                            for (AssetDetail assetDetail : employeeDetail.getAssetsDetails()) {
                                if (assetStatus == null || assetStatus.equals(assetDetail.getStatus())) {
                                    ReportResponseDTO addAsset = new ReportResponseDTO();
                                    try {
                                        addAsset = (ReportResponseDTO) reportsDto.clone();
                                    } catch (CloneNotSupportedException e) {
                                        throw new RuntimeException("Unable to clone object for adding multiple assets");
                                    }

                                    System.out.println("asset details: "+addAsset.getSerialNumber());
                                    addAsset.setSerialNumber(assetDetail.getSerialNumber());
                                    addAsset.setIssueDate(assetDetail.getIssueDate());
                                    addAsset.setReleaseRequestedDate(assetDetail.getReturnDate());
                                    addAsset.setStatus(assetDetail.getStatus());
                                    addAsset.setTrackingNumber(assetDetail.getTrackingNumber());
                                    addAsset.setCognizantAsset(assetDetail.getCognizantAsset());
                                    addAsset.setAssetModel(assetDetail.getAssetMake());
                                    addAsset.setAssetMake(assetDetail.getAssetMake());
                                    if (assetReleaseRepository.findBySerialNumber(assetDetail.getSerialNumber()) != null) {
                                        AssetRelease assetRelease = assetReleaseRepository.findBySerialNumber(assetDetail.getSerialNumber());
                                        addAsset.setDWPickupRequestedDate(assetRelease.getRequestCreationTime());
                                        addAsset.setDWPickupDate(assetRelease.getPickupTimestamp());
                                    }

                                    reportResponseDTOList.add(addAsset);
                                }
                            }
                        } else {
                            reportResponseDTOList.add(reportsDto);
                        }
                    });
            return reportResponseDTOList;
        }
    }
    public List<ReportResponseDTO> generateOptions(List<ReportResponseDTO> reportResponseDTOList, int[] options) {
        List<ReportResponseDTO> finalReportList = new ArrayList<>();
        for (ReportResponseDTO reportResponseDTO : reportResponseDTOList){
            ReportResponseDTO report = new ReportResponseDTO();
            report.setAssetCount(reportResponseDTO.getAssetCount());
            report.setMultipleAsset(reportResponseDTO.getAssetCount()>1);
            for (int option : options) {
                switch (option) {
                    case 1 -> report.setAssociateId(reportResponseDTO.getAssociateId()==null?"N/A":reportResponseDTO.getAssociateId());
                    case 2 -> report.setAssociateName(reportResponseDTO.getAssociateName()==null?"N/A":reportResponseDTO.getAssociateName());
                    case 3 -> report.setAssociateAmexContractorId(reportResponseDTO.getAssociateAmexContractorId()==null?"N/A":reportResponseDTO.getAssociateAmexContractorId());
                    case 4 -> report.setAssociateAmexEmailId(reportResponseDTO.getAssociateAmexEmailId()==null?"N/A":reportResponseDTO.getAssociateAmexEmailId());
                    case 5 -> report.setAssociateCTSEmailId(reportResponseDTO.getAssociateCTSEmailId()==null?"N/A":reportResponseDTO.getAssociateCTSEmailId());
                    case 6 -> report.setAmexDirectorEmail(reportResponseDTO.getAmexDirectorEmail()==null?"N/A":reportResponseDTO.getAmexDirectorEmail());
                    case 7 -> report.setCity(reportResponseDTO.getCity()==null?"N/A":reportResponseDTO.getCity());
                    case 8 -> report.setCountry(reportResponseDTO.getCountry()==null?"N/A":reportResponseDTO.getCountry());
                    case 9 -> report.setServiceLine(reportResponseDTO.getServiceLine()==null?"N/A":reportResponseDTO.getServiceLine());
                    case 10 -> report.setGrade(reportResponseDTO.getGrade()==null?"N/A":reportResponseDTO.getGrade());
                    case 11 -> report.setBusinessUnit(reportResponseDTO.getBusinessUnit()==null?"N/A":reportResponseDTO.getBusinessUnit());
                    case 12 -> report.setPercentAllocation(reportResponseDTO.getPercentAllocation()==null?"N/A":reportResponseDTO.getPercentAllocation());
                    case 13 -> report.setBillability(reportResponseDTO.getBillability()==null?"N/A":reportResponseDTO.getBillability());
                    case 14 -> report.setProjectId(reportResponseDTO.getProjectId()==null?"N/A":reportResponseDTO.getProjectId());
                    case 15 -> report.setProjectName(reportResponseDTO.getProjectName()==null?"N/A":reportResponseDTO.getProjectName());
                    case 16 -> report.setProjectManagerEmpId(reportResponseDTO.getProjectManagerEmpId()==null?"N/A":reportResponseDTO.getProjectManagerEmpId());
                    case 17 -> report.setProjectManagerName(reportResponseDTO.getProjectManagerName()==null?"N/A":reportResponseDTO.getProjectManagerName());
                    case 18 -> report.setProjectStartDate(reportResponseDTO.getProjectStartDate()==null?"N/A":reportResponseDTO.getProjectStartDate());
                    case 19 -> report.setProjectEndDate(reportResponseDTO.getProjectEndDate()==null?"N/A":reportResponseDTO.getProjectEndDate());
                    case 20 -> report.setCtsEPLId(reportResponseDTO.getCtsEPLId()==null?"N/A":reportResponseDTO.getCtsEPLId());
                    case 21-> report.setCtsEPLName(reportResponseDTO.getCtsEPLName()==null?"N/A":reportResponseDTO.getCtsEPLName());
                    case 22 -> report.setSerialNumber(reportResponseDTO.getSerialNumber()==null?"N/A":reportResponseDTO.getSerialNumber());
                    case 23 -> report.setAssetMake(reportResponseDTO.getAssetMake()==null?"N/A":reportResponseDTO.getAssetMake());
                    case 24 -> report.setAssetModel(reportResponseDTO.getAssetModel()==null?"N/A":reportResponseDTO.getAssetModel());
                    case 25 -> report.setIssueDate(reportResponseDTO.getIssueDate()==null?"N/A":reportResponseDTO.getIssueDate());
                    case 26 -> report.setStatus(reportResponseDTO.getStatus()==null?"N/A":reportResponseDTO.getStatus());
                    case 27 -> report.setCognizantAsset(reportResponseDTO.getCognizantAsset()==null?"N/A":reportResponseDTO.getCognizantAsset());
                    case 28 -> report.setTrackingNumber(reportResponseDTO.getTrackingNumber()==null?"N/A":reportResponseDTO.getTrackingNumber());
                    case 29 -> report.setReleaseRequestedDate(reportResponseDTO.getReleaseRequestedDate()==null?"N/A":reportResponseDTO.getReleaseRequestedDate());
                    case 30 -> report.setDWPickupRequestedDate(reportResponseDTO.getDWPickupRequestedDate()==null?"N/A":reportResponseDTO.getDWPickupRequestedDate());
                    case 31 -> report.setDWPickupDate(reportResponseDTO.getDWPickupDate()==null?"N/A":reportResponseDTO.getDWPickupDate());
                    case 32 -> report.setOnboardingStatus(reportResponseDTO.getOnboardingStatus()==null?"N/A":reportResponseDTO.getOnboardingStatus());
                    default -> System.out.println("wrong choice!");
                }
            }
            finalReportList.add(report);
        }
    return finalReportList;
    }

    public List<ReportHeaders> initializeReportHeaders() {
        List<ReportHeaders> reportHeadersList=new ArrayList<>();
        reportHeadersList.add(new ReportHeaders(1,"Associate ID"));
        reportHeadersList.add(new ReportHeaders(2,"Associate Name"));
        reportHeadersList.add(new ReportHeaders(3,"Amex Contractor ID"));
        reportHeadersList.add(new ReportHeaders(4,"Amex Email ID"));
        reportHeadersList.add(new ReportHeaders(5,"Cognizant Email ID"));
        reportHeadersList.add(new ReportHeaders(6,"Amex Director Email ID"));
        reportHeadersList.add(new ReportHeaders(7,"City"));
        reportHeadersList.add(new ReportHeaders(8,"Country"));
        reportHeadersList.add(new ReportHeaders(9,"Service Line"));
        reportHeadersList.add(new ReportHeaders(10,"Grade"));
        reportHeadersList.add(new ReportHeaders(11,"Business Unit"));
        reportHeadersList.add(new ReportHeaders(12,"Percent Allocation"));
        reportHeadersList.add(new ReportHeaders(13,"Billability"));
        reportHeadersList.add(new ReportHeaders(14,"Project ID"));
        reportHeadersList.add(new ReportHeaders(15,"Project Name"));
        reportHeadersList.add(new ReportHeaders(16,"Project Manager ID"));
        reportHeadersList.add(new ReportHeaders(17,"Project Manager Name"));
        reportHeadersList.add(new ReportHeaders(18,"Project Start Date"));
        reportHeadersList.add(new ReportHeaders(19,"Project End Date"));
        reportHeadersList.add(new ReportHeaders(20,"EPL ID"));
        reportHeadersList.add(new ReportHeaders(21,"EPL Name"));
        reportHeadersList.add(new ReportHeaders(22,"Asset Serial Number"));
        reportHeadersList.add(new ReportHeaders(23,"Asset Make"));
        reportHeadersList.add(new ReportHeaders(24,"Asset Model"));
        reportHeadersList.add(new ReportHeaders(25,"Issue Date"));
        reportHeadersList.add(new ReportHeaders(26,"Status"));
        reportHeadersList.add(new ReportHeaders(27,"Cognizant Asset"));
        reportHeadersList.add(new ReportHeaders(28,"Tracking Number"));
        reportHeadersList.add(new ReportHeaders(29,"Release Requested Date"));
        reportHeadersList.add(new ReportHeaders(30,"Pickup Requested Date"));
        reportHeadersList.add(new ReportHeaders(31,"Pickup Date"));
        reportHeadersList.add(new ReportHeaders(32,"Onboarding Status"));
        return reportHeadersList;
    }

    public List<ReportHeaders> reportHeaders(int optionSelected,List<ReportHeaders> reportHeaders) throws Exception {
        List<Integer> options=new ArrayList<>();
        switch (optionSelected) {
            case 1 -> options.addAll(Arrays.asList(1,2,14,15,16,17));
            case 2 -> options.addAll(Arrays.asList(1,2,14,15,29));
            case 3 -> options.addAll(Arrays.asList(1,2,14,15,29,30));
            case 4 -> options.addAll(Arrays.asList(1,2,14,15,29,30,31));
            case 5 -> options.addAll(Arrays.asList(1,2,3,4,14,15,16,17));
            case 6 -> options.addAll(Arrays.asList(1,2,14,15,22,23,24));
            case 7 -> options.addAll(Arrays.asList(1,2,14,15,22,23,24,31));
            case 8 -> options.addAll(Arrays.asList(1,2,14,15,22,23,24,25));
            case 9 -> options.addAll(Arrays.asList(1,2,3,4,14,15));
            default -> throw new Exception("wrong report selected");
        }
        return reportHeaders.stream()
                .peek(reportHeaders1 -> {
                    if(options.contains(reportHeaders1.getNo()))
                        reportHeaders1.setDefaultSelect("selected");
                })
                .toList();

    }

    public byte[] downloadFileById(Long fileId,String type) throws Exception {
        if(type.equals("pdf")){
        return pdfReportRepository.downloadById(fileId);
        } else if (type.equals("excel")) {
            return excelReportRepository.downloadById(fileId);
        }
        else
            throw new Exception("Wrong choice");
    }
    public String getFileName(Long fileId,String type) throws Exception {
        if(type.equals("pdf")){
            return pdfReportRepository.getFileName(fileId);
        } else if (type.equals("excel")) {
            return excelReportRepository.getFileName(fileId);
        }
        else
            throw new Exception("Wrong choice");
    }


}




