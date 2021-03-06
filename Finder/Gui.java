import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JTextPane;


public class Gui {

	public JFrame frame;
	private Map clientMap, simulatedMap;
	private boolean addAntennaButtonWasPressed = false, clientButtonWasPressed = false;
	private int ID = -1;
	private ArrayList <Client> clientsArray = new ArrayList<>();
	private Finder finder = new Finder();
	public JButton btnClientMap[][], btnDirection[];
    public  JLabel lblGeneratedMap[][];
	private int [] directionArray;
	private int i, j, k, l;

	public Gui(Map clientMap, Map simulatedMap) {
		this.clientMap = clientMap;
		this.simulatedMap = simulatedMap;
		this.btnClientMap = new JButton[clientMap.getN()][clientMap.getN()];
		this.btnDirection = new JButton[4];
		this.lblGeneratedMap = new JLabel[clientMap.getN()][clientMap.getN()];
		this.directionArray = new int[]{8, 4, 2, 6};
		initialize();
	}
	
	public void displayGeneratedMap() {
		

		for(i = 0; i < clientMap.getN(); i++)
			for(j = 0; j < clientMap.getN(); j++)
		          if(simulatedMap.getPoint(i, j) instanceof String)
                        this.lblGeneratedMap[i][j].setIcon(new ImageIcon(this.getClass().getResource("cll.png")));
		          else  this.lblGeneratedMap[i][j].setIcon(null);
		
		System.out.println(simulatedMap);
	}
	
	public void displayClientMap() {

		for(i = 0; i < clientMap.getN(); i++)
			for(j = 0; j < clientMap.getN(); j++) {
				
				     if(clientMap.getPoint(i, j) instanceof Antenna)
					 	this.btnClientMap[i][j].setIcon(new ImageIcon(this.getClass().getResource("Antenna.png")));
				     else if(clientMap.getPoint(i, j) instanceof  Client)
					 	this.btnClientMap[i][j].setIcon(new ImageIcon(this.getClass().getResource("cll.png")));
				     else if(clientMap.getPoint(i, j) instanceof String)
					 	this.btnClientMap[i][j].setIcon(new ImageIcon(this.getClass().getResource("Dot.png")));
					 else this.btnClientMap[i][j].setIcon(null);
		
			} 
         
         System.out.println(clientMap);
         
	}

	public void initialize() {
		frame = new JFrame("Finder");
		frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frame.setBounds(100, 100, 970, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.getContentPane().setLayout(null);
		Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("icon.png"));
		frame.setIconImage(image);
		
		
		JTextPane textField = new JTextPane();
		textField.setForeground(new Color(0, 0, 0));
		textField.setFont(new Font("Bitstream Charter", Font.BOLD, 13));
		textField.setText("        Add clients and antennas \n on the map! At least 3 antennas are\nrequired to compute the coordinates\n               of the clients.");
		textField.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textField.setBounds(661, 484, 246, 77);
		textField.setOpaque(false);
		textField.setBorder(null);
		frame.getContentPane().add(textField);
		
		
	    for(i = 0; i < btnDirection.length; i++) {
			final int m = i;
			btnDirection[m] = new JButton("");
			btnDirection[m].setActionCommand("");
			btnDirection[m].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(ID > 0) {
						try {
				        	  (clientsArray.get(ID-1)).move(clientMap, directionArray[m]);
				           }catch(Out w) {
				        	   System.out.println(w.getMessage());
				           }catch(Overlay w) {
				        	   System.out.println(w.getMessage());
				           }
					}
					else textField.setText("\n     You must select a clientButtonWasPressed from \n        the map in order to move.");
					
					displayClientMap();
					if(finder.enoughAntennas()) {
						simulatedMap.initialize();
	                     for(j = 0; j < clientsArray.size(); j++) {
							
							(clientsArray.get(j)).sendDataTo3ClosestAntenna(clientMap);
					     	finder.searchAntennasConnectedWithClient(j+1);
					    	finder.generateClientCoordinates(clientMap, simulatedMap);
					    	finder.setSimulatedClient(simulatedMap);
						    finder.clear();
						}
	                     finder.checkErrors(clientMap, simulatedMap, clientsArray);
	                     displayGeneratedMap();
					}
				}
			});
			btnDirection[m].setBorder(null);
			btnDirection[m].setBackground(new Color(190, 170, 150));
			btnDirection[m].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			frame.getContentPane().add(btnDirection[m]);
		}
		btnDirection[0].setIcon(new ImageIcon(this.getClass().getResource("u.png")));
		btnDirection[0].setBounds(330, 483, 55, 55);
		btnDirection[1].setIcon(new ImageIcon(this.getClass().getResource("l.png")));
		btnDirection[1].setBounds(274, 539, 55, 55);
		btnDirection[2].setIcon(new ImageIcon(this.getClass().getResource("d.png")));
		btnDirection[2].setBounds(330, 539, 55, 55);
		btnDirection[3].setIcon(new ImageIcon(this.getClass().getResource("r.png")));
		btnDirection[3].setBounds(386, 539, 55, 55);
		
		JButton c = new JButton("Add clientButtonWasPressed");
		c.setForeground(Color.BLACK);
		c.setBackground(new Color(190, 170, 150));
		c.setBorder(null);
		c.setFont(new Font("Bitstream Charter", Font.BOLD, 20));
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientButtonWasPressed = true;
			}
		});
		c.setBounds(41, 468, 199, 62);
		c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		c.setContentAreaFilled(true);
		c.setIcon(new ImageIcon(this.getClass().getResource("AddClient.png")));
		frame.getContentPane().add(c);
		
		
		JButton s = new JButton("Add antenna");
		s.setForeground(Color.BLACK);
		s.setBorderPainted(false);
		//s.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		s.setBackground(new Color(190, 170, 150));
		s.setFont(new Font("Bitstream Charter", Font.BOLD, 20));
		s.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAntennaButtonWasPressed = true;
			}
		});
		s.setBounds(41, 542, 199, 62);
		s.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		s.setContentAreaFilled(true);
		s.setIcon(new ImageIcon(this.getClass().getResource("antenna.png")));
		frame.getContentPane().add(s);

		 for(k = 40, i = 0; i < 10; k = k + 40, i++)
		        for(l = 40, j = 0; j < 10; l = l + 40, j++) {
		        	
		        	btnClientMap[i][j] = new JButton("");
		        	btnClientMap[i][j].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		        	btnClientMap[i][j].setOpaque(false);
		        	btnClientMap[i][j].setContentAreaFilled(false);
		        	btnClientMap[i][j].setBorderPainted(false);
		        	btnClientMap[i][j].setBounds(l, k, 40, 40);
					frame.getContentPane().add(btnClientMap[i][j]);
			
		}
		 
			for(i = 0; i < clientMap.getN(); i++)
				for(j = 0; j  < clientMap.getN(); j++) {
					
					final int m = i, n = j;

		        	btnClientMap[m][n].addActionListener(new ActionListener() {
		    			public void actionPerformed(ActionEvent arg0) {
		    				textField.setText("        Add clients and antennas \n on the map! At least 3 antennas are\nrequired to compute the coordinates\n               of the clients.");
		    				
		    				if( (!(clientMap.getPoint(m, n) instanceof Client) && !(clientMap.getPoint(m, n) instanceof Antenna) ) && (clientButtonWasPressed || addAntennaButtonWasPressed) ) {
		    					if(addAntennaButtonWasPressed) {
			    					
			    					addAntennaButtonWasPressed = false;
			    					finder.addAntenna(new Antenna(clientMap, m, n));
			    					
			    				}
			    				else if(clientButtonWasPressed) {
			    					clientButtonWasPressed = false;
			    					clientsArray.add(new Client(clientMap, m, n));
			    				}
		    					ID = -1;
		    				}	
		    				else if(clientButtonWasPressed || addAntennaButtonWasPressed) {
		    					ID = -1;
		    					textField.setText("                   Sorry :(\n       This field is already filled.\n             Try another one!");
		    				}
		    				else if(clientMap.getPoint(m, n) instanceof Client) {
		    					    ID = ((Client) clientMap.getPoint(m, n)).getID();
		    					    System.out.println(ID+"");
		    				     }
                            addAntennaButtonWasPressed = clientButtonWasPressed = false;

		    				displayClientMap();
		    				
		    				if(finder.enoughAntennas()) {
		    					simulatedMap.initialize();
		    					 for(k = 0; k < clientsArray.size(); k++) {
		    						 
		    						    final int o = k;
		    							
		    							(clientsArray.get(o)).sendDataTo3ClosestAntenna(clientMap);
		    					     	finder.searchAntennasConnectedWithClient(o+1);
		    					    	finder.generateClientCoordinates(clientMap, simulatedMap);
		    					    	finder.setSimulatedClient(simulatedMap);
		    						    finder.clear();
		    						 
		    						}
		    					 finder.checkErrors(clientMap, simulatedMap, clientsArray);
		    					 displayGeneratedMap();
		    				}

		    			}

		    		});
		        	
		        }
		
		 
		for(k = 480+40, i = 0; k < 480+401; k = k + 40, i++)
		       for(l = 40, j = 0; l < 401; l = l + 40, j++) {
		        	
	                lblGeneratedMap[j][i] = new JLabel("");
	                lblGeneratedMap[j][i].setBounds(k, l, 40, 40);
	                lblGeneratedMap[j][i].setOpaque(false);
		    		frame.getContentPane().add(lblGeneratedMap[j][i]);
		        	
		        }
	
	
		JLabel clientMap = new JLabel("");
		clientMap.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		clientMap.setOpaque(false);
		clientMap.setBounds(35, 35, 407, 408);
		clientMap.setIcon(new ImageIcon(this.getClass().getResource("/MyMap.png")));
		frame.getContentPane().add(clientMap);
		
		JLabel generatedMap = new JLabel("");
		generatedMap.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		generatedMap.setBounds(511, 35, 407, 408);
		generatedMap.setIcon(new ImageIcon(this.getClass().getResource("/MyMap.png")));
		generatedMap.setOpaque(false);
		frame.getContentPane().add(generatedMap);
		
		JLabel kitty = new JLabel();
		kitty.setBounds(523, 503, 98, 101);
		kitty.setOpaque(false);
	    kitty.setIcon(new ImageIcon(this.getClass().getResource("kitty.png")));
		frame.getContentPane().add(kitty);
		
		JLabel cloud = new JLabel();
		cloud.setBounds(613, 419, 300, 188);
		cloud.setOpaque(false);
		cloud.setIcon(new ImageIcon(this.getClass().getResource("cloud.png")));
		frame.getContentPane().add(cloud);	
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 970, 683);
		lblNewLabel.setIcon(new ImageIcon(this.getClass().getResource("Background.jpg")) );
		frame.getContentPane().add(lblNewLabel);
		
	}
}

