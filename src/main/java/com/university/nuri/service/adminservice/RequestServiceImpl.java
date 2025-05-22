package com.university.nuri.service.adminservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.nuri.repository.adminrepository.RequestDAO;
import com.university.nuri.vo.commonvo.RequestFileVO;
import com.university.nuri.vo.commonvo.RequestVO;

@Service
public class RequestServiceImpl implements RequestService {
	@Autowired RequestDAO requestDAO;

	@Override
	public List<Map<String, Object>> getRequestList() {
		return requestDAO.getRequestList();
	}

	@Override
	public Map<String, Object> getRequestDetail(int req_idx) {
		return requestDAO.getRequestDetail(req_idx);
	}

	@Override
	public List<RequestFileVO> getRequestFileList(int req_idx) {
		return requestDAO.getRequestFileList(req_idx);
	}

	@Override
	@Transactional
	public void requestResponse(int req_idx, int admin_idx, boolean isReject, int req_type) {
		Map<String, Object> detailMap = requestDAO.getRequestDetail(req_idx);
		int user_idx = Integer.parseInt(String.valueOf(detailMap.get("user_idx")));

		// 1. request 테이블 업데이트 (승인 or 반려)
		Map<String, Object> updateMap = new HashMap<>();
		updateMap.put("req_idx", req_idx);
		updateMap.put("admin_idx", admin_idx);
		updateMap.put("req_response", isReject ? 2 : 1); // 1: 승인, 2: 반려
		requestDAO.updateApproval(updateMap);

		// 2. 승인인 경우에만 user.status 변경
		if (!isReject) {
			Map<String, Object> userMap = new HashMap<>();
			userMap.put("user_idx", user_idx);
			int status = 0;
			if(req_type == 1) {
				status = 1;
			}else {
				status = 2;
			}
			userMap.put("status", status);

			requestDAO.updateStatus(userMap);
		}
	}


}
