package kr.green.test.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.green.test.service.BoardService;
import kr.green.test.vo.BoardVO;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
//컨트롤러 밑에 @RequestMapping(value="/board/*")라고하면 아래는 /board를 생략해도되고, 모두 /board를 기본으로 갖고 시작함

public class BoardController {
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value ="/board/list")
	public ModelAndView boardList(ModelAndView mv, String msg) {
		ArrayList<BoardVO> list= boardService.getBoardList();
		mv.addObject("list",list);
		mv.addObject("msg",msg);
		mv.setViewName("board/list");
		return mv;
	}
	
	@RequestMapping(value ="/board/detail")
	public ModelAndView boardDetail(ModelAndView mv, Integer num, String msg) {
		boardService.updateViews(num);
		BoardVO detail = boardService.getBoard(num);
		mv.addObject("msg",msg);
		mv.addObject("detail",detail);
		mv.setViewName("board/detail");
		return mv;
	}
	
	@RequestMapping(value ="/board/write", method = RequestMethod.GET)
	public ModelAndView boardWriteGet(ModelAndView mv) {					
		mv.setViewName("board/write");
		return mv;
	}
	@RequestMapping(value ="/board/write", method = RequestMethod.POST)
	public ModelAndView boardWritePost(ModelAndView mv, BoardVO board) {
		boardService.insertBoard(board);
		mv.setViewName("redirect:/board/list");
		return mv;
	}
	
	@RequestMapping(value ="/board/edit", method = RequestMethod.GET)
	public ModelAndView boardEditGet(ModelAndView mv, Integer num) {
		log.info("/board/edit :" +num);
		BoardVO edit = boardService.getBoard(num);
		mv.addObject("edit",edit);
		mv.setViewName("board/edit");
		return mv;
	}
	@RequestMapping(value ="/board/edit", method = RequestMethod.POST) 
	public ModelAndView boardEditPost(ModelAndView mv, BoardVO board) {
		log.info("/board/modify:POST : " + board); //........?
		int res = boardService.updateBoard(board);
		String msg = res != 0? board.getNum()+ "번 게시글이 수정되었습니다." : "없는 게시글입니다.";
		mv.addObject("msg",msg);
		mv.addObject("num", board.getNum());
		mv.setViewName("redirect:/board/detail");
		return mv;
	}
	

	@RequestMapping(value ="/board/delete", method=RequestMethod.POST)
	public ModelAndView boardDelete(ModelAndView mv, Integer num) {
		log.info("/board/delete : "+ num); //난왜안찍혀???????
		int res = boardService.deleteBoard(num);
		if(res != 0) {
			mv.addObject("msg",num+"번 게시글을 삭제했습니다."); //mv.addObject는 setViewName에게 보내는 것
		}else {
			mv.addObject("msg", "게시글이 없거나 이미 삭제되었습니다.");
		}
		mv.setViewName("redirect:/board/list");
		return mv;
	}
	
}