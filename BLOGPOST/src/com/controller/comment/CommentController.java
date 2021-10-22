package com.controller.comment;

import java.sql.Timestamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.comment.CommentService;
import com.model.comment.CommentVO;
import com.util.JsonResponse;

@Controller
@RequestMapping("/comment")
public class CommentController {

	
	@Autowired
	CommentService commentSvc;
	
	@RequestMapping(method=RequestMethod.POST, value="/insert")
	public @ResponseBody JsonResponse insert(@Valid @ModelAttribute("commentVO")CommentVO commentVO, BindingResult result) {
		JsonResponse res = new JsonResponse();
		if(result.hasErrors()) {
			res.setStatus("FAIL");
			res.setResult(result.getAllErrors());
			/*class DefaultMessageSourceResolvable
			 * show code in listOnePost.jsp
			 */
//			List<ObjectError> list = result.getAllErrors();
//			for(int i = 0; i< list.size(); i++) {
//				list.get(i).getCode();
//			}*/
			return res;
		}
		
		commentVO.setcTime(new Timestamp(System.currentTimeMillis()));
		commentVO.setParentCommentId(null);
		
		int commentId = commentSvc.insert(commentVO);
	
		res.setStatus("SUCCESS");
		res.setResult(commentSvc.findByPrimaryKey(commentId));
		return res;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/update")
	public @ResponseBody JsonResponse update(@Valid @ModelAttribute("commentVO")CommentVO commentVO, BindingResult result) {
		JsonResponse res = new JsonResponse();
		System.out.println("D"+commentVO.getContent());

		if(result.hasFieldErrors("content")) {
			res.setStatus("FAIL");
			System.out.println("fail"+commentVO.getContent());
			res.setResult(result.getFieldError("content"));
			return res;
		}

		commentSvc.updateCommentByCommentId(commentVO.getContent(), new Timestamp(System.currentTimeMillis()), commentVO.getCommentId());
		res.setStatus("SUCCESS");	
		res.setResult(commentSvc.findByPrimaryKey(commentVO.getCommentId()));
		return res;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/delete")
	public @ResponseBody String delete(@RequestParam("commentId")Integer commentId, ModelMap model) {
		
		commentSvc.delete(commentId);
		model.addAttribute("statusMsg", "刪除成功");
		return "post/listOnePost";
	}
}
