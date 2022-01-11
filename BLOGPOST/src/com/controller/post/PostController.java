package com.controller.post;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.comment.CommentVO;
import com.model.member.MemberService;
import com.model.member.MemberVO;
import com.model.post.PostService;
import com.model.post.PostVO;



@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	PostService postSvc;
	
	@Autowired
	MemberService memberSvc;
	
	@RequestMapping(method=RequestMethod.GET, value="addPost")
	public String getPostPage(ModelMap model) {	
		model.addAttribute("postVO", new PostVO());
		System.out.println("addpost");
		return "post/addPost";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="listAllPost/{memberId}")
	public String getPostListPage(ModelMap model, @PathVariable("memberId")Integer memberId,
			HttpServletRequest req, @ModelAttribute("flashAttribute")String flashAttribute) throws JsonProcessingException {	
		System.out.println("flashAttribute"+flashAttribute);
		Enumeration<String> e = req.getSession().getAttributeNames();	
		while(e.hasMoreElements()) {
			String str = e.nextElement();
			System.out.println("--session attribute --"+ str);
		}
		
		
		//Integer memberId = new Integer(memberVO.getMemberId());
	
		
		Set<PostVO> posts = memberSvc.getPostsByMemberId(memberId);
		model.addAttribute("postsByMemberId", posts);
		
		
		System.out.print("--------------"+model.containsKey("postsByMemberId"));
		for(PostVO  postvo : posts) {
			System.out.println(postvo.getTitle());
		}
		req.getSession().setAttribute("curScrollingPageMemberId", memberId);
		return "post/listAllPost";
	}
	
//	@ModelAttribute("postVO")
//	public PostVO initPostForm() {
//		return new PostVO();
//	}
//	
	
	@RequestMapping(method=RequestMethod.POST, value="insert")
	public String insert(@Valid PostVO postVO, BindingResult result, @SessionAttribute("memberId") Integer memberId, ModelMap model) {
		if(result.hasErrors()) {
			System.out.println("errer");
			return "post/addPost";
		}		
		MemberVO memberVO = memberSvc.getOneMember(memberId);
		//MemberVO memberVO = (MemberVO) req.getSession().getAttribute("memberVO");
		postVO.setMemberVO(memberVO);		
		postVO.setPostDate(new java.sql.Timestamp(System.currentTimeMillis()));				
		postSvc.insertPost(postVO);
		model.addAttribute("actionStatus", "新增POST成功");
		return "post/listOnePost";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="delete")
	public String delete(@RequestParam("postId")Integer postId, @SessionAttribute("memberId") Integer memberId, ModelMap model) {	
		
		postSvc.deletePost(postId);
		model.addAttribute("actionStatus", "刪除POST成功");
		
		Set<PostVO> posts_byMemberId = memberSvc.getPostsByMemberId(memberId);
		for(PostVO vo : posts_byMemberId) {
			System.out.println(vo.getPostId());
		}
		
		model.addAttribute("postsByMemberId", posts_byMemberId);
		
		return "post/listAllPost";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="getOneForUpdate")
	public String getOneForUpdate(@RequestParam("postId")Integer postId, ModelMap model) {
		PostVO postVO = postSvc.getOnePost(postId);
		model.addAttribute("postVO", postVO);
		return "post/updatePost";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="listCommentsByPostId")
	@ResponseBody
	public Set<CommentVO> getCommentsByPostId(@RequestParam("postId")Integer postId){
		Set<CommentVO> sets = postSvc.getCommentsByPostId(postId);
		for(CommentVO c : sets) {
			System.out.println("comments:"+c.getcTime());
		}
		return sets;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="update")
	public String update(@Valid PostVO postVO, BindingResult result, ModelMap model, HttpServletRequest req) {
		
		if(result.hasErrors()) {
			System.out.println("error update");
			List<ObjectError> list = result.getAllErrors();
			String errormsg = null;
			for(ObjectError e : list) {
				errormsg = e.getCode()+e.getDefaultMessage();
				
			}
			model.addAttribute("statusMsg", "更新失敗"+errormsg);
			return "post/listOnePost";
		}
	
//		postVO.setPostDate(new Timestamp(System.currentTimeMillis()));
		postSvc.updatePost(postVO);
		model.addAttribute("statusMsg", "更新成功");
		
		return "post/listOnePost";
		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="getOneForList/{postId}")
	public String getOneForList(@PathVariable("postId")Integer postId, ModelMap model) {		
		PostVO postVO = postSvc.getOnePost(postId);
		model.addAttribute("postVO", postVO);
		return "post/listOnePost";		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="getOneURLForList/{postId}")
	@ResponseBody
	public String getOneURLForList(@PathVariable("postId")Integer postId, ModelMap model) {		
		PostVO postVO = postSvc.getOnePost(postId);
		model.addAttribute("postVO", postVO);
		return "post/getOneForList/"+String.valueOf(postId);		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="getAllPosts")
	@ResponseBody
	public Map getAllPost(ModelMap model){
		List resultList = postSvc.getAll();
		
		Map map = new HashMap();
		
		map.put("data", resultList);
		return map;
	}
}
