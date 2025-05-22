package com.university.nuri.service.adminservice;
import java.util.List;
import java.util.Map;
import com.university.nuri.vo.adminvo.AdminVO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;
import com.university.nuri.vo.teachervo.TeacherVO;

public interface UserService {
	
	// 관리자 전체 리스트 불러오기
	public List<Map<String, Object>> getAllAdminList();

	public int teacherInsert(UserVO userVO, TeacherVO teacherVO);
	// 관리자 등록
		public int adminInsert(UserVO userVO, AdminVO adminVO) ;
		// 관리자 아이디 중복검사
		public int chkAdminId(String adminId);	
		// 관리자 조건검색
		public List<Map<String, Object>> searchAdmin(Map<String, Object> searchAdmin);
		// 관리자 상세보기
		public Map<String, Object> getAdminDetail(String user_idx);
	public int countTeachersThisYear(String yearstr , String deptIdx);
	// 관리자 삭제
		public int deleteAdmin(String user_idx);
		// 관리자 수정
		public int updateAdmin(UserVO userVO, AdminVO adminVO);
		

}
