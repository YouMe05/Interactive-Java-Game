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

public class SettingDialog extends JDialog {    
    private MySoundEffect       themeSound, testEffect;
    private static String       currentMusic = "music1";
    private static int          currentMusicVolumn = 50;
    private static int          currentEfxVolumn = 50;
    
    private JPanel              contentpane;  
    private JPanel              centerPanel;  
    
    public SettingDialog(MyFrame frame, MySoundEffect themeSound) {
        super(frame);
        setTitle("Setting");
	setModal(true);
        setLocationRelativeTo(frame);
	setSize(500, 300 );
	setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        
        this.themeSound = themeSound;
        
        testEffect = new MySoundEffect(MyConstants.EFX[0]);
        testEffect.setMusicVolume(50);
        
        AddComponent();
        
        setVisible(false);
    }
    
    public void AddComponent() {
        contentpane = new JPanel();
        contentpane.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Setting", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentpane.add(titleLabel, BorderLayout.NORTH);
        
        //--- Create a Panel for the Center Content
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 0, 30));

        //--- Music Options Dropdown
        JLabel musicLabel = new JLabel("Music Options:");
        String[] musicOptions = {"music1", "music2", "music3", "music4","music5"};
        JComboBox musicDropdown = new JComboBox(musicOptions);
        musicDropdown.setSelectedItem(currentMusic);
        
        JPanel musicPanel = new JPanel();
        musicPanel.setLayout(new BorderLayout(20, 0));
        musicPanel.add(musicLabel, BorderLayout.WEST);
        musicPanel.add(musicDropdown, BorderLayout.CENTER);

        //--- Music Volume Slider
        JLabel musicVolumeLabel = new JLabel("Music Volume:");
        JSlider musicVolumeSlider = new JSlider(0, 100);
        musicVolumeSlider.setMajorTickSpacing(20);
        musicVolumeSlider.setPaintTicks(true);
        musicVolumeSlider.setPaintLabels(true);
        musicVolumeSlider.setValue(currentMusicVolumn);
        
        JPanel musicVolumePanel = new JPanel(new BorderLayout(20, 0));
        musicVolumePanel.add(musicVolumeLabel, BorderLayout.WEST);
        musicVolumePanel.add(musicVolumeSlider, BorderLayout.CENTER);
        
        //--- EFX Volume Slider
        JLabel efxVolumeLabel = new JLabel("EFX Volume   :");
        JSlider efxVolumeSlider = new JSlider(0, 100);
        efxVolumeSlider.setMajorTickSpacing(20);
        efxVolumeSlider.setPaintTicks(true);
        efxVolumeSlider.setPaintLabels(true);
        efxVolumeSlider.setValue(currentEfxVolumn);
        
        JPanel efxVolumePanel = new JPanel(new BorderLayout(20, 0));
        efxVolumePanel.add(efxVolumeLabel, BorderLayout.WEST);
        efxVolumePanel.add(efxVolumeSlider, BorderLayout.CENTER);

        //--- OK Button
        JButton homeButton = new JButton("OK");
        homeButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                setVisible(false);
                requestFocusInWindow();
            }
        });

        //--- Add Components to the Center Panel
        centerPanel.add(musicPanel);
        centerPanel.add(musicVolumePanel);
        centerPanel.add(efxVolumePanel);

        //--- Add Panels to the Frame
        contentpane.add(centerPanel, BorderLayout.CENTER);
        contentpane.add(homeButton, BorderLayout.SOUTH);
 
        add(contentpane);
        
        //--- Play the selected music
        musicDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMusic = (String) musicDropdown.getSelectedItem();
                switch (selectedMusic) {
                    case "music1":
                        themeSound.playMusic(MyConstants.MUSIC[0]);
                        break;
                    case "music2":
                        themeSound.playMusic(MyConstants.MUSIC[1]);
                        break;
                    case "music3":
                        themeSound.playMusic(MyConstants.MUSIC[2]);
                        break;
                    case "music4":
                        themeSound.playMusic(MyConstants.MUSIC[3]);
                        break;
                    case "music5":
                        themeSound.playMusic(MyConstants.MUSIC[4]);
                        break;
                }
                currentMusic = selectedMusic;
            }
        });
        
        //--- Add ChangeListener to Music Volume Slider
        musicVolumeSlider.addChangeListener(e -> {
            int sliderValue = musicVolumeSlider.getValue();
            currentMusicVolumn = sliderValue;
            themeSound.setMusicVolume(currentMusicVolumn);
        });
        
        //--- Add ChangeListener to EFX Volume Slider
        efxVolumeSlider.addChangeListener(e -> {
            int sliderValue = efxVolumeSlider.getValue();
            currentEfxVolumn = sliderValue;
            testEffect.setEfxVolume(currentEfxVolumn);
            testEffect.playOnce();
        });
        
            
    }
}
