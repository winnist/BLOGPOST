package com.controller.tag;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.tag.TagService;
import com.model.tag.TagVO;

@Controller
@RequestMapping("/tag")
public class tagController {
	
	@Autowired
	private TagService tagSvc;
	
	@RequestMapping(method=RequestMethod.GET, value="/findByName/{tagName}")
	public @ResponseBody Set<TagVO> findByName(@PathVariable("tagName")String tagName){
		System.out.println("-----------"+tagName+"--------33");
		return tagSvc.findByName(tagName);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="insert")
	public @ResponseBody Set<TagVO> insert(@PathVariable("tagName")String tagName){
		System.out.println("-----------"+tagName+"--------33");
		tagSvc.
		return 
	}
}
