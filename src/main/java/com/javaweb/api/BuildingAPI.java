package com.javaweb.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;

	@GetMapping("/buildingJPA")
	public List<BuildingDTO> finAllBuildingJPA(@RequestParam(required = false) Map<String, Object> params,
			@RequestParam(name = "typecode", required = false) List<String> typecode) {
		List<BuildingDTO> buildingDTOs = buildingService.findAll(params, typecode);
		return buildingDTOs;
	}
	
	@GetMapping("/buildingSpringJPA")
	public List<BuildingDTO> findAllBuildingSpringJPA() {
		List<BuildingDTO> buildingDTOs = buildingService.findAll();
		return buildingDTOs;
	}
	
	@GetMapping("/buildingByNameSpringJPA")
	public List<BuildingDTO> findBuildingByNameSpringJPA(@RequestParam(name= "name") String name, @RequestParam(name= "managername") String managerName) {
		List<BuildingDTO> buildingDTOs = buildingService.findBuildingByNameAndManagerName(name,managerName);
		return buildingDTOs;
	}
	


}
