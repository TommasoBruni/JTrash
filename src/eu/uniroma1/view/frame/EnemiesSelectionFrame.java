package eu.uniroma1.view.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import eu.uniroma1.controller.FieldController;

public class EnemiesSelectionFrame extends JInternalFrame
{
	private List<ImageIcon> selectedAvatars;
	private ImageIcon[] avatarArray;
	private JLabel avatarLabel1;
	private int indiceIcone;
	
	public EnemiesSelectionFrame(GameFrame owner)
	{
		super("Seleziona nemici");
		
		avatarArray = new ImageIcon[] { new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_7.gif"), "Obito gif"),
									    new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_5.gif"), "Ragno gif"),
									    new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_9.gif"), "Akaza gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_11.gif"), "Deidara gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_14.gif"), "Baby spider gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_4.gif"), "Coccodrillo gif")};
		
		selectedAvatars = null;
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel panel = new JPanel(new GridBagLayout());
        JButton scorriInAlto = new JButton(new ImageIcon(System.getProperty("user.dir").concat("\\resources\\icona_scorrimento_in_alto.png")));
        JButton scorriInBasso = new JButton(new ImageIcon(System.getProperty("user.dir").concat("\\resources\\icona_scorrimento_in_basso.png")));
        
        scorriInAlto.setBackground(Color.ORANGE);
        scorriInBasso.setBackground(Color.ORANGE);
        
        avatarLabel1 = new JLabel(avatarArray[0]);
        
        avatarLabel1.setPreferredSize(new Dimension(avatarArray[0].getIconWidth() + 10, avatarArray[0].getIconHeight() + 10));
        avatarLabel1.setMaximumSize(new Dimension(avatarArray[0].getIconWidth() + 10, avatarArray[0].getIconHeight() + 10));
        avatarLabel1.setMinimumSize(new Dimension(avatarArray[0].getIconWidth() + 10, avatarArray[0].getIconHeight() + 10));
        
        scorriInAlto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (indiceIcone - 1 >= 0)
				{
					avatarLabel1.setIcon(avatarArray[--indiceIcone]);
					avatarLabel1.setPreferredSize(new Dimension(avatarArray[indiceIcone].getIconWidth() + 10, avatarArray[indiceIcone].getIconHeight() + 10));
					avatarLabel1.setMaximumSize(new Dimension(avatarArray[indiceIcone].getIconWidth() + 10, avatarArray[indiceIcone].getIconHeight() + 10));
					avatarLabel1.setMinimumSize(new Dimension(avatarArray[indiceIcone].getIconWidth() + 10, avatarArray[indiceIcone].getIconHeight() + 10));
				}
				pack();
			}
		});
        
        scorriInBasso.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (indiceIcone + 1 < avatarArray.length)
				{
					avatarLabel1.setIcon(avatarArray[++indiceIcone]);
					avatarLabel1.setPreferredSize(new Dimension(avatarArray[indiceIcone].getIconWidth() + 10, avatarArray[indiceIcone].getIconHeight() + 10));
					avatarLabel1.setMaximumSize(new Dimension(avatarArray[indiceIcone].getIconWidth() + 10, avatarArray[indiceIcone].getIconHeight() + 10));
					avatarLabel1.setMinimumSize(new Dimension(avatarArray[indiceIcone].getIconWidth() + 10, avatarArray[indiceIcone].getIconHeight() + 10));
				}
				pack();
			}
		});
        
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.insets = new Insets(10, 0, 0, 0);
		panel.add(scorriInAlto, gbc);
        
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		panel.add(avatarLabel1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.insets = new Insets(0, 0, 10, 0);
		panel.add(scorriInBasso, gbc);
		
        JButton cancelButton = new JButton("Annulla");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
                owner.setupPerInserimentoDati();
            }
        });
        
        JButton goButton = new JButton("Avanti ");
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	owner.impostaCampoDiGioco();
            }
        });
        
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.insets = new Insets(0, 10, 10, 10);
		panel.add(goButton, gbc);
        
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.insets = new Insets(0, 0, 10, 10);
		panel.add(cancelButton, gbc);
		
		add(panel);
		pack();
	}
}
