package com.university.nuri.controller.commoncontroller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.university.nuri.service.commonservice.NoticeService;
import com.university.nuri.vo.commonvo.FaqVO;
import com.university.nuri.vo.commonvo.NoticeVO;
import com.university.nuri.vo.commonvo.Paging;
@Controller
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private Paging paging;
	
	// 공지사항
	// admin 네비 -> 공지사항 연결
	@GetMapping("/noticeList")
	public ModelAndView noticeList(HttpServletRequest request) {
		try {
			ModelAndView mv = new ModelAndView();
			// 페이징 (중요 공지사항 : 최신4개 상단 고정, 일반공지 떠로 처리)
			// 중요공지 최신 5개 조회
			List<Map<String, Object>> necessaryNoticeList = noticeService.getNecessaryNoticeList();
			mv.addObject("necessaryNoticeList",necessaryNoticeList);
			
			// 전체 공지사항 DB에서 가져와서 count에 저장			
			int count = noticeService.getCountNotice();
			paging.setTotalRecord(count);
			
			// 전체 페이지수 계산
			int postsPerPage = 10; // 총 게시물은 15개, 고정 중요공지 5개를 제외한 나머지 게시물 수로 페이지 계산
			int totalNotices = count - 5; // 전체 게시물중 고정용 게시물 수를 제외한 수 저장
			int totalPage = (count == 0) ? 1 :  (int) Math.ceil((double) totalNotices / postsPerPage);
			paging.setTotalPage(totalPage);
			
			// cPage(현재 페이지) 계산
			String cPage = request.getParameter("cPage");
			// 넘어온 파라미터가 없을 경우 1페이지가 보이게 계산
	        int nowPage = (cPage == null || cPage.isEmpty()) ? 1 : Integer.parseInt(cPage);
	        paging.setNowPage(nowPage);
	        /*
			 cPage가  1이거나 null인경우(페이지 선택 전 최신) nowPage를 1로 처리(기본이 1페이지)
			 cPage가 null이 아니면 Integer.parseInt(cPage)로 문자열을 정수로 바꿔서 nowPage에 저장.
			 paging.setNowPage(nowPage)로 계산한 페이지를 저장
	         */
			
			// nowPage를 기준으로 DB에서 가져올 데이터 범위(offset) 계산
	        int offset = postsPerPage * (nowPage - 1);
            if (offset >= totalNotices) {
                offset = Math.max(0, totalNotices - postsPerPage);          
            }
            paging.setOffset(offset);
			
			// 시작블록, 끝블록(페이징 블록 계산)
			// 현재 블록 번호
            paging.setNowBlock( (int) Math.ceil((double) paging.getNowPage() / paging.getPagePerBlock())) ;
			// 시작 페이지 번호
	        paging.setBeginBlock((paging.getNowBlock() - 1) * paging.getPagePerBlock() + 1);                      
	        // 페이징 블록 계산 (페이지 번호 목록 만들기)
	        paging.setEndBlock(paging.getBeginBlock() + paging.getPagePerBlock() - 1);
			
	        // 끝 블록이 총 페이지 수보다 크면 조정
	        if (paging.getEndBlock() > paging.getTotalPage()) {
	        	paging.setEndBlock(paging.getTotalPage());
	        }
	        
	        // 이전,다음 블록이 존재하는지 확인
	        paging.setHasPrevBlock(paging.getBeginBlock() > 1);
	        paging.setHasNextBlock(paging.getEndBlock() < paging.getTotalPage());
	        
			
			// 필요한 게시물 만큼만 DB에서 가져온다. 최신5개 제외
			List<Map<String, Object>> noticeList = noticeService.getAllNoticeList(paging.getNumPerPage(),paging.getOffset());
				
				mv.addObject("noticeList",noticeList);
				mv.addObject("paging",paging);
				mv.setViewName("common/notice/noticeList");
				return mv;				
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/error");
		}
		
	}
	
	
	
		// 공지사항 조건 검색
		@GetMapping("/searchNotice")
		public ModelAndView searchNotice(HttpServletRequest request,			
	        @RequestParam(value = "search", required = false) String search) {
	    try {
	        ModelAndView mv = new ModelAndView();

	        // 1. 검색 결과 개수
	        int count = noticeService.getSearchNoticeCount(search); // 전체 검색결과 개수
	        paging.setTotalRecord(count);
	     // 전체 페이지수 계산
	     	int postsPerPage = 10; // 총 게시물은 15개, 고정 중요공지 5개를 제외한 나머지 게시물 수로 페이지 계산
	     	int totalNotices = count ;
	     	int totalPage = (count == 0) ? 1 :  (int) Math.ceil((double) totalNotices / postsPerPage);
	     	paging.setTotalPage(totalPage);
	       
	     	// cPage(현재 페이지) 계산
	     	String cPage = request.getParameter("cPage");
	     	// 넘어온 파라미터가 없을 경우 1페이지가 보이게 계산
	     	int nowPage = (cPage == null || cPage.isEmpty()) ? 1 : Integer.parseInt(cPage);
	     	paging.setNowPage(nowPage);
	     	
	     // 시작블록, 끝블록(페이징 블록 계산)
	     	// 현재 블록 번호
	        paging.setNowBlock( (int) Math.ceil((double) paging.getNowPage() / paging.getPagePerBlock())) ;
	     	// 시작 페이지 번호
	     	 paging.setBeginBlock((paging.getNowBlock() - 1) * paging.getPagePerBlock() + 1);                      
	     	 // 페이징 블록 계산 (페이지 번호 목록 만들기)
	     	 paging.setEndBlock(paging.getBeginBlock() + paging.getPagePerBlock() - 1);
	     	
	     	 // 끝 블록이 총 페이지 수보다 크면 조정
	     	 if (paging.getEndBlock() > paging.getTotalPage()) {
	     		 paging.setEndBlock(paging.getTotalPage());
	     	        }
	     	        
	     	// 이전,다음 블록이 존재하는지 확인
	     	paging.setHasPrevBlock(paging.getBeginBlock() > 1);
	     	paging.setHasNextBlock(paging.getEndBlock() < paging.getTotalPage());
	     	        
	     			
	     	// 필요한 게시물 만큼만 DB에서 가져온다.
	     	List<Map<String, Object>> noticeList = noticeService.searchNotice(search, paging.getNumPerPage(),paging.getOffset());
	     				
	     	mv.addObject("noticeList",noticeList);
	     	mv.addObject("paging",paging);
	     	mv.setViewName("common/notice/noticeList");
	     	return mv;	
	     	
	     	
	      
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ModelAndView("/error");
	    }
	}

	
	
	
	
	// 공지사항 -> 공지사항 글쓰기페이지 이동
	@GetMapping("/moveInsertNotice")
	public ModelAndView moveInsertNotice() {
		return new ModelAndView("common/notice/noticeInsert");
	}
	
	// 공지사항 작성
	@PostMapping("/noticeInsert")
	public ModelAndView noticeInsert(NoticeVO noticeVO ) {
		 try {
			ModelAndView mv = new ModelAndView();
			int result = noticeService.insertNotice(noticeVO);
			if (result > 0) {
				mv.setViewName("redirect:/detailNotice?notice_idx="+noticeVO.getNotice_idx());
				return mv;
			}else {
				mv.setViewName("redirect:/moveInsertNotice");
				
			}
			return new ModelAndView("notice/noticeInsert");
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/error");
		}
	}
	// 공지사항 자세히 보기
	@GetMapping("/detailNotice")
	public ModelAndView detailNotice(String notice_idx, String a_grade) {
		try {
			ModelAndView mv = new ModelAndView();
			Map<String, Object> detailNotice = noticeService.detailNotice(notice_idx);
			Map<String, Object> previousNotice = noticeService.previousNotice(notice_idx);
			Map<String, Object> nextNotice = noticeService.nextNotice(notice_idx);
			detailNotice.put("a_grade",a_grade);
			
			
			mv.addObject("detailNotice",detailNotice);
			mv.addObject("previousNotice",previousNotice);
			mv.addObject("nextNotice",nextNotice);
			mv.setViewName("common/notice/noticeDetail");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/error");
		}
	}
	
	// 공지사항 수정페이지 이동
	@GetMapping("/moveNoticeUpdate")
	public ModelAndView moveNoticeUpdate(String notice_idx) {
		try {
			ModelAndView mv = new ModelAndView();
			Map<String, Object> detailNotice = noticeService.detailNotice(notice_idx);			
			
			mv.addObject("detailNotice",detailNotice);
		
			mv.setViewName("common/notice/noticeUpdate");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/error");
		}
		
	}
	
	// 공지사항 수정
	@PostMapping("/noticeUpdate")
	public ModelAndView noticeUpdate(NoticeVO noticeVO) {
		try {
			ModelAndView mv = new ModelAndView();
			int result = noticeService.noticeUpdate(noticeVO);
			
			if (result > 0) {
				String notice_idx = noticeVO.getNotice_idx();
				mv.addObject("notice_idx", notice_idx);
				mv.setViewName("redirect:/detailNotice");
				return mv;				
			}else {
				mv.addObject("errorMessage", "error");
				mv.setViewName("redirect:/moveNoticeUpdate");
			}
			    return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/error");
		}
	}
	
	// 공지사항 삭제
	@GetMapping("/deleteNotice")
	public ModelAndView deleteNotice(String notice_idx) {
		try {
			ModelAndView mv = new ModelAndView();
			int result = noticeService.deleteNotice(notice_idx);
			
			if (result > 0) {
				mv.addObject("successMessage", "delete");
				mv.setViewName("redirect:/noticeList");				
				return mv;				
			}else {
				mv.addObject("notice_idx", notice_idx);
				mv.addObject("errorMessage", "error");
				mv.setViewName("redirect:/detailNotice");
			}
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/error");
		}
	}
	
	// FAQ
	// admin 네비 -> faq 연결
		@GetMapping("/faqList")
		public ModelAndView faqList(HttpServletRequest request) {
			try {
				ModelAndView mv = new ModelAndView();
				// 페이징 (중요 공지사항 : 최신4개 상단 고정, 일반공지 떠로 처리)
		
				// 전체 fqp DB에서 가져와서 count에 저장			
				int count = noticeService.getCountFaq();
				paging.setTotalRecord(count);
				
				// 전체 페이지수 계산
				paging.setTotalPage((count == 0) ? 1 :  (int) Math.ceil((double) paging.getTotalRecord() / paging.getNumPerPage()));
				int postsPerPage = 10; // 총 게시물은 15개, 고정 중요공지 5개를 제외한 나머지 게시물 수로 페이지 계산
				int totalNotices = count - 5; // 전체 게시물중 고정용 게시물 수를 제외한 수 저장
				
				// cPage(현재 페이지) 계산
				String cPage = request.getParameter("cPage");
				// 넘어온 파라미터가 없을 경우 1페이지가 보이게 계산
				paging.setNowPage((cPage == null || cPage.isEmpty()) ? 1 : Integer.parseInt(cPage));		       
		       
				
				// nowPage를 기준으로 DB에서 가져올 데이터 범위(offset) 계산
		        paging.setOffset(paging.getNumPerPage() * (paging.getNowPage() - 1));
				
				// 시작블록, 끝블록(페이징 블록 계산)
				// 현재 블록 번호
	            paging.setNowBlock( (int) Math.ceil((double) paging.getNowPage() / paging.getPagePerBlock())) ;
				// 시작 페이지 번호
		        paging.setBeginBlock((paging.getNowBlock() - 1) * paging.getPagePerBlock() + 1);                      
		        // 페이징 블록 계산 (페이지 번호 목록 만들기)
		        paging.setEndBlock(paging.getBeginBlock() + paging.getPagePerBlock() - 1);
				
		        // 끝 블록이 총 페이지 수보다 크면 조정
		        if (paging.getEndBlock() > paging.getTotalPage()) {
		        	paging.setEndBlock(paging.getTotalPage());
		        }
		        
		        // 이전,다음 블록이 존재하는지 확인
		        paging.setHasPrevBlock(paging.getBeginBlock() > 1);
		        paging.setHasNextBlock(paging.getEndBlock() < paging.getTotalPage());
		        
				
				// 필요한 게시물 만큼만 DB에서 가져온다. 최신5개 제외
				List<Map<String, Object>> faqList = noticeService.getAllFaqList(paging.getNumPerPage(),paging.getOffset());
					
					mv.addObject("faqList",faqList);
					mv.addObject("paging",paging);
					mv.setViewName("common/notice/faqList");
					return mv;				
				
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
			
		}
		// faq 조건 검색
		@GetMapping("/searchFaq")
		public ModelAndView searchFaq(HttpServletRequest request,			
		        @RequestParam(value = "search", required = false) String search) {
			try {
				ModelAndView mv = new ModelAndView();
				// 전체 fqp DB에서 가져와서 count에 저장			
				int count = noticeService.getSearchFaqCount(search);
				paging.setTotalRecord(count);
				
				// 전체 페이지수 계산
				paging.setTotalPage((count == 0) ? 1 :  (int) Math.ceil((double) paging.getTotalRecord() / paging.getNumPerPage()));
				int postsPerPage = 10; // 총 게시물은 15개, 고정 중요공지 5개를 제외한 나머지 게시물 수로 페이지 계산
				int totalNotices = count - 5; // 전체 게시물중 고정용 게시물 수를 제외한 수 저장
				
				// cPage(현재 페이지) 계산
				String cPage = request.getParameter("cPage");
				// 넘어온 파라미터가 없을 경우 1페이지가 보이게 계산
				paging.setNowPage((cPage == null || cPage.isEmpty()) ? 1 : Integer.parseInt(cPage));		       
		       
				
				// nowPage를 기준으로 DB에서 가져올 데이터 범위(offset) 계산
		        paging.setOffset(paging.getNumPerPage() * (paging.getNowPage() - 1));
				
				// 시작블록, 끝블록(페이징 블록 계산)
				// 현재 블록 번호
	            paging.setNowBlock( (int) Math.ceil((double) paging.getNowPage() / paging.getPagePerBlock())) ;
				// 시작 페이지 번호
		        paging.setBeginBlock((paging.getNowBlock() - 1) * paging.getPagePerBlock() + 1);                      
		        // 페이징 블록 계산 (페이지 번호 목록 만들기)
		        paging.setEndBlock(paging.getBeginBlock() + paging.getPagePerBlock() - 1);
				
		        // 끝 블록이 총 페이지 수보다 크면 조정
		        if (paging.getEndBlock() > paging.getTotalPage()) {
		        	paging.setEndBlock(paging.getTotalPage());
		        }
		        
		        // 이전,다음 블록이 존재하는지 확인
		        paging.setHasPrevBlock(paging.getBeginBlock() > 1);
		        paging.setHasNextBlock(paging.getEndBlock() < paging.getTotalPage());
		        
				
				// 필요한 게시물 만큼만 DB에서 가져온다. 최신5개 제외
				
				List<Map<String, Object>> faqList = noticeService.searchFaq(search,paging.getNumPerPage(),paging.getOffset());
					
					mv.addObject("faqList",faqList);
					mv.addObject("paging",paging);
					mv.setViewName("common/notice/faqList");
					return mv;	
				
			
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}
		// faq자세히 보기
		@GetMapping("/detailFaq")
		public ModelAndView detaiFaq(String faq_idx, String a_grade) {
			try {
				ModelAndView mv = new ModelAndView();
				Map<String, Object> detailFaq = noticeService.detailFaq(faq_idx);
				Map<String, Object> previousFaq = noticeService.previousFaq(faq_idx);
				Map<String, Object> nextFaq = noticeService.nextFaq(faq_idx);
				detailFaq.put("a_grade",a_grade);
				
				
				mv.addObject("detailFaq",detailFaq);
				mv.addObject("previousFaq",previousFaq);
				mv.addObject("nextFaq",nextFaq);
				mv.setViewName("common/notice/faqDetail");
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}
		
		
	// FAQ -> FAQ 글쓰기페이지 이동
	@GetMapping("/moveFaqInsert")
	public ModelAndView moveFaqInsert() {
		return new ModelAndView("common/notice/faqInsert");
	}
	
	// faq 글쓰기
	@PostMapping("faqInsert")
	public ModelAndView faqInsert(FaqVO faqVO ) {
		 try {
			ModelAndView mv = new ModelAndView();
			int result = noticeService.faqInsert(faqVO);
			if (result > 0) {
				mv.setViewName("redirect:/detailFaq?faq_idx="+faqVO.getFaq_idx());
				return mv;
			}else {
				mv.setViewName("redirect:/moveFaqInsert");
				
			}
			return new ModelAndView("notice/faqInsert");
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/error");
		}
	}
	
	// faq 수정페이지 이동
	@GetMapping("/moveFaqUpdate")
	public ModelAndView moveFaqUpdate(String faq_idx) {
		try {
			ModelAndView mv = new ModelAndView();
			Map<String, Object> detailFaq = noticeService.detailFaq(faq_idx);			
			
			mv.addObject("detailFaq",detailFaq);
		
			mv.setViewName("common/notice/faqUpdate");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/error");
		}
		
	}
	
	
	
	// faq 수정
	@PostMapping("/faqUpdate")
	public ModelAndView faqUpdate(FaqVO faqVO) {
		try {
			ModelAndView mv = new ModelAndView();
			int result = noticeService.faqUpdate(faqVO);
			
			if (result > 0) {
				String faq_idx = faqVO.getFaq_idx();
				mv.addObject("faq_idx", faq_idx);
				mv.setViewName("redirect:/detailFaq");
				return mv;				
			}else {
				mv.addObject("errorMessage", "error");
				mv.setViewName("redirect:/moveFaqUpdate");
			}
			    return mv;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/error");
		}
	}

	// faq 삭제
		@GetMapping("/deleteFaq")
		public ModelAndView deleteFaq(String faq_idx) {
			try {
				ModelAndView mv = new ModelAndView();
				int result = noticeService.deleteFaq(faq_idx);
				
				if (result > 0) {
					mv.addObject("successMessage", "delete");
					mv.setViewName("redirect:/faqList");				
					return mv;				
				}else {
					mv.addObject("faq_idx", faq_idx);
					mv.addObject("errorMessage", "error");
					mv.setViewName("redirect:/detailFaq");
				}
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/error");
			}
		}
		
	
}
