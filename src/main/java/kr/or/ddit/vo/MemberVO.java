package kr.or.ddit.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MemberVO {
	private int memNo;
	private String memId;
	private String memPw;
	private String memName;
	private String memGender;
	private String memEmail;
	private String memPhone;
	private String memPostcode;
	private String memAddress1;
	private String memAddress2;
	private String memAgree;
	private String memProfileimg;		// 프로필 이미지
	private String memRegdate;
	private String enabled;
	
	private MultipartFile imgFile;		// 프로필 이미지 파일을 받을 때 사용
	private String memProfileImg;		// 사용자 프로필 이미지
	
	List<MemberAuthVO> AuthList;
}
