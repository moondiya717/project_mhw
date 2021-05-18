package day16;
//catch가 여러개 줄줄이 이어서 올 수 있음
public class TryTest2 {

	public static void main(String[] args) {
		int num1 = 1, num2 = 0;
		
		try {
			System.out.println( num1 / num2 );
		}catch(Exception e) {
			System.out.println("Exception 예외 발생");
		}
		//catch는 위에서부터 확인하기 때문에 위에있는 Exception이 모든 예외를 처리하기 때문에 여기까지 올 일이 절대 없다. => 에러발생
		//부모클래스를 자식 클래스보다 아래쪽에 배치해야한다. => 부모가 더 많은예외처리를 하기때문에
	//	catch(ArithmeticException e) {
			System.out.println("ArithmeticException 발생");
	//	}

			
			
		try {
			System.out.println(num1 / num2);
		}catch(ArithmeticException e) {
			System.out.println("ArithmeticException 발생");
		}catch(RuntimeException e) {
			System.out.println("RuntimeException 발생");
		}catch(Exception e) {
			System.out.println("Exception 발생");
		}
	}
}
