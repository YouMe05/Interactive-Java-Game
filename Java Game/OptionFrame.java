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
import javax.swing.border.Border;

public class OptionFrame extends MyFrame {
    private final JPanel      contentpane;
    private JPanel            ButtonPanel, SkinPanel;
    private JLabel            skinPreview, skinBg;
    private MyImageIcon       skinIcon;
    private ButtonGroup       group;
    private MyButton          settingButton;
    
    private final int BUTTONWIDTH        = 152;
    private final int BUTTONHEIGHT       = 96;
    private final int BACKBUTTONWIDTH    = 256;
    private final int BACKBUTTONHEIGHT   = 64;
    private final int BUIWIDTH           = 263;
    private final int BUIHEIGHT          = 220;
    
    public static void main(String[] args) {
        new OptionFrame();
    }
    
    public OptionFrame() {
        super();
        
        optionFrame = this;
        
        contentpane = (JPanel) getContentPane();
        contentpane.setLayout( null );
        
        AddComponent();
        
        setting = new SettingDialog(this, themeSound);
    }
    
    public void AddComponent() {
        ButtonPanel = new JPanel();
        ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));
        ButtonPanel.setPreferredSize(new Dimension(200, MyConstants.FRAMEHEIGHT));
        Border greenBorder = BorderFactory.createLineBorder(Color.GREEN, 5);
        group = new ButtonGroup();
        for (int i = 0; i < MyConstants.SKIN.length; i++) {
            final int skinIndex = i;
            
            JRadioButton skinButton = createImageButton(MyConstants.SKIN_BUTTON[i], BUTTONWIDTH, BUTTONHEIGHT);
                skinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                skinButton.setBorder(greenBorder);
                skinButton.setContentAreaFilled(false);
                
            if( skinIndex == currentSkin ) {
                skinButton.setBorderPainted(true);
                skinButton.setSelected(true);
            }
            ButtonPanel.add(skinButton);
            
            group.add(skinButton);
            skinButton.addActionListener(e -> updateSkin(skinIndex));
            skinButton.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    skinButton.setBorderPainted(true);
                }
                else {
                    skinButton.setBorderPainted(false);
                }
            });
        }
        
        
        
        MyImageIcon background = new MyImageIcon(MyConstants.BG_SKIN).resize(600, MyConstants.FRAMEHEIGHT);
        skinBg = new JLabel(background);
        skinBg.setBounds(0, 0, 600, MyConstants.FRAMEHEIGHT);
        SkinPanel = new JPanel();
        SkinPanel.setLayout(null);
       
        skinIcon = new MyImageIcon(MyConstants.SKIN[currentSkin]).resize(BUIWIDTH, BUIHEIGHT);
        skinPreview = new JLabel(skinIcon);
        skinPreview.setBounds(300-(BUIWIDTH/2), 150, BUIWIDTH, BUIHEIGHT);
        
        
        MyButton backButton = new MyButton(MyConstants.BACK_TO_MENU, 300-(BACKBUTTONWIDTH/2), 420, BACKBUTTONWIDTH, BACKBUTTONHEIGHT);
        backButton.addActionListener(e -> {
            menuFrame = new MenuFrame();
            dispose();
        });
        
        
        settingButton = new MyButton(MyConstants.SETTING, 510, 25, MyConstants.SQUARE_BUTTON, MyConstants.SQUARE_BUTTON);
        settingButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                setting.show();
            }
        });
        
        SkinPanel.add(settingButton, 0);
        SkinPanel.add(skinPreview);
        SkinPanel.add(backButton);
        SkinPanel.add(skinBg);
        
        
        JPanel control = new JPanel();
        control.setLayout( new BorderLayout() );
        control.setBounds(0, 0, MyConstants.FRAMEWIDTH, MyConstants.FRAMEHEIGHT);
        
        control.add(ButtonPanel, BorderLayout.WEST);
        control.add(SkinPanel, BorderLayout.CENTER);
        
        
        contentpane.add(control);
        
        
        repaint();
        validate();
        
    }
    
    private JRadioButton createImageButton(String imagePath, int width, int height) {
        MyImageIcon buttonIcon = new MyImageIcon(imagePath);
        buttonIcon.resize(width, height);
        return new JRadioButton (buttonIcon);
    }
    
    public void updateSkin(int index) {
        currentSkin = index;
        skinIcon = new MyImageIcon(MyConstants.SKIN[currentSkin]).resize(BUIWIDTH, BUIHEIGHT);
        skinPreview.setIcon(skinIcon);
    }
}
