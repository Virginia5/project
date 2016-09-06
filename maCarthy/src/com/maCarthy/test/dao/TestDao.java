/**
 * @author Virginia
 *
 */
package com.maCarthy.test.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.maCarthy.test.entity.TestEntity;
import com.maCarthy.test.mapper.TestMapper;

@Repository
public class TestDao {
	@Autowired
	private TestMapper testMapper;
	
	public List<TestEntity> queryTestAll(Map<String, Object> map){
		return testMapper.queryTestAll(map);
	}
	public int create(Map<String, Object> map){
		return testMapper.create(map);
	}
}