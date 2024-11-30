package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.models.exceptions.EmployeeNotFoundException;
import com.cognizant.assettracker.models.entity.AssetDetail;
import com.cognizant.assettracker.repositories.AssetsRepository;
import com.cognizant.assettracker.services.AssetsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsDetailsServiceImpl implements AssetsDetailsService {

	@Autowired
	private AssetsRepository assetsRepository;

	public List<AssetDetail> getAllData() {
		return assetsRepository.findAll();
	}

	public AssetDetail getAssetsDetailsbyID(Long id) {
		return assetsRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found"));
	}

	public AssetDetail findBySerialNumber(String id) {
		AssetDetail assetDetail=new AssetDetail();
		assetDetail=assetsRepository.findBySerialNumber(id);
		if(assetDetail==null){
			throw new EmployeeNotFoundException("Associate not found with Asset: "+id);
		}
		else
			return assetDetail;
	}
	
}
