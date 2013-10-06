package com.cbt.core.entity;

import com.cbt.core.utils.Utils;

import java.util.Arrays;


public class DeviceJobMetadata {
   private String[] testClasses;

   public String[] getTestClasses() {
      return testClasses;
   }

   public void setTestClasses(String[] testClasses) {
      this.testClasses = testClasses;
   }

   @Override
   public String toString() {
      return Utils.toString("DeviceJobMetadata", "testclasses", Arrays.toString(testClasses));
   }

}
