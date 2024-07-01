package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private DistrictRepository districtRepository;

	@Override
	public List<BuildingDTO> findAll(Map<String, Object> params, List<String> typecode) {
		List<BuildingEntity> buildingList = buildingRepository.findAll(params, typecode);
		List<BuildingDTO> buildings = new ArrayList<BuildingDTO>();
		for (BuildingEntity item : buildingList) {
			BuildingDTO building = new BuildingDTO();
			DistrictEntity districtEntity = districtRepository.findNameById(item.getDistrictid());
			building.setName(item.getName());
			building.setAddress(item.getStreet() + ", " + item.getWard() + ", " + districtEntity.getName());
			building.setNumberOfBasemen(item.getNumberofbasement());
			building.setManagername(item.getManagername());
			building.setManagerphonenumber(item.getManagerphonenumber());
			building.setFloorarea(item.getFloorarea());
			building.setRentprice(item.getRentprice() + " triệu/m2");
			building.setBrokeragefee(item.getBrokeragefee());
			building.setRentarea(item.getRentarea());
			buildings.add(building);
		}
		return buildings;
	}

}
