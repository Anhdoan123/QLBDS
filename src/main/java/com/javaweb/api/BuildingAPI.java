package com.javaweb.api;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
	public List<BuildingDTO> test (@RequestParam Map<String, Object> params, List<String> typeCode) {
		List<BuildingDTO> buildingDTOs = buildingService.findAll(params,typeCode);
		return buildingDTOs;
	}
	
	
	
	
	
}    


