package kr.or.ddit.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.ILoginService;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/ddit/board")
public class LoginController {
	
	@Inject
	ILoginService noticeService;

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String noticeLogin(Model model) {
		// css가 깨지지 않게 하기 위함		
		model.addAttribute("bodyText", "login-page");
		
		// 기존에는 ViewResolver가 잡았던 리턴을 이제부터는 tilesViewResolver가 잡을 예정임
		//		-> 우선순위를 바꿈 (헤더와 푸터 페이지를 내부에 포함시킴 > mainTemplate(notice/*)) 
		// servlet-context.xml
		return "board/login";
	}
	
	@PostMapping("/loginCheck.do")
	public String loginCheck(
			HttpServletRequest req,
			MemberVO member, Model model) {
		String goPage = "";
		// 에러 메시지를 담아둘 Map
		Map<String, String> errors = new HashMap<String, String>();
		
		// pom.xml로 이동해서  라이브러리 추가 (lang3) (267번줄)
		if (StringUtils.isBlank(member.getMemId())) {
			errors.put("memId", "아이디를 입력해주세요.");
		}
		if (StringUtils.isBlank(member.getMemPw())) {
			errors.put("memPw", "비밀번호를 입력해주세요.");
		}
		
		if (errors.size() > 0) {	// 에러가 발생했을 때
			model.addAttribute("errors", errors);
			model.addAttribute("member", member);
			model.addAttribute("bodyText", "login-page");
			goPage = "board/login";
		} else {					// 정상적으로 데이터가 입력됐을 때
			MemberVO memberVO = noticeService.loginCheck(member);
			
			if (memberVO != null) {		// 회원 로그인이 성공했을 때
				// 로그인 성공 후 전달받은 memberVO의 데이터를 이용해서 세션 처리
				HttpSession session = req.getSession();
				session.setAttribute("SessionInfo", memberVO);
				
				goPage = "redirect:/ddit/board/list.do";	// 게시판 목록으로 이동
			} else {
				model.addAttribute("message", "로그인 정보를 정확하게 입력해주세요.");
				model.addAttribute("member", member);
				model.addAttribute("bodyText", "login-page");
				goPage = "board/login";
			}
		}
		return goPage;
	}
	
	@RequestMapping(value = "/signup.do", method = RequestMethod.GET)
	public String signupForm(Model model) {
		// css가 깨지지 않게 하기 위함	
		model.addAttribute("bodyText", "register-page");
		return "board/register";
	}
	
	@PostMapping("/idCheck.do")
	public ResponseEntity<ServiceResult> idCheck(@RequestBody Map<String, String> map) {
		log.info("# 넘겨받은 아이디 : " + map.get("memId"));
		
		ServiceResult result = noticeService.idCheck(map.get("memId"));
		
		return new ResponseEntity<ServiceResult>(result, HttpStatus.OK);
	}
	
	@PostMapping("/signup.do")
	public String signup(
			RedirectAttributes ra,
			MemberVO memberVO, Model model) {
		log.info("들어옴");
		log.info(memberVO.toString());
		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>();
		
		if (StringUtils.isBlank(memberVO.getMemId())) {
			errors.put("memId", "아이디를 입력해주세요!");
		}
		if (StringUtils.isBlank(memberVO.getMemPw())) {
			errors.put("memPw", "비밀번호를 입력해주세요!");
		}
		if (StringUtils.isBlank(memberVO.getMemName())) {
			errors.put("memName", "이름을 입력해주세요!");
		}
		
		if (errors.size() > 0) {	// 에러 발생했다면
			model.addAttribute("errors", errors);
			model.addAttribute("member", memberVO);
			model.addAttribute("bodyText", "register-page");
			
			goPage = "board/register";
		} else {		// 정상적인 데이터라면 
			ServiceResult result = noticeService.signup(memberVO);
			
			if (result.equals(ServiceResult.OK)) {
				ra.addFlashAttribute("message", "회원가입을 완료하였습니다!");
				goPage = "redirect:/ddit/board/login.do";
			} else {
				model.addAttribute("message", "서버에러, 다시 시도해주세요!");
				model.addAttribute("member", memberVO);
				model.addAttribute("bodyText", "register-page");
				goPage = "board/register";
			}
		}
		
		return goPage;
	}

	
}
