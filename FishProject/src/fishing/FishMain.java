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
			System.out.println("1.ĳ���ͻ���|2.ĳ���ͻ���|3.����|4.��ũȮ��");
			System.out.println("-------------------------------------------------");
			System.out.print("����> ");
			int selectNum = scan.nextInt();
			
			switch(selectNum) {
			case 1:
				boolean result1 = false;
				System.out.print("�̸� �Է�(10�� ����): ");
				String name = scan.next();
				System.out.print("��й�ȣ �Է�(8�� ����): ");
				String password = scan.next();
				FDTO = new FishDTO();
				FDTO.setName(name);
				FDTO.setPassword(password);
				
				result1 = FDAO.createCh(FDTO);
				if(result1) {
					System.out.println("ĳ���� ���� ����!");
				} else {
					System.out.println("ĳ���� ���� ����.");
				}
				break;
			case 2:
				FDTO = new FishDTO();
				FDAO.listChar();
				System.out.println("ĳ���� ����(�̸�): ");
				name = scan.next();
				System.out.println("��й�ȣ: ");
				password = scan.next();
				FDTO.setName(name);
				FDTO.setPassword(password);
				
				FDAO.stateChar(FDTO);
				break;
			case 3:
				FDTO = new FishDTO();
				System.out.print("�α����� ĳ���͸� �Է�: ");
				name = scan.next();
				System.out.print("��й�ȣ �Է�: ");
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
