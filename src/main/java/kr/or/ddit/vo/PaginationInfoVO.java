package kr.or.ddit.vo;

import java.util.List;

import lombok.Data;

@Data
public class PaginationInfoVO<T> {
	private int totalRecord;		// 총 게시글 수
	private int totalPage;			// 총 페이지 수	
	private int currentPage;		// 현재 페이지
	private int screenSize;			// 페이지 당 게시글 수
	private int blockSize;			// 페이지 블록 수
	
	private int startRow;			// 시작 row
	private int endRow;				// 끝 row
	private int startPage;			// 시작 페이지
	private int endPage;			// 끝 페이지
	private List<T> dataList;		// 결과를 넣을 데이터 리스트 (각 페이지당 조회된 목록 데이터)
	private String searchType;		// 검색 타입 (제목, 작성자, 작성일 등등)
	private String searchWord;		// 검색 키워드
	
	public PaginationInfoVO() {}
	
	// PagenationInfoVO 객체를 만들 때, 한 페이지당 게시글 수와 페이지 블록 수를 원하는 값으로 초기화 할 수 있다.
	public PaginationInfoVO(int screenSize, int blockSize) {
		this.screenSize = screenSize;
		this.blockSize = blockSize;
	}
	
	public void setTotalRecord(int totalRecord) {
		// 총 게시글 수를 저장하고, 총 게시글 수를 페이지당 나타낼 게시글 수로 나눠 총 페이지 수를 구한다.
		this.totalRecord = totalRecord;
		// ceil은 올림
		totalPage = (int)Math.ceil(totalRecord / (double)screenSize);
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;			// 현재 페이지 설정
		endRow = currentPage * screenSize;		// 끝 row = 현재 페이지 * 한 페이지당 게시글 수
		startRow = endRow - (screenSize-1);		// 시작 row = 끝 row - (한 페이지당 게시글 수-1)
		// 마지막 페이지 = (현재 페이지 + (페이지 블록 사이즈-1)) / 페이지 블록 사이즈 * 페이지 블록 사이즈
		// ' / blockSize * blockSize '는 1,2,3,4,5...  페이지마다 실수 계산이 아닌 정수 계산을 이용해 endPage를 구하기 위함
		endPage = (currentPage + (blockSize-1)) / blockSize * blockSize;
		startPage = endPage - (blockSize-1);	// 시작 페이지 = 마지막 페이지 - (페이지 블록 사이즈-1)
	}
	
	public String getPagingHTML() {
		StringBuffer html = new StringBuffer();
		html.append("<ul class='pagination pagination-sm m-0 float-right'>");
		
		// currentPage가 6보다 크다는 뜻 => 한 페이지는 넘겼다.
		if (startPage > 1) {
			// a 태그 : 페이지 번호를 눌렀을 때 반응을 하기 위함
			// 데이터 셋을 활용하기 위해서 data-page에 내가 눌렀을 때 현재 페이지의 번호가 필요
			html.append("<li class='page-item'><a href='' class='page-link' data-page='" + 
						(startPage - blockSize) + "'>Prev</a></li>");
		}
		
		// 반복문 내 조건은 총 페이지가 있고 현재 페이지에 따라서 endPage가 결정됩니다.
		// 총 페이지가 14개고 현재 페이지가 9 페이지라면 넘어가야 할 페이지가 남아있는 것이기 때문에
		// endPage만큼 반복되고 넘어가야 할 페이지가 존재하지 않는 상태라면 마지막 페이지가 포함되어 있는 block 영역이므로
		// totalPage만큼 반복됩니다.
		// 페이지 번호 : for문 안에서 만들어지는 녀석들
		for (int i=startPage; i<=(endPage < totalPage ? endPage : totalPage); i++) {
			// 현재 페이지
			// 현재 페이지일 경우만 active 클래스 적용
			// 현재 페이지일 때는 li 태그가 아닌 span으로 적용 -> 클릭했을 때 a 태그의 역할을 하지 않음
			if(i == currentPage) {
				html.append("<li class='page-item active'><span class='page-link'>" + 
			    		 	i + "</span></li>");
			// 현재 페이지가 아닌 다른 페이지 번호일 때 실행
			// 첫번째 i : 'data-page' 속성에 페이지 번호를 설정하기 위해 사용
			// data-page : 페이징 네비게이션에서 각 페이지 번호를 저장하기 위해 사용
			//           : 클릭한 페이지가 몇 번째 페이지인지 서버로 전송하거나, javascript 통해 그 페이지로 이동하는 기능 구현 가능
			// 두번째 i : 링크 텍스트로 페이지 번호를 표시하기 위해 사용
				
			// 현재 페이지를 제외한 나머지 번호 => a 태그를 통해 클릭을 했을 때 a라는 이벤트 실행 가능
			// a 태그 안에 들어있는 data-page를 javascript에서는 dataset이라고 함
			// dataset : 내가 원하는 특정 '-'을 기준으로 오른쪽에 나오는 것이 key(page)가 됨
			// page를 셋팅하면 page안에 들어있는 i값, 즉 페이지 번호를 가져올 수 있음
				
			// 자바스크립트 : this.dataset.page;
			// 제이쿼리 : $(this).data("page");
			} else {
			    html.append("<li class = 'page-item'><a href ='' class='page-link' data-page='"+
			    		 	i+ "'>" + i + "</a></li>");
			}
		}
		// 현재 블록 다음에 더 많은 페이지 블록이 있다는 의미
		// 현재 마지막 페이지(endPage)가 전체 페이지 (totoalPage) 보다 작을 때 실행 
		// Next 버튼 생성
		// Next 버튼 : endPage + 1로 계산된 다음 블록의 첫 페이지로 이동하도록 설정
		if (endPage < totalPage) {
			html.append("<li class='page-item'><a href='' class='page-link' data-page='"+
		                (endPage + 1) + "'>Next</a></li>");
		}
		
		html.append("</ul>");
		return html.toString();
	}
}
