package com.model.comment;

import java.sql.Timestamp;
import java.util.List;


public interface CommentDAO_interface {
	/*
	 * return : new insert object's ID
	 * */
	public int insert(CommentVO commentVO);
	public void update(CommentVO commentVO);
	public void delete(Integer commentId);
	
	public void update(String content, Timestamp cTime, Integer commentId);
	
	public CommentVO findByPrimaryKey(Integer commentId);
	public List<CommentVO> getAll();
}
