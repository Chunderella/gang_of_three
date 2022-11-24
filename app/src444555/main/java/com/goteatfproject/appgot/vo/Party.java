package com.goteatfproject.appgot.vo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @Setter @ToString
public class Party {

//메인 목록을 디폴트로 출력하기 위해 추가함
  Party() {
    this.meal=null;
  }
  private int no; 
  private String meal;
  private String food;
  private String title;
  private String content;
//  private String nick;
  private String gender;
  private int max;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private Date time; // 모임시간

  private int age;
  private int limit;
  private String location;
  private String post;
  private String address;
  private int viewCnt;
  private boolean pub;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createDate;
  private String thumbnail;

  private Member writer;

  private List<AttachedFile> attachedFiles;

  private Comment commentList;

}