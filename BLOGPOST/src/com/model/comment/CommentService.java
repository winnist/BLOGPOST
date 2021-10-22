package com.model.comment;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

	@Autowired
	CommentDAO_interface dao;
	
	
	public int insert(CommentVO commentVO) {
		return dao.insert(commentVO);
	}
	
	public void update(CommentVO commentVO) {
		dao.update(commentVO);
	}
	
	public void updateCommentByCommentId(String content, Timestamp cTime, Integer commentId) {
		dao.update(content, cTime, commentId);
	}
	
	public void delete(Integer commentId) {
		dao.delete(commentId);
	}
	
	public List<CommentVO> getAll() {
		return dao.getAll();
	}
	
	public CommentVO findByPrimaryKey(Integer commentId) {
		return dao.findByPrimaryKey(commentId);
	}
	
}
