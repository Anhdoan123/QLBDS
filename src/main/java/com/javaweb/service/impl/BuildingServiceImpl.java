package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService{
	@Autowired
	private BuildingRepository buildingRepository;
	@Override
	public List<BuildingDTO> findAll(Map<String, Object> params, List<String> typecode) {
		List<BuildingEntity> buildingList = buildingRepository.findAll(params,typecode);
		List<BuildingDTO> buildings = new ArrayList<BuildingDTO>();
		for(BuildingEntity item : buildingList) {
			BuildingDTO building = new BuildingDTO();
			building.setName(item.getName());
			building.setAddress(item.getStreet()+ ", "+ item.getWard() + ", " + item.getDistrictname());
			building.setNumberOfBasemen(item.getNumberofbasement());
			building.setManagername(item.getManagername());
			building.setManagerphonenumber(item.getManagerphonenumber());
			building.setFloorarea(item.getFloorarea());
			building.setRentprice(item.getRentprice());
			building.setBrokeragefee(item.getBrokeragefee());
			building.setRentarea(item.getRentarea());
			buildings.add(building);
		}
		return buildings;
	}
	
	
}
