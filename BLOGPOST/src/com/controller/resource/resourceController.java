package com.controller.resource;

import java.io.File;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.member.MemberService;
import com.model.member.MemberVO;

@Controller
@MultipartConfig
@RequestMapping("resource")
public class resourceController {
	
	String saveDirectory = "/resource/img";
	@RequestMapping(method=RequestMethod.POST, value="uploadImg")
	public String uploadImg(HttpServletRequest req) throws Exception, ServletException {
		
		Part imgur = req.getPart("imgur");
		String fileName = imgur.getSubmittedFileName();
		System.out.println(fileName+"檔案名稱");
		InputStream fileContent = imgur.getInputStream();
		MemberVO vo = new MemberVO();
		/*byte[] buf = new byte[fileContent.available()];
		vo.setPhoto(buf);
		MemberService memberSvc = new MemberService();
		memberSvc.updateMember(vo);
		*/
		File f = new File( req.getServletContext()+saveDirectory, fileName);
		imgur.write(f.toString());
		
		vo.setPhoto(fileName);
		MemberService memberSvc = new MemberService();
		memberSvc.updateMember(vo);
		
		return "上傳圖片成功";
	}
}
