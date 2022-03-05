import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

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
        graphics.setBackground(new Color(205,196,170));
        graphics.setColor(Color.black);
        graphics.fillRoundRect(100,200,620,100,50);
        graphics.fillRoundRect(80,400,670,100,50);
        graphics.setColor(Color.white);
        graphics.setFont(new TrueTypeFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 40), true));
        graphics.drawString("Catch with HMM Filtering",120,220);
        graphics.drawString("Catch with Particle Filtering",100,420);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input=gameContainer.getInput();

        int xpos=Mouse.getX();
        int ypos=Mouse.getY();
        if(input.isMouseButtonDown(0))
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (xpos >= 100 && xpos <= 720 && ypos >= gameContainer.getHeight() - 300 && ypos <= gameContainer.getHeight() - 200)
            {
              stateBasedGame.enterState(1);
            }
            else if (xpos >= 80 && xpos <= 750 && ypos >= gameContainer.getHeight() - 500 && ypos <= gameContainer.getHeight() - 400)
            {
                stateBasedGame.enterState(2);
            }
        }
    }

    @Override
    public int getID() {
        return 0;
    }
}
