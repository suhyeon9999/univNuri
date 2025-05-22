package com.university.nuri.vo.studentvo;

import org.springframework.web.multipart.MultipartFile;

public class SubmitFileVO {
	private String submit_f_idx, submit_f_name, submit_f_old_name, submit_f_size, submit_f_date, submit_f_type, submit_f_order, submit_idx;
	private MultipartFile submit_file_name;
	
	public String getSubmit_f_idx() {
		return submit_f_idx;
	}

	public void setSubmit_f_idx(String submit_f_idx) {
		this.submit_f_idx = submit_f_idx;
	}

	public String getSubmit_f_name() {
		return submit_f_name;
	}

	public void setSubmit_f_name(String submit_f_name) {
		this.submit_f_name = submit_f_name;
	}

	public String getSubmit_f_old_name() {
		return submit_f_old_name;
	}

	public void setSubmit_f_old_name(String submit_f_old_name) {
		this.submit_f_old_name = submit_f_old_name;
	}

	public MultipartFile getSubmit_file_name() {
		return submit_file_name;
	}

	public void setSubmit_file_name(MultipartFile submit_file_name) {
		this.submit_file_name = submit_file_name;
	}

	public String getSubmit_f_size() {
		return submit_f_size;
	}

	public void setSubmit_f_size(String submit_f_size) {
		this.submit_f_size = submit_f_size;
	}

	public String getSubmit_f_date() {
		return submit_f_date;
	}

	public void setSubmit_f_date(String submit_f_date) {
		this.submit_f_date = submit_f_date;
	}

	public String getSubmit_f_type() {
		return submit_f_type;
	}

	public void setSubmit_f_type(String submit_f_type) {
		this.submit_f_type = submit_f_type;
	}

	public String getSubmit_f_order() {
		return submit_f_order;
	}

	public void setSubmit_f_order(String submit_f_order) {
		this.submit_f_order = submit_f_order;
	}

	public String getSubmit_idx() {
		return submit_idx;
	}

	public void setSubmit_idx(String submit_idx) {
		this.submit_idx = submit_idx;
	}
	
}
