package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.adminrepository.AdminDAO;
import com.university.nuri.repository.adminrepository.UserDAO;
import com.university.nuri.vo.adminvo.AdminVO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;
import com.university.nuri.vo.teachervo.TeacherVO;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AdminDAO adminDAO;
	
	// 관리자 전체 리스트 불러오기
		@Override
		public List<Map<String, Object>> getAllAdminList() {
			return userDAO.getAllAdminList();
		}

		// 관리자 등록
		@Override
		public int adminInsert(UserVO userVO, AdminVO adminVO) {		
			if(userVO == null && adminVO == null) {
				throw new IllegalArgumentException("VO값들이 다 비었음");
			}       
	        return userDAO.insertUser(userVO,adminVO);
	        }
		// 관리자 아이디 중복검사
		@Override
		public int chkAdminId(String adminId) {		
			return userDAO.chkAdminId(adminId) ;
		}
		// 관리자 조건 검색
		@Override
		public List<Map<String, Object>> searchAdmin(Map<String, Object> searchAdmin) {		
			return userDAO.searchAdmin(searchAdmin);
		}
		//관리자 상세보기
		@Override
		public Map<String, Object> getAdminDetail(String user_idx) {
			return userDAO.getAdminDetail(user_idx);
		}


	//관리자 삭제
	@Override
	public int deleteAdmin(String user_idx) {
		return userDAO.deleteAdmin(user_idx);
	}
	
	// 관리자 수정
	@Override
	public int updateAdmin(UserVO userVO, AdminVO adminVO) {		
		return userDAO.updateAdmin(userVO, adminVO);
	}
	// 선생 등록
	@Override
	public int teacherInsert(UserVO userVO, TeacherVO teacherVO) {
		if(userVO == null && teacherVO == null) {
			throw new IllegalArgumentException("VO값들이 다 비었음");
		}
        return userDAO.teacherInsert(userVO, teacherVO);
	}
	// 선생 년도,학과 중복 체크
	@Override
	public int countTeachersThisYear(String yearstr, String deptIdx) {
		return userDAO.countTeachersThisYear(yearstr, deptIdx);
	 }


	}
	
	

	


