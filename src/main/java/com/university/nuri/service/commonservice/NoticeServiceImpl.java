package com.university.nuri.service.commonservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.university.nuri.repository.commonrepository.NoticeDAO;
import com.university.nuri.vo.commonvo.FaqVO;
import com.university.nuri.vo.commonvo.NoticeVO;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeDAO noticeDAO;

	// 공지사항
	// 공지사항 작성
	@Override
	public int insertNotice(NoticeVO noticeVO) {
		return noticeDAO.insertNotice(noticeVO);
	}

	// 전체 게시물 계산
	@Override
	public int getCountNotice() {
		return noticeDAO.getCountNotice();
	}

	// 중요공지 최신5개
	@Override
	public List<Map<String, Object>> getNecessaryNoticeList() {
		return noticeDAO.getNecessaryNoticeList();
	}

	// 공지사항 리스트 불러오기
	@Override
	public List<Map<String, Object>> getAllNoticeList(int limit, int offset) {
		// 최신 중요 공지사항 5개의 notice_idx 조회
		List<Long> topNoticeIds = noticeDAO.getNecessaryNoticeIdx();

		// 모든 공지사항 조회
		List<Map<String, Object>> allNotices = noticeDAO.getAllNoticeList(limit, offset);
		
		// 최신 중요 공지사항 5개 제외
		List<Map<String, Object>> filteredNotices = allNotices.stream()
				.filter(notice -> !topNoticeIds.contains(notice.get("noticeIdx"))).collect(Collectors.toList());

		// 페이지네이션 적용
		int start = Math.min(offset, filteredNotices.size());
		int end = Math.min(start + limit, filteredNotices.size());
		return filteredNotices.subList(start, end);
	}

	// 공지사항 자세히보기
	@Override
	public Map<String, Object> detailNotice(String notice_idx) {
		return noticeDAO.detailNotice(notice_idx);
		
	}
	@Override
	public Map<String, Object> nextNotice(String notice_idx) {
		return noticeDAO.nextNotice(notice_idx);
		
	}
	@Override
	public Map<String, Object> previousNotice(String notice_idx) {
		return noticeDAO.previousNotice(notice_idx);
	}

	// 공지사항 검색
	@Override
	public List<Map<String, Object>> searchNotice(String search,int limit, int offset) {
		return noticeDAO.searchNotice(search,limit, offset);
	}
	// 검색카운트
	@Override
	public int getSearchNoticeCount(String search) {
		// 검색카운트
		return noticeDAO.getSearchNoticeCount(search);
	}
	// 공지사항 수정
	@Override
	public int noticeUpdate(NoticeVO noticeVO) {
		return noticeDAO.noticeUpdate(noticeVO);
	}
	
	// 공지사항 삭제
	@Override
	public int deleteNotice(String notice_idx) {
		return noticeDAO.deleteNotice(notice_idx);
	}
	
	
	// FAQ
	// faq카운트
	@Override
	public int getCountFaq() {
		return noticeDAO.getCountFaq();
	}
	// faq 리스트
	@Override
	public List<Map<String, Object>> getAllFaqList(int limit, int offset) {
		return noticeDAO.getAllFaqList(limit, offset);
	}
	// faq 조건검색
	// 검색카운트
	@Override
	public int getSearchFaqCount(String search) {
		return noticeDAO.getSearchFaqCount(search);
	}
	@Override
	public List<Map<String, Object>> searchFaq(String search,int limit, int offset) {
		return noticeDAO.searchFaq(search,limit, offset);
	}
	// faq 자세히 보기
	@Override
	public Map<String, Object> detailFaq(String faq_idx) {
		return noticeDAO.detaiFaq(faq_idx);
	}
	@Override
	public Map<String, Object> nextFaq(String faq_idx) {
		return noticeDAO.nextFaq(faq_idx);
	}
	@Override
	public Map<String, Object> previousFaq(String faq_idx) {
		return noticeDAO.previousFaq(faq_idx);
	}
	// faq 등록
	@Override
	public int faqInsert(FaqVO faqVO) {
		return noticeDAO.faqInsert(faqVO);
	}
	// faq 수정
	@Override
	public int faqUpdate(FaqVO faqVO) {
		return noticeDAO.faqUpdate(faqVO);
	}

	// faq 삭제
	@Override
	public int deleteFaq(String faq_idx) {
		return noticeDAO.deleteFaq(faq_idx);
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}