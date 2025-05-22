package com.university.nuri.repository.adminrepository;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class OneQuestionDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<Map<String, Object>> getBoardList() {
		try {
			return sqlSessionTemplate.selectList("onequestion.getBoardList");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<String, Object> getBoardDetail(int board_idx) {
		try {
			return sqlSessionTemplate.selectOne("onequestion.getBoardDetail", board_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	public List<Map<String, Object>> getFilteredBoardList(Map<String, String> params) {
		try {
			return sqlSessionTemplate.selectList("onequestion.getFilteredBoardList", params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int boardAnswerUpdateOk(int admin_idx, int board_idx, String content) {
		try {
			Map<String, Object> updateMap = new HashedMap<>();
			updateMap.put("admin_idx", admin_idx);
			updateMap.put("board_idx", board_idx);
			updateMap.put("content", content);
			return sqlSessionTemplate.update("onequestion.boardAnswerUpdateOk", updateMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int boardAnswerDeleteOk(int board_idx) {
		try {
			return sqlSessionTemplate.update("onequestion.boardAnswerDeleteOk", board_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int existsAnswer(int board_idx) {
		try {
			return sqlSessionTemplate.selectOne("onequestion.existsAnswer", board_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int insertAgainAnswer(Map<String, Object> map) {
		try {
			return sqlSessionTemplate.update("onequestion.insertAgainAnswer", map);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int insertAnswer(Map<String, Object> map) {
		try {
			return sqlSessionTemplate.insert("onequestion.insertAnswer", map);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
