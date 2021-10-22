package com.model.comment;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.model.member.MemberVO;
import com.model.post.PostVO;

@Entity
@Table(name="Comment")
public class CommentVO implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer commentId;
	
	//default: cascade = none, lazy = true
	@ManyToOne
	@JoinColumn(name="postId")
	private PostVO postVO;
	
	@ManyToOne
	@JoinColumn(name="memberId")
	private MemberVO memberVO;
	
	private String content;
	
	private Integer parentCommentId;
	
	private Timestamp cTime;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	
	@NotNull(message="不可為空")
	public PostVO getPostVO() {
		return postVO;
	}
	public void setPostVO(PostVO postVO) {
		this.postVO = postVO;
	}
//	public Integer getMemberId() {
//		return memberId;
//	}
//	public void setMemberId(Integer memberId) {
//		this.memberId = memberId;
//	}
	
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	
	public MemberVO getMemberVO() {
		return this.memberVO;
	}

	@NotEmpty(message="不可為空")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getParentCommentId() {
		return parentCommentId;
	}
	public void setParentCommentId(Integer parentCommentId) {
		this.parentCommentId = parentCommentId;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone="Asia/Taipei")
	public Timestamp getcTime() {
		return cTime;
	}
	
	public void setcTime(Timestamp cTime) {
		this.cTime = cTime;
	}
}
