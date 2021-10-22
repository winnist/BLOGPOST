package com.model.post;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.model.comment.CommentVO;

@Repository
@Transactional(readOnly = true)
public class PostDAO implements PostDAO_interface {

	private static final String GET_ALL_STMT = "from PostVO order by postId";
	private static final String QUERY_BY_MEMBERID_STMT = "from PostVO where memberId=:memberId order by postId";
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void insert(PostVO postVO) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(postVO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(PostVO postVO) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(postVO);
	}
	
	/**
	 * delete Post will also delete post's Comments
	 * Tag will remain - tag's casacde = persist, merge
	 * Member will remain
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Integer postId) {
		
		//不影響MemberVO的前提下  刪除POST 和POST底下的COMMENT
		Session session = sessionFactory.getCurrentSession();
		Query queryComment = session.createQuery("delete CommentVO where postId=:postId");
		queryComment.setParameter("postId", postId);
		queryComment.executeUpdate();
		
		Query queryPost = session.createQuery("delete PostVO where postId=:postId");
		queryPost.setParameter("postId", postId);
		queryPost.executeUpdate();		
	}
	
	@Override
	public PostVO findByPrimaryKey(Integer postId) {
		Session session = sessionFactory.getCurrentSession();
		PostVO postVO = session.get(PostVO.class, postId);	
		return postVO;
	}
	
	@Override
	public List<PostVO> getAll(){
		List<PostVO> list = null;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(GET_ALL_STMT);
		list = query.getResultList();
		return list;
	}
	
	@Override
	public Set<CommentVO> getCommentsByPostId(Integer postId){
		Set<CommentVO> set = this.findByPrimaryKey(postId).getComments();
		return set;
	}

}
