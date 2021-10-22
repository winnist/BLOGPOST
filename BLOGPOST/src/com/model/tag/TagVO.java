package com.model.tag;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.model.post.PostVO;


@Entity
@Table(name="Tag")
public class TagVO implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer tagId;
	
	private String tagName;
	
	//the non-owning side must use the mappedBy element of
//	the ManyToManyannotation to specify the relationship field
//	or property of theembeddable class
	@ManyToMany(mappedBy="tags")
	private Set<PostVO> posts = new HashSet<PostVO>();
	
	
	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@JsonIgnore
	public Set<PostVO> getPosts() {
		return posts;
	}

	public void setPosts(Set<PostVO> posts) {
		this.posts = posts;
	}


}
