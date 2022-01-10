package com.model.post;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.model.comment.CommentVO;
import com.model.member.MemberVO;
import com.model.tag.TagVO;
//import com.model.tag.TagVO;

@Entity
@Table(name= "Post")
public class PostVO implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	
	//default cascade:none, lazy = true
	@ManyToOne
	@JoinColumn(name = "memberId")
	private MemberVO memberVO;
	private String title;
	private Timestamp postDate;
	private String content;
	
//	//ower the relationship post_tag
	@ManyToMany
	@JoinTable(name = "post_tag", joinColumns = {@JoinColumn(name="postId")}, inverseJoinColumns = { @JoinColumn(name="tagId")})
	private Set<TagVO> tags = new HashSet<TagVO>();
	
	@OneToMany(cascade= CascadeType.ALL, mappedBy = "postVO")
	@Fetch(FetchMode.JOIN)
	private Set<CommentVO> comments = new HashSet<CommentVO>();
	
	@JsonIgnore
	public Set<CommentVO> getComments(){
		return this.comments;
	}
	
	public void setComments(Set<CommentVO> comments) {
		this.comments = comments;
	}
	
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	
	@NotEmpty(message="不可為空")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	public Timestamp getPostDate() {
		return postDate;
	}
	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}
	
	@NotEmpty(message="不可為空")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonIgnore
	public Set<TagVO> getTags(){
		return this.tags;
	}
	
	public void setTags(Set<TagVO> tags) {
		this.tags = tags;
	}
}
