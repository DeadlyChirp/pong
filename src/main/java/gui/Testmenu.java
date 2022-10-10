package gui;
import javafx.application.Application;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import model.Court; //plus tard pour paramétrer taille, etc
import javafx.stage.Stage;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import gui.Buttoncolor;
import gui.BackgroungPanel;
import javax.swing.SwingConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Testmenu extends Application{

	    public void start (Stage primaryStage) {

	    	App a = new App();
	        // Frame de départ
	        JFrame frame = new JFrame("Pong");
	       
	        //Label pour le titre
			ImageIcon logo = new ImageIcon("/home/jeremy/pong/src/main/java/gui/pong.png");
	        JLabel label = new JLabel("", JLabel.CENTER);
	        label.setIcon(logo);
			frame.add(label);
	    
	        // Panel de départ
	        JPanel panel = new JPanel();

	        // Définir les boutons
	        // JButton Jouer = new JButton("Jouer");
			Buttoncolor Jouer = new Buttoncolor("", "/home/jeremy/pong/src/main/java/gui/play.png", "/home/jeremy/pong/src/main/java/gui/play.png");
	        Buttoncolor Option = new Buttoncolor("", "/home/jeremy/pong/src/main/java/gui/option.png", "/home/jeremy/pong/src/main/java/gui/option.png");
	        Buttoncolor Quitter = new Buttoncolor("", "/home/jeremy/pong/src/main/java/gui/exit.png", "/home/jeremy/pong/src/main/java/gui/exit.png");      
			// Jouer.setPreferredSize(new Dimension(120,60));
			// Option.setPreferredSize(new Dimension(120,60));
			// Quitter.setPreferredSize(new Dimension(120,60));


			// Ajouter les boutons au frame
	        panel.add(Jouer); 
	        panel.add(Option);
	        panel.add(Quitter);
	         
	        // Ajouter label et panel au frame
	        frame.setLayout(new GridLayout(2, 1));
	        frame.add(label);
	        frame.add(panel);
	         
	        frame.pack();
	        frame.setSize(1000, 600);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
	        
	        Quitter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
					System.exit(0);
				}
			});
	        
	        Jouer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {    
					frame.remove(label);   
	    	        panel.remove(Quitter);
	    	        panel.remove(Jouer);
	    	        panel.remove(Option);
					a.start(primaryStage);
				}
			});

			
	        
	        Option.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent a) {
	    	        JButton Commandes = new JButton("Commandes");
	    	        JButton Esthetique = new JButton("Esthetique");
	    	        JButton Stat = new JButton("Statistiques");     
	    	        JButton Retour = new JButton("Retour");

	    	        panel.remove(Quitter);
	    	        panel.remove(Jouer);
	    	        panel.remove(Option);
	    	        panel.add(Commandes); 
	    	        panel.add(Esthetique);
	    	        panel.add(Stat);
	    	        panel.add(Retour);
	    	         
	    	        frame.setLayout(new GridLayout(2, 1));
	    	        frame.add(label);
	    	        frame.add(panel);
	    	         
	    	        frame.pack();
	    	        frame.setSize(1000, 600);
	    	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	        frame.setVisible(true);
	    	        
	    	        Retour.addActionListener(new ActionListener() {
		 	        	public void actionPerformed(ActionEvent a) {
							panel.remove(Commandes);
							panel.remove(Esthetique);
							panel.remove(Stat);
							panel.remove(Retour);
							panel.add(Jouer);
							panel.add(Option);
							panel.add(Quitter);
							frame.setLayout(new GridLayout(2, 1));
							frame.add(label);
							frame.add(panel);
							 
							frame.pack();
							frame.setSize(1000, 600);
							frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							frame.setVisible(true);
		 	        	}
		 	        });
	        	}
	        	 
	        });
	        
	       
			
	    }


}
	
