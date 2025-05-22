package com.university.nuri.service.commonservice;

import java.util.List;
import java.util.Map;

import com.university.nuri.vo.commonvo.FaqVO;
import com.university.nuri.vo.commonvo.NoticeVO;

public interface NoticeService {
	
	// 공지사항
	// 공지사항 작성
	int insertNotice(NoticeVO noticeVO);
	// 전체 계시물 계산
	int getCountNotice();
	// 중요공지 최신 5개
	List<Map<String, Object>> getNecessaryNoticeList();
	// 공지사항 리스트 불러오기
	List<Map<String, Object>> getAllNoticeList(int limit, int offset);
	// 공지사항 자세히 보기
	Map<String, Object> detailNotice(String notice_idx);
	Map<String, Object> previousNotice(String notice_idx);
	Map<String, Object> nextNotice(String notice_idx);
	// 공지사항 검색
	int getSearchNoticeCount(String search);
	List<Map<String, Object>> searchNotice(String search,int limit, int offset);
	// 공지사항 수정
	int noticeUpdate(NoticeVO noticeVO);
	// 공지사항 삭제
	int deleteNotice(String notice_idx);
	
	
	// FAQ
	// faq 전체 게시물 계산
	int getCountFaq();
	// faq리스트 불러오기
	List<Map<String, Object>> getAllFaqList(int limit, int offset);
	// faq조건검색
	int getSearchFaqCount(String search);
	List<Map<String, Object>> searchFaq(String search, int limit, int offset);
	// faq 자세히 보기
	Map<String, Object> detailFaq(String faq_idx);
	Map<String, Object> previousFaq(String faq_idx);
	Map<String, Object> nextFaq(String faq_idx);
	// faq 작성
	int faqInsert(FaqVO faqVO);
	// faq 수정
	int faqUpdate(FaqVO faqVO);
	// faq 삭제
	int deleteFaq(String faq_idx);
	

}
