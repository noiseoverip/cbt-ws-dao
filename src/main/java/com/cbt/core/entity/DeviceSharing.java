package com.cbt.core.entity;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Entity representing device sharing information
 * 
 * @author SauliusAlisauskas 2013-10-27 Initial version
 *
 */
public class DeviceSharing {
   private Long deviceId;
   private Long id;
   @JsonInclude(Include.NON_NULL)
   private User user;
   private Long userId;

   public Long getDeviceId() {
      return deviceId;
   }

   public Long getId() {
      return id;
   }

   public User getUser() {
      return user;
   }

   public Long getUserId() {
      return userId;
   }

   @Column(name = "device_sharing_device_id")
   public void setDeviceId(Long deviceId) {
      this.deviceId = deviceId;
   }

   @Column(name = "device_sharing_id")
   public void setId(Long id) {
      this.id = id;
   }

   public void setUser(User user) {
      this.user = user;
   }

   @Column(name = "device_sharing_user_id")
   public void setUserId(Long userId) {
      this.userId = userId;
   }

}
