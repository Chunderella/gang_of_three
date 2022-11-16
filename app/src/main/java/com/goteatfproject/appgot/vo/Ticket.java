package com.goteatfproject.appgot.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class Ticket {
  private int ticketNo;
  private int memberNo;
  private int eventNo;
  private String  pay;
  private Date  paydate;
  private int  paycount;
  private boolean  cancel;
  private int price;






}
