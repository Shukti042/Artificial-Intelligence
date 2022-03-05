package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Main extends StateBasedGame {
    public static final String gamename="Lines Of Action!";
    public static final int menu=0;
    public static final int play=1;
    public static final int playWithAi=2;
    public static final int play6=3;
    public static final int playWithAi6=4;

    public Main(String gamename){
        super(gamename);
        this.addState(new Menu(menu));
        this.addState(new Play(play));
        this.addState(new PlayWithAi(playWithAi));
        this.addState(new Play6(play6));
        this.addState(new PlayWithAi6(playWithAi6));

    }



    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.getState(menu).init(gameContainer,this);
        this.getState(play).init(gameContainer,this);
        this.getState(playWithAi).init(gameContainer,this);
        this.getState(play6).init(gameContainer,this);
        this.getState(playWithAi6).init(gameContainer,this);
        this.enterState(menu);
    }

    public static void main(String[] args) {
        AppGameContainer appgc;
        try {
            appgc=new AppGameContainer(new Main(gamename));
            appgc.setDisplayMode(800,800,false);
            appgc.start();
        }catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
}
