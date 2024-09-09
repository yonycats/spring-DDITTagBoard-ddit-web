package kr.or.ddit.mapper;

import kr.or.ddit.vo.MemberVO;

public interface ILoginMapper {

	public MemberVO loginCheck(MemberVO member);

	public MemberVO idCheck(String memId);

	public int signup(MemberVO memberVO);

	public void signupAuth(int memNo);

}
