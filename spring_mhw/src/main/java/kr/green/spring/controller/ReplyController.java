package kr.green.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.green.spring.pagination.Criteria;
import kr.green.spring.pagination.PageMaker;
import kr.green.spring.service.MemberService;
import kr.green.spring.service.ReplyService;
import kr.green.spring.vo.MemberVO;
import kr.green.spring.vo.ReplyVO;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor //모든 멤버변수의 객체를 생성하도록 만들어주는 것, Autowired를 안써도 됨. 
public class ReplyController {

	private ReplyService replyService;
	private MemberService memberService;
	
	@PostMapping(value="/reply/ins")	
	public String replyInsPost(@RequestBody ReplyVO reply) {
		replyService.insertReply(reply);
		return "ok";
	}
	
	@GetMapping(value="/reply/list/{num}/{page}")	
	public HashMap<String, Object> replyListGet(
			@PathVariable("num") Integer num,
			@PathVariable("page") Integer page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Criteria cri = new Criteria();
		cri.setPage(page);
		cri.setPerPageNum(5);
		ArrayList<ReplyVO> list = replyService.getReplyList(num, cri);
		PageMaker pm = new PageMaker();

		pm.setCriteria(cri);
		pm.setDisplayPageNum(2);
		int totalCount = replyService.getTotalCount(num);
		pm.setTotalCount(totalCount);
		pm.calcData();
		map.put("pm", pm);
		System.out.println(pm);

		map.put("list", list);
		return map;
	}
	
	@PostMapping(value="/reply/del")	
	public String replyDelPost(@RequestBody ReplyVO reply, HttpServletRequest r) {
		MemberVO user = memberService.getMember(r);
		
		return replyService.deleteReply(reply,user);
	}
	@PostMapping(value="/reply/mod")	
	public String replyModPost(@RequestBody ReplyVO reply, HttpServletRequest r) {
		MemberVO user = memberService.getMember(r);
		
		return replyService.updateReply(reply,user);
	}
}
