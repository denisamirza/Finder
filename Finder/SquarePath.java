import java.util.ArrayList;

public class SquarePath {
	
	public boolean isInBounderies(Map m, int x, int y) {
		if(x >= 0 && x < m.getN() && y >=0 && y < m.getN())
			return true;
		return false;
	}
	
    public ArrayList<Point> generateSquarePath(Map m, int radius, int x, int y) {
		
		ArrayList<Point> p = new ArrayList<>();
		int i, j, k;
		
		j = y-radius;
		k = y+radius;
		
		for(i = x-radius; i <= x+radius; i++) {
			if(isInBounderies(m, i, j))
				p.add(new Point(i, j));
			if(isInBounderies(m, i, k))
				p.add(new Point(i, k));
		}
		
		j = x-radius;
		k = x+radius;
		
		for(i = y-radius; i < y+radius; i++) {
			if(isInBounderies(m, j, i))
				p.add(new Point(j, i));
			if(isInBounderies(m, k, i))
				p.add(new Point(k, i));
		}	
		
		return p;
		
	}

}
