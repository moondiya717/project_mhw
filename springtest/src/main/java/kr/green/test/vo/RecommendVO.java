package kr.green.test.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecommendVO {
	private int num;
	private String id;
	private int state;
	private int board;
}