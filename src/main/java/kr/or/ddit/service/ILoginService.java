package kr.or.ddit.service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.MemberVO;

public interface ILoginService {

	MemberVO loginCheck(MemberVO member);

	ServiceResult idCheck(String memId);

	ServiceResult signup(MemberVO memberVO);

}
	