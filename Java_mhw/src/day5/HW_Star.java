package day5;

public class HW_Star {

	public static void main(String[] args) {
		// 이중반복문 연습을 위해서 다음과 같은 코드를 만들어보세요.
		
		
	
		
		//별의 갯수는 1, 3, 5, 7, 9 개
		/*
		 *     *
		 *    ***
		 *   *****
		 *  *******
		 * *********
		 *
		 * */

		//줄 5줄(1 2 3 4 5) 	&  공백 4 3 2 1 0 	& 	별 1 3 5 7 9  => 1(우측필요0) 2(1) 3(2) 4(3) 5(4)

		int i, j;
		for(i=1; i<=5; i+=1) {
			for(j=5; j>=1+i ; j-=1) {
				System.out.print(" ");
				}if(i-1!=0) {
					for(j=1; j<=i-1; j+=1) {
						System.out.print('*');
					}
			}
			for(j=1; j<=i ; j+=1) {
				System.out.print('*');
			}System.out.println();
		}


			/*  *********
			 *   *******
			 *    *****
			 *     ***
			 *      *
			 * */
			
					// 5줄(1 2 3 4 5) & 공백 0 1 2 3 4 & 별 9 7 5 3 1 
			
			for(i=1; i<=5; i+=1) {					// i 커지는 중 12345 
				for(j=1; j<=i-1; j+=1) {			// j 	     01234
					System.out.print(" ");
				}	
					
				for(j=0; j<=5-i; j+=1) {			//별 	54321 9 7 5 3 1
					System.out.print('*');
				}
				
				for(j=1; j<=5-i ; j+=1) {
					System.out.print('*');					// 43210 별더하기
				}System.out.println();
			}
		
		// For문의 예제를 While문으로 모두 바꾸는 연습을 해보세요.
		
		
		

		
		
		/*선택형 숙제
		 * - 1 ~ 9 사이의 중복되지 않은 3개의 정수를 랜덤으로 생성
		 * - B : 숫자는 있지만 위치는 다른 경우
		 * - S : 숫자도 있고, 위치가 같은 경우
		 * - O : 일치하는 숫자가 하나도 없는 경우
		 * 
		 * 예시 : 3 9 5(예를들어 랜덤으로 만들었을 때)
		 * >입력하세요 : 1 2 3 (입력)
		 * >1B (출력)
		 * >입력하세요 : 4 5 6 
		 * >1B
		 * 입력하세요 3 4 5 
		 * > 2S
		 * 입력하세요 : 3 5 9 
		 * >1S2B
		 * >입력하세요 : 6 7 8
		 * >O
		 * >입력하세요 : 3 9 5 
		 * > 3S
		 * 정답입니다. */

		
		
		
		
	}

}
