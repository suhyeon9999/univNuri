package com.university.nuri.service.adminservice;

import java.util.Map;

public interface AMyPageService {

	public Map<String, String> getMyPage(int admin_idx);

	public int updateApassword(int admin_idx, String newPassword);

	public boolean insertFileFirst(Map<String, Object> fileParam);

	public boolean updateUserFileIdx(int user_idx, int f_idx);

	public int updateFname(int f_idx, String f_name, int f_size, int f_type);

	public int insertFoldName(int f_idx, String f_old_name);

	public int updateFnameFoldName(int f_idx, String f_name, int f_size, int f_type, String f_old_name);

	public int getMyPageUpdateOK(int admin_idx, String phone, String email, String birth);

	public String checkApassword(int admin_idx);

}
