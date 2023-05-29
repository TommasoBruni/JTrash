package eu.uniroma1.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.*;

public class DialogSelezioneAvatar extends JDialog
{
    private String selectedAvatar;
    private ImageIcon[] avatarArray;
    private JLabel avatarLabel1;
    private int indiceIcone;

    public DialogSelezioneAvatar(Frame parent) 
    {
        super(parent, "Selezione Avatar", true);
        avatarArray = new ImageIcon[] { new ImageIcon(System.getProperty("user.dir").concat("\\resources\\avatar_numero1.png")),
        								new ImageIcon(System.getProperty("user.dir").concat("\\resources\\avatar_numero2.png"))};
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
        avatarLabel1 = new JLabel(avatarArray[indiceIcone]);
        avatarLabel1.addMouseListener(new AvatarSelectionListener(System.getProperty("user.dir").concat("\\resources\\avatar_numero1.png")));
        
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
        panel.add(avatarLabel1, gbc);

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
					avatarLabel1.setIcon(avatarArray[++indiceIcone]);
				pack();
			}
		});
        
        scorriADestra.setPreferredSize(new Dimension(50, 50));
        scorriASinistra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (indiceIcone - 1 >= 0)
					avatarLabel1.setIcon(avatarArray[--indiceIcone]);
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
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.ipadx = 50;
		gbc.ipady = 20;
        panel.add(cancelButton, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public String getSelectedAvatar() {
        return selectedAvatar;
    }

    private class AvatarSelectionListener extends MouseAdapter {
        private String avatarPath;

        public AvatarSelectionListener(String avatarPath) {
            this.avatarPath = avatarPath;
        }

        public void mouseClicked(MouseEvent e) {
            selectedAvatar = avatarPath;
            dispose();
        }
    }
}
