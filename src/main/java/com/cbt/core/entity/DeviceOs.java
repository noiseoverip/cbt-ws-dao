package com.cbt.core.entity;

import javax.persistence.Column;

/**
 * Device Operating system (OS) entity
 *
 * @author SauliusAlisauskas 2013-10-27 Initial version
 */
public class DeviceOs {
   private Long id;
   private String name;

   public Long getId() {
      return id;
   }

   @Column(name = "device_os_id")
   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   @Column(name = "device_os_name")
   public void setName(String name) {
      this.name = name;
   }
}
