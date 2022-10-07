package gui;
import javafx.application.Application;
import model.Court; //plus tard pour paramétrer taille, etc
import javafx.stage.Stage;

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
	        JLabel label = new JLabel("PONG", JLabel.CENTER);
	        frame.add(label);
	    
	        // Panel de départ
	        JPanel panel = new JPanel();

	        // Définir les boutons
	        JButton Jouer = new JButton("Jouer");
	        JButton Option = new JButton("Option");
	        JButton Quitter = new JButton("Quitter le jeu");      

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
				}
			});
	        
	        Jouer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {       
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
		 	    	        
		 	    	     
		 	        	}
		 	        });
	        	}
	        	 
	        });
	        
	       
			
	    }


}
	
