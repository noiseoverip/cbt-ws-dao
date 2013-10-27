package com.cbt.core.entity;

import java.util.Date;

import com.cbt.jooq.enums.DeviceState;
import com.google.common.base.Objects;

/**
 * Device object entity
 *
 * @author SauliusAlisauskas
 */
public class Device {
   private Long deviceOsId;
   private Long deviceTypeId;
   private String deviceUniqueId;
   private Long id;
   private boolean owner;
   private Long ownerId;
   private String serialNumber;
   private DeviceState state;
   private String title;
   private Date updated;

   @Override
   public boolean equals(Object object) {
      if (null != object && object instanceof Device) {
         Device other = (Device) object;
         if (getId().equals(other.getId()) && getDeviceOsId().equals(other.getDeviceOsId())
               && getDeviceTypeId().equals(other.getDeviceTypeId())) {
            return true;
         }
      }
      return false;
   }

   public Long getDeviceOsId() {
      return deviceOsId;
   }

   public Long getDeviceTypeId() {
      return deviceTypeId;
   }

   public String getDeviceUniqueId() {
      return deviceUniqueId;
   }

   public Long getId() {
      return id;
   }

   public Long getOwnerId() {
      return ownerId;
   }

   public String getSerialNumber() {
      return serialNumber;
   }

   public DeviceState getState() {
      return state;
   }

   public String getTitle() {
      return title;
   }

   public Date getUpdated() {
      return updated;
   }

   public boolean isOwner() {
      return owner;
   }

   public void setDeviceOsId(Long deviceOsId) {
      this.deviceOsId = deviceOsId;
   }

   public void setDeviceTypeId(Long deviceTypeId) {
      this.deviceTypeId = deviceTypeId;
   }

   public void setDeviceUniqueId(String deviceUniqueId) {
      this.deviceUniqueId = deviceUniqueId;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setOwner(boolean owner) {
      this.owner = owner;
   }

   public void setOwnerId(Long ownerId) {
      this.ownerId = ownerId;
   }

   public void setSerialNumber(String serialNumber) {
      this.serialNumber = serialNumber;
   }

   public void setState(DeviceState state) {
      this.state = state;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public void setUpdated(Date updated) {
      this.updated = updated;
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this.getClass()).add("id", getId()).add("title", title).add("serial", serialNumber)
            .add("state", state).add("deviceTypeId", deviceTypeId).add("owner_id", getOwnerId())
            .add("lastUpdated", updated).toString();
   }
}
