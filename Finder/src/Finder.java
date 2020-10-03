import java.util.ArrayList;

public class Finder {

	private ArrayList<Antenna> allAnt;
	private ArrayList<Antenna> closestAnt;
	private ArrayList <Point> p1;
	private ArrayList <Point> p2;
	private ArrayList <Point> p3;
	private int x, y, ID;
	
	public Finder() {
		allAnt = new ArrayList<>();	
		closestAnt = new ArrayList<>();
	}
	
	public void addAntenna(Antenna a) {
		allAnt.add(a);
	}
	
	public void clear() {
		closestAnt.clear();
		for(int i = 0; i < allAnt.size(); i++)
			allAnt.get(i).deleteData();
	}
	
	public void find3ClosestAnt(int ID) {
		for(int i = 0; i < allAnt.size(); i++)
			for(int j = 0; j < allAnt.get(i).size(); j++)
				if(allAnt.get(i).getDataArr(j).getClient().getID() == ID) {
			    	allAnt.get(i).setPositionOfClient(j);
				    closestAnt.add(allAnt.get(i));
			    }
	}
	
	public void checkErrors(Map m, Map n, Clients c) {
		boolean ok;
		ArrayList <Client> errorArr = new ArrayList<>();

		for(int k = 0; k < c.size(); k++) {
			ok = false;
			for(int i = 0; i < n.getN(); i++) {
				for(int j = 0; j < n.getN(); j++)
					if(n.getPoint(i, j) instanceof String)
					   if(n.getPoint(i, j).equals(c.getClient(k).getID()+"") )
		                      ok = true;
			}
			if(ok == false)
			    errorArr.add(c.getClient(k));
	 }
			
		for(int i = 0; i < errorArr.size(); i++) {
			
	     	this.find3ClosestAnt(errorArr.get(i).getID());
	    	this.findClient(m, n);
	    	this.setGeneratedClient(n);
		}
			
	}
	
    public int size() {
    	return allAnt.size();
    }
    
    public Antenna getAntenna(int index) {
    	return allAnt.get(index);
    }
	
	public boolean sameCoordinates(int i, int j, int k) {
		if(this.p1.get(i).getX() == this.p2.get(j).getX() && this.p2.get(j).getX() == this.p3.get(k).getX())
			if(this.p1.get(i).getY() == this.p2.get(j).getY() && this.p2.get(j).getY() == this.p3.get(k).getY())
	             return true;
		return false;
	}
	
	public void findClient(Map m, Map n) {
		
		int i, j, k;
		boolean ok = true;
		
		Path p = new Path();
		
		ArrayList <Antenna> a = new ArrayList<>();
		
		a = closestAnt;
		
		this.p1=p.generatePath(m, a.get(0).getDataArr(a.get(0).getPositionOfClient()).getDistance(), a.get(0).getX(), a.get(0).getY());
		this.p2=p.generatePath(m, a.get(1).getDataArr(a.get(1).getPositionOfClient()).getDistance(), a.get(1).getX(), a.get(1).getY());
		this.p3=p.generatePath(m, a.get(2).getDataArr(a.get(2).getPositionOfClient()).getDistance(), a.get(2).getX(), a.get(2).getY());
		
		for(i = 0; i < this.p1.size() && ok; i++) {
			
			for(j = 0; j < this.p2.size() && ok; j++) {
				
				for(k = 0; k < this.p3.size() && ok; k++)
			         
					if(this.sameCoordinates(i, j, k)) {
						
			        	   if(n.getPoint(this.p1.get(i).getX(), this.p1.get(i).getY()) == null && !(m.getPoint(this.p1.get(i).getX(), this.p1.get(i).getY()) instanceof Antenna) ) {
			        	        ok = false;
			        	        this.x = this.p1.get(i).getX();
					        	this.y = this.p1.get(i).getY();
					        	this.ID = a.get(0).getDataArr(a.get(0).getPositionOfClient()).getClient().getID();
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
	
	public void setGeneratedClient(Map m) {
		m.refresh(this.x, this.y, this.ID + "");
		m.setPoint(this.x, this.y, this.ID + "");
	}
	
	public boolean enoughAntennas() {
		return (allAnt.size() >= 3);
	}

}
