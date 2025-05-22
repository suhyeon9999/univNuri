package com.university.nuri.service.studentservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.studentrepository.SBoardDAO;

@Service
public class SBoardServiceImpl implements SBoardService{

	@Autowired
	private SBoardDAO boardDAO;
	
	@Override
	public List<Map<String, Object>> getBoardList(int s_idx) {
		return boardDAO.getBoardList(s_idx);
	}

	@Override
	public Map<String, Object> getBoardDetail(int board_idx) {
		return boardDAO.getBoardDetail(board_idx);
	}
	
	@Override
	public String getBoardCheckPassword(int board_idx) {
		return boardDAO.getBoardCheckPassword(board_idx);
	}

	@Override
	public int getBoardUpdate(int board_idx, String title, String content) {
		return boardDAO.getBoardUpdate(board_idx, title, content);
	}
	
	@Override
	public int getBoardDelete(int board_idx) {
		return boardDAO.getBoardDelete(board_idx);
	}
	
	@Override
	public int getBoardWrite(int s_idx, String writer, String title, String content) {
		return boardDAO.getBoardWrite(s_idx, writer, title, content);
	}
	
	@Override
	public int getBoardWriteBoardIdx(int s_idx) {
		return boardDAO.getBoardWriteBoardIdx(s_idx);
	}
	
	@Override
	public void updateAnswerRead(int board_idx) {
		boardDAO.updateAnswerRead(board_idx);
	}
}
