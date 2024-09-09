package kr.or.ddit.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.mapper.ILoginMapper;
import kr.or.ddit.service.ILoginService;
import kr.or.ddit.vo.MemberVO;

@Service
public class LoginServiceImpl implements ILoginService {

	@Inject
	private ILoginMapper loginMapper;

	@Override
	public MemberVO loginCheck(MemberVO member) {
		return loginMapper.loginCheck(member);
	}

	@Override
	public ServiceResult idCheck(String memId) {
		ServiceResult result = null;
		
		MemberVO member = loginMapper.idCheck(memId);
		if (member != null) {
			result = ServiceResult.EXIST; 	// 아이디와 일치하는 회원정보 존재
		} else {
			result = ServiceResult.NOTEXIST;	// 아이디와 일치하는 회원정보 없음
		}
		
		return result;
	}

	@Override
	public ServiceResult signup(MemberVO memberVO) {
		ServiceResult result = null;
		
		int status = loginMapper.signup(memberVO);
		if (status > 0) {	// 등록 성공
			// 한명의 회원이 등록될 때 하나의 권한을 무조건 가질 수 있도록 권한 등록 (스프링 시큐리티 적용시 사용 예정)
			loginMapper.signupAuth(memberVO.getMemNo());
			result = ServiceResult.OK; 
		} else {			// 등록 실패
			result = ServiceResult.FAILED;	
		}
		
		return result;
	}

}
