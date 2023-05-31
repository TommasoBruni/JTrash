package eu.uniroma1.view;

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

import javax.swing.JDialog;
import javax.swing.border.Border;
import javax.swing.*;

public class DialogSelezioneAvatar extends JDialog
{
    private ImageIcon selectedAvatar;
    private ImageIcon[] avatarArray;
    private JButton avatarButton;
    private int indiceIcone;
    
    public DialogSelezioneAvatar(Frame parent)
    {
    	this(parent, null);
    }
    
    public DialogSelezioneAvatar(Frame parent, String defaultAvatarDescr) 
    {
        super(parent, "Selezione Avatar", true);
        avatarArray = new ImageIcon[] { new ImageIcon(System.getProperty("user.dir").concat("\\resources\\avatar_numero1.png"), "avatar Naruto"),
					  new ImageIcon(System.getProperty("user.dir").concat("\\resources\\avatar_numero2.png"), "avatar Sasuke")};
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
        
        setBackground(Color.WHITE);
        //setMinimumSize(new Dimension(300, 300));
        avatarButton = new JButton(avatarArray[indiceIcone]);
		avatarButton.setPreferredSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
		avatarButton.setMaximumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
		avatarButton.setMinimumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));

        avatarButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				selectedAvatar = avatarArray[indiceIcone];
				dispose();
			}
		});
        //avatarLabel1.addMouseListener(new AvatarSelectionListener(System.getProperty("user.dir").concat("\\resources\\avatar_numero1.png")));
        
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
        panel.add(avatarButton, gbc);

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
					avatarButton.setIcon(avatarArray[++indiceIcone]);
					avatarButton.setPreferredSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
					avatarButton.setMaximumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
					avatarButton.setMinimumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
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
					avatarButton.setIcon(avatarArray[--indiceIcone]);
					avatarButton.setPreferredSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
					avatarButton.setMaximumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
					avatarButton.setMinimumSize(new Dimension(avatarArray[indiceIcone].getIconWidth(), avatarArray[indiceIcone].getIconHeight()));
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
        //setVisible(true);
    }

    public ImageIcon getSelectedAvatar() {
        return selectedAvatar;
    }
}
