package com.university.nuri.controller.admincontroller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.university.nuri.service.adminservice.ExcelService;
import com.university.nuri.vo.adminvo.StudentExcelDTO;
import com.university.nuri.vo.commonvo.UserVO;
import com.university.nuri.vo.studentvo.StudentVO;

@Controller
public class ExcelController {

	@Autowired
	private ExcelService excelService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// 엑셀 파일 업로드 페이지를 GET 방식으로 처리
	@GetMapping("/uploadExcel")
	public String showUploadPage() {
		return "admin/userpage/insertManyStudent"; // 업로드 페이지를 JSP로 반환
	}

	// 엑셀 파일 업로드 처리
	@PostMapping("/uploadExcel")
	public String handleFileUpload(@RequestParam("excelFile") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "엑셀 파일을 선택하세요!");
			return "redirect:/uploadExcel";
		}

		try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheetAt(0);
			List<StudentExcelDTO> dtoList = new ArrayList<>();

			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				StudentExcelDTO dto = new StudentExcelDTO();
				dto.setName(getCellValueAsString(row.getCell(1)));
				dto.setBirth(getCellValueAsString(row.getCell(2)));
				dto.setEmail(getCellValueAsString(row.getCell(3)));
				dto.setPhone(getCellValueAsString(row.getCell(4)));
				dto.setS_grade(getCellValueAsString(row.getCell(5)));
				dto.setS_dept(getCellValueAsString(row.getCell(6)));
				dto.setS_address(getCellValueAsString(row.getCell(7)));
				dto.setS_address2(getCellValueAsString(row.getCell(8)));
				dtoList.add(dto);
			}

			// 엑셀 데이터를 모델에 전달하여 JSP에서 출력
			redirectAttributes.addFlashAttribute("studentList", dtoList);
			redirectAttributes.addFlashAttribute("message", "엑셀 업로드 성공!");
		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "엑셀 처리 중 오류 발생");
		}

		return "redirect:/uploadExcel"; // 업로드 후 다시 엑셀 업로드 페이지로 이동
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
	@PostMapping("/saveStudentData")
	public ModelAndView saveStudentData(HttpServletRequest request) {
	    ModelAndView mv = new ModelAndView();
	    String[] names = request.getParameterValues("name");
	    String[] births = request.getParameterValues("birth");
	    String[] emails = request.getParameterValues("email");
	    String[] phones = request.getParameterValues("phone");
	    String[] s_grades = request.getParameterValues("s_grade");
	    String[] dept_names = request.getParameterValues("dept_name");
	    String[] s_addresses = request.getParameterValues("s_address");
	    String[] s_address2s = request.getParameterValues("s_address2");

	    int currentYear = LocalDate.now().getYear();
	    int[] dept_idxs = new int[names.length];

	    List<Map<String, Object>> successList = new ArrayList<>();
	    List<Map<String, Object>> failList = new ArrayList<>();
	    boolean allFailed = true;

	    for (int i = 0; i < names.length; i++) {
	        try {
	            UserVO userVO = new UserVO();
	            StudentVO studentVO = new StudentVO();

	            // 비밀번호
	            String birth6 = births[i].replaceAll("-", "").substring(2, 8);
	            String phoneDigits = phones[i].replaceAll("[^0-9]", "");
	            String phoneLast4 = phoneDigits.substring(phoneDigits.length() - 4);
	            String rawPw = birth6 + phoneLast4;
	            userVO.setUser_pw(passwordEncoder.encode(rawPw));

	            // 학번
	            int dept_idx = excelService.getDeptIdxByName(dept_names[i]);
	            dept_idxs[i] = dept_idx;
	            studentVO.setDept_idx(String.valueOf(dept_idx));
	            int count = excelService.countStudentsThisYear(String.valueOf(currentYear), String.valueOf(dept_idx));
	            String serial = String.format("%03d", count + 1);
	            String studentNumber = "S" + currentYear + "00" + dept_idx + serial;
	            userVO.setUser_id(studentNumber);

	            // 나머지 정보 세팅
	            userVO.setName(names[i]);
	            userVO.setBirth(births[i]);
	            userVO.setPhone(phones[i]);
	            userVO.setEmail(emails[i]);
	            userVO.setUser_level("0");
	            userVO.setStatus("1");

	            studentVO.setS_grade(s_grades[i]);
	            studentVO.setS_address(s_addresses[i]);
	            studentVO.setS_address2(s_address2s[i]);

	            // INSERT
	            excelService.studentManyInsert(userVO, studentVO);

	            // studentVO에도 user_idx 세팅
	            studentVO.setUser_idx(userVO.getUser_idx());

	            Map<String, Object> successData = new HashMap<>();
	            successData.put("user_id", userVO.getUser_id());
	            successData.put("name", userVO.getName());
	            successData.put("birth", userVO.getBirth());
	            successData.put("email", userVO.getEmail());
	            successData.put("phone", userVO.getPhone());
	            successData.put("s_grade", studentVO.getS_grade());
	            successData.put("dept_name", dept_names[i]);
	            successData.put("s_address", studentVO.getS_address());
	            successData.put("s_address2", studentVO.getS_address2());
	            successList.add(successData);

	            allFailed = false;

	        } catch (Exception e) {
	            Map<String, Object> failData = new HashMap<>();
	            failData.put("name", names[i]);
	            failData.put("birth", births[i]);
	            failData.put("email", emails[i]);
	            failData.put("phone", phones[i]);
	            failData.put("s_grade", s_grades[i]);
	            failData.put("dept_name", dept_names[i]);
	            failData.put("s_address", s_addresses[i]);
	            failData.put("s_address2", s_address2s[i]);
	            failList.add(failData);
	        }
	    }

	    mv.addObject("successList", successList);
	    mv.addObject("failList", failList);
	    mv.addObject("allFailed", allFailed);
	    mv.setViewName("admin/userpage/insertManyStudentResult");
	    return mv;
	}
}
