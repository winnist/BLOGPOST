package com.model.member;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.model.post.PostVO;

//import com.model.post.PostVO;

public interface MemberDAO_interface {

	public void insert(MemberVO memberVO);
	public void update(MemberVO memberVO);
	public void delete(Integer memberId);
	
	public MemberVO findByPrimaryKey(Integer memberId);
	
	public MemberVO findByLoginInfo(String email, String password);
	public List<MemberVO> getAll();
	
	public Set<PostVO> getPostsByMemberId(Integer memberId);
	public int updateProfile(String mname, String photo, Date date, Integer memberId);
	//public Set<PostVO> getPostsByPojo(MemberVO memberVO);
	public MemberVO findByEmail(String email);
	public MemberVO findByGoogleSub(String userId);
	public int updateGoogleInfo(String userId);
	public String getPhotoByMemberId(Integer memberId);
}
