package com.model.member;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

//import com.model.post.PostVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.model.post.PostVO;

@Repository
@Transactional(readOnly=true)
public class MemberDAO implements MemberDAO_interface {
	
	private static final String GET_ALL_STMT = "from MemberVO order by memberId";
	private static final String GET_ONE_BY_LOGIN_INFO_STMT = "from MemberVO where email=:email and password=:password";
	private static final String GET_ONE_BY_EMAIL = "from MemberVO where email=:email";
	private static final String GET_ONE_BY_GOOGLESUB = "from MemberVO where googleSub=:googleSub";
	
	private static final String UPDATE_NAME_AND_PHOTO_BY_ID_STMT = "update MemberVO set mname=:mname, bdate=:bdate, photo=:photo where memberId=:memberId";
	private static final String UPDATE_GOOGLEINFO = "update MemberVO set googleSub=:googleSub where memberId=:memberId";
	private static final String GET_PHOTO_BY_MEMBERID = "select photo from MemberVO where memberId=:memberId";
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void insert(MemberVO memberVO) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(memberVO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(MemberVO memberVO) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(memberVO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int updateProfile(String mname, String photo, Date bdate, Integer memberId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(UPDATE_NAME_AND_PHOTO_BY_ID_STMT);
		query.setParameter("mname", mname);
		query.setParameter("photo", photo);
		query.setParameter("bdate", bdate);
		query.setParameter("memberId", memberId);
		return query.executeUpdate();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int updateGoogleInfo(String userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(UPDATE_GOOGLEINFO);
		query.setParameter("googleSub", userId);
		return query.executeUpdate();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Integer memberId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from CommentVO where memberId=:memberId");
		query.setParameter("memberId", memberId);
		query.executeUpdate();
		
		MemberVO memberVO = session.get(MemberVO.class, memberId);
		session.delete(memberVO);
	}
	
	@Override
	public MemberVO findByPrimaryKey(Integer memberId) {
		Session session = sessionFactory.getCurrentSession();
		MemberVO memberVO = session.get(MemberVO.class, memberId);
//		Query query = session.createQuery("select m from MemberVO m join fetch m.posts p where m.memberId=:memberId");
//		query.setParameter("memberId", memberId);
//		MemberVO memberVO = (MemberVO) query.getSingleResult();
		return memberVO;		
	}
	
	@Override
	public List<MemberVO> getAll(){
		
		Session session = sessionFactory.getCurrentSession();
		List<MemberVO> list = session.createQuery(GET_ALL_STMT).getResultList();
		return list;	
	}
	
	@Override
	@Transactional
	public Set<PostVO> getPostsByMemberId(Integer memberId){
		return this.findByPrimaryKey(memberId).getPosts();
	}

	@Override
	public MemberVO findByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(GET_ONE_BY_EMAIL);
		query.setParameter("email", email);
		List<MemberVO> list = query.getResultList();
		if(list.isEmpty()) {
			return null;
		}else return list.get(0);
	}
	
	/**
	 * parameter: userId-> from google token getSubject()
	 */
	@Override
	public MemberVO findByGoogleSub(String userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(GET_ONE_BY_GOOGLESUB);
		query.setParameter("googleSub", userId);
		List<MemberVO> list = query.getResultList();
		if(list.isEmpty()) {
			return null;
		}else return list.get(0);
	}
	
	

	@Override
	public MemberVO findByLoginInfo(String email, String password) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(GET_ONE_BY_LOGIN_INFO_STMT);
		query.setParameter("email", email);
		query.setParameter("password", password);
		List<MemberVO> list = query.getResultList();
		System.out.println("login list -"+list);
		if(list.isEmpty()) {
			return null;
		}else return list.get(0);
	}
	
	@Override
	public String getPhotoByMemberId(Integer memberId){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(GET_PHOTO_BY_MEMBERID);
		query.setParameter("memberId", memberId);
		List<Object> list = query.getResultList();
		return (String) list.get(0);
	}
}
