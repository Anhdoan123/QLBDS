package com.javaweb.repository.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.utils.ConnectJDBC;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository{

	@Override
	public List<RentAreaEntity> findValueIsBuildingId(long id) {
		String sql = "select value from rentarea where buildingid = "+id+"";
		
		List<RentAreaEntity> rentareas = new ArrayList<RentAreaEntity>();
		try(ResultSet rs = ConnectJDBC.executeQuery(sql)) {
			while(rs.next()) {
				RentAreaEntity rentarea = new RentAreaEntity();
				rentarea.setValue(rs.getString("value"));
				rentareas.add(rentarea);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rentareas;
	}
	
}
