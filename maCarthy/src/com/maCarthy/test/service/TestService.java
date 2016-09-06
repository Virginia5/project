/**
 * @author Virginia
 *
 */
package com.maCarthy.test.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maCarthy.test.dao.TestDao;
import com.maCarthy.test.entity.TestEntity;


@Service
public class TestService {
	
	@Autowired
	private TestDao testDao;
	public List<TestEntity> queryTestAll(Map<String, Object> map) {
		return testDao.queryTestAll(map);
	}
	public int create(Map<String, Object> map) {
		return testDao.create(map);
	}
	
	
	
}