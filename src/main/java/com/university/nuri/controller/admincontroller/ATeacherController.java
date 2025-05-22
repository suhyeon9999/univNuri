package com.university.nuri.controller.admincontroller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.university.nuri.service.adminservice.ATeacherService;
import com.university.nuri.vo.adminvo.TeacherExcelDTO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.teachervo.TeacherVO;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ATeacherController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ATeacherService aTeacherService;
	@Autowired
	private HttpSession session;
	
	//선생관리 페이지
	@RequestMapping("/ateacherList")
	public ModelAndView teacherList() {
	    ModelAndView mv = new ModelAndView();
	    List<Map<String, Object>> teacherList = aTeacherService.getTeacherList();
	    mv.addObject("teacherList", teacherList);
	    mv.setViewName("admin/userpage/teacherList");
	    return mv;
	}

	// 선생관리 페이지 - 조건 검색
	@RequestMapping("/filteredTeacherList")
	public ModelAndView filterStudentList(@RequestParam(required = false) String name,
               @RequestParam(required = false) String user_id) {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> teacherList = aTeacherService.getFilteredTeacherList(name, user_id);
		mv.addObject("teacherList", teacherList);
		mv.setViewName("admin/userpage/teacherList");
		return mv;
	}
	
	// 선생관리 -> 선생 상세보기
	@GetMapping("/adetailTeacher")
	public ModelAndView detailTeacher(@RequestParam("user_id") String user_id) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> detailTeacher = aTeacherService.getDetailTeacher(user_id);
		mv.addObject("detailTeacher", detailTeacher);
		mv.addObject("user_id", user_id);
		
		System.out.println(user_id);
		String t_idx = aTeacherService.getTIdx(user_id);
		System.out.println(t_idx);
		String user_level = aTeacherService.getUserLevel(user_id);
		System.out.println("ul"+user_level);
		mv.addObject("user_level", user_level);
		mv.addObject("t_idx", t_idx);
		mv.setViewName("admin/userpage/detailTeacher");
		return mv;
	}
	// 선생관리 -> 선생 수정 페이지
	@GetMapping("/aupdateTeacher")
	public ModelAndView updateTeacher(@RequestParam("user_id") String user_id) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> detailTeacher = aTeacherService.getDetailTeacher(user_id);
		List<Map<String, Object>> deptList = aTeacherService.getDeptList();
		mv.addObject("detailTeacher", detailTeacher);
		mv.addObject("deptList", deptList);
		mv.setViewName("admin/userpage/updateTeacher");
		return mv;
	}	
	
	// 선생관리 -> 선생 수정 페이지
	@PostMapping("/updateOkTeacher")
	@ResponseBody
	public String updateOkTeacher(@RequestParam Map<String, String> params) {
	    try {
	        String user_id = params.get("user_id");
	        String name = params.get("name");
	        String birth = params.get("birth");
	        String email = params.get("email");
	        String phone = params.get("phone");
	        int dept_idx = Integer.parseInt(params.get("dept_idx"));
	        //학과장 여부//퇴직 재직 여부 
	        String t_dept_chairS =params.get("t_dept_chair");
	        String statusS = params.get("status");
	        
	        System.out.println("학과장:"+t_dept_chairS);
	        
	        int t_dept_chair=0;
	        int status=0;
	        if(t_dept_chairS.equals("교수")) {
	        	t_dept_chair=0;
	        }else {
	        	t_dept_chair=1;
	        }
	        
	        if(statusS.equals("재직")) {
	        	status=4;
	        }else {
	        	status=5;
	        }
	        System.out.println("t_dept_chair:" +t_dept_chair);

	        // user 테이블 업데이트
	        int userResult = aTeacherService.getUpdateUserTable(user_id, name, birth, email, phone, status);

	        // teacher 테이블 업데이트
	        int teacherResult = aTeacherService.getUpdateTeacherTable(user_id, dept_idx, t_dept_chair);

	        return (userResult > 0 && teacherResult > 0) ? "OK" : "FAIL";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "FAIL";
	    }
	}
	
	//삭제 비밀번호 체크
	@PostMapping("/aTeacherCheckPassword")
	@ResponseBody
	public String checkPassword(@RequestParam("inputPwd") String inputPwd) {
		UserVO uvo = (UserVO) session.getAttribute("uvo");
		String user_id = uvo.getUser_id();
		// DB에서 비밀번호를 가져옴
		String dbpw = aTeacherService.getATeacherCheckPassword(user_id);  // 평문 비밀번호

		// DB에 저장된 비밀번호가 평문으로 저장되어 있으면 직접 비교
		if (inputPwd != null && passwordEncoder.matches(inputPwd, dbpw)) {
			return "OK";
		} else {
			return "FAIL";
		}
	}
	
	//삭제 확정 
	@PostMapping("/aTeacherDeleteOk")
	@ResponseBody
	public String sBoardDeleteOk(@RequestParam String user_id,
			@RequestParam int t_idx) {
		int userResult = aTeacherService.getDeleteTUserTable(user_id);
		int studentResult = aTeacherService.getDeleteTeacherTable(t_idx);
		return (userResult > 0 && studentResult > 0) ? "OK" : "FAIL";
	}
	
	// 학생 관리-> 학생 등록페이지 이동
	@GetMapping("/amoveInsertTeacher")
	public ModelAndView moveInsertStudent() {
		ModelAndView mv = new ModelAndView();
		List<Map<String, Object>> deptList = aTeacherService.getDeptList();
		mv.addObject("deptList", deptList);
		mv.setViewName("admin/userpage/insertTeacher");
		return mv;
	}
	
	// 일괄등록 페이지
	@GetMapping("/amoveInsertManyTeacher")
	public ModelAndView amoveInsertManyTeacher() {
		return new ModelAndView("admin/userpage/insertManyTeacher");
	}
	
	// 엑셀 파일 업로드 페이지를 GET 방식으로 처리
	@GetMapping("/uploadTeacherExcel")
	public String showUploadPage() {
		return "admin/userpage/insertManyTeacher"; // 업로드 페이지를 JSP로 반환
	}
	
	// 엑셀 파일 업로드 처리
	@PostMapping("/uploadTeacherExcel")
	public String handleFileUpload(@RequestParam("excelFile") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "엑셀 파일을 선택하세요!");
			return "redirect:/uploadTeacherExcel";
		}

		try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheetAt(0);
			List<TeacherExcelDTO> dtoList = new ArrayList<>();

			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				TeacherExcelDTO dto = new TeacherExcelDTO();
				dto.setName(getCellValueAsString(row.getCell(1)));
				dto.setBirth(getCellValueAsString(row.getCell(2)));
				dto.setEmail(getCellValueAsString(row.getCell(3)));
				dto.setPhone(getCellValueAsString(row.getCell(4)));
				dto.setDept_name(getCellValueAsString(row.getCell(5)));
				dto.setT_dept_chair(getCellValueAsString(row.getCell(6)));
				dtoList.add(dto);
				
				System.out.println(getCellValueAsString(row.getCell(1)));
			}
			
			System.out.println(dtoList);

			// 엑셀 데이터를 모델에 전달하여 JSP에서 출력
			redirectAttributes.addFlashAttribute("TeacherList", dtoList);
			redirectAttributes.addFlashAttribute("message", "엑셀 업로드 성공!");
		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "엑셀 처리 중 오류 발생");
		}

		return "redirect:/uploadTeacherExcel"; // 업로드 후 다시 엑셀 업로드 페이지로 이동
	}

	// 셀 값이 숫자형이든 문자열이든 모두 문자열로 반환하는 헬퍼 메서드
	private String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case NUMERIC:
			    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
			        // 날짜 형식 처리
			        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
			    } else {
			        double value = cell.getNumericCellValue();
			        // 정수인지 확인 후 포맷
			        if (value == (long) value) {
			            return String.valueOf((long) value); // 정수로 변환
			        } else {
			            return String.valueOf(value); // 실수 그대로 출력
			        }
			    }
		case STRING:
			// 문자열 형식일 경우 그대로 반환
			return cell.getStringCellValue();
		case BOOLEAN:
			// 불리언 값은 "true" 또는 "false"로 반환
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			// 수식 값은 수식 그대로 반환
			return cell.getCellFormula();
		default:
			return ""; // 기타 타입은 빈 문자열로 반환
		}
	}

	// 일괄등록 처리
	@PostMapping("/saveTeacherData")
	public ModelAndView saveTeacherData(HttpServletRequest request) {
	    ModelAndView mv = new ModelAndView();
	    String[] names = request.getParameterValues("name");
	    String[] births = request.getParameterValues("birth");
	    String[] emails = request.getParameterValues("email");
	    String[] phones = request.getParameterValues("phone");
	    String[] dept_names = request.getParameterValues("dept_name");
	    String[] t_dept_chairs = request.getParameterValues("t_dept_chair");

	    int currentYear = LocalDate.now().getYear();
	    int[] dept_idxs = new int[names.length];

	    List<Map<String, Object>> successList = new ArrayList<>();
	    List<Map<String, Object>> failList = new ArrayList<>();
	    boolean allFailed = true;

	    for (int i = 0; i < names.length; i++) {
	        try {
	            UserVO userVO = new UserVO();
	            TeacherVO teacherVO = new TeacherVO();

	            // 비밀번호
	            String birth6 = births[i].replaceAll("-", "").substring(2, 8);
	            String phoneDigits = phones[i].replaceAll("[^0-9]", "");
	            String phoneLast4 = phoneDigits.substring(phoneDigits.length() - 4);
	            String rawPw = birth6 + phoneLast4;
	            userVO.setUser_pw(passwordEncoder.encode(rawPw));

	            // 학번
	            int dept_idx = aTeacherService.getDeptIdxByName(dept_names[i]);
	            dept_idxs[i] = dept_idx;
	            teacherVO.setDept_idx(String.valueOf(dept_idx));
	            int count = aTeacherService.countTeachersThisYear(String.valueOf(currentYear), String.valueOf(dept_idx));
	            String serial = String.format("%03d", count + 1);
	            String TeacherNumber = "S" + currentYear + "00" + dept_idx + serial;
	            userVO.setUser_id(TeacherNumber);

	            // 나머지 정보 세팅
	            userVO.setName(names[i]);
	            userVO.setBirth(births[i]);
	            userVO.setPhone(phones[i]);
	            userVO.setEmail(emails[i]);
	            userVO.setUser_level("1");
	            userVO.setStatus("4");

	            teacherVO.setT_dept_chair(t_dept_chairs[i]);

	            // INSERT
	            aTeacherService.teacherManyInsert(userVO, teacherVO);

	            // studentVO에도 user_idx 세팅
	            teacherVO.setUser_idx(userVO.getUser_idx());

	            Map<String, Object> successData = new HashMap<>();
	            successData.put("user_id", userVO.getUser_id());
	            successData.put("name", userVO.getName());
	            successData.put("birth", userVO.getBirth());
	            successData.put("email", userVO.getEmail());
	            successData.put("phone", userVO.getPhone());
	            successData.put("dept_name", dept_names[i]);
	            successData.put("t_dept_chair", teacherVO.getT_dept_chair());
	            successList.add(successData);

	            allFailed = false;

	        } catch (Exception e) {
	            Map<String, Object> failData = new HashMap<>();
	            failData.put("name", names[i]);
	            failData.put("birth", births[i]);
	            failData.put("email", emails[i]);
	            failData.put("phone", phones[i]);
	            failData.put("dept_name", dept_names[i]);
	            failData.put("t_dept_chair", t_dept_chairs[i]);
	            failList.add(failData);
	        }
	    }

	    mv.addObject("successList", successList);
	    mv.addObject("failList", failList);
	    mv.addObject("allFailed", allFailed);
	    mv.setViewName("admin/userpage/insertManyTeacherResult");
	    return mv;
	}
	

}
