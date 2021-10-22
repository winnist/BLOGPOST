package com.model.comment;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@Repository
@Transactional(readOnly = true)
public class CommentDAO implements CommentDAO_interface {

	private static final String GET_ALL_STMT = "from CommentVO order by commentId";
	private static final String UPDATE_CONTENT_STMT = "update CommentVO set content=:content, cTime=:cTime where commentId=:commentId";
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int insert(CommentVO commentVO) {
		Session session = sessionFactory.getCurrentSession();	
		session.saveOrUpdate(commentVO);
		return commentVO.getCommentId();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(CommentVO commentVO) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(commentVO);
	}
	
	@Override
	public void update(String content, Timestamp cTime, Integer commentId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(UPDATE_CONTENT_STMT);
		query.setParameter("content", content);
		query.setParameter("cTime", cTime);
		query.setParameter("commentId", commentId);
		
		query.executeUpdate();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Integer commentId) {
		Session session = sessionFactory.getCurrentSession();
		CommentVO commentVO = new CommentVO();
		commentVO.setCommentId(commentId);
		session.delete(commentVO);
	}
	
	@Override
	public CommentVO findByPrimaryKey(Integer commentId) {
		Session session = sessionFactory.getCurrentSession();
		CommentVO commentVO = session.get(CommentVO.class, commentId);
		return commentVO;
	}
	

	
	@Override
	public List<CommentVO> getAll(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(GET_ALL_STMT);
		List<CommentVO> list = query.getResultList();
		return list;
	}
	

}
