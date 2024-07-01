package com.javaweb.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;

	@GetMapping("/test")
	public List<BuildingDTO> test(@RequestParam(required = false) Map<String, Object> params,
			@RequestParam(name = "typecode", required = false) List<String> typecode) {
		if (typecode == null) { 
			typecode = new ArrayList<String>();
		}
		List<BuildingDTO> buildingDTOs = buildingService.findAll(params, typecode);
		return buildingDTOs;
	}

}
