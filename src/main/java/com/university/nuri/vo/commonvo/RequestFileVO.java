package com.university.nuri.vo.commonvo;

import org.springframework.web.multipart.MultipartFile;

public class RequestFileVO {
	private String req_f_idx, req_idx, req_f_name, req_f_old_name, req_f_size, req_f_date, req_f_type, req_f_order;
	MultipartFile req_file_name;
	
	public String getReq_f_idx() {
		return req_f_idx;
	}
	public void setReq_f_idx(String req_f_idx) {
		this.req_f_idx = req_f_idx;
	}
	public String getReq_idx() {
		return req_idx;
	}
	public void setReq_idx(String req_idx) {
		this.req_idx = req_idx;
	}
	public String getReq_f_name() {
		return req_f_name;
	}
	public void setReq_f_name(String req_f_name) {
		this.req_f_name = req_f_name;
	}
	public String getReq_f_old_name() {
		return req_f_old_name;
	}
	public void setReq_f_old_name(String req_f_old_name) {
		this.req_f_old_name = req_f_old_name;
	}
	public String getReq_f_size() {
		return req_f_size;
	}
	public void setReq_f_size(String req_f_size) {
		this.req_f_size = req_f_size;
	}
	public String getReq_f_date() {
		return req_f_date;
	}
	public void setReq_f_date(String req_f_date) {
		this.req_f_date = req_f_date;
	}
	public String getReq_f_type() {
		return req_f_type;
	}
	public void setReq_f_type(String req_f_type) {
		this.req_f_type = req_f_type;
	}
	public String getReq_f_order() {
		return req_f_order;
	}
	public void setReq_f_order(String req_f_order) {
		this.req_f_order = req_f_order;
	}
	public MultipartFile getReq_file_name() {
		return req_file_name;
	}
	public void setReq_file_name(MultipartFile req_file_name) {
		this.req_file_name = req_file_name;
	}
}
