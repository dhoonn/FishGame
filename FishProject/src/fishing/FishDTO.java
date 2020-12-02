package fishing;

public class FishDTO {
	String name;
	String password;
	int str;
	int dex;
	int hp;
	int abil;
	int score;
	String data;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStr() {
		return str;
	}
	public void setStr(int str) {
		this.str = str;
	}
	public int getDex() {
		return dex;
	}
	public void setDex(int dex) {
		this.dex = dex;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getAbil() {
		return abil;
	}
	public void setAbil(int abil) {
		this.abil = abil;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	public FishDTO(String name, String password, int str, int dex, int hp, int abil, int score, String data) {
		super();
		this.name = name;
		this.password = password;
		this.str = str;
		this.dex = dex;
		this.hp = hp;
		this.abil = abil;
		this.score = score;
		this.data = data;
	}
	
	
	public FishDTO() {
		super();
	}
	
	
	@Override
	public String toString() {
		return "FishDTO [name=" + name + ", password=" + password + ", str=" + str + ", dex=" + dex + ", hp=" + hp
				+ ", abil=" + abil + ", score=" + score + ", data=" + data + "]";
	}
	
	

}
