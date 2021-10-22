package com.model.post;

import java.util.List;
import java.util.Set;

import com.model.comment.CommentVO;

public interface PostDAO_interface {

	public void insert(PostVO postVO);
	public void update(PostVO postVO);
	public void delete(Integer postId);
	public PostVO findByPrimaryKey(Integer postId);
	public List<PostVO> getAll();
	
	public Set<CommentVO> getCommentsByPostId(Integer postId);
}
