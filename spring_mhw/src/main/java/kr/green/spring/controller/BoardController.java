package kr.green.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.green.spring.pagination.Criteria;
import kr.green.spring.pagination.PageMaker;
import kr.green.spring.service.BoardService;
import kr.green.spring.service.MemberService;
import kr.green.spring.vo.BoardVO;
import kr.green.spring.vo.FileVO;
import kr.green.spring.vo.MemberVO;
import kr.green.spring.vo.RecommendVO;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class BoardController {
	@Autowired
	BoardService boardService;
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value="/board/list")
	public ModelAndView boardList(ModelAndView mv, Criteria cri) {
		PageMaker pm = new PageMaker();
		cri.setPerPageNum(10); // 한 페이지에 콘텐츠가 2개씩 있도록 
		pm.setCriteria(cri);
		pm.setDisplayPageNum(3); //페이지네이션에 페이지숫자가 2개씩 보이도록
		
		int totalCount = boardService.getTotalCount(cri);
		pm.setTotalCount(totalCount);
		
		pm.calcData();
		//서비스에게 모든 게시글들을 가져오라고 시킴
		ArrayList<BoardVO> list = boardService.getBoardList(cri);
		//화면에 모든 게시글을 전송
		mv.addObject("list",list);
		mv.addObject("pm",pm);
		
//		if(list != null) { //리스트가 제대로 값이 전달되는지 확인하기 위한것
//			for(BoardVO tmp : list) {
//				System.out.println(tmp);
//			}
//		}
		mv.setViewName("/template/board/list");
		return mv;
	}
	
	@RequestMapping(value="/board/detail")
	public ModelAndView boardDetail(ModelAndView mv, Integer num, HttpServletRequest r) { //Integer는 ?넘버가 없어도 실행, int는 ?넘버가 없으면 null값이라서 실행이안되고 에러가 남.
		//게시글을 가져오기 전 조회수를 증가
		//서비스에게 게시글 번호를 주면서 게시글 조회수를 +1증가시키라고 시킴
		boardService.updateViews(num);

		BoardVO board = boardService.getBoard(num);
		//가져온 게시글을 화면에 전달, 이름은 board로
		mv.addObject("board",board); //(왼쪽:화면에서 쓸이름, 오른쪽:실제데이터이름)

		//첨부파일 가져오기
		ArrayList<FileVO> fileList = boardService.getFileVOList(num);
		mv.addObject("fileList",fileList);
		
		//추천 청보 가져오기
		MemberVO user = memberService.getMember(r);
		RecommendVO recommend = boardService.getRecommend(user, num);
		mv.addObject("rvo", recommend);
		mv.setViewName("/template/board/detail");
		return mv;
	}
	
	@RequestMapping(value="/board/register", method=RequestMethod.GET) //등록화면은 GET으로 가져와서, 화면처리는 POST. URI가 너무 길어짐을 방지
	public ModelAndView boardRegisterGet(ModelAndView mv) {		
		mv.setViewName("/template/board/register");
		return mv;
	}
	//화면에서 보내준 제목, 작성자, 내용을 받아서 콘솔에 출력
	@RequestMapping(value="/board/register", method=RequestMethod.POST) 
	public ModelAndView boardRegisterPost(ModelAndView mv, BoardVO board,
										  HttpServletRequest request, MultipartFile [] file) {
		MemberVO user = memberService.getMember(request);
		board.setWriter(user.getId());

		boardService.insertBoard(board,file);
		mv.setViewName("redirect:/board/list"); //등록끝나면 main화면으로 바로 이동시키는 redirect:
		return mv;
	}
	
	@RequestMapping(value="/board/modify", method=RequestMethod.GET)
	public ModelAndView boardModifyGet(ModelAndView mv, Integer num, HttpServletRequest request) {		
		BoardVO board = boardService.getBoard(num);
		
		mv.addObject("board",board);
		mv.setViewName("/template/board/modify");
		
		MemberVO user = memberService.getMember(request);
		if(board == null || !board.getWriter().equals(user.getId())) {
			mv.setViewName("redirect:/board/list");
		}
		ArrayList<FileVO> fileList = boardService.getFileVOList(num);
		mv.addObject("fileList",fileList);
		return mv;
	}
	@RequestMapping(value="/board/modify", method=RequestMethod.POST)
	public ModelAndView boardModifyPost(ModelAndView mv, BoardVO board,HttpServletRequest request,
										MultipartFile[] file, Integer[] fileNum) {						
		// detail로 이동			
		mv.addObject("num", board.getNum()); //detail로 넘어가기전에 게시글번호를 같이 가지고 가게 함
		mv.setViewName("redirect:/board/detail");
		MemberVO user = memberService.getMember(request);
		
		if(!user.getId().equals(board.getWriter())) {
			mv.setViewName("redirect:/board/list");
		}else {
			//서비스에게 게시글을 주면서 수정하라고 요청
			boardService.updateBoard(board, file, fileNum);
		}
		return mv;
	}
	
	@RequestMapping(value="/board/delete")
	public ModelAndView boardDeleteGet(ModelAndView mv, Integer num,HttpServletRequest request) {
		MemberVO user = memberService.getMember(request);

		//서비스에게 게시글 번호를 주면서 삭제하라고 요청
		boardService.deleteBoard(num, user);
		mv.setViewName("redirect:/board/list");
		return mv;
	}
	
	@ResponseBody //return한 값을 전송해?
	@RequestMapping("/board/download")
	public ResponseEntity<byte[]> downloadFile(String fileName)throws Exception{
		ResponseEntity<byte[]> entity = boardService.downloadFile(fileName);
	    return entity;
	}
	
	@ResponseBody 
	@GetMapping("/board/recommend/{state}/{board}")
	public Map<String,Object> boardRecommend(
			@PathVariable("state") int state, 
			@PathVariable("board") int board, 
			HttpServletRequest r){
		HashMap<String,Object> map = new HashMap<String, Object>();
		MemberVO user = memberService.getMember(r);
		//추천/비추천했으면 1, 취소했으면 0, 로그인 안했으면 -1 (버튼을 누르지않으면 실행이되지않아서 아예누르지않은 경우는 생각하지 않아도 됨)
		int res = boardService.updateRecommend(user, board, state);
		map.put("state", state);
		map.put("board", board);
		map.put("result", res);
	    return map;
	}
}
