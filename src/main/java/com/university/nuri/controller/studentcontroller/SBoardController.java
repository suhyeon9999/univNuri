package com.university.nuri.controller.studentcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.studentservice.SBoardService;
import com.university.nuri.vo.commonvo.UserVO;
@Controller
public class SBoardController {

	@Autowired 
	private SBoardService boardService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private HttpSession session;

	// 1:1문의
	@RequestMapping("/sBoardList")
	public ModelAndView getBoardList() {
		try {
			ModelAndView mv = new ModelAndView();

			Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
			int s_idx = (Integer) sInfo.get("s_idx");  // 또는 필요 시 String으로 꺼내서 parseInt

			// service 호출
			List<Map<String, Object>> boardList = boardService.getBoardList(s_idx);

			// jsp에서 사용할 데이터 담기
			mv.addObject("boardList", boardList);
			mv.setViewName("student/onequestion/sBoardList");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 1:1문의 상세보기
	@GetMapping("/sBoardDetail")
	public ModelAndView getBoardDetail(@RequestParam("board_idx") int board_idx) {
	    try {
	        ModelAndView mv = new ModelAndView();

	        Map<String, Object> boardDetail = boardService.getBoardDetail(board_idx);

	        Object answerContent = boardDetail.get("answer_content");
	        Object answerReadObj = boardDetail.get("answer_read");

	        boolean hasAnswer = answerContent != null && !answerContent.toString().trim().isEmpty();
	        boolean unread = answerReadObj == null || Boolean.FALSE.equals(answerReadObj);


	        if (hasAnswer && unread) {
	            boardService.updateAnswerRead(board_idx);
	            boardDetail = boardService.getBoardDetail(board_idx);
	        }
	        if (hasAnswer && unread) {
	     	   boardService.updateAnswerRead(board_idx);
	     	   boardDetail = boardService.getBoardDetail(board_idx); // 다시 불러오기 (최신 상태)
	        }

	        mv.addObject("boardDetail", boardDetail);
	        mv.setViewName("student/onequestion/sBoardDetail");
	        return mv;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	// 1:1 문의 수정
	@RequestMapping("/sBoardUpdate")
	public ModelAndView getBoardUpdate(@RequestParam("board_idx") int board_idx) {
		try {
			ModelAndView mv = new ModelAndView();

			// 서비스에서 Map<String, Object>로 받아오기 (board + answer 정보 포함)
			Map<String, Object> boardDetail = boardService.getBoardDetail(board_idx);

			mv.addObject("boardDetail", boardDetail);
			mv.setViewName("student/onequestion/sBoardUpdate");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 1:1 문의 수정 비밀번호 체크
	@PostMapping("/sBoardCheckPassword")
	@ResponseBody
	public String checkPassword(@RequestParam("board_idx") int board_idx,
			@RequestParam("inputPwd") String inputPwd) {
		// DB에서 비밀번호를 가져옴
		String dbpw = boardService.getBoardCheckPassword(board_idx);  // 평문 비밀번호

		// DB에 저장된 비밀번호가 평문으로 저장되어 있으면 직접 비교
		if (inputPwd != null && passwordEncoder.matches(inputPwd, dbpw)) {
			return "OK";
		} else {
			return "FAIL";
		}
	}


	// 1:1 문의 수정 확정
	@PostMapping("/sBoardUpdateOk")
	@ResponseBody
	public String sBoardUpdateOk(@RequestParam Map<String, String> params) {
		int board_idx = Integer.parseInt(params.get("board_idx"));
		String title   = params.get("title");
		String content = params.get("content");

		int result = boardService.getBoardUpdate(board_idx, title, content);
		return result > 0 ? "OK" : "FAIL";
	}

	// 1:1 문의 삭제 확정
	@PostMapping("/sBoardDeleteOk")
	@ResponseBody
	public String sBoardDeleteOk(@RequestParam String board_idx) {
		int result = boardService.getBoardDelete(Integer.parseInt(board_idx));
		return (result > 0) ? "OK" : "FAIL";
	}

	// 1:1문의 작성
	@RequestMapping("/sBoardWrite")
	public ModelAndView getBoardWrite() {
		try {
			ModelAndView mv = new ModelAndView();

			Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
			int s_idx = (Integer) sInfo.get("s_idx");  // 또는 필요 시 String으로 꺼내서 parseInt

			mv.addObject("s_idx", s_idx); // 필요하면 뷰로도 넘김
			mv.setViewName("student/onequestion/sBoardWrite");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 1:1문의 작성 확정
	@PostMapping("/sBoardWriteOk")
	@ResponseBody 
	public Map<String, Object> sBoardWrite(@RequestParam Map<String, String> params, HttpSession session) {
		try {
			Map<String, Object> sInfo = (Map<String, Object>) session.getAttribute("sInfo");
			int s_idx = (Integer) sInfo.get("s_idx");  // 또는 필요 시 String으로 꺼내서 parseInt
			UserVO uvo = (UserVO) session.getAttribute("uvo");
			String writer = uvo.getName();

			String title = params.get("title");
			String content = params.get("content");

			// 게시글 작성
			int result = boardService.getBoardWrite(s_idx, writer, title, content);
			int board_idx = 0;

			// 게시글이 작성되었으면 board_idx를 가져옴
			if (result > 0) {
				board_idx = boardService.getBoardWriteBoardIdx(s_idx);

				Map<String, Object> response = new HashMap<>();

				// board_idx 가져오기 성공했다면
				if (board_idx > 0) {
					response.put("result", "OK");
					response.put("board_idx", board_idx);  // board_idx 응답에 포함
				} else {
					response.put("result", "FAIL");
					response.put("message", "1:1 문의 등록은 성공했지만, board_idx 가져오기는 실패함");
				}
				return response;
			} else {
				// 게시글 작성 실패
				Map<String, Object> response = new HashMap<>();
				response.put("result", "FAIL");
				response.put("message", "1:1 문의 등록 실패. board_idx 가져오기도 안 함!");
				return response;
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 예외 처리
			Map<String, Object> response = new HashMap<>();
			response.put("result", "FAIL");
			response.put("message", "An error occurred during board creation.");
			return response;
		}
	}
}
