package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.HomePageDTO;
import com.cognizant.assettracker.models.entity.EPLDetails;

import java.util.List;

public interface EPLService {

    public List<EPLDetails> viewAll();

    public void addEpl(EPLDetails newEpL);

    public void deleteEpl(String eplID);

    public List<HomePageDTO> getEmpBydto(String eplId);


}
