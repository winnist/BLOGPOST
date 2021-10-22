package com.model.tag;

import java.util.Set;

public interface TagDAO_interface {

	public void insert(TagVO tagVO);
	public void update(TagVO tagVO);
	public void delete(Integer tagId);
	public Set<TagVO> getAll();
	public TagVO findByPrimaryKey(Integer tagId);
	public Set<TagVO> findByName(String name);
}
