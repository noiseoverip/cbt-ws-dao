package com.cbt.core.entity;

import javax.persistence.Column;

/**
 * Device type entity class
 *
 * @author SauliusAlisauskas 2013-03-22 Initial version
 */
public class DeviceType {
   public Long id;
   private String manufacture;
   private String model;

   public DeviceType() {
   }

   public DeviceType(String theManufacture, String theModel) {
      manufacture = theManufacture;
      model = theModel;
   }

   public Long getId() {
      return id;
   }

   public String getManufacture() {
      return manufacture;
   }

   public String getModel() {
      return model;
   }

   @Column(name = "device_type_id")
   public void setId(Long id) {
      this.id = id;
   }

   @Column(name = "device_type_manufacture")
   public void setManufacture(String manufacture) {
      this.manufacture = manufacture;
   }

   @Column(name = "device_type_model")
   public void setModel(String model) {
      this.model = model;
   }
}
