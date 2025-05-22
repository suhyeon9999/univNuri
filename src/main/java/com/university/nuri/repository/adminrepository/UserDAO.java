package com.university.nuri.repository.adminrepository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.university.nuri.vo.adminvo.AdminVO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;
import com.university.nuri.vo.teachervo.TeacherVO;
@Repository
@Transactional(readOnly = true)
public class UserDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 관리자 전체 정보 불러오기
		public List<Map<String, Object>> getAllAdminList() {
			try {
				return sqlSessionTemplate.selectList("user.getAllAdminList");
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		
		// 관리자 등록
		@Transactional
		public int insertUser(UserVO userVO,AdminVO adminVO) {
			try {
				int result = sqlSessionTemplate.insert("user.insertUser",userVO);
				if (result == 1) {
					String idx = sqlSessionTemplate.selectOne("user.selectUserIdx", userVO);
					adminVO.setUser_idx(idx);
					return sqlSessionTemplate.insert("user.insertAdmin",adminVO);
				}
				return 0;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			
		}

		// 관리자 id중복검사
		public int chkAdminId(String adminId) {
			try {
				return sqlSessionTemplate.selectOne("user.chkAdminId",adminId);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		
		// 관리자 조건 검색
		public List<Map<String, Object>> searchAdmin(Map<String, Object> searchAdmin) {
			try {
				return sqlSessionTemplate.selectList("user.searchAdmin", searchAdmin);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		//관리자 상세보기
		public Map<String, Object> getAdminDetail(String user_idx) {
			try {
				return sqlSessionTemplate.selectOne("user.getAdminDetail", user_idx);			
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}




	// 선생 등록
	@Transactional
	public int teacherInsert(UserVO userVO, TeacherVO teacherVO) {
		try {
			int result = sqlSessionTemplate.insert("user.insertUser",userVO);
			if (result == 1) {
				String idx = sqlSessionTemplate.selectOne("user.selectUserIdx", userVO);
				System.out.println("유저 삽입"+idx);
				teacherVO.setUser_idx(idx);
	            // 1. teacher insert 먼저
	            sqlSessionTemplate.insert("teacher.insertTeacher", teacherVO);

	            // 2. 학과장 지정이면 t_idx 다시 select해서 department 업데이트
	            if (Integer.parseInt(teacherVO.getT_dept_chair()) == 1) {
	                String tIdx = sqlSessionTemplate.selectOne("teacher.selectTidxByUserIdx", idx);
	                teacherVO.setT_idx(tIdx);
	                sqlSessionTemplate.update("teacher.updateTeacher", teacherVO);
	            }

	            return 1;
				
			}else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("User 등록 실패");
		}
		
	}

	// 선생 입사년도 조회 및 학생 수
	public int countTeachersThisYear(@Param("yearstr")String yearstr , @Param("deptIdx")String deptIdx) {
		try {
			return sqlSessionTemplate.selectOne("teacher.yearcount", Map.of("yearstr",yearstr,"deptIdx",deptIdx));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("찾기 실패");
		}
	}


	// 관리자 삭제
		@Transactional
		public int deleteAdmin(String user_idx) {
			try {
				int result =  sqlSessionTemplate.update("user.deleteAdmin", user_idx);	
				if (result == 1) {
					return sqlSessionTemplate.selectOne("user.deleteUser", user_idx);
				}
				return 0;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		// 관리자 수정
		@Transactional
		public int updateAdmin(UserVO userVO, AdminVO adminVO) {
			try {
				int result = sqlSessionTemplate.update("user.updateUser",userVO);
				if (result == 1) {
					String idx = sqlSessionTemplate.selectOne("user.selectUserIdx", userVO);
					adminVO.setUser_idx(idx);
					return sqlSessionTemplate.update("user.updateAdmin",adminVO);
				}
				return 0;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}

}
