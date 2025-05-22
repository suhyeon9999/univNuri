package com.university.nuri.repository.teacherrepository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.commonvo.AssignFileVO;
import com.university.nuri.vo.commonvo.AssignVO;
import com.university.nuri.vo.commonvo.FileVO;
import com.university.nuri.vo.studentvo.SubmitFileVO;
import com.university.nuri.vo.studentvo.SubmitVO;


@Repository
public class AssignDAO {
	
	@Autowired
	private SqlSessionTemplate sessionTemplate;


    // Map을 반환하는 메서드
    public List<Map<String, Object>> getSubmitList(String assign_idx) {
        return sessionTemplate.selectList("assign.submitList", assign_idx);
    }


	public int countSubmit(String assign_idx) {
		return sessionTemplate.selectOne("assign.countSubmit",assign_idx);
	}


	public int countCourseS(String assign_idx) {
		return sessionTemplate.selectOne("assign.countCourseS", assign_idx);
	}


	public AssignVO assignDetail(String assign_idx) {
		return sessionTemplate.selectOne("assign.assignDetail", assign_idx);
	}


	public Map<String, Object> getTLecName(String lect_idx) {
		return sessionTemplate.selectOne("assign.getTLecName", lect_idx);
	}


	public SubmitVO getSubmitDetail(String submit_idx) {
		return sessionTemplate.selectOne("assign.getSubmitDetail", submit_idx);
	}


	public int assignListMakeInsert(AssignVO assignVO) {
		return sessionTemplate.insert("assign.assignListMakeInsert", assignVO);
	}


	public int assignUpdateOK(AssignVO assignVO) {
		return sessionTemplate.update("assign.assginUpdate", assignVO);
	}


	public String getPwd(String t_idx) {
		try {
			return sessionTemplate.selectOne("assign.getPwd", t_idx);
		} catch (Exception e) {
			return null;
		}
	}


	public int getAssignDelete(String assign_idx) {
		try {
			return sessionTemplate.update("assign.assignDelete", assign_idx);
		} catch (Exception e) {
			return 0;
		}
	}


	public Map<String, Object> getStuName(String submit_idx) {
		try {
			return sessionTemplate.selectOne("assign.getStuName", submit_idx);
		} catch (Exception e) {
			return null;
		}
	}


	public int insertAssignFile(AssignFileVO fileVO) {
		try {
			return sessionTemplate.insert("assign.insertAssignFile", fileVO);
		} catch (Exception e) {
			return 0;
		}
	}


	public List<AssignFileVO> getAssignFiles(String assign_idx) {
		try {
			return sessionTemplate.selectList("assign.getAssignFiles", assign_idx);
		} catch (Exception e) {
			return null;
		}
	}


	public List<AssignVO> getDueAssignments() {
		try {
			return sessionTemplate.selectList("assign.getDueAssignments", sessionTemplate);
		} catch (Exception e) {
			return null;
		}
	}


	public int updateAssignEnd(String assign_idx) {
		try {
			return sessionTemplate.update("assign.updateAssignEnd", assign_idx);
		} catch (Exception e) {
			return 0;
		}
	}


	public List<SubmitFileVO> getSubmitFiles(String submit_idx) {
		try {
			return sessionTemplate.selectList("assign.getSubmitFiles", submit_idx);
		} catch (Exception e) {
			return null;
		}
	}


	public int deleteAssignFilesByAssignIdx(String assign_idx) {
		try {
			return sessionTemplate.delete("assign.deleteAssignFilesByAssignIdx",assign_idx);
		} catch (Exception e) {
			return 0;
		}
	}


	public String isEnd(String assign_idx) {
		try {
			return sessionTemplate.selectOne("assign.isEnd", assign_idx);
		} catch (Exception e) {
			return null;
		}
	}
}