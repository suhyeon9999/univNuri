package com.university.nuri.vo.commonvo;

import org.springframework.web.multipart.MultipartFile;

public class AssignFileVO {
	private String assign_f_idx, assign_idx, assign_f_name, assign_f_old_name, f_size, f_date, f_type, assign_f_order;
	MultipartFile assign_file_name;
	public String getAssign_f_idx() {
		return assign_f_idx;
	}
	public void setAssign_f_idx(String assign_f_idx) {
		this.assign_f_idx = assign_f_idx;
	}
	public String getAssign_idx() {
		return assign_idx;
	}
	public void setAssign_idx(String assign_idx) {
		this.assign_idx = assign_idx;
	}
	public String getAssign_f_name() {
		return assign_f_name;
	}
	public void setAssign_f_name(String assign_f_name) {
		this.assign_f_name = assign_f_name;
	}
	public String getAssign_f_old_name() {
		return assign_f_old_name;
	}
	public void setAssign_f_old_name(String assign_f_old_name) {
		this.assign_f_old_name = assign_f_old_name;
	}
	public String getF_size() {
		return f_size;
	}
	public void setF_size(String f_size) {
		this.f_size = f_size;
	}
	public String getF_date() {
		return f_date;
	}
	public void setF_date(String f_date) {
		this.f_date = f_date;
	}
	public String getF_type() {
		return f_type;
	}
	public void setF_type(String f_type) {
		this.f_type = f_type;
	}
	public String getAssign_f_order() {
		return assign_f_order;
	}
	public void setAssign_f_order(String assign_f_order) {
		this.assign_f_order = assign_f_order;
	}
	public MultipartFile getAssign_file_name() {
		return assign_file_name;
	}
	public void setAssign_file_name(MultipartFile assign_file_name) {
		this.assign_file_name = assign_file_name;
	}
	
}
