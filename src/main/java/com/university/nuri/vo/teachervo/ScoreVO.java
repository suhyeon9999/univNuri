package com.university.nuri.vo.teachervo;


	
	public class ScoreVO {
	    private int scoreIdx;       // 성적 IDX
	    private int enrollIdx;      // 수강 IDX (enroll 테이블 참조)
	    private Integer scoreMid;   // 중간고사 점수
	    private Integer scoreFinal; // 기말고사 점수
	    private Integer scoreAssign;// 과제 점수
	    private Integer scoreAttend;// 출결 점수
	    private Integer scoreTotal; // 총점

	    // 기본 생성자
	    public ScoreVO() {}

	    // 전체 필드를 포함한 생성자
	    public ScoreVO(int scoreIdx, int enrollIdx, Integer scoreMid, Integer scoreFinal,
	                   Integer scoreAssign, Integer scoreAttend, Integer scoreTotal) {
	        this.scoreIdx = scoreIdx;
	        this.enrollIdx = enrollIdx;
	        this.scoreMid = scoreMid;
	        this.scoreFinal = scoreFinal;
	        this.scoreAssign = scoreAssign;
	        this.scoreAttend = scoreAttend;
	        this.scoreTotal = scoreTotal;
	    }

	    // Getter/Setter
	    public int getScoreIdx() {
	        return scoreIdx;
	    }

	    public void setScoreIdx(int scoreIdx) {
	        this.scoreIdx = scoreIdx;
	    }

	    public int getEnrollIdx() {
	        return enrollIdx;
	    }

	    public void setEnrollIdx(int enrollIdx) {
	        this.enrollIdx = enrollIdx;
	    }

	    public Integer getScoreMid() {
	        return scoreMid;
	    }

	    public void setScoreMid(Integer scoreMid) {
	        this.scoreMid = scoreMid;
	    }

	    public Integer getScoreFinal() {
	        return scoreFinal;
	    }

	    public void setScoreFinal(Integer scoreFinal) {
	        this.scoreFinal = scoreFinal;
	    }

	    public Integer getScoreAssign() {
	        return scoreAssign;
	    }

	    public void setScoreAssign(Integer scoreAssign) {
	        this.scoreAssign = scoreAssign;
	    }

	    public Integer getScoreAttend() {
	        return scoreAttend;
	    }

	    public void setScoreAttend(Integer scoreAttend) {
	        this.scoreAttend = scoreAttend;
	    }

	    public Integer getScoreTotal() {
	        return scoreTotal;
	    }

	    public void setScoreTotal(Integer scoreTotal) {
	        this.scoreTotal = scoreTotal;
	    }
	}


