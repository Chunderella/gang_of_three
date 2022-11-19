package com.goteatfproject.appgot.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Criteria {

  private String keyword;

  private int page;
  private int perPageNum;

  public int getPageStart() {
    return (this.page-1)*perPageNum;
  }

  public Criteria() {
    this.page = 1;
    this.perPageNum = 8;
  }

  public int getPage() {
    return page;
  }
  public void setPage(int page) {
    if(page <= 0) {
      this.page = 1;
    } else {
      this.page = page;
    }
  }
  public int getPerPageNum() {
    return perPageNum;
  }
  public void setPerPageNum(int pageCount) {
    int cnt = this.perPageNum;
    if(pageCount != cnt) {
      this.perPageNum = cnt;
    } else {
      this.perPageNum = pageCount;
    }
  }

  //현재 페이지의 게시글 시작 번호 = (현재 페이지 번호 - 1) * 페이지당 보여줄 게시글 갯수
//  getPerPageNum = (this.page - 1) * perPageNum

}