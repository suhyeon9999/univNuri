package com.university.nuri.service.studentservice;

import java.util.Map;

public interface SMyPageService {

	public Map<String, String> getMyPageInfo(int s_idx);

	public String checkSpassword(int s_idx);

	public int updateSpassword(int s_idx, String newPassword);

	public int getMyPageInfoUpdateOK(int s_idx, String phone, String email, String birth, 
			String address1, String address2);

	    // 파일 관련
	    public boolean insertFileFirst(Map<String, Object> fileParam);

	    public boolean updateUserFileIdx(int user_idx, int f_idx);

	    public int updateFname(int f_idx, String f_name, int f_size, int f_type);

	    public int insertFoldName(int f_idx, String f_old_name);

	    public int updateFnameFoldName(int f_idx, String f_name, int f_size, int f_type, String f_old_name);


}
