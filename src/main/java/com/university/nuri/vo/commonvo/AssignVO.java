package com.university.nuri.vo.commonvo;

import java.util.Date;

public class AssignVO {
	private String assign_idx,
		lect_idx,
		assign_title,
		assign_content,
		upload_time,
	    end,
	    assign_active;
	private Date start_time, assign_due_date;

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getAssign_due_date() {
		return assign_due_date;
	}

	public void setAssign_due_date(Date assign_due_date) {
		this.assign_due_date = assign_due_date;
	}

	public String getAssign_idx() {
		return assign_idx;
	}

	public void setAssign_idx(String assign_idx) {
		this.assign_idx = assign_idx;
	}

	public String getLect_idx() {
		return lect_idx;
	}

	public void setLect_idx(String lect_idx) {
		this.lect_idx = lect_idx;
	}

	public String getAssign_title() {
		return assign_title;
	}

	public void setAssign_title(String assign_title) {
		this.assign_title = assign_title;
	}

	public String getAssign_content() {
		return assign_content;
	}

	public void setAssign_content(String assign_content) {
		this.assign_content = assign_content;
	}

	public String getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(String upload_time) {
		this.upload_time = upload_time;
	}


	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getAssign_active() {
		return assign_active;
	}

	public void setAssign_active(String assign_active) {
		this.assign_active = assign_active;
	}
}
