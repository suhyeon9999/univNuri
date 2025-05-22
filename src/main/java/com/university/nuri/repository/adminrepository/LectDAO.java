package com.university.nuri.repository.adminrepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public class LectDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	// 강의 리스트
	public List<Map<String, Object>> getAllLectList() {
		try {
			return sqlSessionTemplate.selectList("lecture.getAllLectList");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 강의 상세보기
	public Map<String, Object> detailLect(String lect_idx) {
		try {
			return sqlSessionTemplate.selectOne("lecture.detailLect",lect_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 강의 상세보기 -과목,과목군 불러오기
	public List<Map<String, Object>> lestSubSetList(String lect_idx) {
		try {
			return sqlSessionTemplate.selectList("lecture.lestSubSetList",lect_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 정보 미입력 강의보기
	public List<Map<String, Object>> searchNullLect() {
		try {
			return sqlSessionTemplate.selectList("lecture.searchNullLect");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 강의검색
	// 강의코드로 검색		
	public List<Map<String, Object>> searchLectId(String lect_id) {
		try {
			return sqlSessionTemplate.selectList("lecture.searchLectId", lect_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 강의명으로 검색
	public List<Map<String, Object>> searchLectName(String lect_name) {
		try {
			return sqlSessionTemplate.selectList("lecture.searchLectName", lect_name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 개설학과로 검색
	public List<Map<String, Object>> searchDeptName(String dept_name) {
		try {
			return sqlSessionTemplate.selectList("lecture.searchDeptName", dept_name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 강의요일로 검색
	public List<Map<String, Object>> searchLectDay(String lect_day) {
		try {
			return sqlSessionTemplate.selectList("lecture.searchLectDay", lect_day);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 담당교수로 검색
	public List<Map<String, Object>> searchName(String name) {
		try {
			return sqlSessionTemplate.selectList("lecture.searchName", name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 강의 등록전 과목군 검색
		public List<Map<String, Object>> getGroupListByDept(Map<String, String> paramMap) {
			try {
				return sqlSessionTemplate.selectList("lecture.getGroupListByDept", paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		// 강의등록 학과 불러오기
		public List<Map<String, Object>> getAllActiveDepts() {
			try {
				return sqlSessionTemplate.selectList("lecture.getAllActiveDepts");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		// 강의등록 선생 불러오기
		public List<Map<String, Object>> getAllActiveTeachers() {
			try {
				return sqlSessionTemplate.selectList("lecture.getAllActiveTeachers");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		// 강의등록 - 학과 선택하면 해당 선생 불러오기
		public List<Map<String, Object>> getTeachersByDept(@RequestParam("dept_idx") String deptIdx) {
			try {
				return sqlSessionTemplate.selectList("lecture.getTeachersByDept",deptIdx);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		// 강의건물 선택 시 해당 강의실 목록 조회
		public List<Map<String, Object>> getRoomsByBuilding(String building) {
			try {
				return sqlSessionTemplate.selectList("lecture.getRoomsByBuilding", building);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		// 강의실 건물,호실 선택하면 시간 나오게
		public List<String> getAvailableStartTimes(Map<String, String> paramMap) {
			try {
				return sqlSessionTemplate.selectList("lecture.getAvailableStartTimes",paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		 // 1. lecture 테이블 insert
	    public void insertLecture(Map<String, String> param) {
	        sqlSessionTemplate.insert("lecture.insertLecture", param);
	    }

	    // 2. 강의실 정보 조회 (building + room 기준)
	    public Map<String, Object> getClassByLocation(String building, String room) {
	        Map<String, Object> param = new HashMap<>();
	        param.put("building", building);
	        param.put("room", room);
	        return sqlSessionTemplate.selectOne("lecture.getClassByLocation", param);
	    }
	    public Integer getSubSetIdxByGroupName(String subSetName) {
	    	try {
	    		return sqlSessionTemplate.selectOne("lecture.getSubSetIdxByGroupName", subSetName);
				
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	    }

	    // 4. lecture_subject_set insert
	    public void insertLectureSubjectSet(int lectIdx, int subSetIdx) {
	    	try {
				
	    		Map<String, Object> param = new HashMap<>();
	    		param.put("lect_idx", lectIdx);
	    		param.put("sub_set_idx", subSetIdx);
	    		sqlSessionTemplate.insert("lecture.insertLectureSubjectSet", param);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }

	    // 5. 학과 idx → 학과 코드(dept_id) 조회
	    public String getDeptCodeByIdx(String deptIdx) {
	    	try {
	    		return sqlSessionTemplate.selectOne("lecture.getDeptCodeByIdx", deptIdx);
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
				
			}
	    }

	    // 6. 학과코드로 시작하는 강의코드 중 최대번호 조회
	    public String getMaxLectureCodeByDept(String deptCode) {
	    	try {
				
	    		return sqlSessionTemplate.selectOne("lecture.getMaxLectureCodeByDept", deptCode);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	    }
	    public List<Map<String, Object>> getSubjectGroupListByLecture(String lectIdx) {
	    	try {
	    		return sqlSessionTemplate.selectList("lecture.getSubjectGroupListByLecture",lectIdx);
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
				
			}
	    	}
		public boolean deactivateLecture(String lect_idx) {
			try {
				int result = sqlSessionTemplate.update("lecture.deactivateLectures",lect_idx);
				return result > 0;
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		public List<String> getSubjectGroupsByLectureIdx(String dept_idx) {
			try {
				return sqlSessionTemplate.selectList("lecture.getSubjectGroupsByLectureIdx",dept_idx);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		public List<Map<String, Object>> getProfessorListByDept(String dept_idx) {
			try {
				return sqlSessionTemplate.selectList("lecture.getProfessorListByDept",dept_idx);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		public List<String> classRoomListByBuilding(String building) {
		    return sqlSessionTemplate.selectList("lecture.classRoomListByBuilding", building);
		}

	    public int updateLecture(Map<String, Object> param) {
	        try {
	            return sqlSessionTemplate.update("lecture.updateLecture",param); // update 성공시 1 반환
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            return 0;
	        }
	    }


	

}
