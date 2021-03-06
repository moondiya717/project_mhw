package kr.green.test.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import kr.green.test.pagination.Criteria;
import kr.green.test.vo.BoardVO;
import kr.green.test.vo.FileVO;
import kr.green.test.vo.MemberVO;
import kr.green.test.vo.RecommendVO;

public interface BoardDAO {

	ArrayList<BoardVO> getBoardList(@Param("cri")Criteria cri);

	BoardVO getBoard(@Param("num")Integer num);

	int updateBoard(@Param("board")BoardVO board);

	void insertBoard(@Param("board")BoardVO board, MemberVO user);

	int getTotalCount(@Param("cri")Criteria cri);

	void insertFile(@Param("file")FileVO fvo);

	ArrayList<FileVO> getFileList(@Param("num")Integer num);

	void deleteFileVO(@Param("file")FileVO tmpList);

	RecommendVO getRecommend(@Param("board")int board, @Param("id")String id);

	void insertRecommend(@Param("board")int board, @Param("id")String id, @Param("state")int state);

	void updateRecommend(@Param("rvogP")RecommendVO rvo);

}
