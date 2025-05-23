package com.university.nuri.service.adminservice;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.university.nuri.repository.adminrepository.LectDAO;

@Service
public class LectServiceImpl implements LectService{
	@Autowired
	private LectDAO lectDAO;
	// 강의 리스트
	@Override
	public List<Map<String, Object>> getAllLectList() {
		return lectDAO.getAllLectList();
	}
	// 강의 상세보기
	@Override
	public Map<String, Object> detailLect(String lect_idx) {
		try {
			Map<String, Object> detailLect = lectDAO.detailLect(lect_idx);
			String[] days = {"", "일", "월", "화", "수", "목", "금", "토"};
			String dayStr = String.valueOf(detailLect.get("lect_day"));
			List<String> result = new ArrayList<>();
			for (char ch : dayStr.toCharArray()) {
			    int idx = Character.getNumericValue(ch);
			    if (idx >= 1 && idx <= 7) result.add(days[idx]);
			}
			detailLect.put("lect_day", String.join(",", result));
			
			int classNum = ((Number) detailLect.get("class_building")).intValue();

			String className=null;
			switch (classNum) {
			case 0: className="미래관"; break;
			case 1: className="현재관"; break;
			case 2: className="과거관"; break;
			default: className = "미정"; break;
			}
			detailLect.put("class_building", className);		
			return detailLect;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			
	}
	// 강의 상세보기 -과목,과목군 불러오기
	@Override
	public List<Map<String, Object>> lestSubSetList(String lect_idx) {
		return lectDAO.lestSubSetList(lect_idx);
	}
	
	// 정보 미입력 강의보기
	@Override
	public List<Map<String, Object>> searchNullLect() {
		return lectDAO.searchNullLect();
	}
	// 강의검색
		// 강의코드로 검색
	@Override
	public List<Map<String, Object>> searchLectId(String lect_id) {
		return lectDAO.searchLectId(lect_id);
	}
	// 강의명으로 검색
	@Override
	public List<Map<String, Object>> searchLectName(String lect_name) {
		return lectDAO.searchLectName(lect_name);
	}
	// 개설학과로 검색
	@Override
	public List<Map<String, Object>> searchDeptName(String dept_name) {
		return lectDAO.searchDeptName(dept_name);
	}
	// 강의요일로 검색
	@Override
	public List<Map<String, Object>> searchLectDay(String lect_day) {
		try {
			 List<Map<String, Object>> lectureList = lectDAO.searchLectDay(lect_day);

			    for ( Map<String, Object> lectureDay : lectureList) {
			    	String dayNumStr = String.valueOf(lectureDay.get("lect_day")); // 예: "25"
					 StringBuilder dayNames = new StringBuilder();

					 for (int i = 0; i < dayNumStr.length(); i++) {
					     char ch = dayNumStr.charAt(i);

					     switch (ch) {
					         case '1': dayNames.append("일"); break;
					         case '2': dayNames.append("월"); break;
					         case '3': dayNames.append("화"); break;
					         case '4': dayNames.append("수"); break;
					         case '5': dayNames.append("목"); break;
					         case '6': dayNames.append("금"); break;
					         case '7': dayNames.append("토"); break;
					     }
					     

					     if (i != dayNumStr.length() - 1) {
					         dayNames.append(",");
					     }
					 }

					 lectureDay.put("lect_day", dayNames.toString());
			    }
			    System.out.println(lectureList);
			    return lectureList;
			 
		} catch (Exception e) {
			 e.printStackTrace();
				return null;
		}
		
	}
	// 담당교수로 검색
	@Override
	public List<Map<String, Object>> searchName(String name) {
		return lectDAO.searchName(name);
	}
		
		
	// 강의 등록전 과목군 검색
	@Override
	public List<Map<String, Object>> getGroupListByDept(Map<String, String> paramMap) {
		return lectDAO.getGroupListByDept(paramMap);
	}
	// 강의등록 학과 불러오기
	@Override
	public List<Map<String, Object>> getAllActiveDepts() {
		return lectDAO.getAllActiveDepts();
	}
	// 강의등록 선생 불러오기
	@Override
	public List<Map<String, Object>> getAllActiveTeachers() {
		// TODO Auto-generated method stub
		return lectDAO.getAllActiveTeachers();
	}
	// 강의등록 - 학과 선택하면 해당 선생 불러오기
	@Override
	public List<Map<String, Object>> getTeachersByDept(@RequestParam("dept_idx") String deptIdx) {
		return lectDAO.getTeachersByDept(deptIdx);
	}
	// 강의건물 선택 시 해당 강의실 목록 조회
	@Override
	public List<Map<String, Object>> getRoomsByBuilding(String building) {
	    return lectDAO.getRoomsByBuilding(building);
	}
	// 강의실 건물,호실 선택하면 시간 나오게
	@Override
	public List<String> getAvailableStartTimes(Map<String, String> paramMap) {
		return lectDAO.getAvailableStartTimes(paramMap);
	}
	
	// 강의등록 
	    @Override
	    @Transactional
	    public int insertLecture(Map<String, String> param) {

	        // ✅ 1. 등록일 기준 학기 구분: 3월~8월 → 2학기 / 9월~2월 → 다음해 1학기
	        LocalDate now = LocalDate.now();
	        int month = now.getMonthValue();
	        int year = now.getYear();

	        LocalDate startDate;
	        LocalDate endDate;

	        if (month >= 9 || month <= 2) {
	            // 1학기 → 내년 3.1 ~ 6.30
	            startDate = LocalDate.of(year + 1, 3, 1);
	            endDate = LocalDate.of(year + 1, 6, 30);
	        } else {
	            // 2학기 → 올해 9.1 ~ 12.31
	            startDate = LocalDate.of(year, 9, 1);
	            endDate = LocalDate.of(year, 12, 31);
	        }

	        param.put("lect_start_date", startDate.toString());
	        param.put("lect_end_date", endDate.toString());

	        // ✅ 2. 강의실 정보 조회
	        Map<String, Object> classInfo = lectDAO.getClassByLocation(
	            param.get("class_building"),
	            param.get("class_room")
	        );

	        if (classInfo == null) {
	            throw new RuntimeException("❌ 지정된 강의실이 존재하지 않습니다.");
	        }

	        param.put("class_idx", String.valueOf(classInfo.get("class_idx")));
	        param.put("lect_max", String.valueOf(classInfo.get("class_max")));

	        // ✅ 3. lecture 테이블 insert (useGeneratedKeys → lect_idx 자동 채워짐)
	        lectDAO.insertLecture(param);

	        Object lectIdxObj = param.get("lect_idx");
	        int lectIdx = (lectIdxObj instanceof BigInteger)
	            ? ((BigInteger) lectIdxObj).intValue()
	            : Integer.parseInt(String.valueOf(lectIdxObj));
	        
	        String[] groupList = param.get("subject_group").split(",");
	        for (String group : groupList) {
	            Integer subSetIdx = lectDAO.getSubSetIdxByGroupName(group.trim());
	            if (subSetIdx == null) {
	                throw new RuntimeException("❌ 과목군 '" + group.trim() + "'은 존재하지 않습니다.");
	            }

	            lectDAO.insertLectureSubjectSet(lectIdx, subSetIdx);
	        }
	        return lectIdx;
	    }

	    @Override
	    public String generateLectureId(String deptIdx) {
	        String deptCode = lectDAO.getDeptCodeByIdx(deptIdx); // 예: "CS"
	        String maxCode = lectDAO.getMaxLectureCodeByDept(deptCode); // 예: "005"
	        int nextCode = (maxCode == null) ? 1 : Integer.parseInt(maxCode) + 1;
	        return deptCode + String.format("%03d", nextCode); // 예: CS006
	    }
	
			@Override
			public List<Map<String, Object>> getSubjectGroupListByLecture(String lectIdx) {
				List<Map<String, Object>> rawList = lectDAO.getSubjectGroupListByLecture(lectIdx);

			    Map<String, List<String>> grouped = new LinkedHashMap<>();
			    for (Map<String, Object> item : rawList) {
			        String group = (String) item.get("sub_set_name");
			        String subject = (String) item.get("subject_name");

			        grouped.computeIfAbsent(group, k -> new ArrayList<>()).add(subject);
			    }

			    List<Map<String, Object>> result = new ArrayList<>();
			    for (Map.Entry<String, List<String>> entry : grouped.entrySet()) {
			        Map<String, Object> map = new HashMap<>();
			        map.put("sub_set_name", entry.getKey());
			        map.put("subject_list", String.join(", ", entry.getValue()));
			        result.add(map);
			    }

			    return result;
				}
			
			@Override
			public boolean deleteLecture(String lect_idx) {
			      return lectDAO.deactivateLecture(lect_idx);
			}
			@Override
			public List<String> getSubjectGroupsByLectureIdx(String dept_idx) {
				return lectDAO.getSubjectGroupsByLectureIdx(dept_idx);
			}
			@Override
			public List<Map<String, Object>> getProfessorListByDept(String dept_idx) {
				return lectDAO.getProfessorListByDept(dept_idx);
			}
			@Override
			public List<String> classRoomListByBuilding(String building) {
			    return lectDAO.classRoomListByBuilding(building);
			}
		    @Override
		    public boolean updateLecture(Map<String, Object> param) {
		        try {
		        	
		        	Map<String, Object> classInfo = lectDAO.getClassByLocation(
		        		    (String) param.get("class_building"),
		        		    (String) param.get("class_room")
		        		);

		        		if (classInfo != null) {
		        		    param.put("class_idx", classInfo.get("class_idx"));
		        		} else {
		        		    throw new RuntimeException("❌ 유효하지 않은 강의실 정보입니다.");
		        		}
		        		  param.put("class_idx", String.valueOf(classInfo.get("class_idx")));
		            int updated = lectDAO.updateLecture(param); // update 성공시 1 반환
		            // 3. 과목군 동기화 시작 (이 부분 추가)
		            String lectIdx = param.get("lect_idx").toString();
		            String deptIdx = param.get("dept_idx").toString();
		            List<String> subSetNames = (List<String>) param.get("sub_set_names"); // 예: ["전공핵심1", "전공기초2"]

		            List<Integer> oldSubSetIdxList = lectDAO.getSubSetIdxListByLectIdx(lectIdx);

		            // 새로 전달된 sub_set_idx 목록
		            List<Integer> newSubSetIdxList = new ArrayList<>();
		            for (String subSetName : subSetNames) {
		                Integer subSetIdx = lectDAO.getSubSetIdxByNameAndDept(subSetName, deptIdx);
		                if (subSetIdx != null) {
		                    newSubSetIdxList.add(subSetIdx);
		                }
		            }

		            // 비교
		            Set<Integer> oldSet = new HashSet<>(oldSubSetIdxList);
		            Set<Integer> newSet = new HashSet<>(newSubSetIdxList);

		            Set<Integer> toInsert = new HashSet<>(newSet);
		            toInsert.removeAll(oldSet);

		            Set<Integer> toDelete = new HashSet<>(oldSet);
		            toDelete.removeAll(newSet);

		            for (Integer subSetIdx : toInsert) {
		                lectDAO.insertLectureSubjectSetByLectIdx(lectIdx, subSetIdx);
		            }

		            for (Integer subSetIdx : toDelete) {
		                lectDAO.deleteLectureSubjectSetByLectIdx(lectIdx, subSetIdx);
		            }
		            System.out.println("INSERT 대상: " + toInsert);
		            System.out.println("기존 과목군: " + oldSubSetIdxList);
		            System.out.println("새로운 과목군: " + newSubSetIdxList);
		            return updated > 0;

		        } catch (Exception e) {
		            e.printStackTrace();
		            return false;
		        }
		    }
		
}
