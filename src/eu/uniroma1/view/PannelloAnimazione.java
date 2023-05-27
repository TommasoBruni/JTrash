package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class PannelloAnimazione extends JPanel implements ActionListener
{
	private int x;
	private int y;
	private int xSpeed = 15;
	private int ySpeed = 15;
	private ImageIcon moveIcon;
	private JButton mazzoDiCarte;
	private Timer animationTimer;
	private int i;
	
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		Graphics2D graphic2D = (Graphics2D)g;
		
		if (i < 1)
		{
			x = getWidth()/2 - moveIcon.getIconWidth()/2;
			y = getHeight()/2 - moveIcon.getIconHeight()/2;	
		}
		graphic2D.drawImage(moveIcon.getImage(), x, y, null);
		i++;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		x += xSpeed;
		y += ySpeed;
		//carteDaPescare2.setLocation(x, y);
		repaint();
	}
	
	public PannelloAnimazione()
	{
		//setPreferredSize(new Dimension(500, 500));
		try
		{
			/* Leggo l'immagine salvata nella directory "resources" dentro il progetto */
			moveIcon = new ImageIcon(System.getProperty("user.dir").concat("\\resources\\carta_da_gioco_verticale.jpg"));
		} 
		catch (Exception ex) 
		{
			/* Lanciare un'altra eccezione */
		    throw ex;
		}
		
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
        
        mazzoDiCarte = new JButton();
        mazzoDiCarte.setIcon(moveIcon);
        mazzoDiCarte.setPreferredSize(new Dimension(39, 55));
        
        add(mazzoDiCarte, gbc);
        //add(carteDaPescare);
        //add(carteDaPescare2);
        /*
        JPanel buttons = new JPanel();
        buttons.add(carteDaPescare);
        buttons.add(carteDaPescare2);

        add(buttons, BorderLayout.CENTER);
        */
        //this.x = carteDaPescare2.getLocation().x;
        //this.y = carteDaPescare2.getLocation().y;
        
		animationTimer = new Timer(50, this);
		animationTimer.restart();
	}
}
