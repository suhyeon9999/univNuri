package com.university.nuri.service.adminservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.nuri.repository.adminrepository.SubjectSetDAO;
import com.university.nuri.vo.adminvo.SubjectSetVO;

@Service
@Transactional
public class SubjectSetServiceImpl implements SubjectSetService{
	@Autowired
	private SubjectSetDAO subjectSetDAO;
	
	// 과목군 리스트
	@Override
	public List<Map<String, Object>> getAllSubjectSetList() {		
		return subjectSetDAO.getAllSubjectSetList();
	}
	// 과목군 조건 검색
	@Override
	public List<Map<String, Object>> searchSubjectSet(String searchType, String keyword) {
		if (keyword == null || keyword.trim().isEmpty() || searchType == null || searchType.trim().isEmpty()) {
			// 검색어가 없거나 유효하지 않으면 전체 반환
            return getAllSubjectSetList();
        }
		return subjectSetDAO.searchSubjectSet(searchType, keyword);
	}
	// 과목군명 중복검사
	@Override
	public boolean chkSubSetName(String sub_set_name) {
		return subjectSetDAO.chkSubSetName(sub_set_name) ;
	}
	// 과목군 등록
	@Override
	public String insertSubjectSet(SubjectSetVO subjectSetVO, String[] subjectIdxArr) {
		 String subSetNum = subjectSetDAO.getNextSubSetNum();
		    List<SubjectSetVO> voList = new ArrayList<>();

		    for (String subjectIdx : subjectIdxArr) {
		        SubjectSetVO vo = new SubjectSetVO();
		        vo.setSub_set_num(subSetNum);
		        vo.setSub_set_name(subjectSetVO.getSub_set_name());
		        vo.setDept_idx(subjectSetVO.getDept_idx());
		        vo.setSubject_idx(subjectIdx.trim());
		        voList.add(vo);
		    }

		    subjectSetDAO.insertSubjectSet(voList);
		    return  subSetNum;
	}
	// 과목군 상세보기
	@Override
	public List<Map<String, Object>> detailSubjectSet(int sub_set_idx) {		
		return subjectSetDAO.detailSubjectSet(sub_set_idx);
	}
	// 과목군 수정
	@Override
	@Transactional
	public int updateSubjectSet(SubjectSetVO subjectSetVO, int subSetNumInt, String[] subjectIdxArr) {
	    try {
	        // 1. 새로운 과목군 번호 생성
	        String subSetNum = subjectSetDAO.getNextSubSetNum();  // 새로운 과목군 번호 생성
	        System.out.println("Generated subSetNum: " + subSetNum);  // 디버깅 로그 추가

	        // 2. 기존 과목군의 active 값을 0으로 업데이트 (비활성화)
	        int updateResult = subjectSetDAO.updateSubjectSet(subSetNumInt);
	        System.out.println("Updating subject set with sub_set_num: " + subSetNumInt);  // 디버깅 로그
	        if (updateResult < 1) {
	            throw new IllegalStateException("기존 과목군을 비활성화하는데 실패했습니다.");
	        }

	        // 3. 새로운 과목군 데이터 생성 및 삽입
	        List<SubjectSetVO> voList = new ArrayList<>();
	        for (String subjectIdx : subjectIdxArr) {
	            SubjectSetVO vo = new SubjectSetVO();
	            vo.setSub_set_num(subSetNum); // 새롭게 생성된 과목군 번호 사용
	            vo.setSub_set_name(subjectSetVO.getSub_set_name());
	            vo.setDept_idx(subjectSetVO.getDept_idx());
	            vo.setSubject_idx(subjectIdx.trim());  // 기존의 subject_idx 사용
	            voList.add(vo);
	        }

	        // 4. 새 과목군 삽입
	        int result = subjectSetDAO.insertSubjectSet(voList);
	        System.out.println("Insert result: " + result);  // 디버깅 로그 추가

	        // 5. 새 sub_set_num 반환
	        return Integer.parseInt(subSetNum);

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("과목군 수정 중 오류 발생: " + e.getMessage());
	    }
	}
	// 과목군 삭제
	@Override
	public int deleteSubjectSet(int sub_set_num) {
		return subjectSetDAO.updateSubjectSet(sub_set_num);
	}


}
