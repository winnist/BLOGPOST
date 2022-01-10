package com.controller.login;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.model.member.MemberService;
import com.model.member.MemberVO;
import com.model.post.PostVO;

@Controller
@RequestMapping("/*")
public class LoginController {
	
	@Autowired
	MemberService memberSvc;
	private static String CLIENT_ID = "794664733369-9f9rmecr9uptjtm802r2nppp4tr3l6am.apps.googleusercontent.com";
	
	
	@RequestMapping(method=RequestMethod.GET, value={"/*", "login", "index"})
	public String loadLoginPage(ModelMap model, HttpServletRequest req) {
		Integer memberId = (Integer) req.getSession().getAttribute("memberId");
		if(memberId != null){			
			return "index";
		}
		return "login";
	}
	
		
	@RequestMapping(method=RequestMethod.POST, value="signInGoogle")
	@ResponseBody
	public String signInGoogle(@RequestParam("idtoken")String idtokenString, ModelMap model, HttpServletRequest req) throws GeneralSecurityException, IOException {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory()).
				setAudience(Collections.singletonList(CLIENT_ID)).build();
	
		GoogleIdToken idToken = verifier.verify(idtokenString);
		if(idToken != null) {
			Payload payload = idToken.getPayload();
			String userId = payload.getSubject();
			String email = payload.getEmail();
			String name = (String) payload.get("name");
			
			boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			System.out.println(userId+"---"+email+"---"+emailVerified);
			
			MemberVO memberVO = null;
			
			boolean beAuthorized = false;
			boolean beSignUp = false;
			
			memberVO = memberSvc.findByGoogleSub(userId);
			if(memberVO != null) beAuthorized = true;
			
			memberVO =  memberSvc.findByGoogleMail(email);
			if(memberVO !=null) beSignUp = true;
			
			if(!beAuthorized) {
				
			//case 1: Not Authorized, but Sign-up with email.
				if(beSignUp) {				
					memberVO.setGoogleSub(userId);
					memberSvc.updateMember(memberVO);
					
				}else {
					
					//case 2: Not Authorized, not Sign-up.
					memberVO = new MemberVO();
					
					memberVO.setEmail(email);
					memberVO.setGoogleSub(userId);
					memberVO.setMname(name);
					
					memberSvc.addMember(memberVO);					
					System.out.println(memberVO);
				}
			}
			//case 3: Authorized.
			
		
			req.getSession().setAttribute("memberId", memberVO.getMemberId());				
			Set<PostVO> posts = memberSvc.getPostsByMemberId(memberVO.getMemberId());
	
			model.addAttribute("postsByMemberId", posts);
		
			
			return "post/listAllPost/"+memberVO.getMemberId();
		}
	
		model.addAttribute("loginMsg", "無此帳號");	
		
		
		return null;	
	}
	
	@RequestMapping(method=RequestMethod.GET, value="logout")
	@ResponseBody
	public String logout(HttpServletRequest req) {
		
		System.out.println("logout");
		HttpSession session = req.getSession();
		Object memberId = session.getAttribute("memberId");
		System.out.println(session+"----"+session.getAttribute("memberId"));
		req.getSession().invalidate();
		session = req.getSession(false);
		//System.out.println("session:"+session+"----"+session.getAttribute("memberId"));
		return "success";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="login")
	public ModelAndView login(@Email @NotEmpty @RequestParam("email")String email, 
			@NotEmpty @RequestParam("password")String password, ModelMap model,HttpServletRequest req,
			RedirectAttributes attributes
			) {
		
		MemberVO memberVO = memberSvc.findByLoginInfo(email, password);
		System.out.println("login" + memberVO);
		if(memberVO == null) {
			model.addAttribute("loginMsg", "帳號或密碼錯誤");
			return new ModelAndView("login", model);
		}
		
		req.getSession().setAttribute("memberId", memberVO.getMemberId());
		
		System.out.println("login target:"+ req.getSession().getAttribute("target"));
		attributes.addFlashAttribute("flashAttribute", "321 flash");
		return new ModelAndView("redirect:/post/listAllPost/"+memberVO.getMemberId(), model);
	}
	/*
	 * @ExceptionHandler(value = { ConstraintViolationException.class })
	 * 
	 * @ResponseStatus(value = HttpStatus.BAD_REQUEST) public ModelAndView
	 * handleValidError(HttpServletRequest req,ConstraintViolationException e) {
	 * Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	 * StringBuilder strBuilder = new StringBuilder(); 
	 * for (ConstraintViolation<?> violation : violations ) { strBuilder.append(violation.getMessage() +
	 * "<br>"); } String message = strBuilder.toString(); return new
	 * ModelAndView("index", "message", "錯誤訊息:<br>"+message); }
	 */
}
