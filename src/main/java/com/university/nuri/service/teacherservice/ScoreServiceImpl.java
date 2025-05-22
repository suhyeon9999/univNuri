package com.university.nuri.service.teacherservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.teacherrepository.ScoreDAO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.teachervo.ScoreVO;


@Service
public class ScoreServiceImpl implements ScoreService{
	
	
	@Autowired
	private ScoreDAO scoreDAO;

	@Override
	// 성적 입력 완료 인원수 구하기
	public int getScoreInputComplete(int lect_idx){

		return scoreDAO.getScoreInputComplete(lect_idx);
	}
	
	// 성적관리메인에서 강의 선택해서 해당 강의 수강하는 학생들의 성적 리스트 전체 혹은 이름이나 학번으로 검색 조회
	@Override
	public List<Map<String, Object>> getScoreDetail(Map<String, Object> paramMap) {

		return  scoreDAO.getScoreDetail( paramMap);
	}



	// 저장 버튼 눌러서 성적 업데이트
	@Override
	public int getScoreUpdateOk(Map<String, Object> scoreStudent ) {


			
			return scoreDAO.getScoreUpdateOk(scoreStudent );

	}

	@Override
	public String getLectureName(int lect_idx) {
		return scoreDAO.getLectureName(lect_idx);
	}
	
	
	


}
