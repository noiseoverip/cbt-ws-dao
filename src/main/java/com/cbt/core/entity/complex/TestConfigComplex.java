package com.cbt.core.entity.complex;

import java.util.Date;

import javax.persistence.Column;

import com.cbt.core.entity.TestProfile;
import com.cbt.core.entity.TestScript;
import com.cbt.core.entity.TestTarget;
import com.google.common.base.Objects;

/**
 * Entity representing test configuration data.
 * 
 * @author SauliusAlisauskas 2013-03-24 Initial version
 */
public class TestConfigComplex {	
	private Long id;
	private String name;
	private TestProfile testProfile;
	private TestScript testScript;
	private TestTarget testTarget;
	private Date updated;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public TestProfile getTestProfile() {
		return testProfile;
	}
	public TestScript getTestScript() {
		return testScript;
	}
	public TestTarget getTestTarget() {
		return testTarget;
	}
	public Date getUpdated() {
		return updated;
	}
	
	@Column(name = "test_config_id")
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "test_config_name")
	public void setName(String name) {
		this.name = name;
	}

	public void setTestProfile(TestProfile testProfile) {
		this.testProfile = testProfile;
	}

	public void setTestScript(TestScript testScript) {
		this.testScript = testScript;
	}

	public void setTestTarget(TestTarget testTarget) {
		this.testTarget = testTarget;
	}	
	
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this.getClass())
				.add("id", getId())
				.add("testProfile", getTestProfile())
				.add("testScript", getTestScript())
				.add("testTarget", getTestTarget())
				.toString();

	}
	
}
