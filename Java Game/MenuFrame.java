package Project3_6613116;

/*
    Jinjutar   Sookprasert     6613116
    Ruaengsiri Nantavit        6613122
    Pawanvaree Gonsup          6613255
    Patthinan  Sukutamatunti   6613262
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuFrame extends MyFrame {
    private final JPanel      contentpane;
    private JPanel            buttonPanel;
    private JLabel            background, player;
    
    private MyButton          playButton, optionsButton, quitButton;
    private MyButton          settingButton;
    
    private final int BUTTONWIDTH        = 256;
    private final int BUTTONHEIGHT       = 64;
    private final int BUIWIDTH           = 280;
    private final int BUIHEIGHT          = 230;
    
    public MenuFrame() {
        super();
        
        menuFrame = this;
        
        contentpane = (JPanel) getContentPane();
        contentpane.setLayout( null );
        
        AddComponent();
        
        setting = new SettingDialog(this, themeSound);
    }
    
    public void AddComponent() {
        MyImageIcon backgroundImg  = new MyImageIcon(MyConstants.MENU_BG).resize(framewidth - 10, frameheight - 20);
	background = new JLabel( backgroundImg );
	background.setIcon(backgroundImg);
        background.setLayout( null );
        background.setBounds(0, 0, framewidth - 10, frameheight - 20);
        
        
        Font font = new Font("SansSerif", Font.BOLD, 14);
        JTextField member = new JTextField("Member : Jinjutar 6613116, Ruaengsiri 6613122, Pawanvaree 6613255, Patthinan 6613262");		
	member.setEditable(false);
        member.setBounds(100, 430, 620, 30);
        member.setFont(font);
        member.setVisible(true);
        
        playButton = new MyButton(MyConstants.PLAY_BUTTON, 0, 0, BUTTONWIDTH, BUTTONHEIGHT);
        playButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                gameFrame = new GameFrame();
                dispose();
            }
        });
        
        
        optionsButton = new MyButton(MyConstants.OPT_BUTTON, 0, 0, BUTTONWIDTH, BUTTONHEIGHT);
        optionsButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                optionFrame = new OptionFrame();
                dispose();
            }
        });
        
        
        quitButton = new MyButton(MyConstants.QUIT_BUTTON, 0, 0, BUTTONWIDTH, BUTTONHEIGHT);
        quitButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                System.exit(0);
            }
        });
        
        buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10)); 
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(130, 165, 256, 250);
        buttonPanel.add(playButton);
        buttonPanel.add(optionsButton);
        buttonPanel.add(quitButton);
        
        
        settingButton = new MyButton(MyConstants.SETTING, 710, 25, MyConstants.SQUARE_BUTTON, MyConstants.SQUARE_BUTTON);
        settingButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                setting.show();
            }
        });
        
        
        
        player = new JLabel();
        MyImageIcon playerImg = new MyImageIcon(MyConstants.SKIN[currentSkin]).resize(280, 230);
        player.setIcon(playerImg);
        player.setBounds(440, 175, BUIWIDTH, BUIHEIGHT);
        
        contentpane.add(settingButton);
        contentpane.add(player);
        contentpane.add(buttonPanel);
        contentpane.add(member);
        contentpane.add(background);
        
        repaint();
        validate();
    }
    
    public static void main(String[] args) {
        new MenuFrame();
    }
}
