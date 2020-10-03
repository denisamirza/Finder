import java.util.ArrayList;

public class Antenna {

	private int pos, x, y;
	private ArrayList<ClientData> ClientDataArray = new ArrayList<>();
	
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

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	//position of the searched client in the ClientDataArray of the antenna
	public void setPositionOfClient(int pos) {
		this.pos = pos;
	}
	
	public int getPositionOfClient() {
		return this.pos;
	}
	
	public int size() {
		return ClientDataArray.size();
	}
	
	public String toString() {
		return "A";
	}
	
	public ClientData getClientData(int index) {
		return ClientDataArray.get(index);
	}
	
	public void addClientData(ClientData d) {
		ClientDataArray.add(d);
	}
	
	public void deleteClientDataArray() {
		ClientDataArray.clear();
	}
	
}
