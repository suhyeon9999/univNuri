package com.university.nuri.service.adminservice;

import java.util.List;
import java.util.Map;

import com.university.nuri.vo.commonvo.RequestFileVO;
import com.university.nuri.vo.commonvo.RequestVO;

public interface RequestService {

	public List<Map<String, Object>> getRequestList();

	public Map<String, Object> getRequestDetail(int req_idx);

	public List<RequestFileVO> getRequestFileList(int req_idx);

	public void requestResponse(int req_idx, int admin_idx, boolean isReject, int req_type);


}
