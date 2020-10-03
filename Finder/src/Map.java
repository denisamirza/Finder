
public class Map{
	
   private Object[][] map;
   private int N;
   
   public Map(int N) {
	   this.N = N;
	   this.map = new Object[N][N];
   }
   
   public int getN() {
	   return this.N;
   }
   
   public void setPoint(int x, int y, Object o) {
	   this.map[x][y] = o;
   }
   
   public void refresh(int x, int y, String ID) {
	   
	   for(int i = 0; i < this.N; i++)
		   for(int j = 0; j < this.N; j++) 
			   if(i != x && j != y && this.getPoint(i, j) instanceof String)
			       if( this.getPoint(i, j).equals(ID))
					   this.setPoint(i, j, null);
   }
   
   public void delete() {
	   for(int i = 0; i < this.N; i++)
		   for(int j = 0; j < this.N; j++)
			   this.setPoint(i, j, null);
   }
   
   public Object getPoint(int x, int y) {
	   return map[x][y];
   }
   
   public String toString() {
	   String s = "";
	   for(int i = 0; i < this.N; i++) {
		   for(int j = 0; j < this.N; j++)
			   if(map[i][j] != null)
			       s = s + map[i][j] + " ";
			   else s = s + "0 ";
		   s = s + "\n";
	   }
	   return s;
   }

}
