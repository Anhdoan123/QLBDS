package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.model.errorDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository{
	static final String url = "jdbc:mysql://localhost:3306/Building_DB";
	static final String user = "root";
	static final String pass = "root";
	@Override
	public List<BuildingEntity> findAll() {
		String sql = "select * from Building";
		List<BuildingEntity> buildingEntities = new ArrayList<>();
		try(	Connection conn = DriverManager.getConnection(url,user,pass);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while(rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setId(rs.getInt("id"));
				building.setDistrict(rs.getString("district"));
				building.setWard(rs.getString("ward"));
				buildingEntities.add(building);
			}
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return buildingEntities;
	}

}
