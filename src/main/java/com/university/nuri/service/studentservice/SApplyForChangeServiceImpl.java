package com.university.nuri.service.studentservice;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.university.nuri.repository.studentrepository.SApplyForChangeDAO;
import com.university.nuri.vo.commonvo.RequestFileVO;
import com.university.nuri.vo.commonvo.RequestVO;

@Service
public class SApplyForChangeServiceImpl implements SApplyForChangeService {
	@Autowired
	private SApplyForChangeDAO sApplyForChangeDAO;

	@Override
	public List<Map<String, Object>> getApplyList(int s_idx) {
		return sApplyForChangeDAO.getApplyList(s_idx);
	}

	@Override
	public List<Map<String, Object>> filteredSApplyList(int s_idx, Integer req_type, Integer req_response) {
		return sApplyForChangeDAO.filteredSApplyList(s_idx, req_type, req_response);
	}

	@Override
	public int insertRequestTable(RequestVO rvo) {
		return sApplyForChangeDAO.insertRequestTable(rvo);
	}

	@Override
	public void insertRequestFileTable(RequestFileVO rfvo) {
		sApplyForChangeDAO.insertRequestFileTable(rfvo);
	}

	@Override
	public RequestVO getRequestDetail(int req_idx) {
		return sApplyForChangeDAO.getRequestDetail(req_idx);
	}

	@Override
	public List<RequestFileVO> getRequestFileList(int req_idx) {
		return sApplyForChangeDAO.getRequestFileList(req_idx);
	}

	// ---------------------------------------
	@Override
	public int updateRequest(RequestVO rvo) {
	    return sApplyForChangeDAO.updateRequest(rvo);
	}

	@Override
	public int deleteRequestFilesByReqIdx(String req_idx) {
	    return sApplyForChangeDAO.deleteRequestFilesByReqIdx(req_idx);
	}

	@Override
	public int insertRequestFile(RequestFileVO rfvo) {
		return sApplyForChangeDAO.insertNewFile(rfvo); 
	}

	
	// -----------------------------------------------
	@Override
	public int requestDeleteOk(int req_idx) {
		return sApplyForChangeDAO.requestDeleteOk(req_idx);
	}
	
	@Override
	public int requestFileDeleteOk(int req_idx) {
		return sApplyForChangeDAO.requestFileDeleteOk(req_idx);
	}
	





}
