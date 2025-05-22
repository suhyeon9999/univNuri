package com.university.nuri.service.teacherservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.teacherrepository.LectureDAO;
import com.university.nuri.vo.commonvo.AssignVO;
import com.university.nuri.vo.teachervo.TestMakeVO;

@Service
public class LectureServiceImpl implements LectureService{

	@Autowired
	private LectureDAO tLectureDAO;
	
	@Override
	public Map<String, Object> getLectureInfo(String lect_idx) {
		 Map<String, Object> lectureInfo= tLectureDAO.getLectureInfo(lect_idx);
		
		String dayOfWeek= Integer.toString((int) lectureInfo.get("lect_day"));
        StringBuilder dayNames = new StringBuilder();

        for (int i = 0; i < dayOfWeek.length(); i++) {
            switch (dayOfWeek.charAt(i)) {
                case '1': dayNames.append("일"); break;
                case '2': dayNames.append("월"); break;
                case '3': dayNames.append("화"); break;
                case '4': dayNames.append("수"); break;
                case '5': dayNames.append("목"); break;
                case '6': dayNames.append("금"); break;
                case '7': dayNames.append("토"); break;
            }
            if (i != dayOfWeek.length() - 1) {
                dayNames.append(",");
            }
        }
        lectureInfo.put("lect_day", dayNames.toString());

        // 건물명 변환
        int classNum = ((Number) lectureInfo.get("class_building")).intValue();
				String className=null;
				switch (classNum) {
				case 0: className="미래관"; break;
				case 1: className="현재관"; break;
				case 2: className="과거관"; break;
				default: className = "미정"; break;
				}
				lectureInfo.put("class_building", className);
		return lectureInfo;
	}

	@Override
	public List<Map<String, Object>> getAssignList(Map<String, Object> paramMap) {
		List<Map<String, Object>> assignList= tLectureDAO.getAssignList(paramMap);
		return assignList;
	}

	@Override
	public List<TestMakeVO> getExamList(String lect_idx) {
		List<TestMakeVO> examList = tLectureDAO.getExamList(lect_idx);
		return examList;
	}

	@Override
	public int midExist(String lect_idx) {
		return tLectureDAO.midExist(lect_idx);
	}

	@Override
	public int finalExist(String lect_idx) {
		return tLectureDAO.finalExist(lect_idx);
	}

}