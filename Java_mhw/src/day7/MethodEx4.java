package day7;

public class MethodEx4 {
/*gcd1은 재사용성이 높아서 최소 공배수 lcm을 구할 때 사용할 수 있지만
 * gcd2는 재사용성이 낮아서 최소 공배수 lcm을 구할 때 사용할 수 없다.*/
	
	public static void main(String[] args) {
		// 재사용성이 높은 메소드를 보여주기 위한 비교
		//(두 방법으로 최대공약수를 구하는 메소드 이후) 최대공약수를 이용하여 최소 공배수를 구하는 메소드를 작성하세요.
		//두 정수를 A, B라하고 최대 공약수를 G, 최소 공배슈를 L이라고 했을때 
		//A=Ga, B=Gb로 표현할 수 있고, L=G*a*b
		//L=A*B/G로 나타낼 수 있다.
	
		System.out.println(gcd1(8,12));
		gcd2(8,12);
		System.out.println(lcm(8,12));
	}
	/* 기능: 두 정수의 최대 공약수를 알려주는 메소드
	 * 매개변수: 두 정수=> int num1, int num2
	 * 리턴타입: 최대공약수 결과값=> 정수 => int
	 * 메소드명: gcd1 
	 * */
public static int gcd1(int num1, int num2) {
	int res=1;
	for(int i=1; i<=num1; i+=1) {
		if(num1%i==0 && num2%i==0) {
			res=i;
		}
	}
	return res;
}


	/* 기능: 두 정수의 최대 공약수를 출력하는 메소드
	 * 매개변수: 두 정수=> int num1, int num2
	 * 리턴타입: 없음 => void 
	 * 메소드명: gcd2
	 * */
public static void gcd2(int num1, int num2) {
	int res=1;	//최소공약수는 1이니까 res가 1부터 시작한다고 초기화
	for(int i=1; i<=num1; i+=1) {
		if(num1%i==0 && num2%i==0) {
			res=i;
		}
	}
	System.out.println(num1+"과"+num2+"의 최대공약수: "+res);
}
	/* 기능: 두 정수의 최소 공배수를 알려주는 메소드
	 * 매개변수: 두 정수=> int num1, int num2
	 * 리턴타입: 최소 공배수 => 정수 => int
	 * 메소드명: 1cm
	 * */
	public static int lcm(int num1, int num2) {
		int res=num1*num2/ gcd1(num1,num2);
		return res;
	}
}
