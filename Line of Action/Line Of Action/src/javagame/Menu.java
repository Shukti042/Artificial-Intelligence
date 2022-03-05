package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;
public class Menu extends BasicGameState {
    //public String mouse="No input yet";
    Font font;
    public Menu(int state) {
    }

    public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
    {
        gc.setShowFPS(false);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //graphics.drawString(mouse,50,50);
        Color back=new Color(244, 164, 96);
        graphics.setColor(back);
        graphics.fillRoundRect(150,100,500,100,50);
        graphics.fillRoundRect(150,250,500,100,50);
        graphics.fillRoundRect(150,400,500,100,50);
        graphics.fillRoundRect(150,550,500,100,50);
        graphics.setColor(Color.black);
        graphics.setFont(new TrueTypeFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 50), true));
        graphics.drawString("PLAY 1V1 8X8",210,120);
        graphics.drawString("Play With Ai 8X8",170,270);
        graphics.drawString("PLAY 1V1 6X6",210,420);
        graphics.drawString("Play With Ai 6X6",170,570);
//        graphics.drawString("Play 1 V 1",50,50);
//        graphics.drawOval(50,50,50,50);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input=gameContainer.getInput();

        int xpos=Mouse.getX();
        int ypos=Mouse.getY();
        if(xpos>=150&&xpos<=650&&ypos>=gameContainer.getHeight()-200&&ypos<=gameContainer.getHeight()-100)
        {
            if(input.isMouseButtonDown(0))
            {
                stateBasedGame.enterState(1);
            }
        }
        if(xpos>=150&&xpos<=650&&ypos>=gameContainer.getHeight()-350&&ypos<=gameContainer.getHeight()-250)
        {
            if(input.isMouseButtonDown(0))
            {
                stateBasedGame.enterState(2);
            }
        }
        if(xpos>=150&&xpos<=650&&ypos>=gameContainer.getHeight()-500&&ypos<=gameContainer.getHeight()-400)
        {
            if(input.isMouseButtonDown(0))
            {
                stateBasedGame.enterState(3);
            }
        }
        if(xpos>=150&&xpos<=650&&ypos>=gameContainer.getHeight()-650&&ypos<=gameContainer.getHeight()-550)
        {
            if(input.isMouseButtonDown(0))
            {
                stateBasedGame.enterState(4);
            }
        }
       // mouse="Mouse Position x: "+xpos+" y: "+ypos;
    }

    @Override
    public int getID() {
        return 0;
    }
}
