import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Particle extends BasicGameState {
    Random random ;
    double randn;
    boolean catchflag=false;
    boolean gameover=false;
    boolean won;
    String notification="";
    Font font;
    int boardsize=9;
    int cellsize=60;
    Color light=new Color(205,196,170);
    Color dark=new Color(78,53,36);
    Cell[][] board=new Cell[boardsize][boardsize];
    int ghostIndexi=5,ghostIndexj=6;
    Image winimage,looseimage;
    Color colors[]={Color.red,Color.orange,Color.green};
    // double[][] colorprob={{0.9,0.09,0.01},{0.1,0.8,0.1},{0.01,0.09,0.9}};
    double[][] colorprob={{1,0,0},{0,1,0},{0,0,1}};
    public int checkColor(int manhattandistance)
    {
        if (manhattandistance<5)
            return 0;
        else if(manhattandistance<11)
            return 1;
        else
            return 2;
    }
    public Particle(int state) {
    }

    public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
    {
        random=new Random();
        gc.setShowFPS(false);
        winimage=new Image("res/win.png");
        looseimage=new Image("res/loose.png");
        for (int i=0;i<boardsize;i++)
        {
            for (int j=0;j<boardsize;j++)
            {
                board[i][j]=new Cell();
                board[i][j].partices=10;
                board[i][j].h=cellsize;
                board[i][j].w=cellsize;
                board[i][j].x=130+cellsize*i;
                board[i][j].y=100+cellsize*j;
                board[i][j].indexi=i;
                board[i][j].indexj=j;
                board[i][j].color=Color.white;
                board[i][j].p=1/81.0;
                board[i][j].transitions=new Transitions();
                if(i-1<0&&j-1<0)
                {
                    board[i][j].transitions.right=0.46;
                    board[i][j].transitions.down=0.46;
                    board[i][j].transitions.down_right=0.04;
                    board[i][j].transitions.steady=0.04;
                }
                else if(i-1<0&&j+1==9)
                {
                    board[i][j].transitions.right=0.46;
                    board[i][j].transitions.up=0.46;
                    board[i][j].transitions.up_right=0.04;
                    board[i][j].transitions.steady=0.04;
                }
                else if(i+1==9&&j+1==9)
                {
                    board[i][j].transitions.left=0.46;
                    board[i][j].transitions.up=0.46;
                    board[i][j].transitions.up_left=0.04;
                    board[i][j].transitions.steady=0.04;
                }
                else if(i+1==9&&j-1<0)
                {
                    board[i][j].transitions.left=0.46;
                    board[i][j].transitions.down=0.46;
                    board[i][j].transitions.down_left=0.04;
                    board[i][j].transitions.steady=0.04;
                }
                else if(i-1<0)
                {
                    board[i][j].transitions.right=0.32;
                    board[i][j].transitions.up=0.32;
                    board[i][j].transitions.down=0.32;
                    board[i][j].transitions.up_right=0.01;
                    board[i][j].transitions.down_right=0.01;
                    board[i][j].transitions.steady=0.02;
                }
                else if(i+1==9)
                {
                    board[i][j].transitions.left=0.32;
                    board[i][j].transitions.up=0.32;
                    board[i][j].transitions.down=0.32;
                    board[i][j].transitions.up_left=0.01;
                    board[i][j].transitions.down_left=0.01;
                    board[i][j].transitions.steady=0.02;
                }
                else if(j-1<0)
                {
                    board[i][j].transitions.right=0.32;
                    board[i][j].transitions.left=0.32;
                    board[i][j].transitions.down=0.32;
                    board[i][j].transitions.down_right=0.01;
                    board[i][j].transitions.down_left=0.01;
                    board[i][j].transitions.steady=0.02;
                }
                else if(j+1==9)
                {
                    board[i][j].transitions.right=0.32;
                    board[i][j].transitions.left=0.32;
                    board[i][j].transitions.up=0.32;
                    board[i][j].transitions.up_right=0.01;
                    board[i][j].transitions.up_left=0.01;
                    board[i][j].transitions.steady=0.02;
                }
                else
                {
                    board[i][j].transitions.left=0.23;
                    board[i][j].transitions.right=0.23;
                    board[i][j].transitions.up=0.23;
                    board[i][j].transitions.down=0.23;
                    board[i][j].transitions.up_left=0.015;
                    board[i][j].transitions.down_left=0.015;
                    board[i][j].transitions.up_right=0.015;
                    board[i][j].transitions.down_right=0.015;
                    board[i][j].transitions.steady=0.02;

                }

            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setBackground(light);
        graphics.setFont(new TrueTypeFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 18), true));
        for (int i=0;i<boardsize;i++)
        {
            for (int j = 0; j < boardsize; j++)
            {
                graphics.setColor(board[i][j].color);
                graphics.fillRect(board[i][j].x,board[i][j].y,board[i][j].w,board[i][j].h);
                graphics.setColor(Color.black);
                graphics.setLineWidth(5);
                graphics.drawRect(board[i][j].x,board[i][j].y,board[i][j].w,board[i][j].h);
                graphics.drawString(String.valueOf(board[i][j].partices),board[i][j].x+8,board[i][j].y+20);
            }
        }
        graphics.setColor(Color.black);
        graphics.fillRoundRect(170,700,200,60,50);
        graphics.setColor(new Color(214, 0, 3));
        graphics.fillRoundRect(450,700,150,60,50);
        graphics.setColor(Color.white);
        graphics.setFont(new TrueTypeFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 27), true));
        graphics.drawString("Next Time",195,715);
        graphics.drawString("Catch!!",470,715);
        graphics.setFont(new TrueTypeFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 30), true));
        if(gameover)
        {
            if (won)
            {
                graphics.setColor(new Color(34, 123, 4));
                graphics.drawString(notification,100,30);
                winimage.draw(board[ghostIndexi][ghostIndexj].x,board[ghostIndexi][ghostIndexj].y);
            }
            else
            {
                graphics.setColor(new Color(198, 0, 0));
                graphics.drawString(notification,160,30);
                looseimage.draw(board[ghostIndexi][ghostIndexj].x,board[ghostIndexi][ghostIndexj].y);
            }
        }
        else
        {
            graphics.setColor(Color.black);
            graphics.drawString(notification,110,30);
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input=gameContainer.getInput();

        int xpos=Mouse.getX();
        int ypos=Mouse.getY();
        if(!gameover)
        {
            if (input.isMouseButtonDown(0)) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (xpos >= 170 && xpos <= 370 && ypos >= gameContainer.getHeight() - 760 && ypos <= gameContainer.getHeight() - 700)
                {

                    int[][] prevparticles = new int[9][9];
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            prevparticles[i][j] = board[i][j].partices;
                        }
                    }
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            for (int k=0;k<prevparticles[i][j];k++)
                            {
                                randn = random.nextDouble();
                                board[i][j].partices--;
                                if (i - 1 < 0 && j - 1 < 0) {
                                    if (randn < 0.46) {
                                        board[i + 1][j].partices++;
                                    } else if (randn < 92) {
                                        board[i][j + 1].partices++;
                                    } else if (randn < 96) {
                                        board[i + 1][j + 1].partices++;
                                    }
                                    else
                                    {
                                        board[i][j].partices++;
                                    }
                                } else if (i - 1 < 0 && j + 1 == 9) {
                                    if (randn < 0.46) {
                                        board[i + 1][j].partices++;
                                    } else if (randn < 92) {
                                        board[i][j-1].partices++;
                                    } else if (randn < 96) {
                                        board[i+1][j-1].partices++;
                                    }
                                    else
                                    {
                                        board[i][j].partices++;
                                    }
                                } else if (i + 1 == 9 && j + 1 == 9) {
                                    if (randn < 0.46) {
                                        board[i - 1][j].partices++;
                                    } else if (randn < 92) {
                                        board[i][j-1].partices++;
                                    } else if (randn < 96) {
                                        board[i-1][j-1].partices++;
                                    }
                                    else
                                    {
                                        board[i][j].partices++;
                                    }
                                } else if (i + 1 == 9 && j - 1 < 0) {
                                    if (randn < 0.46) {
                                        board[i - 1][j].partices++;
                                    } else if (randn < 92) {
                                        board[i][j+1].partices++;
                                    } else if (randn < 96) {
                                        board[i-1][j+1].partices++;
                                    }
                                    else
                                    {
                                        board[i][j].partices++;
                                    }
                                } else if (i - 1 < 0) {
                                    if (randn < 0.32) {
                                        board[i + 1][j].partices++;
                                    } else if (randn < 0.64) {
                                        board[i][j+1].partices++;
                                    } else if (randn < 0.96) {
                                        board[i][j-1].partices++;
                                    } else if (randn < 0.97) {
                                        board[i + 1][j+1].partices++;
                                    } else if (randn < 0.98) {
                                        board[i + 1][j-1].partices++;
                                    }
                                    else
                                    {
                                        board[i][j].partices++;
                                    }
                                } else if (i + 1 == 9) {
                                    if (randn < 0.32) {
                                        board[i-1][j].partices++;
                                    } else if (randn < 0.64) {
                                        board[i][j+1].partices++;
                                    } else if (randn < 0.96) {
                                        board[i][j-1].partices++;
                                    } else if (randn < 0.97) {
                                        board[i-1][j+1].partices++;
                                    } else if (randn < 0.98) {
                                        board[i-1][j-1].partices++;
                                    }
                                    else
                                    {
                                        board[i][j].partices++;
                                    }
                                } else if (j - 1 < 0) {
                                    if (randn < 0.32) {
                                        board[i][j+1].partices++;
                                    } else if (randn < 0.64) {
                                        board[i+1][j].partices++;
                                    } else if (randn < 0.96) {
                                        board[i-1][j].partices++;
                                    } else if (randn < 0.97) {
                                        board[i+1][j+1].partices++;
                                    } else if (randn < 0.98) {
                                        board[i-1][j+1].partices++;
                                    }
                                    else
                                    {
                                        board[i][j].partices++;
                                    }
                                } else if (j + 1 == 9) {
                                    if (randn < 0.32) {
                                        board[i][j-1].partices++;
                                    } else if (randn < 0.64) {
                                        board[i+1][j].partices++;
                                    } else if (randn < 0.96) {
                                        board[i-1][j].partices++;
                                    } else if (randn < 0.97) {
                                        board[i+1][j-1].partices++;
                                    } else if (randn < 0.98) {
                                        board[i-1][j-1].partices++;
                                    }
                                    else
                                    {
                                        board[i][j].partices++;
                                    }
                                } else {
                                    if (randn < 0.23) {
                                        board[i+1][j].partices++;
                                    } else if (randn < 0.46) {
                                        board[i][j+1].partices++;
                                    } else if (randn < 0.69) {
                                        board[i-1][j].partices++;
                                    } else if (randn < 0.92) {
                                        board[i][j-1].partices++;
                                    } else if (randn < 0.935) {
                                        board[i+1][j+1].partices++;
                                    } else if (randn < 0.95) {
                                        board[i+1][j-1].partices++;
                                    } else if (randn < 0.965) {
                                        board[i-1][j+1].partices++;
                                    } else if (randn < 0.98) {
                                        board[i-1][j-1].partices++;
                                    }
                                    else
                                    {
                                        board[i][j].partices++;
                                    }

                                }
                            }

                        }
                    }

                    randn = random.nextDouble();
                    if (ghostIndexi == 0 && ghostIndexj == 0) {
                        if (randn < 0.46) {
                            ghostIndexi++;
                        } else if (randn < 92) {
                            ghostIndexj++;
                        } else if (randn < 96) {
                            ghostIndexi++;
                            ghostIndexj++;
                        }
                    } else if (ghostIndexi == 0 && ghostIndexj == 8) {
                        if (randn < 0.46) {
                            ghostIndexi++;
                        } else if (randn < 92) {
                            ghostIndexj--;
                        } else if (randn < 96) {
                            ghostIndexi++;
                            ghostIndexj--;
                        }
                    } else if (ghostIndexi == 8 && ghostIndexj == 8) {
                        if (randn < 0.46) {
                            ghostIndexi--;
                        } else if (randn < 92) {
                            ghostIndexj--;
                        } else if (randn < 96) {
                            ghostIndexi--;
                            ghostIndexj--;
                        }
                    } else if (ghostIndexi == 8 && ghostIndexj == 0) {
                        if (randn < 0.46) {
                            ghostIndexi--;
                        } else if (randn < 92) {
                            ghostIndexj++;
                        } else if (randn < 96) {
                            ghostIndexi--;
                            ghostIndexj++;
                        }
                    } else if (ghostIndexi == 0) {
                        if (randn < 0.32) {
                            ghostIndexi++;
                        } else if (randn < 0.64) {
                            ghostIndexj++;
                        } else if (randn < 0.96) {
                            ghostIndexj--;
                        } else if (randn < 0.97) {
                            ghostIndexi++;
                            ghostIndexj++;
                        } else if (randn < 0.98) {
                            ghostIndexi++;
                            ghostIndexj--;
                        }
                    } else if (ghostIndexi == 8) {
                        if (randn < 0.32) {
                            ghostIndexi--;
                        } else if (randn < 0.64) {
                            ghostIndexj++;
                        } else if (randn < 0.96) {
                            ghostIndexj--;
                        } else if (randn < 0.97) {
                            ghostIndexi--;
                            ghostIndexj++;
                        } else if (randn < 0.98) {
                            ghostIndexi--;
                            ghostIndexj--;
                        }
                    } else if (ghostIndexj == 0) {
                        if (randn < 0.32) {
                            ghostIndexj++;
                        } else if (randn < 0.64) {
                            ghostIndexi++;
                        } else if (randn < 0.96) {
                            ghostIndexi--;
                        } else if (randn < 0.97) {
                            ghostIndexi++;
                            ghostIndexj++;
                        } else if (randn < 0.98) {
                            ghostIndexi--;
                            ghostIndexj++;
                        }
                    } else if (ghostIndexj == 8) {
                        if (randn < 0.32) {
                            ghostIndexj--;
                        } else if (randn < 0.64) {
                            ghostIndexi++;
                        } else if (randn < 0.96) {
                            ghostIndexi--;
                        } else if (randn < 0.97) {
                            ghostIndexi--;
                            ghostIndexj--;
                        } else if (randn < 0.98) {
                            ghostIndexi++;
                            ghostIndexj--;
                        }
                    } else {
                        if (randn < 0.23) {
                            ghostIndexi++;
                        } else if (randn < 0.46) {
                            ghostIndexj++;
                        } else if (randn < 0.69) {
                            ghostIndexi--;
                        } else if (randn < 0.92) {
                            ghostIndexj--;
                        } else if (randn < 0.935) {
                            ghostIndexi++;
                            ghostIndexj++;
                        } else if (randn < 0.95) {
                            ghostIndexi++;
                            ghostIndexj--;
                        } else if (randn < 0.965) {
                            ghostIndexi--;
                            ghostIndexj++;
                        } else if (randn < 0.98) {
                            ghostIndexi--;
                            ghostIndexj--;
                        }
                    }
                    System.out.println("Current Ghost Position : " + ghostIndexi + " , " + ghostIndexj);

                }
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        board[i][j].color = Color.white;
                        if (xpos > board[i][j].x && xpos <= (board[i][j].x + board[i][j].w) && ypos >= (gameContainer.getHeight() - (board[i][j].y + board[i][j].h)) && ypos <= (gameContainer.getHeight() - board[i][j].y))
                        {
                            int manhattandistance = Math.abs(board[i][j].indexi - ghostIndexi) + Math.abs(board[i][j].indexj - ghostIndexj);
                            int colorindex = checkColor(manhattandistance);
                            board[i][j].color = colors[colorindex];
                            double vanishedparticles = 0,aliveparticles=0;
                            for (int m = 0; m < 9; m++) {
                                for (int n = 0; n < 9; n++) {
                                    int newmanhdis = Math.abs(board[i][j].indexi - m) + Math.abs(board[i][j].indexj - n);
                                    int newcol = checkColor(newmanhdis);
                                    if (newcol!=colorindex)
                                    {
                                        vanishedparticles+=board[m][n].partices;
                                        board[m][n].partices=0;
                                    }
                                    else
                                    {
                                        aliveparticles+=board[m][n].partices;
                                    }
                                }
                            }
                            for (int m = 0; m < 9; m++) {
                                for (int n = 0; n < 9; n++) {
                                    double newParticlesTobeadded=(board[m][n].partices*vanishedparticles)/aliveparticles;
                                    board[m][n].partices += Math.round(newParticlesTobeadded);
                                }
                            }
                            if(catchflag)
                            {
                                gameover=true;
                                if(board[i][j].indexi==ghostIndexi&&board[i][j].indexj==ghostIndexj)
                                {
                                    notification="Yeyy!! You have caugth the ghost!!";
                                    won=true;
                                }
                                else
                                {
                                    notification="Opps !! The ghost isn't here !!";
                                    won=false;
                                }
                            }
                        }
                    }
                }
                if (xpos >= 450 && xpos <= 600 && ypos >= gameContainer.getHeight() - 760 && ypos <= gameContainer.getHeight() - 700) {
                    catchflag = !catchflag;
                    if (catchflag) {
                        notification = "Select the Cell to Catch the Ghost!!";
                    } else {
                        notification = "";
                    }
                }
            }
        }

    }

    @Override
    public int getID() {
        return 2;
    }
}
