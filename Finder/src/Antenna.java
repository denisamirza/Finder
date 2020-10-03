import java.util.ArrayList;

public class Antenna {

	private int pos, x, y;
	private ArrayList<Data> dataArr = new ArrayList<>();
	
	public Antenna(Map m, int x, int y) {
		this.x = x;
		this.y = y;
		m.setPoint(x, y, this);
		System.out.println("Antenna added");
	}
	
	public Antenna(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPositionOfClient(int pos) {
		this.pos = pos;
	}
	
	public int getPositionOfClient() {
		return this.pos;
	}
	
	public int size() {
		return dataArr.size();
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public String toString() {
		return "A";
	}
	
	public Data getDataArr(int index) {
		return dataArr.get(index);
	}
	
	public void addData(Data d) {
		dataArr.add(d);
	}
	
	public void deleteData() {
		dataArr.clear();
	}
	
}
