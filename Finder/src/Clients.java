import java.util.ArrayList;

public class Clients {
	
	private ArrayList<Client> arrClients= new ArrayList<>();
	
	public void addClient(Client c) {
		arrClients.add(c);
	}
	
	public Client getClient(int index) {
		return arrClients.get(index);
	}
	
	public int size() {
		return arrClients.size();
	}

}
