package fishing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FishDAO {
	Connection con = null; // 접속허가 정보를 담는 변수
	PreparedStatement pstmt = null; // 쿼리문 전송을 담당하는 변수
	PreparedStatement pstmt1 = null; // 쿼리문 전송을 담당하는 변수
	ResultSet rs = null; // SELECT 결과를 저장하는 변수
	ResultSet rs1 = null; // SELECT 결과를 저장하는 변수
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
				System.out.print("캐릭터 이름: "+rs.getString("NAME") + "\t");
				System.out.print("스탯 포인트: "+rs.getInt("ABILPOINT") + "\t");
				System.out.print("파워: "+rs.getInt("STR") + "\t");
				System.out.print("민첩: "+rs.getInt("DEX") + "\t");
				System.out.print("체력: "+rs.getInt("HP") + "\t");
				System.out.print("캐릭터 생성날짜: "+rs.getString("FDATE") + "\t");
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
					System.out.print("STR 투자 포인트: ");
					int str = scan.nextInt();
					System.out.print("DEX 투자 포인트: ");
					int dex = scan.nextInt();
					System.out.print("HP 투자 포인트: ");
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
						System.out.println("Point가 부족합니다.");
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
					System.out.println("저수지(1) | 한강(2) | 울릉도(3) | 메뉴창 가기(4)");
					System.out.println("----------------------------------------------");
					System.out.print("지역을 선택하세요(숫자): ");
					int selectP = scan.nextInt();
					int ran = (int) (Math.random() * 10) + 1;
					
					switch(selectP) {
					case 3:
						if(ran <= 7) {
							System.out.println("무언가 걸림.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=7) {
								System.out.println("잔챙이다.");
								fishN = "잔챙이";
								System.out.println("잔챙이를 낚았습니다.");
								System.out.println("회칠까요?(1) 놓아줄까요?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("방생");
								}
							} else {
								System.out.println("잔챙이였습니다.\n 잔챙이를 놓쳤습니다.");
							}
						} else if (8<=ran && ran<=9) {
							System.out.println("무언가 걸림.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=6) {
								System.out.println("붕어다.");
								fishN = "붕어";
								System.out.println("붕어를 낚았습니다.");
								System.out.println("회칠까요?(1) 놓아줄까요?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("방생");
								}
							} else {
								System.out.println("붕어였습니다.\n 붕어를 놓쳤습니다.");
							}
						} else {
							System.out.println("무언가 걸림.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=5) {
								System.out.println("베스다.");
								fishN = "베스";
								System.out.println("베스를 낚았습니다.");
								System.out.println("회칠까요?(1) 놓아줄까요?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("방생");
								}
							} else {
								System.out.println("베스였습니다.\n 베스를 놓쳤습니다.");
							}
						}
						break;
					case 2:
						if(ran <= 5) {
							System.out.println("무언가 걸림.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=6) {
								System.out.println("베스다.");
								fishN = "베스";
								System.out.println("베스를 낚았습니다.");
								System.out.println("회칠까요?(1) 놓아줄까요?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("방생");
								}
							} else {
								System.out.println("베스였습니다.\n 베스를 놓쳤습니다.");
							}
						} else if (6<=ran && ran<=8) {
							System.out.println("무언가 걸림.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=5) {
								System.out.println("장어다.");
								fishN = "장어";
								System.out.println("장어를 낚았습니다.");
								System.out.println("회칠까요?(1) 놓아줄까요?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("방생");
								}
							} else {
								System.out.println("장어였습니다.\n 장어를 놓쳤습니다.");
							}
						} else {
							System.out.println("무언가 걸림.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=8) {
								System.out.println("잔챙이다.");
								fishN = "잔챙이";
								System.out.println("장챈이를 낚았습니다.");
								System.out.println("회칠까요?(1) 놓아줄까요?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("방생");
								}
							} else {
								System.out.println("잔챙이였습니다.\n 잔챙이를 놓쳤습니다.");
							}
						}
						break;
					case 1:
						if(ran <= 6) {
							System.out.println("무언가 걸림.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=4) {
								System.out.println("참동이다.");
								fishN = "참돔";
								System.out.println("참돔을 낚았습니다.");
								System.out.println("회칠까요?(1) 놓아줄까요?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("방생");
								}
							} else {
								System.out.println("참돔이였습니다.\n 참돔을 놓쳤습니다.");
							}
						} else if (7<=ran && ran<=9) {
							System.out.println("무언가 걸림.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=8) {
								System.out.println("잔챙이다.");
								fishN = "잔챙이";
								System.out.println("잔챙이를 낚았습니다.");
								System.out.println("회칠까요?(1) 놓아줄까요?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("방생");
								}
							} else {
								System.out.println("잔챙이였습니다.\n 잔챙이를 놓쳤습니다.");
							}
						} else {
							System.out.println("무언가 걸림.");
							int ran1 = (int) (Math.random() * 10) + 1;
							if(ran1<=3) {
								System.out.println("다금바리다.");
								fishN = "다금바리";
								System.out.println("다금바리를 낚았습니다.");
								System.out.println("회칠까요?(1) 놓아줄까요?(2)");
								int selectT = scan.nextInt();
								if(selectT == 1) {
									janFight(charName, fishN);
								} else {
									System.out.println("방생");
								}
							} else {
								System.out.println("다금바리였습니다.\n 다금바리를 놓쳤습니다.");
							}
						}
						break;
					case 4:
						System.out.println("메뉴창으로 갑니다.");
						run = false;
					}
				}
			} else {
				System.out.println("이름 또는 비밀번호를 틀렸습니다.");
				System.out.println("다시 입력하세요.");
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
			System.out.print("어택:\t");
			System.out.println(pSTR+ "의 Damage 줌.");
			pHP = pHP - fSTR;
			System.out.print("뺘악\t");
			System.out.print(fSTR+ "의 Damage 받음.\t");
			System.out.println("플레이어의 HP는 "+pHP+ "남음.");
			if(fHP<=0) {
				System.out.println(fishN+"을 꿀꺽");
				scoreP(charName, fishN, fScore, fPoint);
				run = false;
			} else if(pHP<=0) {
				System.out.println(fishN + "에게 졌습니다");
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
		System.out.println(charName+ "님 점수 스코어: 현재 " + resultScore + "점입니다.");
		System.out.println(charName+ "님 스탯 포인트: 현재" + resultPoint + "개입니다.");
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
		System.out.println("Score 순위입니다.");
		while(rs.next()) {
			System.out.println(rs.getString("NAME") + "님 점수: " + rs.getInt("SCORE"));
		}
		System.out.println("-------------------------");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	
}
