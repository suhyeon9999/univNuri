package com.university.nuri.controller.admincontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.adminservice.OneQuestionService;
import com.university.nuri.vo.commonvo.UserVO;

@Controller
public class OneQuestionController {

	@Autowired
	private OneQuestionService oneQuestionService;
	@Autowired
	private HttpSession session;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	// admin 네비 -> 1:1문의 연결	
	@GetMapping("/aBoardList")
	public ModelAndView boardList() {
		try {
			ModelAndView mv = new ModelAndView();
			// 서비스에서 게시글 목록 조회
			List<Map<String, Object>> boardList = oneQuestionService.getBoardList();
			mv.addObject("boardList", boardList);
			mv.setViewName("admin/onequestion/boardList");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/aBoardListFiltered")
	public ModelAndView filteredBoardList(@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView("admin/onequestion/boardList"); // 경로에 맞게 수정

		List<Map<String, Object>> boardList = oneQuestionService.getFilteredBoardList(params);

		mv.addObject("boardList", boardList);
		mv.addObject("param", params); // 검색값 유지용
		return mv;
	}
	
	@GetMapping("/aBoardDetail")
	public ModelAndView getBoardDetail(@RequestParam("board_idx") int board_idx) {
		try {
			ModelAndView mv = new ModelAndView();
			
			// 서비스에서 Map<String, Object>로 받아오기 (board + answer 정보 포함)
			Map<String, Object> boardDetail = oneQuestionService.getBoardDetail(board_idx);
			
			mv.addObject("boardDetail", boardDetail);
			mv.setViewName("admin/onequestion/boardDetail");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/aBoardAnswer")
	public ModelAndView getBoardAnswer(@RequestParam("board_idx") int board_idx) {
		try {
			ModelAndView mv = new ModelAndView();
			
			// 서비스에서 Map<String, Object>로 받아오기 (board + answer 정보 포함)
			Map<String, Object> boardDetail = oneQuestionService.getBoardDetail(board_idx);

			mv.addObject("boardDetail", boardDetail);
			mv.setViewName("admin/onequestion/boardAnswer");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping("/aBoardAnswerOk")
	@ResponseBody
	public String getBoardAnswerOk(@RequestParam Map<String, String> params) {
	    try {
	        Map<String, Object> aInfo = (Map<String, Object>) session.getAttribute("aInfo");
	        int admin_idx = (Integer) aInfo.get("admin_idx");

	        int board_idx = Integer.parseInt(params.get("board_idx"));
	        String content = params.get("content");

	        // 1. 파라미터 모음
	        Map<String, Object> map = new HashMap<>();
	        map.put("admin_idx", admin_idx);
	        map.put("board_idx", board_idx);
	        map.put("content", content);

	        // 2. 답변 존재 여부 확인 → update or insert 분기
	        int exists = oneQuestionService.existsAnswer(board_idx);

	        int result = 0;
	        if (exists > 0) {
	            result = oneQuestionService.insertAgainAnswer(map); // 답변 1번이라도 달았던거. 삭제했었던 경우.
	        } else {
	            result = oneQuestionService.insertAnswer(map); // 완전 처음 답변 다는거.
	        }

	        return result > 0 ? "OK" : "FAIL";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "FAIL";
	    }
	}
	
	@GetMapping("/aBoardAnswerUpdate")
	public ModelAndView boardAnswerUpdate(@RequestParam("board_idx") int board_idx) {
		try {
			ModelAndView mv = new ModelAndView();
			
			// 서비스에서 Map<String, Object>로 받아오기 (board + answer 정보 포함)
			Map<String, Object> boardDetail = oneQuestionService.getBoardDetail(board_idx);

			mv.addObject("boardDetail", boardDetail);
			mv.setViewName("admin/onequestion/boardAnswerUpdate");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping("/aBoardAnswerUpdateOk")
	@ResponseBody
	public String boardAnswerUpdateOk(@RequestParam Map<String, String> params) {
		try {
			Map<String, Object> aInfo = (Map<String, Object>) session.getAttribute("aInfo");
			int admin_idx = (Integer) aInfo.get("admin_idx");  // 또는 필요 시 String으로 꺼내서 parseInt
			
			int board_idx = Integer.parseInt(params.get("board_idx"));
			String content = params.get("content");
			
			int result = oneQuestionService.boardAnswerUpdateOk(admin_idx, board_idx, content);
			return result > 0 ? "OK" : "FAIL";
			
		} catch (Exception e) {
			return "FAIL";
		}
	}
	
	// 비밀번호 체크
	@PostMapping("/aBoardCheckPassword")
	@ResponseBody
	public String checkPassword(@RequestParam("inputPwd") String inputPwd) {
		UserVO uvo = (UserVO) session.getAttribute("uvo");
		String user_pw = uvo.getUser_pw();

		// DB에 저장된 비밀번호가 평문으로 저장되어 있으면 직접 비교
		if (inputPwd != null && passwordEncoder.matches(inputPwd, user_pw)) {
			return "OK";
		} else {
			return "FAIL";
		}
	}
	
	@PostMapping("/aBoardAnswerDeleteOk")
	@ResponseBody
	public String boardAnswerDeleteOk(@RequestParam("board_idx") int board_idx) {
		try {
			int result = oneQuestionService.boardAnswerDeleteOk(board_idx);
			return result > 0 ? "OK" : "FAIL";
			
		} catch (Exception e) {
			return "FAIL";
		}
	}
}
