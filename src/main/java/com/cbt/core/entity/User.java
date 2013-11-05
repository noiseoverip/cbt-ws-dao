package com.cbt.core.entity;

import javax.persistence.Column;

/**
 * Class for storing user variables
 *
 * @author SauliusAlisauskas
 */
public class User {

   private String name;
   private Long id;
   private String email;

   public String getEmail() {
      return email;
   }

   @Column(name = "user_email")
   public void setEmail(String email) {
      this.email = email;
   }

   public Long getId() {
      return id;
   }

   @Column(name = "user_id")
   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   @Column(name = "user_name")
   public void setName(String name) {
      this.name = name;
   }
}
