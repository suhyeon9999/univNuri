package com.university.nuri.repository.adminrepository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.commonvo.RequestFileVO;
import com.university.nuri.vo.commonvo.RequestVO;

@Repository
public class RequestDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<Map<String, Object>> getRequestList() {
		try {
			return sqlSessionTemplate.selectList("request.getRequestList");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<String, Object> getRequestDetail(int req_idx) {
		try {
			return sqlSessionTemplate.selectOne("request.getRequestDetail", req_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<RequestFileVO> getRequestFileList(int req_idx) {
		try {
			return sqlSessionTemplate.selectList("request.getRequestFileList", req_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void updateApproval(Map<String, Object> updateMap) {
		try {
			sqlSessionTemplate.update("request.updateApproval", updateMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateStatus(Map<String, Object> userMap) {
		try {
			sqlSessionTemplate.update("request.updateStatus", userMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
