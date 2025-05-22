package com.university.nuri.repository.studentrepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SBoardDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<Map<String, Object>> getBoardList(int s_idx) {
		try {
			return sqlSessionTemplate.selectList("board.getBoardList", s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<String, Object> getBoardDetail(int board_idx) {
		try {
			return sqlSessionTemplate.selectOne("board.getBoardDetail", board_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getBoardCheckPassword(int board_idx) {
		try {
			return sqlSessionTemplate.selectOne("board.getBoardCheckPassword", board_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getBoardUpdate(int board_idx, String title, String content) {
		try {
		        // board_idx, title, content를 Map으로 묶어서 전달
		        Map<String, Object> updateMap = new HashMap<>();
		        updateMap.put("board_idx", board_idx);
		        updateMap.put("title", title);
		        updateMap.put("content", content);
			return sqlSessionTemplate.update("board.getBoardUpdate", updateMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getBoardDelete(int board_idx) {
		try {
			return sqlSessionTemplate.update("board.getBoardDelete", board_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getBoardWrite(int s_idx, String writer, String title, String content) {
		try {
		     // board_idx, title, content를 Map으로 묶어서 전달
		     Map<String, Object> insertMap = new HashMap<>();
		     insertMap.put("s_idx", s_idx);
		     insertMap.put("writer", writer);
		     insertMap.put("title", title);
		     insertMap.put("content", content);
			return sqlSessionTemplate.insert("board.getBoardWrite", insertMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getBoardWriteBoardIdx(int s_idx) {
		try {
			return sqlSessionTemplate.selectOne("board.getBoardWriteBoardIdx", s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void updateAnswerRead(int board_idx) {
		    try {
			    sqlSessionTemplate.update("board.updateAnswerRead", board_idx);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	
}
