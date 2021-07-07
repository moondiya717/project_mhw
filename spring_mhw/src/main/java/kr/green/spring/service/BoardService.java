package kr.green.spring.service;

import java.util.ArrayList;

import kr.green.spring.vo.BoardVO;

public interface BoardService {

	ArrayList<BoardVO> getBoardList();

	BoardVO getBoard(Integer num);

	void insertBoard(BoardVO board);

	int updateViews(Integer num);

	int updateBoard(BoardVO board);
	//void로 떴는데, 혹시라도 return값을 받아서 처리할 일이 생길 수도 있으니까 int로 변경
	int deleteBoard(Integer num); 

}
