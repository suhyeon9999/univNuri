package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;


public interface OneQuestionService {

	public List<Map<String, Object>> getBoardList();

	public Map<String, Object> getBoardDetail(int board_idx);

	// 필터링
	public List<Map<String, Object>> getFilteredBoardList(Map<String, String> params);

	public int boardAnswerUpdateOk(int admin_idx, int board_idx, String content);

	public int boardAnswerDeleteOk(int board_idx);

	public int existsAnswer(int board_idx);

	public int insertAgainAnswer(Map<String, Object> map);

	public int insertAnswer(Map<String, Object> map);


}
