package com.university.nuri.vo.commonvo;

public class AccessVO {
	private String acc_idx,
				   admin_idx,
				   a_grade,
				   acc_info_update,
				   acc_info_delete,
				   acc_user_insert,
				   acc_lect_insert,
				   acc_enroll_insert,
				   acc_user_search,
				   acc_lect_search,
				   acc_enroll_search,
				   acc_user_update,
				   acc_lect_update,
				   acc_enroll_update,
				   acc_user_delete,
				   acc_lect_delete,
				   acc_enroll_delete;

	private String sanitize(String value) {
		if (value == null) return "0";
		return value.replace(",", "").trim();
	}

	public String getAcc_idx() {
		return acc_idx;
	}

	public void setAcc_idx(String acc_idx) {
		this.acc_idx = acc_idx;
	}

	public String getAdmin_idx() {
		return admin_idx;
	}

	public void setAdmin_idx(String admin_idx) {
		this.admin_idx = sanitize(admin_idx);
	}

	public String getA_grade() {
		return a_grade;
	}

	public void setA_grade(String a_grade) {
		this.a_grade = sanitize(a_grade);
	}

	public String getAcc_info_update() {
		return acc_info_update;
	}

	public void setAcc_info_update(String acc_info_update) {
		this.acc_info_update = sanitize(acc_info_update);
	}

	public String getAcc_info_delete() {
		return acc_info_delete;
	}

	public void setAcc_info_delete(String acc_info_delete) {
		this.acc_info_delete = sanitize(acc_info_delete);
	}

	public String getAcc_user_insert() {
		return acc_user_insert;
	}

	public void setAcc_user_insert(String acc_user_insert) {
		this.acc_user_insert = sanitize(acc_user_insert);
	}

	public String getAcc_lect_insert() {
		return acc_lect_insert;
	}

	public void setAcc_lect_insert(String acc_lect_insert) {
		this.acc_lect_insert = sanitize(acc_lect_insert);
	}

	public String getAcc_enroll_insert() {
		return acc_enroll_insert;
	}

	public void setAcc_enroll_insert(String acc_enroll_insert) {
		this.acc_enroll_insert = sanitize(acc_enroll_insert);
	}

	public String getAcc_user_search() {
		return acc_user_search;
	}

	public void setAcc_user_search(String acc_user_search) {
		this.acc_user_search = sanitize(acc_user_search);
	}

	public String getAcc_lect_search() {
		return acc_lect_search;
	}

	public void setAcc_lect_search(String acc_lect_search) {
		this.acc_lect_search = sanitize(acc_lect_search);
	}

	public String getAcc_enroll_search() {
		return acc_enroll_search;
	}

	public void setAcc_enroll_search(String acc_enroll_search) {
		this.acc_enroll_search = sanitize(acc_enroll_search);
	}

	public String getAcc_user_update() {
		return acc_user_update;
	}

	public void setAcc_user_update(String acc_user_update) {
		this.acc_user_update = sanitize(acc_user_update);
	}

	public String getAcc_lect_update() {
		return acc_lect_update;
	}

	public void setAcc_lect_update(String acc_lect_update) {
		this.acc_lect_update = sanitize(acc_lect_update);
	}

	public String getAcc_enroll_update() {
		return acc_enroll_update;
	}

	public void setAcc_enroll_update(String acc_enroll_update) {
		this.acc_enroll_update = sanitize(acc_enroll_update);
	}

	public String getAcc_user_delete() {
		return acc_user_delete;
	}

	public void setAcc_user_delete(String acc_user_delete) {
		this.acc_user_delete = sanitize(acc_user_delete);
	}

	public String getAcc_lect_delete() {
		return acc_lect_delete;
	}

	public void setAcc_lect_delete(String acc_lect_delete) {
		this.acc_lect_delete = sanitize(acc_lect_delete);
	}

	public String getAcc_enroll_delete() {
		return acc_enroll_delete;
	}

	public void setAcc_enroll_delete(String acc_enroll_delete) {
		this.acc_enroll_delete = sanitize(acc_enroll_delete);
	}
}
