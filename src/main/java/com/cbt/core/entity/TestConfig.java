package com.cbt.core.entity;

import javax.persistence.Column;

import com.google.common.base.Objects;

/**
 * Entity representing test configuration data.
 *
 * @author SauliusAlisauskas 2013-03-24 Initial version
 */
public class TestConfig {
   private Long id;
   private String name;
   private Long testProfileId;
   private Long testScriptId;
   private Long testTargetId;
   private Long userId;

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof TestConfig) {
         TestConfig other = (TestConfig) obj;
         return this.getId().equals(other.getId()) && this.getName().equals(other.getName())
               && this.getTestProfileId().equals(other.getTestProfileId())
               && this.getTestScriptId().equals(other.getTestScriptId())
               && this.getTestTargetId().equals(other.getTestTargetId());
      }
      return false;
   }

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public Long getTestProfileId() {
      return testProfileId;
   }

   public Long getTestScriptId() {
      return testScriptId;
   }

   public Long getTestTargetId() {
      return testTargetId;
   }

   // TODO: set column name to testconfig_user_id
   public Long getUserId() {
      return userId;
   }

   @Column(name = "test_config_id")
   public void setId(Long id) {
      this.id = id;
   }

   @Column(name = "test_config_name")
   public void setName(String name) {
      this.name = name;
   }

   // TODO: should be testconfig_testprofile_id
   @Column(name = "test_profile_id")
   public void setTestProfileId(Long testProfileId) {
      this.testProfileId = testProfileId;
   }

   // TODO: should be testconfig_testscript_id
   @Column(name = "test_script_id")
   public void setTestScriptId(Long testScriptId) {
      this.testScriptId = testScriptId;
   }

   // TODO: should be testconfig_testtarget_id
   @Column(name = "test_target_id")
   public void setTestTargetId(Long testTargetId) {
      this.testTargetId = testTargetId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this.getClass()).add("id", this.id).add("testScriptId", testScriptId)
            .add("testTargetId", testTargetId).add("testProfileId", testProfileId).toString();
   }
}
