package com.maCarthy.test.mapper;

import java.util.List;
import java.util.Map;

import com.maCarthy.test.entity.TestEntity;

public interface TestMapper {

	/*public TestEntity queryTestById(Map<String, Object> map);
	
	public int insertTest(Map<String, Object> map);
	
	public int updateTest(Map<String, Object> map);
	
	public int deleteTest(Map<String, Object> map);*/
	
	public List<TestEntity> queryTestAll(Map<String, Object> map);
	public int create(Map<String, Object> map);
}
