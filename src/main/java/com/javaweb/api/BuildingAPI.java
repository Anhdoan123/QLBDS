package com.javaweb.api;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.customEX.InvalidRequiredException;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;
@RestController                                                                                                                    
public class BuildingAPI {  
	@Autowired
	private BuildingService buildingService;
	
	@GetMapping("/test")                                                                                                           
	public List<BuildingDTO> test () {
		List<BuildingDTO> buildingDTOs = buildingService.findAll();
		return buildingDTOs;
	}
	
	public void valiRequired(BuildingDTO building) throws InvalidRequiredException {
	    if (building.getAdress() == null || building.getAdress().equals("")) {
	        throw new InvalidRequiredException("Chưa lấy đủ thông tin");
	    } 
	}
	
	
	
}    


