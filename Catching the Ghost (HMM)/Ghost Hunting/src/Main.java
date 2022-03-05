import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Main extends StateBasedGame {
    public static final String gamename="Catch the Ghost!!";
    public static final int menu=0;
    public static final int hmm=1;
    public static final int particle=2;

    public Main(String gamename){
        super(gamename);
        this.addState(new Menu(menu));
        this.addState(new HMM(hmm));
        this.addState(new Particle(particle));

    }



    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.getState(menu).init(gameContainer,this);
        this.getState(hmm).init(gameContainer,this);
        this.getState(particle).init(gameContainer,this);
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
