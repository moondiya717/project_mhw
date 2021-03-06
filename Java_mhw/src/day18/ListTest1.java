package day18;

import java.util.ArrayList;

public class ListTest1 {

	public static void main(String[] args) {

		/* 문자열을 저장할 ArrayList를 생성
		 <> 가 들어간 클래스는 제네릭 클래스
		 제네릭클래스 : 멤버변수/메소드의 타입이 객체를 생성할 때 결정되는 클래스, 타입을 클래스로 해야 한다. 일반 변수X =>Wrapper class 사용하기
		 => <>안에있는 클래스에 따라서 ArrayList저거의 타입이 결정되는 클래스 종류를 제네릭 클래스라고 함 
		  */
		ArrayList<String> list = new ArrayList<String>();
		
		//.add() : 리스트에 추가
		for(int i=0; i<10; i++) {
			list.add("학원 출근");
		}
		list.add("수업");	//리스트가 꽉차면 자동으로 크기를 늘린다.
							//위에 10개넣고 수업으로 총 11개넣었는데, 기본 10개를 넘었지만 에러가 안남. 갯수 넘으면 자동으로 길이를 늘려줌
		
		//.get(번지): 번지에 있는 값을 가져옴
		System.out.println(list.get(10));	//값: 수업
		
		//.size() : 현재의 사이즈(지금까지 값이 얼마나 들어가있는지)를 알 수 있음 => size로 쓰고 length아님, 소괄호()있음
		System.out.println("리스트 크기: " + list.size());
		
		
		list.add(10,"아침식사");							//10번지에 추가함
		System.out.println("리스트 크기: " + list.size());	//길이가 +1 늘었음
		System.out.println(list.get(10));				//위에서 10번지에 아침식사를 넣어서 10번지 값 : 아침식사가되고, 원래있던 10번지 수업은 11번지로 밀린거임
				
		//set(번지,값) : 번지에 값을 설정(덮어쓰기)
		list.set(10, "간식");
		System.out.println(list.get(10));
		System.out.println("리스트 크기: " + list.size());
		
		//indexOf(값) : 값이 리스트에 있는지 없는지 확인하여 있으면 번지를 없으면-1을 반환
		// 해당 클래스의 equals를 호출하여 같은지를 확인
		//String클래스서 있었던메소드 여기도 있는데, 완전 똑같지는 않아
		
		System.out.println("리스트에 간식은 "+ list.indexOf("간식") + "번지");	//10번지에 있다
		System.out.println("리스트에 저녁은 "+ list.indexOf("저녁") + "번지");	//없다
		
		//contains(값) : 값이 리스트에 있는지 없는지 확인하여 알려주는 메소드, equals()를 이용
		System.out.println("리스트에 간식이 있다? "+ list.contains("간식"));		//true
		System.out.println("리스트에 저녁이 있다? "+ list.contains("저녁"));		//false

		
		//clear() : 리스트를 (전체) 비움
		list.clear();
		System.out.println("리스트 크기: " + list.size());		//비워서 0됨
	
		
		//isEmpty() : 리스트가 비어있는지 알려주는 코드
		System.out.println("리스트가 비어있습니까? " + list.isEmpty());	//위에서 비워서 없어서 true나옴
		
		//테스트하려고 다시 리스트 추가하는
		list.add("출근");
		list.add("준비");
		list.add("수업");
		list.add("점심");

		//remove(번지)  : 해당 번지의 값을 제거
		//remove(값)   : 값과 일치하는 내용을 제거
		System.out.println("리스트 0 번지: " + list.get(0));	//출근
		list.remove(0);										//0번지 제거		
		System.out.println("리스트 0 번지: " + list.get(0));	//준비	//값을삭제했으니까 뒤에있는애들이 하나씩 당겨온걸 알 수 있음
		list.remove(0);										//0번지 제거		
		System.out.println("리스트 0 번지: " + list.get(0));	//수업
		
		
		
		
	}
}
