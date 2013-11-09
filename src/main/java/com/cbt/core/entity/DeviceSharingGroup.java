package com.cbt.core.entity;

import javax.persistence.Column;

/**
 * Entity representing device sharing information
 *
 * @author SauliusAlisauskas 2013-11-08 Initial version
 *
 */
public class DeviceSharingGroup {
   private Long id;
   private Long userGroupId;

   public Long getUserGroupId() {
      return userGroupId;
   }

   @Column(name = "device_sharing_group_group_id")
   public void setUserGroupId(Long userGroupId) {
      this.userGroupId = userGroupId;
   }

   public Long getId() {
      return id;
   }

   @Column(name = "device_sharing_group_id")
   public void setId(Long id) {
      this.id = id;
   }
}
