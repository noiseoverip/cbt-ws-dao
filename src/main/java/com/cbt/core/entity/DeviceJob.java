package com.cbt.core.entity;

import com.cbt.jooq.enums.DeviceJobDeviceJobStatus;
import com.google.common.base.Objects;

import javax.persistence.Column;

/**
 * Device job entity. Represents information describing a job(test) for specific device.
 *
 * @author SauliusAlisauskas 2013-03-24 Initial version
 */
public class DeviceJob {
   private Long deviceId;
   private Long id;
   private DeviceJobMetadata metadata = new DeviceJobMetadata();
   private DeviceJobDeviceJobStatus status;
   private Long testRunId;
   private TestScript testScript;
   private TestTarget testTarget;

   /**
    * Default constructor
    */
   public DeviceJob() {

   }

   public Long getDeviceId() {
      return deviceId;
   }

   public Long getId() {
      return id;
   }

   public DeviceJobMetadata getMetadata() {
      return metadata;
   }

   public DeviceJobDeviceJobStatus getStatus() {
      return status;
   }

   public Long getTestRunId() {
      return testRunId;
   }

   public TestScript getTestScript() {
      return testScript;
   }

   public TestTarget getTestTarget() {
      return testTarget;
   }

   @Column(name = "device_job_device_id")
   public void setDeviceId(Long deviceId) {
      this.deviceId = deviceId;
   }

   @Column(name = "device_job_id")
   public void setId(Long id) {
      this.id = id;
   }

   /**
    * Metadata is not mapped directly bu JOOQ since it contains JSON, therefore, it's mapped by ObjectMapper in
    * {@link DevicejobDao#mDeviceJobMapper}
    *
    * @param metadata
    */
   public void setMetadata(DeviceJobMetadata metadata) {
      this.metadata = metadata;
   }

   @Column(name = "device_job_status")
   public void setStatus(DeviceJobDeviceJobStatus status) {
      this.status = status;
   }

   @Column(name = "device_job_testrun_id")
   public void setTestRunId(Long testRunId) {
      this.testRunId = testRunId;
   }

   public void setTestScript(TestScript testScript) {
      this.testScript = testScript;
   }

   public void setTestTarget(TestTarget testTarget) {
      this.testTarget = testTarget;
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this.getClass()).add("id", getId()).add("deviceId", getDeviceId())
            .add("testRunId", getTestRunId()).add("testScript", getTestScript()).add("testTarget", getTestTarget())
            .add("status", getStatus()).add("meta", getMetadata()).toString();
   }
}
