package com.university.nuri.service.studentservice;

import java.util.List;
import java.util.Map;

public interface SBoardService {

	public List<Map<String, Object>> getBoardList(int s_idx);

	public Map<String, Object> getBoardDetail(int board_idx);

	public String getBoardCheckPassword(int board_idx);

	public int getBoardUpdate(int board_idx, String title, String content);

	public int getBoardDelete(int board_idx);

	public int getBoardWrite(int s_idx, String writer, String title, String content);

	public int getBoardWriteBoardIdx(int s_idx);

	public void updateAnswerRead(int board_idx);

}
