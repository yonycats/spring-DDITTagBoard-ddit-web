package kr.or.ddit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.IDDITBoardService;
import kr.or.ddit.vo.DDITBoardVO;
import kr.or.ddit.vo.DDITTagVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PaginationInfoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/ddit/board")
public class DDITBoardController {
	
	@Inject
	IDDITBoardService boardService;
	
	// 게시판 리스트 보기
	@GetMapping("/list.do")
	public String list(
			@RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false) String searchWord,
			Model model
			) {
		log.info("currentPage >>>>> " + currentPage);
		
		PaginationInfoVO<DDITBoardVO> pagingVO = new PaginationInfoVO<DDITBoardVO>(10, 5);
		
		if (StringUtils.isNotBlank(searchWord)) {
			pagingVO.setSearchWord(searchWord);
			pagingVO.setSearchType(searchType);
			model.addAttribute("searchWord", searchWord);
			model.addAttribute("searchType", searchType);
		}
		pagingVO.setCurrentPage(currentPage);
		
		int totalRecord = boardService.selectBoardCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);

		List<DDITBoardVO> dataList = boardService.selectList(pagingVO);
		pagingVO.setDataList(dataList);
		
		model.addAttribute("pagingVO", pagingVO);
		
		return "board/list";
	}
	
	// 게시글 Form 요청하기
	@GetMapping("/insert.do")
	public String insertForm(HttpServletRequest req, RedirectAttributes ra) {
		String goPage = "";
		
		HttpSession session = req.getSession();
		MemberVO member = (MemberVO)session.getAttribute("SessionInfo");
		
		if (member != null) {
			goPage = "board/form";
		} else {
			ra.addFlashAttribute("message", "로그인 후 사용 가능합니다!");
			goPage = "redirect:/ddit/board/login.do";
		}
		return goPage;
	}
	
	// 게시글 등록하기
	@PostMapping("/insert.do")
	public String insert(HttpServletRequest req, RedirectAttributes ra, DDITBoardVO board, Model model) {
		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>();
		
		if (StringUtils.isBlank(board.getBoTitle())) {
			errors.put("boTitle", "제목을 입력해주세요!");
		}
		if (StringUtils.isBlank(board.getBoContent())) {
			errors.put("boContent", "내용을 입력해주세요!");
		}
		if (errors.size() > 0) {
			model.addAttribute("errors", errors);
			model.addAttribute("board", board);
			
			goPage = "board/form";
			
		} else {
			HttpSession session = req.getSession();
			MemberVO member = (MemberVO)session.getAttribute("SessionInfo");
			
			if (member != null) {	// 비어있는 곳도 없고 로그인도 잘 되어있을 때
				board.setBoWriter(member.getMemId());
				
				// 1. board 내용 등록하기
				log.info(">>>>>>>>>>>>> " + member.toString());
				log.info(">>>>>>>>>>>>> " + board.toString());
				
				ServiceResult result = boardService.insertForm(board);
				
				// 2. 태그 등록하기
				// 태그 띄어쓰기 기준으로 잘라서 반복문으로 리스트 VO에 집어넣기
				String tag = board.getTag();
				log.info("tag >>>>>>>>>>>>>>>> " + tag);
				if (tag != "") {
					String[] tagSplit = board.getTag().split(" "); 
					List<DDITTagVO> tagList = new ArrayList<DDITTagVO>();
					
					for (int i = 0; i < tagSplit.length; i++) {
						DDITTagVO tagVO = new DDITTagVO();
						// 시퀀스로 생성된 게시물 번호 가져와서 태그에 게시물 번호 세팅하기
						tagVO.setBoNo(board.getBoNo());
						tagVO.setTag(tagSplit[i]);
						tagList.add(i, tagVO);
					}
					board.setTagList(tagList);
					log.info(">>>>>>>>>>>>> " + board.getTagList().toString());
					
					boardService.insertTags(board);
				}
				
				
				// 3. 게시물 등록 결과에 따라 이동할 페이지 정보 리턴하기
				if (result.equals(ServiceResult.OK)) {
					ra.addFlashAttribute("message", "게시글이 등록 완료되었습니다!");
					goPage = "redirect:/ddit/board/detail.do?boNo=" + board.getBoNo();
				} else {
					ra.addFlashAttribute("message", "서버 에러, 다시 시도해주세요!");
					goPage = "board/form";
				}
				
			} else {
				ra.addFlashAttribute("message", "로그인 후 사용 가능합니다!");
				goPage = "redirect:/ddit/board/login.do";
			}
		}
		return goPage;
	}
	
	// 게시글 상세보기
	@GetMapping("/detail.do")
	public String detail(int boNo, Model model) {
		log.info("boNo >>> " + boNo);
		DDITBoardVO board = boardService.selectDetail(boNo);
		
		model.addAttribute("board", board);
		return "board/detail";
	}
		
	// 게시글 수정 Form 요청하기
	@GetMapping("/modify.do")
	public String modifyForm(HttpServletRequest req, RedirectAttributes ra, int boNo, Model model) {
		DDITBoardVO board = boardService.selectDetail(boNo);
		
		String tag = "";
		
		List<DDITTagVO> tagList = board.getTagList();
		
		for (DDITTagVO tagVO : tagList) {
			if (tag.equals("")) {
				tag = tagVO.getTag();
			} else {
				tag = tag + " " + tagVO.getTag();
			}
		}
		board.setTag(tag);
		
		log.info(">>>>>>>>>>>>> " + board.toString());
		model.addAttribute("board", board);
		return "board/modify";
	}
	
	// 게시글 수정하기
	@PostMapping("/modify.do")
	public String modify(HttpServletRequest req, RedirectAttributes ra, DDITBoardVO board, Model model) {
		log.info("modify 들어와땅");
		
		String goPage = "";

		// 1. board 내용 수정하기
		ServiceResult result = boardService.modifyForm(board);
		
		// 2. 태그 등록하기
		// 태그 띄어쓰기 기준으로 잘라서 반복문으로 리스트 VO에 집어넣기
		String tag = board.getTag();
		log.info("tag >>>>>>>>>>>>>>>> " + tag);
		
		if (tag != "") {
			String[] tagSplit = board.getTag().split(" "); 
			List<DDITTagVO> tagList = new ArrayList<DDITTagVO>();
			
			for (int i = 0; i < tagSplit.length; i++) {
				DDITTagVO tagVO = new DDITTagVO();
				// 시퀀스로 생성된 게시물 번호 가져와서 태그에 게시물 번호 세팅하기
				tagVO.setBoNo(board.getBoNo());
				tagVO.setTag(tagSplit[i]);
				tagList.add(i, tagVO);
			}
			board.setTagList(tagList);
			log.info(">>>>>>>>>>>>> " + board.getTagList().toString());
			
			boardService.modifyTags(board);
		} else {
			boardService.deleteTags(board.getBoNo());
		}
		
		
		// 3. 게시물 등록 결과에 따라 이동할 페이지 정보 리턴하기
		if (result.equals(ServiceResult.OK)) {
			ra.addFlashAttribute("message", "게시글이 수정 완료되었습니다!");
			goPage = "redirect:/ddit/board/detail.do?boNo=" + board.getBoNo();
		} else {
			ra.addFlashAttribute("message", "서버 에러, 다시 시도해주세요!");
			goPage = "redirect:/ddit/board/detail.do?boNo=" + board.getBoNo();
		}
		return goPage;
	}
		
	// 게시글 삭제하기
	@PostMapping("/delete.do")
	public String delete(RedirectAttributes ra, int boNo) {
		String goPage = "";
		ServiceResult result = boardService.deleteForm(boNo);
		
		if (result.equals(ServiceResult.OK)) {
			ra.addFlashAttribute("message", "게시글 삭제가 완료되었습니다!");
			goPage = "redirect:/ddit/board/list.do";
		} else {
			ra.addFlashAttribute("message", "서버 에러, 다시 시도해주세요!");
			goPage = "redirect:/ddit/board/detail.do?boNo=" + boNo;
		}
		return goPage;
	}
}

