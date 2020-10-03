import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {
		
		int n = 10;
		Map clientMap = new Map(n);
		Map simulatedMap = new Map(n);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui(clientMap, simulatedMap);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
