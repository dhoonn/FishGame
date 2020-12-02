package fishing;

import java.util.Scanner;

public class FishMain {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		FishDTO FDTO = null;
		FishDAO FDAO = new FishDAO();
		boolean run = true;
		
		FDAO.dbConnection();
		
		while(run) {
			System.out.println("-------------------------------------------------");
			System.out.println("1.캐릭터생성|2.캐릭터상태|3.시작|4.랭크확인");
			System.out.println("-------------------------------------------------");
			System.out.print("선택> ");
			int selectNum = scan.nextInt();
			
			switch(selectNum) {
			case 1:
				boolean result1 = false;
				System.out.print("이름 입력(10자 이하): ");
				String name = scan.next();
				System.out.print("비밀번호 입력(8자 이하): ");
				String password = scan.next();
				FDTO = new FishDTO();
				FDTO.setName(name);
				FDTO.setPassword(password);
				
				result1 = FDAO.createCh(FDTO);
				if(result1) {
					System.out.println("캐릭터 생성 성공!");
				} else {
					System.out.println("캐릭터 생성 실패.");
				}
				break;
			case 2:
				FDTO = new FishDTO();
				FDAO.listChar();
				System.out.println("캐릭터 선택(이름): ");
				name = scan.next();
				System.out.println("비밀번호: ");
				password = scan.next();
				FDTO.setName(name);
				FDTO.setPassword(password);
				
				FDAO.stateChar(FDTO);
				break;
			case 3:
				FDTO = new FishDTO();
				System.out.print("로그인할 캐릭터명 입력: ");
				name = scan.next();
				System.out.print("비밀번호 입력: ");
				password = scan.next();
				FDTO.setName(name);
				FDTO.setPassword(password);
				
				FDAO.startGame(FDTO);
				
				
				break;
			case 4:
				FDAO.Rank(FDTO);
				break;
			}
		}
		scan.close();

	}

}
