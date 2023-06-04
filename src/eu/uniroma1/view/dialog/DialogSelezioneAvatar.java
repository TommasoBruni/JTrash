package eu.uniroma1.view.dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.border.Border;
import javax.swing.*;

public class DialogSelezioneAvatar extends JDialog
{
    private ImageIcon selectedAvatar;
    private ImageIcon[] avatarArray;
    private JLabel avatarLabel;
    private int indiceIcone;
    
    public DialogSelezioneAvatar(Frame parent)
    {
    	this(parent, null);
    }
    
    public DialogSelezioneAvatar(Frame parent, String defaultAvatarDescr) 
    {
        super(parent, "Selezione Avatar", true);
        avatarArray = new ImageIcon[] { new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_3.gif"), "Naruto gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_2.gif"), "Sasuke gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_1.gif"), "Itachi gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_4.gif"), "Tanjiro gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_5.gif"), "Ragno gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_6.gif"), "Ragazzina fiore gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_7.gif"), "Obito gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_8.gif"), "hanako kun gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_9.gif"), "Akaza gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_10.gif"), "Rengoku gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_11.gif"), "Deidara gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_12.gif"), "Anya gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_13.gif"), "Pikachu gif")};
        if (defaultAvatarDescr != null)
        {
        	while (indiceIcone < avatarArray.length &&
        		   !defaultAvatarDescr.equals(avatarArray[indiceIcone].getDescription()))
        		indiceIcone++;
        }
        selectedAvatar = null;
        GridBagConstraints gbc = new GridBagConstraints();
        
        setResizable(false);
        JPanel panel = new JPanel(new GridBagLayout());
        JButton scorriADestra = new JButton(new ImageIcon(System.getProperty("user.dir").concat("\\resources\\icona_scorrimento_a_destra.png")));
        JButton scorriASinistra = new JButton(new ImageIcon(System.getProperty("user.dir").concat("\\resources\\icona_scorrimento_a_sinistra.png")));

        scorriADestra.setBackground(Color.ORANGE);
        scorriASinistra.setBackground(Color.ORANGE);
        
        //setMinimumSize(new Dimension(300, 300));
        avatarLabel = new JLabel(avatarArray[indiceIcone]);
        
		avatarLabel.setPreferredSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
		avatarLabel.setMaximumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
		avatarLabel.setMinimumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));

		/*
        avatarButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				selectedAvatar = avatarArray[indiceIcone];
				dispose();
			}
		});
		*/
        avatarLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				/* Non interessante */
			}
			
			@Override
			public void mousePressed(MouseEvent e) 
			{
				selectedAvatar = (ImageIcon)avatarLabel.getIcon();
				dispose();
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
			}
			
			@Override
			public void mouseEntered(MouseEvent e) 
			{
			}
			
			@Override
			public void mouseClicked(MouseEvent e) 
			{
			}
		});
        
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
        panel.add(avatarLabel, gbc);

        JButton cancelButton = new JButton("Annulla");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedAvatar = null;
                dispose();
            }
        });
        
        //cancelButton.setPreferredSize(new Dimension(100, 100));
        
        scorriADestra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (indiceIcone + 1 < avatarArray.length)
				{
					avatarLabel.setIcon(avatarArray[++indiceIcone]);
					avatarLabel.setPreferredSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
					avatarLabel.setMaximumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
					avatarLabel.setMinimumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
				}
				pack();
			}
		});
        
        scorriADestra.setPreferredSize(new Dimension(50, 50));
        scorriASinistra.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (indiceIcone - 1 >= 0)
				{
					avatarLabel.setIcon(avatarArray[--indiceIcone]);
					avatarLabel.setPreferredSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
					avatarLabel.setMaximumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
					avatarLabel.setMinimumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
				}
				pack();
			}
		});
        
        scorriASinistra.setPreferredSize(new Dimension(50, 50));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(scorriADestra, gbc);
        
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(scorriASinistra, gbc);
        
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		//gbc.insets = new Insets(10, 0, 0, 0);
		gbc.ipadx = 50;
		gbc.ipady = 10;
        panel.add(cancelButton, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    public ImageIcon getSelectedAvatar() {
        return selectedAvatar;
    }
}
