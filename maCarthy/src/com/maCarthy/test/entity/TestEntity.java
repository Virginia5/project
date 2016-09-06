/**
 * @author Virginia
 *
 */
package com.maCarthy.test.entity;

import java.io.Serializable;

public class TestEntity implements Serializable {
	
	private static final long serialVersionUID = -4425216256101673776L;
	
	private String test;

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

}