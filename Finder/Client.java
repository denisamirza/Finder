import java.util.ArrayList;

public class Client {
	
	private int ID, x, y, lastX, lastY;
	private static int i = 1;
	private SquarePath p;
	
	public Client(Map m, int x, int y) {
		this.x = x;
		this.y = y;
		this.ID = i;
		p = new SquarePath();
		i++;
		m.setPoint(x, y, this); 
		System.out.println("Client added");
	}
	
	public int getID() {
		return this.ID;
	}
	
	public String toString() {
		return ID + "";
	}

    public void move(Map m, int direction) throws Out, Overlay {
		
		this.lastX = this.x;
		this.lastY = this.y;
			
		switch(direction) {
			case 8: this.x = this.x-1;
                  break;
			case 2: this.x = this.x+1;
			      break;
			case 4: this.y = this.y-1;
                  break;
			case 6: this.y = this.y+1;
			      break;	
		}
					
		if(this.x < 0 || this.y < 0 || this.x >= m.getN() || this.y >= m.getN()) {
			this.x = this.lastX;
			this.y = this.lastY;
			throw new Out();
		}
		else if(m.getPoint(this.x, this.y) != null && !(m.getPoint(this.x, this.y) instanceof String) ) {
			this.x = this.lastX;
			this.y = this.lastY;
			throw new Overlay();
		}
		else {
		   
		   m.setPoint(this.x, this.y, this);
		   m.setPoint(this.lastX, this.lastY, ".");
		}
		
	}

	//find the 3 closest antennas and send them the distance betweeen the client and each antenna
	public void sendDataTo3ClosestAntenna(Map m) {
		
		int radius, contor = 0, i;
		ArrayList<Point> path = new ArrayList<>();
		
		for(radius = 1; radius < m.getN(); radius++) {
			path = p.generateSquarePath(m, radius, this.x, this.y);
			for(i = 0; i < path.size() && (contor < 3); i++)
				if(m.getPoint(path.get(i).getX(), path.get(i).getY()) instanceof Antenna) {				
				    ClientData d = new ClientData(this, radius);
					((Antenna) m.getPoint(path.get(i).getX(), path.get(i).getY())).addClientData(d);
					contor++;
				}
		}
	}

}
