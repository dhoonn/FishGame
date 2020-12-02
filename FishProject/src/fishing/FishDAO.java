package fishing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FishDAO {
	Connection con = null; // �����㰡 ������ ��� ����
	PreparedStatement pstmt = null; // ������ ������ ����ϴ� ����
	PreparedStatement pstmt1 = null; // ������ ������ ����ϴ� ����
	ResultSet rs = null; // SELECT ����� �����ϴ� ����
	ResultSet rs1 = null; // SELECT ����� �����ϴ� ����
	Scanner scan = new Scanner(System.in);
	
	
	public void dbConnection() {
		con = DBConnection.makeConnection();
		
	}


	public boolean createCh(FishDTO FDTO) {
		int result = 0;
		boolean re = false;
		String sql = "INSERT INTO FISHTABLE (NAME, PASSWORD, STR, DEX, HP, ABILPOINT, SCORE, FDATE)"
				+ "VALUES(?, ?, 5, 5, 5, 3, 0, SYSDATE)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, FDTO.getName());
			pstmt.setNString(2, FDTO.getPassword());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result>0) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}


	public void listChar() {
		String sql = "SELECT * FROM FISHTABLE";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.print("ĳ���� �̸�: "+rs.getString("NAME") + "\t");
				System.out.print("���� ����Ʈ: "+rs.getInt("ABILPOINT") + "\t");
				System.out.print("�Ŀ�: "+rs.getInt("STR") + "\t");
				System.out.print("��ø: "+rs.getInt("DEX") + "\t");
				System.out.print("ü��: "+rs.getInt("HP") + "\t");
				System.out.print("ĳ���� ������¥: "+rs.getString("FDATE") + "\t");
				System.out.println("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void stateChar(FishDTO FDTO) {
		String sql = "UPDATE FISHTABLE SET ABILPOINT = ?, STR = ?, DEX = ?, HP = ?"
				+ " WHERE NAME = ? AND PASSWORD = ?";
		String sql1 = "SELECT * FROM FISHTABLE WHERE NAME = ?";
		try {
			pstmt1 = con.prepareStatement(sql1);
			pstmt1.setString(1, FDTO.getName());
			pstmt1.executeUpdate();
			rs = pstmt1.executeQuery();
			while(rs.next()) {
				if(FDTO.getPassword().equals(rs.getString("PASSWORD")) && FDTO.getName().equals(rs.getString("NAME"))) {
					System.out.print("STR ���� ����Ʈ: ");
					int str = scan.nextInt();
					System.out.print("DEX ���� ����Ʈ: ");
					int dex = scan.nextInt();
					System.out.print("HP ���� ����Ʈ: ");
					int hp = scan.nextInt();
					int point = str + dex + hp;
					if (rs.getInt("ABILPOINT") - point >= 0) {
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1, rs.getInt("ABILPOINT") - point);
						pstmt.setInt(2, rs.getInt("STR") + str);
						pstmt.setInt(3, rs.getInt("DEX") + dex);
						pstmt.setInt(4, rs.getInt("HP") + hp);
						pstmt.setString(5, FDTO.getName());
						pstmt.setString(6, FDTO.getPassword());
						pstmt.executeUpdate();
					} else {
						System.out.println("Point�� �����մϴ�.");
					}
					
				}
				
			}
			System.out.println("");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	


	public void startGame(FishDTO FDTO) {
		String charName = "";
		String fishN = "";
		String charPassword = "";
		String sql = "SELECT * FROM FISHTABLE WHERE NAME = ? AND PASSWORD = ?";
//		String sql1 = "SELECT * FROM FISH WHERE FISHNAME = ?";
		boolean run = true;
		try {
			pstmt = con.prepareStatement(sql);
//			pstmt1 = con.prepareStatement(sql1);
			pstmt.setString(1, FDTO.getName());
			pstmt.setString(2, FDTO.getPassword());
			pstmt.executeUpdate();
			rs = pstmt.executeQuery();
			while(rs.next()) {
				charName = rs.getString("NAME");
				charPassword = rs.getNString("PASSWORD");
			}
			if(FDTO.getName().equals(charName) && FDTO.getPassword().equals(charPassword)) {
				while(run) {
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					System.out.println("������(1) | �Ѱ�(2) | �︪��(3) | �޴�â ����(4)");
					System.out.println("----------------------------------------------");
					System.out.print("������ �����ϼ���(����): ");
					int selectP = scan.nextInt();
					int ran = (int) (Math.random() * 10) + 1;
					
					switch(selectP) {
					case 3:
						if(ran <= 7) {
							System.out.println("���� �ɸ�.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=7) {
								System.out.println("��ì�̴�.");
								fishN = "��ì��";
								System.out.println("��ì�̸� ���ҽ��ϴ�.");
								System.out.println("ȸĥ���?(1) �����ٱ��?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("���");
								}
							} else {
								System.out.println("��ì�̿����ϴ�.\n ��ì�̸� ���ƽ��ϴ�.");
							}
						} else if (8<=ran && ran<=9) {
							System.out.println("���� �ɸ�.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=6) {
								System.out.println("�ؾ��.");
								fishN = "�ؾ�";
								System.out.println("�ؾ ���ҽ��ϴ�.");
								System.out.println("ȸĥ���?(1) �����ٱ��?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("���");
								}
							} else {
								System.out.println("�ؾ���ϴ�.\n �ؾ ���ƽ��ϴ�.");
							}
						} else {
							System.out.println("���� �ɸ�.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=5) {
								System.out.println("������.");
								fishN = "����";
								System.out.println("������ ���ҽ��ϴ�.");
								System.out.println("ȸĥ���?(1) �����ٱ��?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("���");
								}
							} else {
								System.out.println("���������ϴ�.\n ������ ���ƽ��ϴ�.");
							}
						}
						break;
					case 2:
						if(ran <= 5) {
							System.out.println("���� �ɸ�.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=6) {
								System.out.println("������.");
								fishN = "����";
								System.out.println("������ ���ҽ��ϴ�.");
								System.out.println("ȸĥ���?(1) �����ٱ��?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("���");
								}
							} else {
								System.out.println("���������ϴ�.\n ������ ���ƽ��ϴ�.");
							}
						} else if (6<=ran && ran<=8) {
							System.out.println("���� �ɸ�.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=5) {
								System.out.println("����.");
								fishN = "���";
								System.out.println("�� ���ҽ��ϴ�.");
								System.out.println("ȸĥ���?(1) �����ٱ��?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("���");
								}
							} else {
								System.out.println("�����ϴ�.\n �� ���ƽ��ϴ�.");
							}
						} else {
							System.out.println("���� �ɸ�.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=8) {
								System.out.println("��ì�̴�.");
								fishN = "��ì��";
								System.out.println("��æ�̸� ���ҽ��ϴ�.");
								System.out.println("ȸĥ���?(1) �����ٱ��?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("���");
								}
							} else {
								System.out.println("��ì�̿����ϴ�.\n ��ì�̸� ���ƽ��ϴ�.");
							}
						}
						break;
					case 1:
						if(ran <= 6) {
							System.out.println("���� �ɸ�.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=4) {
								System.out.println("�����̴�.");
								fishN = "����";
								System.out.println("������ ���ҽ��ϴ�.");
								System.out.println("ȸĥ���?(1) �����ٱ��?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("���");
								}
							} else {
								System.out.println("�����̿����ϴ�.\n ������ ���ƽ��ϴ�.");
							}
						} else if (7<=ran && ran<=9) {
							System.out.println("���� �ɸ�.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=8) {
								System.out.println("��ì�̴�.");
								fishN = "��ì��";
								System.out.println("��ì�̸� ���ҽ��ϴ�.");
								System.out.println("ȸĥ���?(1) �����ٱ��?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("���");
								}
							} else {
								System.out.println("��ì�̿����ϴ�.\n ��ì�̸� ���ƽ��ϴ�.");
							}
						} else {
							System.out.println("���� �ɸ�.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=3) {
								System.out.println("�ٱݹٸ���.");
								fishN = "�ٱݹٸ�";
								System.out.println("�ٱݹٸ��� ���ҽ��ϴ�.");
								System.out.println("ȸĥ���?(1) �����ٱ��?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("���");
								}
							} else {
								System.out.println("�ٱݹٸ������ϴ�.\n �ٱݹٸ��� ���ƽ��ϴ�.");
							}
						}
						break;
					case 4:
						System.out.println("�޴�â���� ���ϴ�.");
						run = false;
					}
				}
			} else {
				System.out.println("�̸� �Ǵ� ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
				System.out.println("�ٽ� �Է��ϼ���.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


private void janFight(String charName, String fishN) {
	String sql = "SELECT * FROM FISHTABLE WHERE NAME = ?";
	String sql1 = "SELECT * FROM FISH WHERE FISHNAME = ?";
	int pSTR = 0;
	int pHP = 0;
	int fSTR = 0;
	int fHP = 0;
	int fScore = 0, fPoint = 0;
	boolean run = true;
	try {
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, charName);
		pstmt1 = con.prepareStatement(sql1);
		pstmt1.setString(1, fishN);
//		pstmt.executeUpdate();
//		pstmt1.executeUpdate();
		rs = pstmt.executeQuery();
		rs1 = pstmt1.executeQuery();
		while(rs.next()) {
			pSTR = rs.getInt("STR");
			pHP = rs.getInt("HP");
//			pScore = rs.getInt("SCORE");
//			pPoint = rs.getInt("ABILPOINT");
		}
		while(rs1.next()) {
			fSTR = rs1.getInt("FISHSTR");
			fHP = rs1.getInt("FISHHP");
			fScore = rs1.getInt("FISHSCORE");
			fPoint = rs1.getInt("FISHPOINT");
		}
		while(run) {
			fHP = fHP - pSTR;
			System.out.println("--------------------------------------");
			System.out.print("����:\t");
			System.out.println(pSTR+ "�� Damage ��.");
			pHP = pHP - fSTR;
			System.out.print("����\t");
			System.out.print(fSTR+ "�� Damage ����.\t");
			System.out.println("�÷��̾��� HP�� "+pHP+ "����.");
			if(fHP<=0) {
				System.out.println(fishN+"�� �ܲ�");
				scoreP(charName, fishN, fScore, fPoint);
				run = false;
			} else if(pHP<=0) {
				System.out.println(fishN + "���� �����ϴ�");
				run = false;
			}
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


private void scoreP(String charName, String fishN, int fScore, int fPoint) {
	String sql = "UPDATE FISHTABLE SET SCORE = SCORE + ?, ABILPOINT = ABILPOINT + ? WHERE NAME= ?";
	String sql1 = "SELECT SCORE, ABILPOINT FROM FISHTABLE WHERE NAME = ?";
	int resultScore = 0;
	int resultPoint = 0;
	try {
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, fScore);
		pstmt.setInt(2, fPoint);
		pstmt.setString(3, charName);
		pstmt1 = con.prepareStatement(sql1);
		pstmt1.setString(1, charName);
		pstmt.executeUpdate();
		rs1 = pstmt1.executeQuery();
		while(rs1.next()) {
			resultScore = rs1.getInt("SCORE");
			resultPoint = rs1.getInt("ABILPOINT");
		}
		System.out.println(charName+ "�� ���� ���ھ�: ���� " + resultScore + "���Դϴ�.");
		System.out.println(charName+ "�� ���� ����Ʈ: ����" + resultPoint + "���Դϴ�.");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


public void Rank(FishDTO FDTO) {
	String sql = "SELECT NAME, SCORE FROM FISHTABLE ORDER BY SCORE DESC";
	try {
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		System.out.println("-------------------------");
		System.out.println("Score �����Դϴ�.");
		while(rs.next()) {
			System.out.println(rs.getString("NAME") + "�� ����: " + rs.getInt("SCORE"));
		}
		System.out.println("-------------------------");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	
}
