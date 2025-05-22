package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.adminrepository.SubjectDAO;
import com.university.nuri.vo.adminvo.SubjectVO;

@Service
public class SubjectServicesImpl implements SubjectService{
	@Autowired
	private SubjectDAO subjectDAO;
	
	// 과목리스트
	@Override
	public List<Map<String, Object>> getAllSubjectList() {	
		return subjectDAO.getAllSubjectList();
	}
	// 과목등록
	@Override
	public int insertSubject(SubjectVO subjectVO) {
		return subjectDAO.insertSubject(subjectVO) ;
	}
	// 과목 상세보기
	@Override
	public Map<String, Object> detailSubject(String sub_set_num) {
		return subjectDAO.detailSubject(sub_set_num);
	}
	// 과목 삭제
	@Override
	public int deleteSubject(String subject_idx) {
		return subjectDAO.deleteSubject(subject_idx);
	}
	// 과목 조건 검색
	@Override
	public List<Map<String, Object>> searchSubject(String searchType, String keyword) {
		if (keyword == null || keyword.trim().isEmpty() || searchType == null || searchType.trim().isEmpty()) {
			// 검색어가 없거나 유효하지 않으면 전체 반환
            return getAllSubjectList();
        }
		return subjectDAO.searchSubject (searchType, keyword);
	}

}
