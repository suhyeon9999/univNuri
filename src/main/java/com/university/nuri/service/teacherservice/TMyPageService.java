package com.university.nuri.service.teacherservice;

import java.util.Map;

public interface TMyPageService {

	public Map<String, String> getMyPageInfo(int t_idx);

	public String checkSpassword(int t_idx);

	public int updateSpassword(int t_idx, String newPassword);

	public int getMyPageInfoUpdateOK(int t_idx, String phone, String email, String birth);

	    // 파일 관련
	    public boolean insertFileFirst(Map<String, Object> fileParam);

	    public boolean updateUserFileIdx(int user_idx, int f_idx);

	    public int updateFname(int f_idx, String f_name, int f_size, int f_type);

	    public int insertFoldName(int f_idx, String f_old_name);

	    public int updateFnameFoldName(int f_idx, String f_name, int f_size, int f_type, String f_old_name);


}
