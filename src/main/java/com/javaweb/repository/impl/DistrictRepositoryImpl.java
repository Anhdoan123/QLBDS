package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.utils.ConnectJDBC;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {

	@Override
	public DistrictEntity findNameById(long id) {
		String sql = "select name from district where id = " + id + "";
		DistrictEntity districtEntity = new DistrictEntity();
		try (ResultSet rs = ConnectJDBC.executeQuery(sql.toString());) {
			while (rs.next()) {
				districtEntity.setName(rs.getString("name"));
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return districtEntity;
	}
}
