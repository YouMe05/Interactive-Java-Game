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
import javax.sound.sampled.*;

interface MyConstants {
    
    //----- Sizes and locations
    static final int FRAMEWIDTH          = 800;
    static final int FRAMEHEIGHT         = 565;
    static final int SQUARE_BUTTON       = 40;
    static final int ITEM_HEIGHT         = 80;
    static final int ITEM_WEIDTH         = 60;
    static final int HEART_WEIDTH        = 30;
    static final int HEART_HEIGHT        = 25;
    static final int PLAYER_WEIDTH       = 180;
    static final int PLAYER_HEIGHT       = 150;
    
    //----- FILE PATH
    static final String PATH_IMG         = "src/main/java/Project3_6613116/resources/img/";
    static final String PATH_SOUND       = "src/main/java/Project3_6613116/resources/sound/";
    
    //----- Background
    static final String MENU_BG          = PATH_IMG + "MENU_BG.jpg";
    static final String BACKGROUND       = PATH_IMG + "MAP.jpg";
    static final String BG_SKIN          = PATH_IMG + "BG_SKIN.jpg";
    
    //----- Button
    static final String[] PLAY_BUTTON    = {PATH_IMG + "PLAY.png", PATH_IMG + "PLAY_hover.png"};
    static final String[] OPT_BUTTON     = {PATH_IMG + "OPT.png", PATH_IMG + "OPT_hover.png"};
    static final String[] QUIT_BUTTON    = {PATH_IMG + "QUIT.png", PATH_IMG + "QUIT_hover.png"};
    static final String[] OK             = {PATH_IMG + "OK.png", PATH_IMG + "OK_hover.png"};
    static final String[] SETTING        = {PATH_IMG + "SETTING.png", PATH_IMG + "SETTING_hover.png"};
    static final String[] INFO           = {PATH_IMG + "INFO.png", PATH_IMG + "INFO_hover.png"};
    static final String[] PAUSE          = {PATH_IMG + "PAUSE.png", PATH_IMG + "PAUSE_hover.png"};
    static final String[] RESUME         = {PATH_IMG + "RESUME.png", PATH_IMG + "RESUME_hover.png"};
    static final String[] BACK_TO_MENU   = {PATH_IMG + "MENU.png", PATH_IMG + "MENU_hover.png"};
    static final String[] HOME           = {PATH_IMG + "HOME.png", PATH_IMG + "HOME_hover.png"};
    static final String[] SKIN_BUTTON    = {PATH_IMG + "BUTTON_BUI_1.png", PATH_IMG + "BUTTON_BUI_2.png", PATH_IMG + "BUTTON_BUI_3.png", PATH_IMG + "BUTTON_BUI_4.png", PATH_IMG + "BUTTON_BUI_5.png"};
    
    //----- Game Components
    static final String[] SKIN           = {PATH_IMG + "BUI_1.png", PATH_IMG + "BUI_2.png", PATH_IMG + "BUI_3.png", PATH_IMG + "BUI_4.png", PATH_IMG + "BUI_5.png"};
    static final String[] SKIN_2         = {PATH_IMG + "BUI_1_2.png", PATH_IMG + "BUI_2_2.png", PATH_IMG + "BUI_3_2.png", PATH_IMG + "BUI_4_2.png", PATH_IMG + "BUI_5_2.png"};
    
    static final int[] ITEM_COST         = {3, 5, 10};
    static final String[] ITEM1          = {PATH_IMG + "ITEM1_gray.png", PATH_IMG + "ITEM1.png"};
    static final String[] ITEM2          = {PATH_IMG + "ITEM2_gray.png", PATH_IMG + "ITEM2.png"};
    static final String[] ITEM3          = {PATH_IMG + "ITEM3_gray.png", PATH_IMG + "ITEM3.png"};
    
    static final String[] HEART          = {PATH_IMG + "HEART_1.png", PATH_IMG + "HEART_0.png"};
    
    static final String COIN             = PATH_IMG + "COIN.png";
    static final String OBSTACLE         = PATH_IMG + "CONE.png";
    
    static final String CLOCK            = PATH_IMG + "CLOCK.png";
    
    static final String HOW_TO_PLAY      = PATH_IMG + "HOW_TO_PLAY.png";
    static final String LOSS             = PATH_IMG + "END_LOSS.png";
    static final String WIN              = PATH_IMG + "END_WIN.png";
    
    //---- SOUND
    static final String[] MUSIC          = {PATH_SOUND + "music1.wav", PATH_SOUND + "music2.wav", PATH_SOUND + "music3.wav", PATH_SOUND + "music4.wav", PATH_SOUND + "music5.wav"};
    static final String[] EFX            = {PATH_SOUND + "coin-recieved.wav", PATH_SOUND + "ouch.wav"};
}

class MyFrame extends JFrame {
    protected int framewidth   = MyConstants.FRAMEWIDTH;
    protected int frameheight  = MyConstants.FRAMEHEIGHT;
    
    protected static MySoundEffect       themeSound;
    protected static int                 currentSkin = 0;
    protected SettingDialog              setting;
   
    protected static MenuFrame           menuFrame;
    protected static GameFrame           gameFrame;
    protected static OptionFrame         optionFrame;
    
    public MyFrame() {
        setTitle("BUI BACK HOME!");
        setSize(framewidth, frameheight); 
        setLocationRelativeTo(null);
	setVisible(true);
        setResizable(false);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        
        if(themeSound == null) {
            themeSound = new MySoundEffect(MyConstants.MUSIC[0]);
            themeSound.setMusicVolume(50);
            themeSound.playLoop();
        }
    }
}

class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
	Image oldimg = this.getImage();
	Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        return new MyImageIcon(newimg);
    }
}

class MySoundEffect
{
    private Clip            clip;
    private FloatControl    gainControl;         
    private float           dB;
    private static float    efxVolumn;

    public MySoundEffect(String filename)
    {
	try {
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);            
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //--- Sound EFX
    public void playOnce() { 
        gainControl.setValue(efxVolumn); 
        clip.setMicrosecondPosition(0); 
        clip.start(); 
    }
    
    public void setEfxVolume(float gain)
    {
        efxVolumn = (float)(Math.log10( gain / 100.0 ) * 20.0);
        gainControl.setValue(efxVolumn);
    }
    
    //--- Music
    public void playLoop()             { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    
    public void setMusicVolume(float gain)
    {
        dB = (float)(Math.log10( gain / 100.0 ) * 20.0);
        gainControl.setValue(dB);
    }
    
    public void playMusic(String filename) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
        
        try {
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            
            gainControl.setValue(dB);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class MyButton extends JButton implements MouseListener {
    private MyImageIcon  buttonIcon, hoverIcon;
    private int width, height;
    
    public MyButton(String[] icon, int x, int y, int width, int height) {
        setContentAreaFilled(false);
        setBackground(null);
        setBorderPainted(false);
        setFocusPainted(false);
        
        this.width = width;
        this.height = height;
        
        buttonIcon = new MyImageIcon(icon[0]).resize(this.width, this.height);
        hoverIcon = new MyImageIcon(icon[1]).resize(this.width, this.height);
        
        setBounds(x, y, width, height);
        
        setIcon(buttonIcon);
        
        addMouseListener( this );
        
    }
    
    // For Pause-Resume Button
    public void changeIcon(String[] icon){
        buttonIcon = new MyImageIcon(icon[0]).resize(width, height);
        hoverIcon = new MyImageIcon(icon[1]).resize(width, height);
        
        setIcon(buttonIcon);
    }
    
    public void mousePressed( MouseEvent e )	{ }
    public void mouseReleased( MouseEvent e )	{ }
    public void mouseClicked( MouseEvent e )    { }
    
    @Override
    public void mouseEntered( MouseEvent e )	{ 
        setIcon(hoverIcon);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }	
    public void mouseExited( MouseEvent e )	{ 
        setIcon(buttonIcon);
    }
}

