import java.util.ArrayList;

public class Finder {

	private ArrayList<Antenna> allAntennas;
	private ArrayList<Antenna> closestAntennas;
	private ArrayList <Point> squarePathFirstAntenna;
	private ArrayList <Point> squarePathSecondAntenna;
	private ArrayList <Point> squarePathThirdAntenna;
	private int x, y, ID;
	
	public Finder() {
		allAntennas = new ArrayList<>();
		closestAntennas = new ArrayList<>();
	}
	
	public void addAntenna(Antenna a) {
		allAntennas.add(a);
	}
	
	public void clear() {
		closestAntennas.clear();
		for(int i = 0; i < allAntennas.size(); i++)
			allAntennas.get(i).deleteClientDataArray();
	}

	//search for the antennas (in allAntennas arraylist) the client with a specific ID has connected to
	//then add that 3 finded closest antennas in the closestAntennas arraylist
	public void searchAntennasConnectedWithClient(int ID) {
		for (int i = 0; i < allAntennas.size(); i++)
			for (int j = 0; j < allAntennas.get(i).size(); j++)
				if (allAntennas.get(i).getClientData(j).getClient().getID() == ID) {
					allAntennas.get(i).setPositionOfClient(j);
					closestAntennas.add(allAntennas.get(i));
				}
	}
	
    public int size() {
    	return allAntennas.size();
    }
    
    public Antenna getAntenna(int index) {
    	return allAntennas.get(index);
    }
	
	public boolean sameCoordinates(int i, int j, int k) {
		if(this.squarePathFirstAntenna.get(i).getX() == this.squarePathSecondAntenna.get(j).getX() && this.squarePathSecondAntenna.get(j).getX() == this.squarePathThirdAntenna.get(k).getX())
			if(this.squarePathFirstAntenna.get(i).getY() == this.squarePathSecondAntenna.get(j).getY() && this.squarePathSecondAntenna.get(j).getY() == this.squarePathThirdAntenna.get(k).getY())
	             return true;
		return false;
	}
	
	public void generateClientCoordinates(Map m, Map n) {
		
		int i, j, k;
		boolean ok = true;
		
		SquarePath p = new SquarePath();
		
		ArrayList <Antenna> a = new ArrayList<>();
		
		a = closestAntennas;
		
		this.squarePathFirstAntenna =p.generateSquarePath(m, a.get(0).getClientData(a.get(0).getPositionOfClient()).getDistance(), a.get(0).getX(), a.get(0).getY());
		this.squarePathSecondAntenna =p.generateSquarePath(m, a.get(1).getClientData(a.get(1).getPositionOfClient()).getDistance(), a.get(1).getX(), a.get(1).getY());
		this.squarePathThirdAntenna =p.generateSquarePath(m, a.get(2).getClientData(a.get(2).getPositionOfClient()).getDistance(), a.get(2).getX(), a.get(2).getY());
		
		for(i = 0; i < this.squarePathFirstAntenna.size() && ok; i++) {
			
			for(j = 0; j < this.squarePathSecondAntenna.size() && ok; j++) {
				
				for(k = 0; k < this.squarePathThirdAntenna.size() && ok; k++)
			         
					if(this.sameCoordinates(i, j, k)) {
						
			        	   if(n.getPoint(this.squarePathFirstAntenna.get(i).getX(), this.squarePathFirstAntenna.get(i).getY()) == null && !(m.getPoint(this.squarePathFirstAntenna.get(i).getX(), this.squarePathFirstAntenna.get(i).getY()) instanceof Antenna) ) {
			        	        ok = false;
			        	        this.x = this.squarePathFirstAntenna.get(i).getX();
					        	this.y = this.squarePathFirstAntenna.get(i).getY();
					        	this.ID = a.get(0).getClientData(a.get(0).getPositionOfClient()).getClient().getID();
			        	        break;
			        	   }

			           }
				if(ok == false)
					break;
			}
			if(ok == false)
				break;
		}
			
	}

	//put the generated client in the second map
	public void setSimulatedClient(Map m) {
		m.deleteTrace(this.x, this.y, this.ID + "");
		m.setPoint(this.x, this.y, this.ID + "");
	}
	
	public boolean enoughAntennas() {
		return (allAntennas.size() >= 3);
	}

}
