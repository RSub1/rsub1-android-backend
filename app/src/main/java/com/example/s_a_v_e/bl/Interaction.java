package com.example.s_a_v_e.bl;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Interaction {
  private String id;
  private Date exposureStart;
  private Date exposureEnd;

  public Interaction(String id){
    this.id = id;
    exposureStart = Calendar.getInstance().getTime();
  }

  public void end(){
    exposureEnd = Calendar.getInstance().getTime();
  }

  public boolean isEnded(){
    return exposureEnd != null;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getExposureStart() {
    return exposureStart;
  }

  public void setExposureStart(Date exposureStart) {
    this.exposureStart = exposureStart;
  }

  public Date getExposureEnd() {
    return exposureEnd;
  }

  public void setExposureEnd(Date exposureEnd) {
    this.exposureEnd = exposureEnd;
  }
}
