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

public class GameFrame extends MyFrame implements KeyListener {
    private final JPanel        contentpane;
    private JPanel              howtoplayPanel, pausePanel, itemPanel, heartPanel;
    private JLabel              coinLabel, clockLabel, background, gameResult;
    private HeartLabel[]        heart;
    private PlayerLabel         player;
    private ItemLabel[]         items;
    private MyButton            howToPlayButton, stopButton, settingButton, backToMenu, homeButton;
    private TimerBar            timerBar;
    private Timer               timer;
    private JTextField          coinText;
    private MyImageIcon         resultImg; 
    private int                 coin = 0;
    
    // Game state -----------------------------------------------------------
    private boolean             isRunning;
    private boolean             outOfTime = false;
    private boolean             gamePause = false;
    private boolean             pauseBySetting = false;
    
    // Timer ----------------------------------------------------------------
    int timerDuration  = 28000; 
    int updateInterval = 10; 
    int totalSteps     = timerDuration / updateInterval; 
    int[] currentStep  = {totalSteps};
    
    public static void main(String[] args) {
        new GameFrame();
    }
    
    public GameFrame() {
        super();
        
        gameFrame = this;
        
        contentpane = (JPanel) getContentPane();
	contentpane.setLayout( null ); 
        
        AddComponent();
        
        setting = new SettingDialog(this, themeSound);
         
        addKeyListener( this );
        
        setFocusable(true);
    }
    
    //--- Key Listener
    public void keyPressed( KeyEvent e ) { 
        if(isRunning && !gamePause) {
            if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) && !player.getIsJump() && !player.isHit()) {
                player.setIsJump();
            }
            if (e.getKeyCode() == KeyEvent.VK_1) {
                if(items[0].isAvailable() && player.getLife() < 3) buyItem(items[0]);
            }
            if (e.getKeyCode() == KeyEvent.VK_2 && player.getLife() < 3) {
                if(items[1].isAvailable()) buyItem(items[1]);
            }
            if (e.getKeyCode() == KeyEvent.VK_3) {
                if(items[2].isAvailable()) buyItem(items[2]);
            }
        }
    }
    public void keyReleased( KeyEvent e )	{ }
    public void keyTyped( KeyEvent e )          { }
    
    
    public void AddComponent() {
        //--- Background
        background = new JLabel();
        MyImageIcon backgroundImg  = new MyImageIcon(MyConstants.BACKGROUND).resize(framewidth*25, frameheight);
        background.setIcon(backgroundImg);
        background.setBounds(-framewidth*25+800, 0, framewidth*25, frameheight);
        
        
        //--- How to Play
        howtoplayPanel = new JPanel();
        howtoplayPanel.setOpaque(false);
        howtoplayPanel.setBounds(100, 60, 600, 400);
        howtoplayPanel.setLayout( null );
        howtoplayPanel.setVisible(true);
        
        MyImageIcon htpImg = new MyImageIcon(MyConstants.HOW_TO_PLAY).resize(600, 400);
        JLabel htp = new JLabel(htpImg);
        htp.setBounds(0, 0, 600, 400);
        
        MyButton close_htp = new MyButton(MyConstants.OK, 200, 320, 200, 50);
        close_htp.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                howtoplayPanel.setVisible(false);
                if(!isRunning) 
                {
                    gameStart();
                    coinText.setVisible(true);
                }
            }
        });
        
        howtoplayPanel.add(close_htp );
        howtoplayPanel.add(htp);
        
        
        //--- Button
        homeButton = new MyButton(MyConstants.HOME, 560, 25, MyConstants.SQUARE_BUTTON, MyConstants.SQUARE_BUTTON);
        homeButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                if(isRunning) 
                {
                    timer.stop();
                    isRunning = false;
                    menuFrame = new MenuFrame();
                    dispose();
                }
            }
        });
        
        howToPlayButton = new MyButton(MyConstants.INFO, 610, 25, MyConstants.SQUARE_BUTTON, MyConstants.SQUARE_BUTTON);
        howToPlayButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                if(isRunning) 
                {
                    if(!gamePause) 
                    {
                        timer.stop();
                        gamePause = true;
                        stopButton.changeIcon(MyConstants.RESUME);
                        pausePanel.setVisible(true);
                    }
                    howtoplayPanel.setVisible(true);
                }
            }
        });
        
        stopButton = new MyButton(MyConstants.PAUSE, 660, 25, MyConstants.SQUARE_BUTTON, MyConstants.SQUARE_BUTTON);
        stopButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                if(isRunning) 
                {
                    if(!gamePause) 
                    {
                        timer.stop();
                        gamePause = true;
                        stopButton.changeIcon(MyConstants.RESUME);
                        pausePanel.setVisible(true);
                    }
                    else 
                    {
                        timer.start();
                        gamePause = false;
                        pauseBySetting = false;
                        stopButton.changeIcon(MyConstants.PAUSE);
                        pausePanel.setVisible(false);
                    }
                }
                requestFocusInWindow();
            }
        });
        
        settingButton = new MyButton(MyConstants.SETTING, 710, 25, MyConstants.SQUARE_BUTTON, MyConstants.SQUARE_BUTTON);
        settingButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                if(isRunning) 
                {
                    if(!gamePause && !pauseBySetting) 
                    {
                        timer.stop();
                        gamePause = true;
                        pauseBySetting = true;
                        stopButton.changeIcon(MyConstants.RESUME);
                        pausePanel.setVisible(true);
                    }
                    setting.show();
                }
                requestFocusInWindow();
            }
        });
        
        
        //--- TimerBar
        timerBar = new TimerBar(0, 100, 75, 35, 400, 20);
        clockLabel = new JLabel(new MyImageIcon(MyConstants.CLOCK).resize(40, 40));
        clockLabel.setBounds(30, 25, 40, 40);
        
        
        //--- Item
        itemPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        itemPanel.setOpaque(false);
        items = new ItemLabel[3];        
        items[0] = new ItemLabel(MyConstants.ITEM1, MyConstants.ITEM_WEIDTH, MyConstants.ITEM_HEIGHT, MyConstants.ITEM_COST[0]);
        items[1] = new ItemLabel(MyConstants.ITEM2, MyConstants.ITEM_WEIDTH, MyConstants.ITEM_HEIGHT, MyConstants.ITEM_COST[1]);
        items[2] = new ItemLabel(MyConstants.ITEM3, MyConstants.ITEM_WEIDTH, MyConstants.ITEM_HEIGHT, MyConstants.ITEM_COST[2]);
        for(int i = 0; i < 3; i++) {
            itemPanel.add(items[i]);
        }
        itemPanel.setBounds(540, 75, MyConstants.ITEM_WEIDTH*3 + 20, MyConstants.ITEM_HEIGHT);
        
        //--- Heart
        heartPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        heartPanel.setOpaque(false);
        heart = new HeartLabel[3];
        for(int i = 0; i < 3; i++) {
            heart[i] = new HeartLabel(MyConstants.HEART_WEIDTH, MyConstants.HEART_HEIGHT);
            heartPanel.add(heart[i]);
        }
        heartPanel.setBounds(70, 70, MyConstants.HEART_WEIDTH*3 + 20, MyConstants.HEART_HEIGHT);
        
        
        //--- Player
        player = new PlayerLabel(currentSkin, 600, 340, MyConstants.PLAYER_WEIDTH, MyConstants.PLAYER_HEIGHT);
        player.setHeart(heart);
        
        
        //--- Coin
        coinLabel = new JLabel(new MyImageIcon(MyConstants.COIN).resize(30, 30));
        coinLabel.setBounds(70, 105, 40, 40);
        Font font = new Font("SansSerif", Font.BOLD, 20);
        coinText = new JTextField(Integer.toString(coin));		
	coinText.setEditable(false);
        coinText.setBounds(110, 110, 70, 30);
        coinText.setFont(font);
        coinText.setVisible(false);
        
        
        //--- Pause Panel
        pausePanel = new JPanel();
        pausePanel.setLayout( new BorderLayout() );
        pausePanel.setBackground( new Color(0,0,0,125) );
        JLabel text = new JLabel("Game Paused");
        text.setFont(new Font("SansSerif", Font.BOLD, 20));
        text.setHorizontalAlignment(SwingConstants.CENTER); 
        text.setVerticalAlignment(SwingConstants.CENTER);   
        pausePanel.add(text, BorderLayout.CENTER);
        pausePanel.setBounds(0, 0, framewidth, frameheight);
        pausePanel.setVisible(false);
        
        contentpane.add(howtoplayPanel, 0);
        contentpane.add(homeButton);
        contentpane.add(howToPlayButton);
        contentpane.add(stopButton);
        contentpane.add(settingButton);
        contentpane.add(timerBar);
        contentpane.add(itemPanel);
        contentpane.add(heartPanel);
        contentpane.add(coinText);
        contentpane.add(coinLabel);
        contentpane.add(clockLabel);
        contentpane.add(pausePanel);
        contentpane.add(player);
        contentpane.add(background);
        
        repaint();
        validate();
    }
    
    public void gameStart() {
        int elapsedTime[] = {0};
        
        isRunning = true;
        setPlayerThread();
        backgroundMove();
        
        timer = new Timer(updateInterval, e -> {
            currentStep[0]--;
            
            elapsedTime[0] += updateInterval;
            if (elapsedTime[0] % 1000 == 0 && !gamePause && !player.isHit() ) 
            {
                if(background.getX() < -framewidth) setItemThread();
            }
            
            timerBar.update(currentStep[0], totalSteps);
            
            if (currentStep[0] <= 0) 
            {
                outOfTime = true;
            }
        });
        
        timer.start();
    }
    synchronized public void gameEnd(String result) {
        if(isRunning) {
            timer.stop();
            isRunning = false;

            backToMenu = new MyButton(MyConstants.BACK_TO_MENU, 300, 300, 200, 50);
            backToMenu.addActionListener( new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    menuFrame = new MenuFrame();
                    dispose();
                }
            });

            if(result.equals("lose")) resultImg = new MyImageIcon(MyConstants.LOSS).resize(framewidth - 10, frameheight - 20);
            else                      resultImg = new MyImageIcon(MyConstants.WIN).resize(framewidth - 10, frameheight - 20);

            gameResult = new JLabel();
            gameResult.setIcon(resultImg);
            gameResult.setBounds(0, 0, framewidth - 10, frameheight - 20);
            contentpane.add(gameResult, 0);
            contentpane.add(backToMenu, 0);

            repaint();
        }
    }
    
    //--- Set Thread   (1) item  (2) player  (3) background
    public void setItemThread() {
        Thread itemThread = new Thread() {
            
            public void run()
            {
                ObjectLabel object = new ObjectLabel();
                contentpane.add(object, 1);
                repaint();
                
                while(isRunning) 
                {
                    if(!gamePause && !player.isHit()) 
                    {
                        object.updateLocation();
                        
                        if(object.getBounds().intersects(player.getBounds()) && !object.isHit()) 
                        {
                            if(object.getType() == 0) 
                            {
                                object.coin();
                                updateCoin();
                            }
                            else 
                            {
                                object.setIsHit();
                                player.setIsHit();
                                player.minusLife();
                                
                                if(player.getLife() == 0) 
                                {
                                    gameEnd("lose");
                                }
                                else 
                                {
                                    try {
                                        Thread.sleep(300);
                                        player.setIsHit();
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                        break;
                                    } 
                                }
                            }
                        }
                    }
                    else 
                    { 
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                    
                    if(object.getX() > framewidth) break;
                    validate();
                } 
            }
        };
        
        itemThread.start();
    }
    
    public void setPlayerThread()
    {
        Thread playerThread = new Thread() {
            
            public void run()
            {
                while(isRunning) 
                {
                    player.setRunning(!gamePause);
                    if(player.getIsJump() && !player.isHit()) 
                    {
                        player.jump();
                    }
                    else 
                    { 
                        try {
                            Thread.sleep(10);
                        } 
                        catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                    validate();
                }
                player.setRunning(false);
            }
        };
        
        playerThread.start();
    }
    
    public void backgroundMove() {
        Thread bgThread = new Thread() {
            
            public void run()
            {                
                while(isRunning) 
                {
                    if(background.getX() != 0) 
                    {
                        if(outOfTime) gameEnd("lose");
                        
                        if(!gamePause && !player.isHit()) 
                        {
                            int curX = background.getX();
                            background.setLocation(curX + 10, 0);
                            try {
                                Thread.sleep(15);
                            } 
                            catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                break;
                            }
                        }
                        else 
                        {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                break;
                            }
                        }
                    }
                    else 
                    {
                        gameEnd("win");
                    }
                    
                    validate();
                }
            }
        };
        
        bgThread.start();
    }
    
    // Manage coin & item
    public void updateCoin()
    {
        coin++;
        coinText.setText( Integer.toString(coin) );
        
        for(int i = 0; i < 3; i++) {
            if(coin >= items[i].getCost() && !items[i].isAvailable()) {
                items[i].updateAvailable();
            }
        }
    } 
    public void buyItem(ItemLabel item) {
        int cost = item.getCost();
        if(coin >= cost) coin -= cost;
        coinText.setText( Integer.toString(coin) );

        if(item == items[0]) {
            player.plusLife(1);
        }
        if(item == items[1]) {
            player.plusLife(2);
        }
        if(item == items[2]) {
            currentStep[0] += 500;
            if (currentStep[0] > totalSteps) currentStep[0] = totalSteps;
        }

        for(int i = 0; i < 3; i++) {
            if(coin < items[i].getCost() && items[i].isAvailable()) {
                items[i].updateAvailable();
            }
        }
    }    
}

