package com.javaweb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long>{
//	List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode);
	List<BuildingEntity> findByNameContainingAndManagernameContaining(String name,String managerName);
}
