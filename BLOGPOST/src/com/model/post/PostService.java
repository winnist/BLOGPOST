package com.model.post;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.comment.CommentVO;

@Service
public class PostService {

	@Autowired
	PostDAO_interface dao;
	
	public void insertPost(PostVO postVO) {	
		dao.insert(postVO);
	}
	
	
	public void updatePost(PostVO postVO) {
		dao.update(postVO);
	}
	
	public void deletePost(Integer postId) {
		dao.delete(postId);
	}
	public PostVO getOnePost(Integer postId) {
		return dao.findByPrimaryKey(postId);
	}
	public List<PostVO> getAll(){
		return dao.getAll();
	}
	
	public Set<CommentVO> getCommentsByPostId(Integer postId){
		return dao.getCommentsByPostId(postId);
	}
}
