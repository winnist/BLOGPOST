package com.model.tag;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
	
	@Autowired
	private TagDAO_interface dao;
	
	public Set<TagVO> findByName(String name) {
		return dao.findByName(name);
	}
	
	public void insert(TagVO tagVO) {
		
	}
}
