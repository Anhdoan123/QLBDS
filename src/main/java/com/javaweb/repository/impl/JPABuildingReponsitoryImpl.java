package com.javaweb.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.CheckUtil;


@Repository
@Primary
public class JPABuildingReponsitoryImpl implements BuildingRepository{
	@PersistenceContext
	private EntityManager entityManager;
	
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
		String typeCode = String.valueOf(typecode);
		System.out.println("asdasd: " + typeCode);
		if (!CheckUtil.valueIsEmpty(typeCode)) {
			sql.append(" and(");
			String sqlQ = typecode.stream().map(item -> "r.code like '%"+item+"%'").collect(Collectors.joining(" or "));
			sql.append(sqlQ);
			sql.append(")");
		}
	}

	
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typecode) {
		// Select
		StringBuilder sql = new StringBuilder("select b.* from building b");
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
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();
	}

}
