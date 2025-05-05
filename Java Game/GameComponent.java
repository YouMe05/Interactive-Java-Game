package Project3_6613116;

/*
    Jinjutar   Sookprasert     6613116
    Ruaengsiri Nantavit        6613122
    Pawanvaree Gonsup          6613255
    Patthinan  Sukutamatunti   6613262
*/

import java.awt.*;
import java.util.Random;
import javax.swing.*;

class ItemLabel extends JLabel { 
    private MyImageIcon     itemDefault, itemAvailable;
    
    private int             cost;
    private boolean         avaliable = false;
    
    public ItemLabel(String[] icon, int width, int height, int c) {
        itemDefault = new MyImageIcon(icon[0]).resize(width, height);
        itemAvailable = new MyImageIcon(icon[1]).resize(width, height);
        
        setIcon(itemDefault);
        
        cost = c;
    }
    
    public int      getCost()                { return cost; }
    public boolean  isAvailable()            { return avaliable; }
    
    public void updateAvailable() { 
        if(avaliable) {
            setIcon(itemDefault);
            avaliable = false;
        }
        else {
            setIcon(itemAvailable);
            avaliable = true;
        }
        repaint();
    }
}

class HeartLabel extends JLabel {
    private MyImageIcon     heart, noHeart;
    
    public HeartLabel(int width, int height) {
        heart = new MyImageIcon(MyConstants.HEART[0]).resize(width, height);
        noHeart = new MyImageIcon(MyConstants.HEART[1]).resize(width, height);
        
        setIcon(heart);
    }
    
    public void minusHeart() {
        setIcon(noHeart);
    }
    public void plusHeart() {
        setIcon(heart);
    }
}

class PlayerLabel extends JLabel {
    private MyImageIcon     icon_1, icon_2;
    private int             curX, curY;
    
    // Player Life
    private int             life = 3;
    private HeartLabel[]    heart;
    private boolean         isHit = false;
    
    // Jump
    private int             jumpHeight = 90;
    private boolean         isJumping = false;
    private boolean         goingUp = true;
    
    // Animation
    private boolean         isRunning = false;
    private Timer           runAnimationTimer;
    private int             runFrame = 0;
    
    public PlayerLabel(int index, int x, int y, int width, int height) {
        icon_1 = new MyImageIcon(MyConstants.SKIN[index]).resize(width, height);
        icon_2 = new MyImageIcon(MyConstants.SKIN_2[index]).resize(width, height);
        curX = x;
        curY = y;
        setIcon(icon_1);
        setBounds(curX, curY, width - 5, height - 10);
        
        runAnimationTimer = new Timer(200, e -> updateRunAnimation());
    }
    
    public boolean isHit()                            { return isHit; }
    public void setIsHit()                            { isHit = !isHit; }
    public void setHeart(HeartLabel[] heart)          { this.heart = heart; }
    
    //--- Life Method
    public int getLife()    { return life; }
    public void minusLife() { 
        heart[life - 1].minusHeart();
        life--;
    }
    public void plusLife(int value) {
        for(int i = 1; i <= value; i++) {
            if(life == 3) break;
            heart[life].plusHeart();
            life++;
        }
    }
    
    //--- Jump Method
    public boolean  getIsJump()                       { return isJumping; }
    public void     setIsJump()                       { isJumping = !isJumping; }
    public void jump() {
        setIcon(icon_1);
        if (goingUp) {
            curY -= 20;
            if(curY <= jumpHeight) goingUp = false;
        } else {
            curY += 10;
            if (curY >= 340) {
                curY = 340;
                isJumping = false; 
                goingUp = true;
            }
        }
        setLocation(curX, curY);
        try { Thread.sleep(18); } 
        catch (InterruptedException e) { e.printStackTrace(); }
    }
    
    //--- Animation
    public void setRunning(boolean running) {
        isRunning = running;
        if (isRunning) {
            runAnimationTimer.start();
        } else {
            runAnimationTimer.stop();
            setIcon(icon_1);
        }
    }
    public void updateRunAnimation() {
        if(!isJumping && !isHit) {
            if (runFrame == 0) setIcon(icon_1);
            else               setIcon(icon_2);
            runFrame = 1 - runFrame; //toggle
        }
        if(isHit) setIcon(icon_1);
    }
    
    
}

class ObjectLabel extends JLabel {
    private MyImageIcon     coinIcon, obstacleIcon;
    private int             curX = -10, curY = 385;
    private int             type; // 0 = coin, 1 = obstacle
    
    private boolean         isHit = false;
    private MySoundEffect   hitSound;
    
    public ObjectLabel() {
        coinIcon = new MyImageIcon(MyConstants.COIN).resize(50, 50);
        obstacleIcon = new MyImageIcon(MyConstants.OBSTACLE).resize(85, 100);
        
        Random rand = new Random();
        type = rand.nextInt(2);
        
        if(type == 0) {
            setIcon(coinIcon);
            setBounds(curX, curY, 50, 50);
        }
        else {
            setIcon(obstacleIcon);
            setBounds(curX, curY, 85, 100);
        }
        hitSound  = new MySoundEffect(MyConstants.EFX[type]);
    }
    
    public int getType()        { return type; }
    
    public void coin() {
        hitSound.playOnce();
        curY += 200;
        setLocation(curX, curY);
        repaint();
    }
    
    public void updateLocation() {
        if(curX <= 800) curX += 10;
        setLocation(curX, curY);
        repaint();
        
        try { Thread.sleep(15); } 
        catch (InterruptedException e) { e.printStackTrace(); }
    }
    
    public boolean isHit() {
        return isHit;
    }
    public void setIsHit() {
        hitSound.playOnce();
        isHit = true;
    }
    
}

class TimerBar extends JProgressBar {
    public TimerBar(int min, int max, int x, int y, int width, int height) {
        super(min, max);
        setValue(100);
        
        setStringPainted(false);
        setForeground(Color.red);
        setBorderPainted(false);
        setBounds(x, y, 400, 20);
    }
    
    public void update(int currentStep, int totalSteps) {
        int progress = (int) ((currentStep / (float) totalSteps) * 100);
        setValue(progress);
    }
}