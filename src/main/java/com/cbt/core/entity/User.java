package com.cbt.core.entity;

/**
 * Class for storing user variables
 *
 * @author SauliusAlisauskas
 */
public class User {

   private String name;
   private Long id;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
