package com.model.member;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.post.PostVO;

//import com.model.post.PostVO;

@Service
public class MemberService {

	@Autowired
	private MemberDAO_interface dao;
	
//	@Autowired
//	public MemberService(MemberDAO_interface dao) {
//		this.dao = dao;
//	}
	
	public List<MemberVO> getAll(){
		return dao.getAll();
	}
	
	public MemberVO getOneMember(Integer memberId) {
		return dao.findByPrimaryKey(memberId);
	}
	
	public Set<PostVO> getPostsByMemberId(Integer memberId){
		return dao.getPostsByMemberId(memberId);
	}
	
	public void deleteMember(Integer memberId) {
		dao.delete(memberId);
	}
	
	//not use Spring MVC
	public MemberVO addMember(String mName, String email, Date bdate, String password,
	 String photo, String googleSub) {
		MemberVO memberVO = new MemberVO();
		memberVO.setMname(mName);
		memberVO.setEmail(email);
		memberVO.setBdate(bdate);
		memberVO.setPassword(password);
		memberVO.setPhoto(photo);
		memberVO.setGoogleSub(googleSub);
		dao.insert(memberVO);
		return memberVO;
	}
	
	
	//FOR Spring MVC
	public void addMember(MemberVO memberVO) {
		dao.insert(memberVO);
	}
	
	//FOR Spring MVC	
	public void updateMember(MemberVO memberVO) {
		dao.update(memberVO);
	}
	
	public int updateProfile(String mname, String photo, Date date, Integer memberId) {
		return dao.updateProfile(mname, photo, date, memberId);
	}
	
	public int updateGoogleInfo(String userId) {
		return dao.updateGoogleInfo(userId);
	}
	
	public MemberVO updateMember(Integer memberId, String mName, String email, Date bdate, String password,
	 String photo, String googleSub) {
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(memberId);
		memberVO.setMname(mName);
		memberVO.setEmail(email);
		memberVO.setBdate(bdate);
		memberVO.setPassword(password);
		memberVO.setPhoto(photo);
		memberVO.setGoogleSub(googleSub);
		dao.update(memberVO);
		return memberVO;
	}
	
	public MemberVO findByLoginInfo(String email, String password) {
		return dao.findByLoginInfo(email, password);
	}
	
	public MemberVO findByGoogleMail(String emailFromGoogle) {
		return dao.findByEmail(emailFromGoogle);
	}
	
	public MemberVO findByGoogleSub(String googleSub) {
		return dao.findByGoogleSub(googleSub);
	}
}
