package com.university.nuri.vo.studentvo;

import java.util.Date;

public class SubmitVO {
	private String submit_idx, assign_idx, enroll_idx, submit_title, submit_content, submit_active;
	private Date submit_write_date, updated_at;

	
	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Date getSubmit_write_date() {
		return submit_write_date;
	}

	public void setSubmit_write_date(Date submit_write_date) {
		this.submit_write_date = submit_write_date;
	}

	public  String getSubmit_idx() {
		return submit_idx;
	}

	public void setSubmit_idx(String submit_idx) {
		this.submit_idx = submit_idx;
	}

	public String getAssign_idx() {
		return assign_idx;
	}

	public void setAssign_idx(String assign_idx) {
		this.assign_idx = assign_idx;
	}

	public String getEnroll_idx() {
		return enroll_idx;
	}

	public void setEnroll_idx(String enroll_idx) {
		this.enroll_idx = enroll_idx;
	}

	public String getSubmit_title() {
		return submit_title;
	}

	public void setSubmit_title(String submit_title) {
		this.submit_title = submit_title;
	}

	public String getSubmit_content() {
		return submit_content;
	}

	public void setSubmit_content(String submit_content) {
		this.submit_content = submit_content;
	}


	public String getSubmit_active() {
		return submit_active;
	}

	public void setSubmit_active(String submit_active) {
		this.submit_active = submit_active;
	}
	
	
}
