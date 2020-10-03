import java.util.ArrayList;

public class Path {
	
	public boolean isBounded(Map m, int x, int y) {
		if(x >= 0 && x < m.getN() && y >=0 && y < m.getN())
			return true;
		return false;
	}
	
    public ArrayList<Point> generatePath(Map m, int radius, int x, int y) {
		
		ArrayList<Point> p = new ArrayList<>();
		int i, j, k;
		
		j = y-radius;
		k = y+radius;
		
		for(i = x-radius; i <= x+radius; i++) {
			if(isBounded(m, i, j))
				p.add(new Point(i, j));
			if(isBounded(m, i, k))
				p.add(new Point(i, k));
		}
		
		j = x-radius;
		k = x+radius;
		
		for(i = y-radius; i < y+radius; i++) {
			if(isBounded(m, j, i))
				p.add(new Point(j, i));
			if(isBounded(m, k, i))
				p.add(new Point(k, i));
		}	
		
		return p;
		
	}

}
