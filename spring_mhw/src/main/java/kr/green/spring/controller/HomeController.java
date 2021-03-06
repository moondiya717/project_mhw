package kr.green.spring.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import kr.green.spring.service.MemberService;
import kr.green.spring.vo.MemberVO;

@Controller
public class HomeController {
    @Autowired
    MemberService memberService;
    @Autowired
    private JavaMailSender mailSender;
    
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView mv) {		
		mv.setViewName("/template/main/home");

		return mv;
	}
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView signinGet(ModelAndView mv) {		
		//System.out.println(123);
		mv.setViewName("/template/member/signin");
		return mv;
	}
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ModelAndView signinPost(ModelAndView mv, MemberVO user) {		
		//System.out.println(user);
		//서비스에게 아이디와 비밀번호를 전달하면 
		//해당 정보가 DB에 있으면 회원정보를 반환, 없으면 null을 반환
		MemberVO dbUser = memberService.signin(user);
		//회원 정보가 있으면 => 로그인에 성공하면 
		if(dbUser != null) {
			//작업이 다 끝난 후 URI가 redirect:/ 주소로 이동 /만쓰면 /으로 감
			//redirect:내가원하는 URI => redirect는 다른 URI를 실행시킴
			mv.setViewName("redirect:/");
		// 회원정보가 없으면 => 일치하는 아이디가 없던지, 비밀번호가 잘못되던지 
		//				 =>로그인실패		
		}else {
			mv.setViewName("redirect:/signin");
		}
		mv.addObject("user",dbUser);
		return mv;
	}
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signupGet(ModelAndView mv) {		
		mv.setViewName("/template/member/signup");
		return mv;
	}
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView signupPost(ModelAndView mv, MemberVO user) {		
		//서비스에게 회원 정보를 주면서 회원가입하라고 일을 시키고, 
		//회원가입이 성공하면 true, 실패하면 false를 알려달라고 요청
		boolean isSignup = memberService.signup(user);
		
		//회원가입에 성공하면 메인으로 실패하면 회원가입 페이지로 이동
		if(isSignup) {
			mv.setViewName("redirect:/");
		}else {
			mv.setViewName("redirect:/signup");
		}
		return mv;
	}
	
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public ModelAndView signoutGet(ModelAndView mv, 
			HttpServletRequest request,
			HttpServletResponse response) {
		//회원의 ID정보를 가져오기 위함
		MemberVO user = (MemberVO)request.getSession().getAttribute("user");
		if(user !=null) {
			request.getSession().removeAttribute("user");
			request.getSession().invalidate();
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if(loginCookie !=null) { //로그인 된 유저의 세션만료시키기
				loginCookie.setPath("/"); //홈으로이동시키고
				loginCookie.setMaxAge(0); //세션유지시간0초로 만들기
				response.addCookie(loginCookie);
				memberService.keeplogin(user.getId(), "none", new Date()); //세션만료시간을 지금시간으로 끝내주기
			}
		}
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@ResponseBody
	@GetMapping(value = "/member/idcheck/{id}")
	public String memberIdcheckPost(@PathVariable("id") String id) { //PathVariable 경로변수, 경로에있는 것을 가져오고=> String 으로 변환
		MemberVO user = memberService.getMember(id);
		String res= user!=null? "IMPOSSIBLE":"POSSIBLE";
		return res;
	}
	
	@ResponseBody
	@PostMapping(value = "/member/signin")
	public String memberSigninPost(@RequestBody MemberVO user, HttpServletRequest r) { 
		MemberVO dbUser = memberService.signin(user);
		if(dbUser != null) {
			r.getSession().setAttribute("user", dbUser);		
		}
		return dbUser!=null? "success" : "fail";
	}
	
	@GetMapping("/find/pw")
	public ModelAndView findPwGet(ModelAndView mv) {
		mv.setViewName("/template/main/findpw");
		return mv;
	}
	@ResponseBody
	@GetMapping("/find/pw/{id}")
	public String findPwPost(@PathVariable("id") String id) {
		MemberVO user = memberService.getMember(id); //getMember는 id를 주면 email을 가져옴
		if(user == null) {
			return "FAIL";
		}
		try {
		        MimeMessage message = mailSender.createMimeMessage();
		        MimeMessageHelper messageHelper
		            = new MimeMessageHelper(message, true, "UTF-8");
		       //임시 비밀번호 발금
		       String newPw = newPw(); //예시로 1234를 넣어놓은 것뿐
		       //새 비밀번호를 DB에 저장
		       user.setPw(newPw);
		       memberService.updateMember(user);
		        
		        
		       messageHelper.setFrom("randonId@gmail.com");  // 보내는사람 생략하거나 하면 정상작동을 안함, 아무글자나 넣고 웹에서 이메일입력만 똑바로하면 발송됨(빈문자열은안됨)
		       messageHelper.setTo(user.getEmail());     // 받는사람 이메일
		       messageHelper.setSubject("새 비밀번호를 발급합니다."); // 메일제목은 생략이 가능하다
		       //태그사용하려면 "", 앞에 빈문자열과반점넣기 그럼 이메일내용에 적용되어있음 + 사이트에서 내용은 안들어가고 밑에가 그대로 들어가는데?
		       messageHelper.setText("","발급된 새 비밀번호는 <b>" + newPw + "</b>입니다.");  // 메일 내용
		        
		       mailSender.send(message);
		       return "SUCCESS";
		    } catch(Exception e){
		        System.out.println(e);
		    }
		return "FAIL"; //실행된다면 위에 messageHelper에서 잘못된거임
	}
	
	@GetMapping("/find/id")
	public ModelAndView findIdGet(ModelAndView mv) {
		mv.setViewName("/template/main/findid");
		return mv;
	}
	@ResponseBody
	@PostMapping("/find/id")
	public String findIdPost(String email) {
		System.out.println(email);
		//현재사이트에 이메일인증이 없어서 이메일이 중복될 수 있는 상황이라(=아이디여러개) ArrayList씀, 있는걸로해야나옴
		ArrayList<MemberVO> userList = memberService.getMemberByEmail(email); 
		System.out.println(userList);
		if(userList == null || userList.size() == 0) {
			return "FAIL";
		}
		try {
			ArrayList<String> idList = new ArrayList<String>();
			for(MemberVO user : userList) { //향상된 for문으로 user에 userList값을 하나씩 꺼내서 유저의 이메일을 배열에 저장해
				idList.add(user.getId());
			}
			System.out.println(idList);
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper
	           = new MimeMessageHelper(message, true, "UTF-8");		        
	        
			messageHelper.setFrom("randomyay@gmail.com");  // 보내는사람 생략하거나 하면 정상작동을 안함, 아무글자나 넣고 웹에서 이메일입력만 똑바로하면 발송됨(빈문자열은안됨)
			messageHelper.setTo(email);     // 받는사람 이메일
			messageHelper.setSubject("가입된 아이디를 알려드립니다."); // 메일제목은 생략이 가능하다
			//태그사용하려면 "", 앞에 빈문자열과반점넣기 그럼 이메일내용에 적용되어있음 + 사이트에서 내용은 안들어가고 밑에가 그대로 들어감
			//replaceAll("[\\[\\]]","") 메일내용에서 [아이디]로 나오는걸 []를 제거하고 아이디만 보내려고 처리하는 것
			messageHelper.setText("","가입된 아이디는 <b>" + idList.toString().replaceAll("[\\[\\]]","") + "</b>입니다.");  // 메일 내용
	        
			mailSender.send(message); //실제로 이메일을 발송하는 코드
			return "SUCCESS";
	    }catch(Exception e){
	       System.out.println(e);
	    }
		return "FAIL"; //실행된다면 위에 messageHelper에서 잘못된거임
	}
	
	//8자리의 숫자 or 영문 대/소문자로된 비밀번호
	private String newPw() {
		//랜덤숫자 : 0~9 => 문자열 : 0~9
		//랜덤숫자 : 10~35 => 문자열 : a-z(소문자)
		//랜덤숫자 : 36~61 => 문자열 : A-Z(대문자)
		// 12 => c(소문자)
		String pw="";
		int max = 61, min = 0; 
		for(int i=0; i<8; i++) {
			int r = (int)(Math.random()*(max-min+1)) + min;
			//int r= (int)(Math.random()*62);
			if(r<=9) {
				pw += r;
			}else if(r <= 35) {
				pw+= (char)('a'+(r-10));
			}else {
				pw+= (char)('A'+(r-36));
			}
		}
		return pw;
	}
}
