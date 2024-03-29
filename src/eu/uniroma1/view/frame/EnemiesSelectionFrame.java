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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import eu.uniroma1.controller.FieldController;
import eu.uniroma1.controller.MainPlayerController;

/**
 * Enemies selection frame class 
 */
public class EnemiesSelectionFrame extends JInternalFrame
{
	private ImageIcon[] avatarArray;
	private JLabel[] avatarLabels;
	
	private class IndexManager
	{
		private int i;
	}
	
	/**
	 * Enemies selection frame class builder
	 * @param owner frame to make start the frame 
	 */
	public EnemiesSelectionFrame(GameFrame owner)
	{
		super("Select enemies");
		
		avatarArray = new ImageIcon[] { new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_7.gif"), "Obito"),
									    new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_5.gif"), "John"),
									    new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_9.gif"), "Akaza"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_11.gif"), "Deidara"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_14.gif"), "Timmy"),
					  					new ImageIcon(System.getProperty("user.dir").concat("\\resources\\AVATAR_IMAGE_4.gif"), "Chuck")};
        GridBagConstraints gbc = new GridBagConstraints();
        
        int i;
        JPanel panel = new JPanel(new GridBagLayout());
        JButton scrollUp;
        JButton scrollDown;
        avatarLabels = new JLabel[FieldController.getInstance().getNumberOfPlayingPlayers() - 1];
        
        for (i = 0; i < avatarLabels.length; i++)
        {
        	avatarLabels[i] = new JLabel(avatarArray[0]);
        	avatarLabels[i].setPreferredSize(new Dimension(avatarArray[0].getIconWidth() + 10, avatarArray[0].getIconHeight() + 10));
        	avatarLabels[i].setMaximumSize(new Dimension(avatarArray[0].getIconWidth() + 10, avatarArray[0].getIconHeight() + 10));
        	avatarLabels[i].setMinimumSize(new Dimension(avatarArray[0].getIconWidth() + 10, avatarArray[0].getIconHeight() + 10));
        }
        
        for (i = 0; i < FieldController.getInstance().getNumberOfPlayingPlayers() - 1; i++)
        {
        	JLabel currAvatarLabel = avatarLabels[i];
        	IndexManager currentIconsIndex = new IndexManager();
            scrollUp = new JButton(new ImageIcon(System.getProperty("user.dir").concat("\\resources\\icona_scorrimento_in_alto.png")));
            scrollDown = new JButton(new ImageIcon(System.getProperty("user.dir").concat("\\resources\\icona_scorrimento_in_basso.png")));
        	
            scrollUp.setBackground(Color.ORANGE);
            scrollDown.setBackground(Color.ORANGE);
            
            scrollUp.addActionListener(new ActionListener() {
            	private JLabel labelToOperate = currAvatarLabel;
            	private IndexManager iconsIndex = currentIconsIndex;
            	
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				if (iconsIndex.i - 1 >= 0)
    				{
    					labelToOperate.setIcon(avatarArray[--iconsIndex.i]);
    					labelToOperate.setPreferredSize(new Dimension(avatarArray[iconsIndex.i].getIconWidth() + 10, avatarArray[iconsIndex.i].getIconHeight() + 10));
    					labelToOperate.setMaximumSize(new Dimension(avatarArray[iconsIndex.i].getIconWidth() + 10, avatarArray[iconsIndex.i].getIconHeight() + 10));
    					labelToOperate.setMinimumSize(new Dimension(avatarArray[iconsIndex.i].getIconWidth() + 10, avatarArray[iconsIndex.i].getIconHeight() + 10));
    				}
    				pack();
    			}
    		});
            
            scrollDown.addActionListener(new ActionListener() {
            	private JLabel labelToOperate = currAvatarLabel;
            	private IndexManager iconsIndex = currentIconsIndex;
            	
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				if (iconsIndex.i + 1 < avatarArray.length)
    				{
    					labelToOperate.setIcon(avatarArray[++iconsIndex.i]);
    					labelToOperate.setPreferredSize(new Dimension(avatarArray[iconsIndex.i].getIconWidth() + 10, avatarArray[iconsIndex.i].getIconHeight() + 10));
    					labelToOperate.setMaximumSize(new Dimension(avatarArray[iconsIndex.i].getIconWidth() + 10, avatarArray[iconsIndex.i].getIconHeight() + 10));
    					labelToOperate.setMinimumSize(new Dimension(avatarArray[iconsIndex.i].getIconWidth() + 10, avatarArray[iconsIndex.i].getIconHeight() + 10));
    				}
    				pack();
    			}
    		});
            
    		gbc.gridx = i;
    		gbc.gridy = 0;
    		gbc.weightx = 0.1;
    		gbc.weighty = 0.1;
    		gbc.insets = new Insets(10, 0, 0, 0);
    		panel.add(scrollUp, gbc);
            
    		gbc.gridx = i;
    		gbc.gridy = 1;
    		gbc.weightx = 0.1;
    		gbc.weighty = 0.1;
    		panel.add(currAvatarLabel, gbc);
    		
    		gbc.gridx = i;
    		gbc.gridy = 2;
    		gbc.weightx = 0.1;
    		gbc.weighty = 0.1;
    		gbc.insets = new Insets(0, 0, 10, 0);
    		panel.add(scrollDown, gbc);
        }
		
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	if (MainPlayerController.getInstance().getPlayerData().isEmptyData())
            		owner.showInsertionDataFrame();
            	else
            		owner.showInsertionNPlayersDialog();
            }
        });
        
        JButton goButton = new JButton("Start game ");
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	FieldController.getInstance().setEnemiesIcon(Arrays.stream(avatarLabels)
            													   .map(label -> (ImageIcon)label.getIcon())
            													   .collect(Collectors.toList()));
            	
            	dispose();
            	owner.setGameField();
            }
        });
        
        cancelButton.setPreferredSize(goButton.getPreferredSize());
        
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.insets = new Insets(0, 10, 10, 10);
		panel.add(goButton, gbc);
        
		gbc.gridx = i == 1 ? i : i - 1;
		gbc.gridy = 3;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.insets = new Insets(0, 0, 10, 10);
		panel.add(cancelButton, gbc);
		
		add(panel);
		pack();
	}
}
