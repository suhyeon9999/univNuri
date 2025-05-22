package com.university.nuri.service.teacherservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.teacherrepository.AssignDAO;
import com.university.nuri.vo.commonvo.AssignFileVO;
import com.university.nuri.vo.commonvo.AssignVO;
import com.university.nuri.vo.commonvo.FileVO;
import com.university.nuri.vo.studentvo.SubmitFileVO;
import com.university.nuri.vo.studentvo.SubmitVO;

@Service
public class AssignServiceImpl implements AssignService{
	
	@Autowired
	private AssignDAO assignDAO;

    @Override
    public List<Map<String, Object>> getSubmitList(String assign_idx) {
        return assignDAO.getSubmitList(assign_idx);
    }

	@Override
	public int countSubmit(String assign_idx) {
		return assignDAO.countSubmit(assign_idx);
	}

	@Override
	public int countCourseS(String assign_idx) {
		return assignDAO.countCourseS(assign_idx);
	}

	@Override
	public AssignVO assignDetail(String assign_idx) {
		return assignDAO.assignDetail(assign_idx);
	}

	@Override
	public Map<String, Object> getTLecName(String lect_idx) {
		return assignDAO.getTLecName(lect_idx);
	}

	@Override
	public SubmitVO getSubmitDetail(String submit_idx) {
		return assignDAO.getSubmitDetail(submit_idx);
	}

	@Override
	public int assignListMakeInsert(AssignVO assignVO) {
		return assignDAO.assignListMakeInsert(assignVO);
	}

	@Override
	public int assignUpdateOK(AssignVO assignVO) {
		return assignDAO.assignUpdateOK(assignVO);
	}

	@Override
	public String getPwd(String t_idx) {
		return assignDAO.getPwd(t_idx);
	}

	@Override
	public int getAssignDelete(String assign_idx) {
		return assignDAO.getAssignDelete(assign_idx);
	}

	@Override
	public Map<String, Object> getStuName(String submit_idx) {
		return assignDAO.getStuName(submit_idx);
	}

	@Override
	public String getFileType(String originName) {
	    if (originName.endsWith(".jpg") || originName.endsWith(".png")) return "0";
	    else if (originName.endsWith(".txt")) return "1";
	    else if (originName.endsWith(".ppt") || originName.endsWith(".pptx")) return "2";
	    else if (originName.endsWith(".pdf")) return "3";
	    else if (originName.endsWith(".doc") || originName.endsWith(".docx")) return "4";
		return null;
	}

	@Override
	public int insertAssignFile(AssignFileVO fileVO) {
		return assignDAO.insertAssignFile(fileVO);
	}

	@Override
	public List<AssignFileVO> getAssignFiles(String assign_idx) {
		return assignDAO.getAssignFiles(assign_idx);
	}

	@Override
	public List<AssignVO> getDueAssignments() {
		return assignDAO.getDueAssignments();
	}

	@Override
	public int updateAssignEnd(String assign_idx) {
		return assignDAO.updateAssignEnd(assign_idx);
	}

	@Override
	public List<SubmitFileVO> getSubmitFiles(String submit_idx) {
		return assignDAO.getSubmitFiles(submit_idx);
	}

	@Override
	public int deleteAssignFilesByAssignIdx(String assign_idx) {
		return assignDAO.deleteAssignFilesByAssignIdx(assign_idx);
	}

	@Override
	public String isEnd(String assign_idx) {
		return assignDAO.isEnd(assign_idx);
	}
}
