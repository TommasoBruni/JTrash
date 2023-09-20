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

public class AvatarSelectionDialog extends JDialog
{
    private ImageIcon selectedAvatar;
    private ImageIcon[] avatarArray;
    private JLabel avatarLabel;
    private int iconsIndex;
    
    public AvatarSelectionDialog(Frame parent)
    {
    	this(parent, null);
    }
    
    public AvatarSelectionDialog(Frame parent, String defaultAvatarDescr) 
    {
        super(parent, "Selezione Avatar", true);
        avatarArray = new ImageIcon[] { new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_3.gif"), "Naruto gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_2.gif"), "Sasuke gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_6.gif"), "Ragazzina fiore gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_8.gif"), "hanako kun gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_10.gif"), "Rengoku gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_12.gif"), "Anya gif"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_1.gif"), "Megumi gif")};
        if (defaultAvatarDescr != null)
        {
        	while (iconsIndex < avatarArray.length &&
        		   !defaultAvatarDescr.equals(avatarArray[iconsIndex].getDescription()))
        		iconsIndex++;
        }
        selectedAvatar = null;
        GridBagConstraints gbc = new GridBagConstraints();
        
        setResizable(false);
        JPanel panel = new JPanel(new GridBagLayout());
        JButton goRight = new JButton(new ImageIcon(System.getProperty("user.dir").concat("\\resources\\icona_scorrimento_a_destra.png")));
        JButton goLeft = new JButton(new ImageIcon(System.getProperty("user.dir").concat("\\resources\\icona_scorrimento_a_sinistra.png")));

        goRight.setBackground(Color.ORANGE);
        goLeft.setBackground(Color.ORANGE);
        
        //setMinimumSize(new Dimension(300, 300));
        avatarLabel = new JLabel(avatarArray[iconsIndex]);
        
		avatarLabel.setPreferredSize(new Dimension(avatarArray[iconsIndex].getIconWidth(), avatarArray[iconsIndex].getIconHeight()));
		avatarLabel.setMaximumSize(new Dimension(avatarArray[iconsIndex].getIconWidth(), avatarArray[iconsIndex].getIconHeight()));
		avatarLabel.setMinimumSize(new Dimension(avatarArray[iconsIndex].getIconWidth(), avatarArray[iconsIndex].getIconHeight()));

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
				/* Non interessante */
			}
			
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				/* Non interessante */
			}
			
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				/* Non interessante */
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
        
        goRight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (iconsIndex + 1 < avatarArray.length)
				{
					avatarLabel.setIcon(avatarArray[++iconsIndex]);
					avatarLabel.setPreferredSize(new Dimension(avatarArray[iconsIndex].getIconWidth(), avatarArray[iconsIndex].getIconHeight()));
					avatarLabel.setMaximumSize(new Dimension(avatarArray[iconsIndex].getIconWidth(), avatarArray[iconsIndex].getIconHeight()));
					avatarLabel.setMinimumSize(new Dimension(avatarArray[iconsIndex].getIconWidth(), avatarArray[iconsIndex].getIconHeight()));
				}
				pack();
			}
		});
        
        goRight.setPreferredSize(new Dimension(50, 50));
        goLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (iconsIndex - 1 >= 0)
				{
					avatarLabel.setIcon(avatarArray[--iconsIndex]);
					avatarLabel.setPreferredSize(new Dimension(avatarArray[iconsIndex].getIconWidth(), avatarArray[iconsIndex].getIconHeight()));
					avatarLabel.setMaximumSize(new Dimension(avatarArray[iconsIndex].getIconWidth(), avatarArray[iconsIndex].getIconHeight()));
					avatarLabel.setMinimumSize(new Dimension(avatarArray[iconsIndex].getIconWidth(), avatarArray[iconsIndex].getIconHeight()));
				}
				pack();
			}
		});
        
        goLeft.setPreferredSize(new Dimension(50, 50));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(goRight, gbc);
        
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(goLeft, gbc);
        
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
