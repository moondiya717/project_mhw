package day2;
import java.util.Scanner;

public class Test2_5 {

	public static void main(String[] args) {
		/* 정수를 입력받아 6의 배수이면 6의 배수라고 출력하고,
		 * 2의 배수이면 2의 배수라고 출력하고,
		 * 3의 배수이면 3의 배수라고 출력하고
		 * 2,3,6의 배수가 아니면 2,3,6의 배수가 아닙니다. 라고 출력하는 코드를 작성하세요
		 * 단, 6,12와 같이 6의 배수인 경우는 6의 배수입니다. 라고 하나만 출력되어야 한다. */

		
		System.out.print("양의 정수를 입력하세요 : ");
		
		Scanner scan = new Scanner(System.in);  
		int num = scan.nextInt();
		
		if(num % 6 == 0) {
			System.out.println(num + "은(는) 6의 배수입니다.");
			
		} else if (num % 2 == 0) {
			System.out.println(num + "은(는) 2의 배수입니다. ");
			
		} else if (num % 3 == 0) {
			System.out.println(num + "은(는) 3의 배수입니다. ");
			
		} else {
			System.out.println(num + "은(는) 2, 3, 6의 배수가 아닙니다. ");
		}
		
		scan.close();
		
	}

}
