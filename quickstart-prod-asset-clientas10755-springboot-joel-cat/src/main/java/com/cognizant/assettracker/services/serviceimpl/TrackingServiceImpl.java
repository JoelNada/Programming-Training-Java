package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.models.Status;
import com.cognizant.assettracker.models.TrackingResponse;
import com.cognizant.assettracker.models.entity.AssetDetail;
import com.cognizant.assettracker.models.entity.AssetRelease;
import com.cognizant.assettracker.repositories.AssetsRepository;
import com.cognizant.assettracker.models.dto.EmployeeDocumentDTO;
import com.cognizant.assettracker.services.AssetReleaseService;
import com.cognizant.assettracker.services.EmployeeDocumentService;
import com.cognizant.assettracker.services.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackingServiceImpl implements TrackingService {
    @Autowired
    EmployeeDocumentService employeeDocumentService;
    @Autowired
    AssetReleaseService assetReleaseService;
    @Autowired
    AssetsRepository assetsRepository;

    public TrackingResponse track(String serialnumber)
    {
       if(serialnumber.equals("empty")||serialnumber==null||serialnumber=="empty")
        {

            Status status=new Status();
            status.setStatus("Asset not assigned");
            return new TrackingResponse(null,null,serialnumber,null,status);
        }
        else
        {
            AssetDetail assetBySerialNumber = assetsRepository.findBySerialNumber(serialnumber);
            if(assetBySerialNumber.getAssetMake()==null || assetBySerialNumber.getAssetMake().equals("")||assetBySerialNumber.getAssetModel()==null||assetBySerialNumber.getAssetModel().equals(""))
            {
                Status stat=new Status();
                stat.setStatus("Asset details incomplete");
                assetBySerialNumber.setStatus("Asset details incomplete");
                //assetsRepository.save(assetBySerialNumber);
                TrackingResponse res=new TrackingResponse(null,null,serialnumber,null,stat);
                return  res;
            }
            try
            {

                AssetRelease assetRelease = assetReleaseService.findBySerialNumber(serialnumber);
                List<EmployeeDocumentDTO> requestForm = employeeDocumentService.getFile(serialnumber, "requestForm");
                List<EmployeeDocumentDTO> pickupReceipt = employeeDocumentService.getFile(serialnumber, "pickupReceipt");
                String dwPickupRequested = assetRelease.getRequestCreationTime();
                String dwpickupdatecheck = assetRelease.getPickupTimestamp();
                String releaseDate = assetBySerialNumber.getReturnDate();
                String dwPickupDate = dwpickupdatecheck == null ? "" : dwpickupdatecheck;
                if(assetBySerialNumber.getAssetMake()==null || assetBySerialNumber.getAssetMake().equals("")||assetBySerialNumber.getAssetModel()==null||assetBySerialNumber.getAssetModel().equals(""))
                {
                    Status stat=new Status();
                    stat.setStatus("Asset details incomplete");
                    assetBySerialNumber.setStatus("Asset details incomplete");
                    //assetsRepository.save(assetBySerialNumber);
                    TrackingResponse res=new TrackingResponse(assetRelease.getRequestCreationTime(),assetRelease.getAssetReleaseReason(),serialnumber,assetRelease.getPickupTimestamp(),stat);
                    return  res;
                }
                if(!releaseDate.equals("") && !dwPickupRequested.equals("") && !dwPickupDate.equals(""))
                {
                    Status stat=new Status();
                    stat.setStatus("Asset pickup completed");
                    assetBySerialNumber.setStatus("Asset pickup completed");
                    //assetsRepository.save(assetBySerialNumber);
                    return new TrackingResponse(assetRelease.getRequestCreationTime(),assetRelease.getAssetReleaseReason(),serialnumber,assetRelease.getPickupTimestamp(),stat);
                }
                if(!releaseDate.equals("") && !dwPickupRequested.equals(""))
                {
                    ArrayList<String> dates=new ArrayList<>();
                    dates.add(releaseDate);

                    Status stat=new Status();
                    stat.setStatus("Asset pickup requested");
                    assetBySerialNumber.setStatus("Asset pickup requested");
                    //assetsRepository.save(assetBySerialNumber);
                    stat.setContent(dates);
                    return new TrackingResponse(assetRelease.getRequestCreationTime(),assetRelease.getAssetReleaseReason(),serialnumber,assetRelease.getPickupTimestamp(),stat);

                }
                if(!releaseDate.equals(""))
                {
                    ArrayList<String> dates=new ArrayList<>();
                    dates.add(releaseDate);
                    Status stat=new Status();
                    stat.setStatus("Asset release requested");
                    assetBySerialNumber.setStatus("Asset release requested");
                    //assetsRepository.save(assetBySerialNumber);
                    stat.setContent(dates);
                    return new TrackingResponse(assetRelease.getRequestCreationTime(),assetRelease.getAssetReleaseReason(),serialnumber,assetRelease.getPickupTimestamp(),stat);

                }

                if(serialnumber!=null && releaseDate.equals("")&& dwPickupRequested.equals("")&& dwPickupDate.equals("")){
                    Status stat=new Status();
                    stat.setStatus("Asset Assigned");
                    assetBySerialNumber.setStatus("Asset Assigned");
                    //assetsRepository.save(assetBySerialNumber);
                    return new TrackingResponse(null,null,serialnumber,null,stat);
                }
            }
            catch (Exception e)
            {
                //TODO Exception
                System.out.println(e.getMessage());
                if(serialnumber!=null || serialnumber!="")
                {
                    assetBySerialNumber.setStatus("Asset Assigned");
                    //assetsRepository.save(assetBySerialNumber);
                }


            }
        }
        Status stat=new Status();
        stat.setStatus("Asset Assigned");

        return new TrackingResponse(null,null,serialnumber,null,stat);

    }
}

