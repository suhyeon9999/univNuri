package com.university.nuri.controller.teachercontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.university.nuri.service.teacherservice.ScoreService;

@Controller
public class ScoreController {

	@Autowired
	private ScoreService scoreService;

	/**
	 * 강의 성적 상세 조회 (교사용)
	 * 금학기 혹은 과거강좌에서 선택한 강의에 대해 학생별 성적 정보를 조회하고, 점수 입력 현황을 반환
	 */
	@GetMapping("/get-lecture-score-list")
	public ModelAndView getScoreDetail(@RequestParam("currentPastIs") String currentPastIs, // 금학기인지 과거인지 여부
			@RequestParam("lect_idx") int lect_idx) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("lect_idx", lect_idx);

		List<Map<String, Object>> scoreList = scoreService.getScoreDetail(paramMap); // 강의 idx 가지고 가서 강의 성적 정보 가지고 오기

		int studentNum = scoreList.size();// 전체 학생수
		int scoreInputComplete = scoreService.getScoreInputComplete(lect_idx); // 입력 완료 학생수
		int scoreInputNotComplete = studentNum - scoreInputComplete; // 입력 미완료 학생 수

		ModelAndView mv = new ModelAndView();
		mv.addObject("studentNum", studentNum);
		mv.addObject("scoreInputComplete", scoreInputComplete);
		mv.addObject("scoreInputNotComplete", scoreInputNotComplete);
		List<String> scoreTypes = Arrays.asList("score_mid", "score_final", "score_assign", "score_attend",
				"score_total");
		mv.addObject("scoreTypes", scoreTypes);

		mv.setViewName("teacher/score/scoreDetail");

		String lect_name = scoreService.getLectureName(lect_idx); // 강의명 가져오기
		mv.addObject("lect_name", lect_name);
		mv.addObject("scoreList", scoreList);
		mv.addObject("lect_idx", lect_idx);
		mv.addObject("currentPastIs", currentPastIs);

		return mv;
	}

	
	// name과 학번으로 학생 검색하기
	@PostMapping("/get-name-idx-student-score")
	@ResponseBody
	public Map<String, Object> getFilteredStudentListAjax(@RequestBody Map<String, Object> params) {
		String name = (String) params.get("name");
		String user_id = (String) params.get("user_id");
		String lect_idx = (String) params.get("lect_idx");

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("lect_idx", lect_idx);
		paramMap.put("name", name);
		paramMap.put("user_id", user_id);

		List<Map<String, Object>> scoreList = scoreService.getScoreDetail(paramMap);

		// 프론트에서 기대하는 항목들 추가
		Map<String, Object> result = new HashMap<>();
		result.put("scoreList", scoreList);
		result.put("studentNum", scoreList != null ? scoreList.size() : 0);

		// scoreTypes 명시적으로 추가
		result.put("scoreTypes",
				Arrays.asList("score_mid", "score_final", "score_assign", "score_attend", "score_total"));

		// 현재 수업인지 과거 수업인지 구분 (예: 기본값을 current로 설정)
		result.put("currentPastIs", "current"); // 또는 로직으로 판별

		return result;
	}

	
	// 성적 업데이트 
	@PostMapping("/score-update")
	public ModelAndView getSoreUpdate(@RequestParam("lect_idx") int lect_idx) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("lect_idx", lect_idx);
		List<Map<String, Object>> scoreList = scoreService.getScoreDetail(paramMap);

		int studentNum = scoreList.size();// 전체 학생수
		int scoreInputComplete = scoreService.getScoreInputComplete(lect_idx); // 입력 완료 학생수
		int scoreInputNotComplete = studentNum - scoreInputComplete; // 입력 미완료 학생 수

		ModelAndView mv = new ModelAndView("teacher/score/scoreUpdate");

		mv.addObject("studentNum", studentNum);
		mv.addObject("scoreInputComplete", scoreInputComplete);
		mv.addObject("scoreInputNotComplete", scoreInputNotComplete);

		String lect_name = scoreService.getLectureName(lect_idx);
		mv.addObject("lect_name", lect_name);
		mv.addObject("scoreList", scoreList);
		mv.addObject("lect_idx", lect_idx);
		return mv;

	}

	private List<Integer> convertToIntegerList(List<String> stringList) {
		List<Integer> result = new ArrayList<>();
		for (String s : stringList) {
			if (s == null || s.trim().isEmpty()) {
				result.add(null);
			} else {
				try {
					result.add(Integer.parseInt(s.trim()));
				} catch (NumberFormatException e) {
					result.add(null); // 예외 시 null 처리
				}
			}
		}
		return result;
	}

	// 입력하지 않으면 null 값으로 받아야하기 때문에 List<Integer>가 아닌 List<String>으로 받음
	// 이때 리스트인 이유는 학생들이 여러명일 수 있기 때문
	@PostMapping("/score_update_ok")
	public String updateScores(@RequestParam("lect_idx") int lectIdx,
			@RequestParam("enroll_idx[]") List<Integer> enrollIdxList, // 학생들의 수강 idx
			@RequestParam("score_mid[]") List<String> midScores, // 학생들의 중간고사 점수
			@RequestParam("score_final[]") List<String> finalScores, // 학생들의 기말고사 점수
			@RequestParam("score_assign[]") List<String> assignScores, // 학생들의 과제 점수
			@RequestParam("score_attend[]") List<String> attendScores // 학생들의 출석 점수
	) {

		// null 이면 null로 숫자였던경우는 String에서 Integer 형태로 바꾸는 코드
		// Integer로 바꾸는 이유는 학생들의 총합 계산해서 학점 도출해내기 위함

		// 점수 리스트들을 변환 (빈 값은 null로)
		List<Integer> midScoresInteger = convertToIntegerList(midScores);
		List<Integer> finalScoresInteger = convertToIntegerList(finalScores);
		List<Integer> assignScoresInteger = convertToIntegerList(assignScores);
		List<Integer> attendScoresInteger = convertToIntegerList(attendScores);

		// 학점 계산
		// 학생들의 점수를 합한 후 90점 이상이면 "A", 80점 이상이면 "B", 70점 이상이면 "C" 그리고 그 이하면 "F"인 각 학생들의
		// 학점을 도출

		// 학점 계산
		List<String> totalGrades = new ArrayList<>();
		for (int i = 0; i < enrollIdxList.size(); i++) {
			Integer mid = midScoresInteger.get(i);
			Integer fin = finalScoresInteger.get(i);
			Integer assign = assignScoresInteger.get(i);
			Integer attend = attendScoresInteger.get(i);

			String grade = null;
			if (mid != null && fin != null && assign != null && attend != null) {
				int total = mid + fin + assign + attend;
				grade = (total >= 90) ? "A" : (total >= 80) ? "B" : (total >= 70) ? "C" : "F";
			}
			totalGrades.add(grade);
		}

		// 학생들의 각 점수를 db에 업데이트
		// DB 업데이트
		for (int i = 0; i < enrollIdxList.size(); i++) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("enroll_idx", enrollIdxList.get(i));
			paramMap.put("score_mid", midScoresInteger.get(i));
			paramMap.put("score_final", finalScoresInteger.get(i));
			paramMap.put("score_assign", assignScoresInteger.get(i));
			paramMap.put("score_attend", attendScoresInteger.get(i));
			paramMap.put("score_total", totalGrades.get(i));

			scoreService.getScoreUpdateOk(paramMap);
		}

		// 성적 리스트 보는 화면으로 돌아감
		return "redirect:/get-lecture-score-list?currentPastIs=current&lect_idx=" + lectIdx;
	}

}