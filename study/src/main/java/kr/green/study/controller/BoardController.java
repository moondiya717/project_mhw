package kr.green.study.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.green.study.service.BoardService;
import kr.green.study.service.MemberService;
import kr.green.study.vo.BoardVO;
import kr.green.study.vo.MemberVO;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;
	private MemberService memberService;
	
	@GetMapping("/list")
	public ModelAndView listGet(ModelAndView mv) {
		ArrayList<BoardVO> list = boardService.getBoardList();
		//System.out.println(list);
		mv.addObject("list", list);
		mv.setViewName("/template/board/list");
		return mv;
	}
	@GetMapping("/detail")
	public ModelAndView detailGet(ModelAndView mv, Integer num) {
		//System.out.println(num);
		BoardVO board=boardService.getBoard(num);
		mv.addObject("board",board);
		mv.setViewName("/template/board/detail");
		return mv;
	}
	@GetMapping("/register")
	public ModelAndView registerGet(ModelAndView mv) {
		mv.setViewName("/template/board/register");
		return mv;
	}
	@PostMapping("/register")
	public ModelAndView registerPost(ModelAndView mv, BoardVO board,
			MultipartFile []fileList, HttpServletRequest request) {
		//System.out.println(board);
//		for(MultipartFile tmp : fileList) {
//			if(tmp != null) {
//				System.out.println(tmp.getOriginalFilename());
//			}
//		}
		MemberVO user = memberService.getMemberByRequest(request);
		boardService.insertBoard(board,fileList,user);
		mv.setViewName("redirect:/board/list");
		return mv;
	}
}
