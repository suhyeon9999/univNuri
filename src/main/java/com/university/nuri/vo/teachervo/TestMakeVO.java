package com.university.nuri.vo.teachervo;

import java.sql.Time;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class TestMakeVO {
	private String test_make_idx, lect_idx, f_idx, test_subject;
	private int mid_final;
	private Date test_date;
	private Time teststart_time, testend_time;
	//db에 없는 내용 추가 
	private String midFinalName;
	
	public String getMidFinalName() {
		return midFinalName;
	}
	public void setMidFinalName(String midFinalName) {
		this.midFinalName = midFinalName;
	}
	public String getTest_make_idx() {
		return test_make_idx;
	}
	public void setTest_make_idx(String test_make_idx) {
		this.test_make_idx = test_make_idx;
	}
	public String getLect_idx() {
		return lect_idx;
	}
	public void setLect_idx(String lect_idx) {
		this.lect_idx = lect_idx;
	}
	public String getF_idx() {
		return f_idx;
	}
	public void setF_idx(String f_idx) {
		this.f_idx = f_idx;
	}
	public String getTest_subject() {
		return test_subject;
	}
	public void setTest_subject(String test_subject) {
		this.test_subject = test_subject;
	}
	public int getMid_final() {
		return mid_final;
	}
	public void setMid_final(int mid_final) {
		this.mid_final = mid_final;
	}
	public Date getTest_date() {
		return test_date;
	}
	public void setTest_date(Date test_date) {
		this.test_date = test_date;
	}
	public Time getTeststart_time() {
		return teststart_time;
	}
	public void setTeststart_time(Time teststart_time) {
		this.teststart_time = teststart_time;
	}
	public Time getTestend_time() {
		return testend_time;
	}
	public void setTestend_time(Time testend_time) {
		this.testend_time = testend_time;
	}
	
	
	
}
