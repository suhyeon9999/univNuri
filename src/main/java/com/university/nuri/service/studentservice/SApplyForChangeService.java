package com.university.nuri.service.studentservice;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.university.nuri.vo.commonvo.RequestFileVO;
import com.university.nuri.vo.commonvo.RequestVO;

public interface SApplyForChangeService {

	public List<Map<String, Object>> getApplyList(int s_idx);

	public List<Map<String, Object>> filteredSApplyList(int s_idx, Integer req_type, Integer req_response);

	public int insertRequestTable(RequestVO rvo);

	public void insertRequestFileTable(RequestFileVO rfvo);

	public RequestVO getRequestDetail(int req_idx);

	public List<RequestFileVO> getRequestFileList(int req_idx);


	
	// -----------------------------------------------
	public int updateRequest(RequestVO rvo); // 신청 내용 수정

	public int deleteRequestFilesByReqIdx(String req_idx); // 전체 파일 일괄 삭제 (추가됨)

	public int insertRequestFile(RequestFileVO fileVO); // 새 파일 추가

	// -----------------------------------------------
	public int requestDeleteOk(int req_idx);

	public int requestFileDeleteOk(int req_idx);

}
