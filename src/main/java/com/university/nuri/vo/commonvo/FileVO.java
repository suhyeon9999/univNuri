package com.university.nuri.vo.commonvo;

import org.springframework.web.multipart.MultipartFile;

public class FileVO {
	private String f_idx, f_name, f_size, f_date, f_type, f_old_name;
	MultipartFile file_name;
	public String getF_idx() {
		return f_idx;
	}
	public void setF_idx(String f_idx) {
		this.f_idx = f_idx;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
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
	public String getF_old_name() {
		return f_old_name;
	}
	public void setF_old_name(String f_old_name) {
		this.f_old_name = f_old_name;
	}
	public MultipartFile getFile_name() {
		return file_name;
	}
	public void setFile_name(MultipartFile file_name) {
		this.file_name = file_name;
	}
}
