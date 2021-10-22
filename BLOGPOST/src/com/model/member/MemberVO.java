package com.model.member;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.model.post.PostVO;


@Entity
@Table(name= "Member")
public class MemberVO implements Serializable {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer memberId;
	private String mname;
	private String email;
	private Date bdate;
	private String password;
	private String photo;
	private String googleSub;
	
	public String getGoogleSub() {
		return googleSub;
	}

	public void setGoogleSub(String googleSub) {
		this.googleSub = googleSub;
	}
	@OneToMany(cascade= CascadeType.ALL, mappedBy="memberVO")
	@OrderBy("postId asc")
	@Fetch(FetchMode.JOIN)
	private Set<PostVO> posts = new HashSet<PostVO>();
	
	@JsonIgnore
	public Set<PostVO> getPosts(){
		return this.posts;		
	}
	
	public void setPosts(Set<PostVO> posts) {
		this.posts = posts;
	}
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	@NotEmpty(message="不可為空-姓名")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)(\\s)]{2,10}$", message = "只能輸入中文 英文 數字 _ 在2到10之間")
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	
	@NotEmpty(message="不可為空-信箱")
	@Email(message = "格式錯誤")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	//must add formatter convert the data string to data object
	//method:
	//		1.add @DataTimeFormat or 
	//      2.Declare a initbinder in controller to convert the data string to data object
	//if don't, will occur Exception
	


	@DateTimeFormat(pattern="yyyy-MM-dd") 
	@NotNull(message="請輸入生日")
	public Date getBdate() {
		return bdate;
	}
	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}
	
	//@NotEmpty(message="不可為空")
	@Pattern(regexp = "^[(a-zA-Z0-9_)]{8,}$", message = "只能輸入英文 數字和 _, 英文大小寫 必須含只少一位,密碼長度最少為8位")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
