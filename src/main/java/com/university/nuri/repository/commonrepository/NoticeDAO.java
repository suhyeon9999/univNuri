package com.university.nuri.repository.commonrepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.university.nuri.vo.commonvo.FaqVO;
import com.university.nuri.vo.commonvo.NoticeVO;

@Repository
public class NoticeDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	// 공지사항 등록
	public int insertNotice(NoticeVO noticeVO) {
		try {
			return sqlSessionTemplate.insert("notice.insertNotice",noticeVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 전체 게시물 계산
	public int getCountNotice() {
		try {
			return sqlSessionTemplate.selectOne("notice.getCountNotice");			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 중요공지 최신5개
	public List<Map<String, Object>> getNecessaryNoticeList() {
		try {
			return sqlSessionTemplate.selectList("notice.getNecessaryNoticeList");
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 중요공지 최신5개 idx
	public List<Long> getNecessaryNoticeIdx() {
		try {
			return sqlSessionTemplate.selectList("notice.getNecessaryNoticeIdx");			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	// 공지사항 리스트 불러오기
	public List<Map<String, Object>> getAllNoticeList(int limit, int offset) {
		try {
			Map<String, Integer> map = new HashedMap<String, Integer>();
			map.put("limit", limit);
			map.put("offset", offset);
			return sqlSessionTemplate.selectList("notice.getAllNoticeList", map);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 공지사항 자세히보기
	public Map<String, Object> detailNotice(String notice_idx) {
		try {
			// 현재 곻지사항
			return sqlSessionTemplate.selectOne("notice.detailNotice", notice_idx);		
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Map<String, Object> nextNotice(String notice_idx) {
		try {
			// 이전글
			return sqlSessionTemplate.selectOne("notice.previousNotice", notice_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Map<String, Object> previousNotice(String notice_idx) {
		try {
			// 다음글
			return sqlSessionTemplate.selectOne("notice.nextNotice", notice_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 공지사항 검색
	public List<Map<String, Object>> searchNotice(String search,int limit, int offset) {
		try {
			Map<String, Object> map = new HashedMap<String, Object>();
			map.put("limit", limit);
			map.put("offset", offset);
			map.put("search", search);
			return sqlSessionTemplate.selectList("notice.searchNotice", map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 조건게시물수
	public int getSearchNoticeCount(String search) {
		try {
			return sqlSessionTemplate.selectOne("notice.getSearchNoticeCount",search);			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 공지사항 수정
	public int noticeUpdate(NoticeVO noticeVO) {
		try {
			return sqlSessionTemplate.update("notice.noticeUpdate",noticeVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// 공지사항 삭제
	public int deleteNotice(String notice_idx) {
		try {
			return sqlSessionTemplate.update("notice.deleteNotice",notice_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	
	// FAQ
		// faq카운트
	public int getCountFaq() {
		try {
			return sqlSessionTemplate.selectOne("notice.getCountFaq");			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// faq 리스트
	public List<Map<String, Object>> getAllFaqList(int limit, int offset) {
		try {
			Map<String, Integer> map = new HashedMap<String, Integer>();
			map.put("limit", limit);
			map.put("offset", offset);
			System.out.println(map);
			return sqlSessionTemplate.selectList("notice.getAllFaqList", map);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 조건게시물수
	public int getSearchFaqCount(String search) {
		try {
			return sqlSessionTemplate.selectOne("notice.getSearchFaqCount",search);			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// faq 조건검색
	public List<Map<String, Object>> searchFaq(String search,int limit, int offset) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("limit", limit);
			map.put("offset", offset);
			map.put("search", search);
			return sqlSessionTemplate.selectList("notice.searchFaq",map);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// faq 자세히 보기
	public Map<String, Object> detaiFaq(String faq_idx) {
		try {
		// 현재 faq
		return sqlSessionTemplate.selectOne("notice.detaiFaq", faq_idx);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Map<String, Object> nextFaq(String faq_idx) {
		try {
			// 다음faq
			return sqlSessionTemplate.selectOne("notice.nextFaq", faq_idx);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Map<String, Object> previousFaq(String faq_idx) {
		try {
			// 이전faq
			return sqlSessionTemplate.selectOne("notice.previousFaq", faq_idx);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// faq 작성
	public int faqInsert(FaqVO faqVO) {
		try {
			return sqlSessionTemplate.insert("notice.faqInsert",faqVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// faq 수정
	public int faqUpdate(FaqVO faqVO) {
		try {
			return sqlSessionTemplate.update("notice.faqUpdate",faqVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// faq 삭제
	public int deleteFaq(String faq_idx) {
		try {
			return sqlSessionTemplate.update("notice.deleteFaq",faq_idx);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	}
