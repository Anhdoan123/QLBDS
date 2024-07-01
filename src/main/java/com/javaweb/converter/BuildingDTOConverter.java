package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;

@Component
public class BuildingDTOConverter {
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private RentAreaRepository areaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		BuildingDTO building = modelMapper.map(item, BuildingDTO.class);
		DistrictEntity districtEntity = districtRepository.findNameById(item.getDistrictid());
		building.setAddress(item.getStreet() + ", " + item.getWard() + ", " + districtEntity.getName());
		building.setRentprice(item.getRentprice() + " triệu/m2");
		List<RentAreaEntity> listArea= areaRepository.findValueIsBuildingId(item.getId());
		String listAreaString = listArea.stream().map(i -> i.getValue().toString()).collect(Collectors.joining(","));
		building.setRentarea(listAreaString);
//		
//		building.setName(item.getName());
//		building.setNumberOfBasemen(item.getNumberofbasement());
//		building.setManagername(item.getManagername());
//		building.setManagerphonenumber(item.getManagerphonenumber());
//		building.setFloorarea(item.getFloorarea());
//		building.setBrokeragefee(item.getBrokeragefee());
		
		return building;
	}
}
