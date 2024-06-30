package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.Until.checkUntil;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static final String url = "jdbc:mysql://localhost:3306/estatebasic";
	static final String user = "root";
	static final String pass = "root";

	public void joinTable(Map<String, Object> params, StringBuilder sql) {
		String staffid = String.valueOf(params.get("staffid"));
		if (!checkUntil.valueIsEmpty(staffid)) {
			sql.append(" inner join assignmentbuilding a on a.buildingid = b.id");
		}

		String typeCode = String.valueOf(params.get("typecode"));
		if (!checkUntil.valueIsEmpty(typeCode)) {
			sql.append(" inner join buildingrenttype b2 on b2.buildingid = b.id inner join renttype r on r.id = b2.renttypeid");
		}

		sql.append(" inner join rentarea r2 on r2.buildingid = b.id ");

		sql.append(" inner join district d on d.id = b.districtid");
	}

	public void normalQuery(Map<String, Object> params, StringBuilder sql) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			if (!key.startsWith("area") && !key.equals("typecode") &&  !key.startsWith("rentprice") && !checkUntil.valueIsEmpty(value)) {
				if (checkUntil.isNumber(value)) {
					sql.append(" and "+key+" = "+value+"");
				}
				else {
					sql.append(" and "+key+" like '%"+value+"%'");
				}
			}
		}
	}
	
	public void specialQuery(Map<String, Object> params,List<String> typecode, StringBuilder sql) {
		checkArea(params, sql);
		checkRentprice(params,sql);
		checkTypeCode(typecode, sql);
	}
	
	private void checkArea(Map<String, Object> params,StringBuilder sql) {
		String areaFrom = String.valueOf(params.get("areafrom"));
		String areaTo = String.valueOf(params.get("areato"));
		if(!checkUntil.valueIsEmpty(areaFrom)) {
			sql.append(" and r2.value >= "+areaFrom+"");
		}
		if(!checkUntil.valueIsEmpty(areaTo)) {
			sql.append(" and r2.value <= "+areaTo+"");
		}
	}
	
	private void checkRentprice(Map<String, Object> params,StringBuilder sql) {
		String rentpriceFrom = String.valueOf(params.get("rentpricefrom"));
		String rentpriceTo = String.valueOf(params.get("rentpriceto"));
		if(!checkUntil.valueIsEmpty(rentpriceFrom)) {
			sql.append(" and b.rentprice >= "+rentpriceFrom+"");
		}
		if(!checkUntil.valueIsEmpty(rentpriceTo)) {
			sql.append(" and b.rentprice <= "+rentpriceTo+"");
		}
	}

	private void checkTypeCode(List<String> typecode, StringBuilder sql) {
		Integer size = typecode.size();
		int flag = 0;
		String typeCode = String.valueOf(typecode);
		if (!checkUntil.valueIsEmpty(typeCode)) {
			for (String item : typecode) {
				if (size == 1) {
					sql.append(" and r.code like '%" + item + "%'");
					return;
				}
				if (size > 1 && flag < 1) {
					sql.append(" and (r.code like '%" + item + "%'");
				}
				else {
					sql.append(" or r.code like '%" + item + "%'");
				}
				flag++;
			}
			if(flag > 0) sql.append(")");
		}
	}
	
	
	

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typecode) {
		//Select
		StringBuilder sql = new StringBuilder("select b.name,b.street,b.ward,d.name,b.numberofbasement,b.managername,"
				+ "b.managerphonenumber,b.floorarea,b.rentprice,b.servicefee,b.brokeragefee,"
				+ "GROUP_CONCAT(DISTINCT r2.value ) 'rentarea' from building b");
		//from
		joinTable(params, sql);
		StringBuilder where = new StringBuilder(" where 1=1");
		//where
		sql.append(where);
		normalQuery(params, sql);
		specialQuery(params,typecode,sql);
		//group by
		StringBuilder groupBy = new StringBuilder(" group by b.name,b.street,b.ward,d.name,b.numberofbasement,b.managername,"
				+ "b.managerphonenumber,b.floorarea,b.rentprice,b.servicefee,b.brokeragefee");
		sql.append(groupBy);
		
		
		List<BuildingEntity> buildingEntities = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			while (rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setName(rs.getString("b.name"));
				building.setStreet(rs.getString("b.street"));
				building.setWard(rs.getString("b.ward"));
				building.setDistrictname(rs.getString("d.name"));
				building.setNumberofbasement(rs.getInt("b.numberofbasement"));
				building.setManagername(rs.getString("b.managername"));
				building.setManagerphonenumber(rs.getString("b.managerphonenumber"));
				building.setFloorarea(rs.getInt("b.floorarea"));
				building.setRentprice(rs.getInt("b.rentprice"));
				building.setServicefee(rs.getString("b.servicefee"));
				building.setBrokeragefee(rs.getString("b.brokeragefee"));
				building.setRentarea(rs.getString("rentarea"));
				buildingEntities.add(building);
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return buildingEntities;
	}

}
