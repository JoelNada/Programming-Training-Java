package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.ReportHeaders;
import com.cognizant.assettracker.models.ReportOptions;
import com.cognizant.assettracker.models.dto.ReportResponseDTO;
import com.cognizant.assettracker.models.entity.AssetDetail;
import com.cognizant.assettracker.models.entity.AssetRelease;
import com.cognizant.assettracker.models.entity.EmployeeDetail;

import javax.management.relation.RoleNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public interface ReportService {
    public List<EmployeeDetail> assignEmployeeList() throws RoleNotFoundException;
    public List<AssetDetail> assignAssetList() throws RoleNotFoundException;

    public List<AssetRelease> assignAssetReleaseList() throws RoleNotFoundException;

    public List<ReportResponseDTO> onboardingReport() throws RoleNotFoundException;
    public List<ReportResponseDTO> ReleaseRequested() throws RoleNotFoundException;
    public  List<ReportResponseDTO> AssetReleaseRequested() throws RoleNotFoundException;
    public  List<ReportResponseDTO> AssetReturnCompleted() throws RoleNotFoundException;
    public List<ReportResponseDTO> amexCidFalse() throws RoleNotFoundException;
    public List<ReportResponseDTO> amexCidTrue() throws RoleNotFoundException;
    public  List<ReportResponseDTO> amexLaptopHolders() throws IOException, RoleNotFoundException;
    public  List<ReportResponseDTO> laptopReturnPerMonth(String month, String year ) throws IOException, RoleNotFoundException;
    public  List<ReportResponseDTO> incompleteAssetDetails() throws RoleNotFoundException;
    public  List<ReportResponseDTO> cidWithoutAsset() throws RoleNotFoundException;
    public  List<ReportResponseDTO> laptopReturnPerYear(String year ) throws IOException, RoleNotFoundException;
    public  List<ReportOptions> reportList();
    public List<ReportResponseDTO> generateReportList(List<EmployeeDetail> employeeDetails, LinkedHashMap<String,Integer> laptopReturnPerYear);
    public List<ReportResponseDTO> generateStatusReportList(List<EmployeeDetail> employeeDetails, LinkedHashMap<String,Integer> laptopReturnPerYear, String assetStatus);
    public List<ReportResponseDTO> generateOptions(List<ReportResponseDTO> reportResponseDTOList, int[] options);
    public List<ReportHeaders> initializeReportHeaders();
    public List<ReportHeaders> reportHeaders(int optionSelected,List<ReportHeaders> reportHeaders) throws Exception;
    public byte[] downloadFileById(Long fileId,String type) throws Exception;
    public String getFileName(Long fileId,String type) throws Exception;
}
