package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.CheckUtil;
import com.javaweb.utils.ConnectJDBC;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	public void joinTable(Map<String, Object> params, StringBuilder sql) {
		String staffid = String.valueOf(params.get("staffid"));
		if (!CheckUtil.valueIsEmpty(staffid)) {
			sql.append(" inner join assignmentbuilding a on a.buildingid = b.id");
		}

		String typeCode = String.valueOf(params.get("typecode"));
		if (!CheckUtil.valueIsEmpty(typeCode)) {
			sql.append(" inner join buildingrenttype b2 on b2.buildingid = b.id");
			sql.append(" inner join renttype r on r.id = b2.renttypeid");
		}

		sql.append(" inner join rentarea r2 on r2.buildingid = b.id");
	}

	public void normalQuery(Map<String, Object> params, StringBuilder sql) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			if (!key.startsWith("area") && !key.equals("typecode") && !key.startsWith("rentprice")
					&& !CheckUtil.valueIsEmpty(value)) {
				if (CheckUtil.isNumber(value)) {
					sql.append(" and " + key + " = " + value + "");
				} else {
					sql.append(" and " + key + " like '%" + value + "%'");
				}
			}
		}
	}

	public void specialQuery(Map<String, Object> params, List<String> typecode, StringBuilder sql) {
		checkArea(params, sql);
		checkRentprice(params, sql);
		checkTypeCode(typecode, sql);
	}

	private void checkArea(Map<String, Object> params, StringBuilder sql) {
		String areaFrom = String.valueOf(params.get("areafrom"));
		String areaTo = String.valueOf(params.get("areato"));
		if (!CheckUtil.valueIsEmpty(areaFrom)) {
			sql.append(" and r2.value >= " + areaFrom + "");
		}
		if (!CheckUtil.valueIsEmpty(areaTo)) {
			sql.append(" and r2.value <= " + areaTo + "");
		}
	}

	private void checkRentprice(Map<String, Object> params, StringBuilder sql) {
		String rentpriceFrom = String.valueOf(params.get("rentpricefrom"));
		String rentpriceTo = String.valueOf(params.get("rentpriceto"));
		if (!CheckUtil.valueIsEmpty(rentpriceFrom)) {
			sql.append(" and b.rentprice >= " + rentpriceFrom + "");
		}
		if (!CheckUtil.valueIsEmpty(rentpriceTo)) {
			sql.append(" and b.rentprice <= " + rentpriceTo + "");
		}
	}

	private void checkTypeCode(List<String> typecode, StringBuilder sql) {
		Integer size = typecode.size();
		int flag = 0;
		String typeCode = String.valueOf(typecode);
		if (!CheckUtil.valueIsEmpty(typeCode)) {
			for (String item : typecode) {
				if (size == 1) {
					sql.append(" and r.code like '%" + item + "%'");
					return;
				}
				if (size > 1 && flag < 1) {
					sql.append(" and (r.code like '%" + item + "%'");
				} else {
					sql.append(" or r.code like '%" + item + "%'");
				}
				flag++;
			}
			if (flag > 0)
				sql.append(")");
		}
	}

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typecode) {
		// Select
		StringBuilder sql = new StringBuilder("select b.id,b.name,b.street,b.ward,b.numberofbasement,b.managername,"
				+ "b.managerphonenumber,b.floorarea,b.districtid,b.rentprice,b.servicefee,b.brokeragefee"
				+ " from building b");
		// from
		joinTable(params, sql);
		StringBuilder where = new StringBuilder(" where 1=1");
		// where
		sql.append(where);
		normalQuery(params, sql);
		specialQuery(params, typecode, sql);
		// group by
		StringBuilder groupBy = new StringBuilder(
				" group by b.id,b.name,b.street,b.ward,b.numberofbasement,b.managername,b.districtid,"
						+ "b.managerphonenumber,b.floorarea,b.rentprice,b.servicefee,b.brokeragefee");
		sql.append(groupBy);

		List<BuildingEntity> buildingEntities = new ArrayList<>();
		try (ResultSet rs = ConnectJDBC.executeQuery(sql.toString());) {
			while (rs.next()) { 
				BuildingEntity building = new BuildingEntity();
				building.setId(rs.getLong("b.id"));
				building.setName(rs.getString("b.name"));
				building.setStreet(rs.getString("b.street"));
				building.setWard(rs.getString("b.ward"));
				building.setDistrictid(rs.getLong("b.districtid"));
				building.setNumberofbasement(rs.getInt("b.numberofbasement"));
				building.setManagername(rs.getString("b.managername"));
				building.setManagerphonenumber(rs.getString("b.managerphonenumber"));
				building.setFloorarea(rs.getInt("b.floorarea"));
				building.setRentprice(rs.getInt("b.rentprice"));
				building.setServicefee(rs.getString("b.servicefee"));
				building.setBrokeragefee(rs.getString("b.brokeragefee"));
				buildingEntities.add(building);
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return buildingEntities;
	}

}
