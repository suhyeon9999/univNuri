package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.adminrepository.OneQuestionDAO;

@Service
public class OneQuestionServiceImpl implements OneQuestionService{
	@Autowired
	private OneQuestionDAO oneQuestionDAO;

	@Override
	public List<Map<String, Object>> getBoardList() {
		return oneQuestionDAO.getBoardList();
	}

	@Override
	public Map<String, Object> getBoardDetail(int board_idx) {
		return oneQuestionDAO.getBoardDetail(board_idx);
	}


	@Override
	public List<Map<String, Object>> getFilteredBoardList(Map<String, String> params) {
		return oneQuestionDAO.getFilteredBoardList(params);
	}

	@Override
	public int boardAnswerUpdateOk(int admin_idx, int board_idx, String content) {
		return oneQuestionDAO.boardAnswerUpdateOk(admin_idx, board_idx, content);
	}

	@Override
	public int boardAnswerDeleteOk(int board_idx) {
		return oneQuestionDAO.boardAnswerDeleteOk(board_idx);
	}

	@Override
	public int existsAnswer(int board_idx) {
		return oneQuestionDAO.existsAnswer(board_idx);
	}

	@Override
	public int insertAgainAnswer(Map<String, Object> map) {
		return oneQuestionDAO.insertAgainAnswer(map);
	}

	@Override
	public int insertAnswer(Map<String, Object> map) {
		return oneQuestionDAO.insertAnswer(map);
	}
}
