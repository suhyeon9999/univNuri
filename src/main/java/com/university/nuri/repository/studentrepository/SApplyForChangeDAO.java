package com.university.nuri.repository.studentrepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.university.nuri.vo.commonvo.RequestFileVO;
import com.university.nuri.vo.commonvo.RequestVO;

@Repository
public class SApplyForChangeDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<Map<String, Object>> getApplyList(int s_idx) {
		try {
			return sqlSessionTemplate.selectList("sapplyforchange.getApplyList", s_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, Object>> filteredSApplyList(int s_idx, Integer req_type, Integer req_response) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("s_idx", s_idx);
			params.put("req_type", req_type);
			params.put("req_response", req_response);
			return sqlSessionTemplate.selectList("sapplyforchange.filteredSApplyList", params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int insertRequestTable(RequestVO rvo) {
		try {
			return sqlSessionTemplate.insert("sapplyforchange.insertRequestTable", rvo);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void insertRequestFileTable(RequestFileVO rfvo) {
		try {
			sqlSessionTemplate.insert("sapplyforchange.insertRequestFileTable", rfvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RequestVO getRequestDetail(int req_idx) {
		try {
			return sqlSessionTemplate.selectOne("sapplyforchange.getRequestDetail", req_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<RequestFileVO> getRequestFileList(int req_idx) {
		try {
			return sqlSessionTemplate.selectList("sapplyforchange.getRequestFileList", req_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	// -----------------------------------------------------

	    public int updateRequest(RequestVO rvo) {
	        try {
	            return sqlSessionTemplate.update("sapplyforchange.updateRequest", rvo);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return 0;
	        }
	    }

	    public int insertNewFile(RequestFileVO rfvo) {
	        try {
	            return sqlSessionTemplate.insert("sapplyforchange.insertNewFile", rfvo);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return 0;
	        }
	    }
	    
	    public int deleteRequestFilesByReqIdx(String req_idx) {
	        try {
	            return sqlSessionTemplate.delete("sapplyforchange.deleteRequestFilesByReqIdx", req_idx);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return 0;
	        }
	    }

	public int requestDeleteOk(int req_idx) {
		try {
			return sqlSessionTemplate.update("sapplyforchange.requestDeleteOk", req_idx);
		} catch (Exception e) {
	            e.printStackTrace();
	            return 0;
		}
	}

	public int requestFileDeleteOk(int req_idx) {
		try {
			return sqlSessionTemplate.delete("sapplyforchange.requestFileDeleteOk", req_idx);
		} catch (Exception e) {
	            e.printStackTrace();
	            return 0;
		}
	}

}
