package com.model.tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class TagDAO implements TagDAO_interface {
	
	private static final String GET_ALL_STMT = "from TagVO order by tagId";
	private static final String QUERY_BY_NAME_STMT = "from TagVO where tagName like ?";
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void insert(TagVO tagVO) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(tagVO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(TagVO tagVO) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(tagVO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Integer tagId) {
		Session session = sessionFactory.getCurrentSession();
		TagVO tagVO = new TagVO();
		tagVO.setTagId(tagId);
		session.delete(tagVO);
	}
	
	@Override
	public Set<TagVO> getAll(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(GET_ALL_STMT);
		Set<TagVO> sets = new HashSet(query.getResultList());
		return sets;
	}
	
	@Override
	public TagVO findByPrimaryKey(Integer tagId) {
		Session session = sessionFactory.getCurrentSession();
		TagVO tagVO = session.get(TagVO.class, tagId);
		return tagVO;
	}
	
	@Override
	public Set<TagVO> findByName(String name){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(QUERY_BY_NAME_STMT);
		query.setParameter(0, name + '%');
		Set<TagVO> sets = new HashSet(query.getResultList());
		for(TagVO vo : sets) {
			System.out.println("query--"+ vo.getTagName());
		}
		return sets;
	}
}
