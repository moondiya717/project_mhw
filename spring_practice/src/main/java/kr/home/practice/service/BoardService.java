package kr.home.practice.service;

import java.util.ArrayList;

import kr.home.practice.pagination.Criteria;
import kr.home.practice.vo.BoardVO;

public interface BoardService {

	ArrayList<BoardVO> getBoardList(Criteria cri);

	BoardVO getBoardDetail(Integer num);

	void registerBoard(BoardVO board);

	int updateBoard(BoardVO board);

	int deleteBoard(Integer num);

	int getTotalCount(Criteria cri);

}