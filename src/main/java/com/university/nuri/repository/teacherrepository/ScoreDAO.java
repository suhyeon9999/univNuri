package com.university.nuri.repository.teacherrepository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.teachervo.ScoreVO;

@Repository
public class ScoreDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	
	// 성적 입력 완료 인원수 구하기
		public int getScoreInputComplete(int lect_idx){
			try {

				return sqlSessionTemplate.selectOne("t_score.scoreInputComplete", lect_idx);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}

			// 성적관리메인에서 강의 선택해서 해당 강의 수강하는 학생들의 성적 리스트 전체 혹은 이름이나 학번으로 검색 조회
		public List<Map<String, Object>> getScoreDetail(Map<String, Object> paramMap) {
			try {

				return sqlSessionTemplate.selectList("t_score.scoreDetail",paramMap);
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}


		// 저장 버튼 눌러서 성적 업데이트
		public int getScoreUpdateOk(Map<String, Object> scoreStudent ) {
			try {

				return sqlSessionTemplate.update("t_score.getScoreUpdateOk", scoreStudent);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		
		
		public String getLectureName(int lect_idx) {
			try {

				return sqlSessionTemplate.selectOne("t_score.getLectureName", lect_idx);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
}
