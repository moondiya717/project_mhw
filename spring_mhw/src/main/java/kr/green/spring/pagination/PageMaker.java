package kr.green.spring.pagination;

import lombok.Data;


@Data
public class PageMaker {
	private int totalCount;//콘텐츠 수
	private int startPage;//시작할 페이지네이션 번호
	private int endPage;//페이지네이션끝번호
	private boolean prev;
	private boolean next;
	private int displayPageNum;//페이지네이션에보여줄 이전<사이에 페이지갯수>다음
	private Criteria criteria;//페이지내이션 1.페이지 2.페이지당 콘텐츠 수 3.현재페이지번호
	
	/* endPage, startPage, prev, next 값 계산 */
	public void calcData() {
		/* starPage와 endPage는 현재 페이지 정보인 criteria와 displayPageNum을 이용하여 계산
		 * displayPageNum이 10이고 현재 페이지가 3페이지면 startPage = 1, endPage = 10이 되도록 계산 */
		//endPage = (int)(Math.ceil(3/(double)10)* 10) //ceil 소수점첫자리에서 올림
		endPage = (int) (Math.ceil(criteria.getPage()/(double) displayPageNum)*displayPageNum);
		
		//보여줄displayPageNum마지막이 endPage라서 남은수에+1하면 1~10일때1, 21~30일때 21이 시작숫자
		startPage = (endPage - displayPageNum)+1;
		
		/* 총 콘텐츠 갯수를 이용하여 마지막 페이지 번호를 계산 
		 * 예시: 총 콘텐츠 갯수가 61개이고, 한 페이지에 컨텐츠 수가 20개*/		
		int tempEndPage = (int)(Math.ceil(totalCount/(double)criteria.getPerPageNum()));
		//tmpEncPage = (int)(Math.ceil(61/20.0))		
		/* 현재 페이지에 계산된 현재 페이지메이커의 마지막 페이지 번호와 실제 마지막 페이지 번호를 비교하여
		 * 작은 값이 마지막 페이지 번호가 됨 */
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		/* 현재 페이지메이커에 시작페이지가 1페이지면 prev가 없어야 함 */
		prev = startPage == 1 ? false : true; //false:숨김 true:보임
		/* 현재 페이지메이커에 마지막 페이지에 컨텐츠의 마지막이 포함되어 있으면 next가 없어야 함 */
		next = endPage * criteria.getPerPageNum() >= totalCount ? false:true;
	}

}
