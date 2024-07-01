package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.DistrictEntity;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {
	static final String url = "jdbc:mysql://localhost:3306/estatebasic";
	static final String user = "root";
	static final String pass = "root";

	@Override
	public DistrictEntity findNameById(long id) {
		String sql = "select name from district where id = " + id + "";
		DistrictEntity districtEntity = new DistrictEntity();
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			while (rs.next()) {
				districtEntity.setName(rs.getString("name"));
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return districtEntity;
	}
}
