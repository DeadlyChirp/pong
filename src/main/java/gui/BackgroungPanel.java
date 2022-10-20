package gui;
 
import java.awt.Graphics;
 
import javax.swing.ImageIcon;
import javax.swing.JPanel;
 
public class BackgroungPanel extends JPanel {
 
    private static final long serialVersionUID = 1L;
    private ImageIcon background;
   
    public BackgroungPanel(String fileName) {
        super();
        this.background = new ImageIcon(fileName);
    }
 
    public void setBackground(ImageIcon background) {
        this.background = background;
    }
 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, this);
    }
}