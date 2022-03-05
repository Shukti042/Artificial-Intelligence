package javagame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import java.util.ArrayList;

public class Play6 extends BasicGameState {
    boolean noLegalMove=false;
    boolean gameover=false;
    String turnString;
    ArrayList<Cell> potentialCells;
    Checker selectedChecker;
    int boardsize=6;
    int cellsize=100;
    Color light=new Color(205,133,63);
    Color dark=new Color(139,69,19);
    Cell[][] board=new Cell[boardsize][boardsize];
    int turn=1;
    Checker[] checkers=new Checker[16];

    public Play6(int state) {
    }
    public void decideGameOver()
    {
        int mover=turn;
        for (int check=0;check<=1;check++) {
            ArrayList<Checker> bfs = new ArrayList<Checker>();
            Checker currentChecker = null;
            for (int i = 0; i < checkers.length; i++) {
                if (checkers[i].alive && checkers[i].colorCode == mover) {
                    currentChecker = checkers[i];
                    break;
                }
            }
            bfs.add(currentChecker);
            currentChecker.visited = true;
            while (!bfs.isEmpty()) {
                currentChecker = bfs.remove(0);
                int i = currentChecker.cell.indexi;
                int j = currentChecker.cell.indexj;
                if (i + 1 < boardsize) {
                    if (board[i + 1][j].checker != null) {
                        if (board[i + 1][j].checker.alive && board[i + 1][j].checker.colorCode == mover && !board[i + 1][j].checker.visited) {
                            board[i + 1][j].checker.visited = true;
                            bfs.add(board[i + 1][j].checker);
                        }
                    }
                }
                if (j + 1 < boardsize) {
                    if (board[i][j + 1].checker != null) {
                        if (board[i][j + 1].checker.alive && board[i][j + 1].checker.colorCode == mover && !board[i][j + 1].checker.visited) {
                            board[i][j + 1].checker.visited = true;
                            bfs.add(board[i][j + 1].checker);
                        }
                    }
                }
                if (i - 1 >= 0) {
                    if (board[i - 1][j].checker != null) {
                        if (board[i - 1][j].checker.alive && board[i - 1][j].checker.colorCode == mover && !board[i - 1][j].checker.visited) {
                            board[i - 1][j].checker.visited = true;
                            bfs.add(board[i - 1][j].checker);
                        }
                    }
                }
                if (j - 1 >= 0) {
                    if (board[i][j - 1].checker != null) {
                        if (board[i][j - 1].checker.alive && board[i][j - 1].checker.colorCode == mover && !board[i][j - 1].checker.visited) {
                            board[i][j - 1].checker.visited = true;
                            bfs.add(board[i][j - 1].checker);
                        }
                    }
                }
                if (i + 1 < boardsize && j + 1 < boardsize) {
                    if (board[i + 1][j + 1].checker != null) {
                        if (board[i + 1][j + 1].checker.alive && board[i + 1][j + 1].checker.colorCode == mover && !board[i + 1][j + 1].checker.visited) {
                            board[i + 1][j + 1].checker.visited = true;
                            bfs.add(board[i + 1][j + 1].checker);
                        }
                    }
                }
                if (i - 1 >= 0 && j + 1 < boardsize) {
                    if (board[i - 1][j + 1].checker != null) {
                        if (board[i - 1][j + 1].checker.alive && board[i - 1][j + 1].checker.colorCode == mover && !board[i - 1][j + 1].checker.visited) {
                            board[i - 1][j + 1].checker.visited = true;
                            bfs.add(board[i - 1][j + 1].checker);
                        }
                    }
                }
                if (i + 1 < boardsize && j - 1 >= 0) {
                    if (board[i + 1][j - 1].checker != null) {
                        if (board[i + 1][j - 1].checker.alive && board[i + 1][j - 1].checker.colorCode == mover && !board[i + 1][j - 1].checker.visited) {
                            board[i + 1][j - 1].checker.visited = true;
                            bfs.add(board[i + 1][j - 1].checker);
                        }
                    }
                }
                if (i - 1 >= 0 && j - 1 >= 0) {
                    if (board[i - 1][j - 1].checker != null) {
                        if (board[i - 1][j - 1].checker.alive && board[i - 1][j - 1].checker.colorCode == mover && !board[i - 1][j - 1].checker.visited) {
                            board[i - 1][j - 1].checker.visited = true;
                            bfs.add(board[i - 1][j - 1].checker);
                        }
                    }
                }

            }
            boolean flag = true;
            for (int i = 0; i < checkers.length; i++) {
                if (checkers[i].colorCode == mover && checkers[i].alive) {
                    if (!checkers[i].visited) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag)
            {
                gameover = true;
                if (mover == 0) {
                    turnString = "White Wins!!";
                } else {
                    turnString = "Black Wins!!";
                }
                break;
            }
            for (int i=0;i<checkers.length;i++)
            {
                checkers[i].visited=false;
            }
            mover=1-turn;
        }

    }

    public boolean makeMove(GameContainer gameContainer)
    {
        if(selectedChecker==null)
        {
            return false;
        }
        if(potentialCells.size()==0)
        {
            return false;
        }
        int xpos=Mouse.getX();
        int ypos=Mouse.getY();
        for (int i=0;i<potentialCells.size();i++)
        {
            if(xpos>=potentialCells.get(i).x&&xpos<=potentialCells.get(i).x+potentialCells.get(i).w&&ypos>=gameContainer.getHeight()-(potentialCells.get(i).y+potentialCells.get(i).h)&&ypos<=gameContainer.getHeight()-potentialCells.get(i).y)
            {
                if(potentialCells.get(i).checker!=null)
                {
                    potentialCells.get(i).checker.alive=false;
                }
                selectedChecker.cell.checker=null;
                selectedChecker.cell=potentialCells.get(i);
                potentialCells.get(i).checker=selectedChecker;
                for (int j=0;j<potentialCells.size();j++)
                {
                    potentialCells.get(j).border=false;
                }
                potentialCells.clear();
                noLegalMove=false;
                decideGameOver();
                selectedChecker=null;
                turn=1-turn;
                return true;
            }
        }
        return false;
    }

    public void showPotentialMove()
    {
        for (int i=0;i<potentialCells.size();i++)
        {
            potentialCells.get(i).border=false;
        }
        potentialCells.clear();
        noLegalMove=false;
        if (selectedChecker!=null)
        {
            int count=0;
            for (int i=0;i<boardsize;i++)
            {
                if (board[i][selectedChecker.cell.indexj].checker!=null)
                {
                    count++;
                }
            }
            boolean flag=true;
            if(selectedChecker.cell.indexi+count>=boardsize)
            {
                flag=false;
            }
            else {
                if (board[selectedChecker.cell.indexi + count][selectedChecker.cell.indexj].checker != null) {
                    if (board[selectedChecker.cell.indexi + count][selectedChecker.cell.indexj].checker.colorCode == selectedChecker.colorCode) {
                        flag = false;
                    }
                }
                for (int i = 1; i < count; i++) {
                    if (board[selectedChecker.cell.indexi + i][selectedChecker.cell.indexj].checker != null) {
                        if (board[selectedChecker.cell.indexi + i][selectedChecker.cell.indexj].checker.colorCode != selectedChecker.colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                board[selectedChecker.cell.indexi+count][selectedChecker.cell.indexj].border=true;
                potentialCells.add(board[selectedChecker.cell.indexi+count][selectedChecker.cell.indexj]);
            }
            flag=true;
            if(selectedChecker.cell.indexi-count<0)
            {
                flag=false;
            }
            else {
                if (board[selectedChecker.cell.indexi - count][selectedChecker.cell.indexj].checker != null) {
                    if (board[selectedChecker.cell.indexi - count][selectedChecker.cell.indexj].checker.colorCode == selectedChecker.colorCode) {
                        flag = false;
                    }
                }
                for (int i = 1; i < count; i++) {
                    if (board[selectedChecker.cell.indexi - i][selectedChecker.cell.indexj].checker != null) {
                        if (board[selectedChecker.cell.indexi - i][selectedChecker.cell.indexj].checker.colorCode != selectedChecker.colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                board[selectedChecker.cell.indexi-count][selectedChecker.cell.indexj].border=true;
                potentialCells.add(board[selectedChecker.cell.indexi-count][selectedChecker.cell.indexj]);
            }
            count=0;

            for (int j=0;j<boardsize;j++)
            {
                if (board[selectedChecker.cell.indexi][j].checker!=null)
                {
                    count++;
                }
            }
            flag=true;
            if(selectedChecker.cell.indexj+count>=boardsize)
            {
                flag=false;
            }
            else {
                if (board[selectedChecker.cell.indexi][selectedChecker.cell.indexj + count].checker != null) {
                    if (board[selectedChecker.cell.indexi][selectedChecker.cell.indexj + count].checker.colorCode == selectedChecker.colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (board[selectedChecker.cell.indexi][selectedChecker.cell.indexj + j].checker != null) {
                        if (board[selectedChecker.cell.indexi][selectedChecker.cell.indexj + j].checker.colorCode != selectedChecker.colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                board[selectedChecker.cell.indexi][selectedChecker.cell.indexj+count].border=true;
                potentialCells.add(board[selectedChecker.cell.indexi][selectedChecker.cell.indexj+count]);
            }
            flag=true;
            if(selectedChecker.cell.indexj-count<0)
            {
                flag=false;
            }
            else {
                if (board[selectedChecker.cell.indexi][selectedChecker.cell.indexj - count].checker != null) {
                    if (board[selectedChecker.cell.indexi][selectedChecker.cell.indexj - count].checker.colorCode == selectedChecker.colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (board[selectedChecker.cell.indexi][selectedChecker.cell.indexj - j].checker != null) {
                        if (board[selectedChecker.cell.indexi][selectedChecker.cell.indexj - j].checker.colorCode != selectedChecker.colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                board[selectedChecker.cell.indexi][selectedChecker.cell.indexj-count].border=true;
                potentialCells.add(board[selectedChecker.cell.indexi][selectedChecker.cell.indexj-count]);
            }

            count=0;
            for (int i=selectedChecker.cell.indexi,j=selectedChecker.cell.indexj;i<boardsize&&j>=0;i++,j--)
            {
                if (board[i][j].checker!=null)
                {
                    count++;
                }
            }
            for (int i=selectedChecker.cell.indexi-1,j=selectedChecker.cell.indexj+1;j<boardsize&&i>=0;i--,j++)
            {
                if (board[i][j].checker!=null)
                {
                    count++;
                }
            }
            flag=true;
            if(selectedChecker.cell.indexj+count>=boardsize||selectedChecker.cell.indexi-count<0)
            {
                flag=false;
            }
            else {
                if (board[selectedChecker.cell.indexi - count][selectedChecker.cell.indexj + count].checker != null) {
                    if (board[selectedChecker.cell.indexi - count][selectedChecker.cell.indexj + count].checker.colorCode == selectedChecker.colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (board[selectedChecker.cell.indexi - j][selectedChecker.cell.indexj + j].checker != null) {
                        if (board[selectedChecker.cell.indexi - j][selectedChecker.cell.indexj + j].checker.colorCode != selectedChecker.colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                board[selectedChecker.cell.indexi-count][selectedChecker.cell.indexj+count].border=true;
                potentialCells.add(board[selectedChecker.cell.indexi-count][selectedChecker.cell.indexj+count]);
            }

            flag=true;
            if(selectedChecker.cell.indexi+count>=boardsize||selectedChecker.cell.indexj-count<0)
            {
                flag=false;
            }
            else {
                if (board[selectedChecker.cell.indexi + count][selectedChecker.cell.indexj - count].checker != null) {
                    if (board[selectedChecker.cell.indexi + count][selectedChecker.cell.indexj - count].checker.colorCode == selectedChecker.colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (board[selectedChecker.cell.indexi + j][selectedChecker.cell.indexj - j].checker != null) {
                        if (board[selectedChecker.cell.indexi + j][selectedChecker.cell.indexj - j].checker.colorCode != selectedChecker.colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }

            if(flag)
            {
                board[selectedChecker.cell.indexi+count][selectedChecker.cell.indexj-count].border=true;
                potentialCells.add(board[selectedChecker.cell.indexi+count][selectedChecker.cell.indexj-count]);
            }

            count=0;
            for (int i=selectedChecker.cell.indexi,j=selectedChecker.cell.indexj;i>=0&&j>=0;i--,j--)
            {
                if (board[i][j].checker!=null)
                {
                    count++;
                }
            }
            for (int i=selectedChecker.cell.indexi+1,j=selectedChecker.cell.indexj+1;j<boardsize&&i<boardsize;i++,j++)
            {
                if (board[i][j].checker!=null)
                {
                    count++;
                }
            }
            flag=true;
            if(selectedChecker.cell.indexj+count>=boardsize||selectedChecker.cell.indexi+count>=boardsize)
            {
                flag=false;
            }
            else {
                if (board[selectedChecker.cell.indexi + count][selectedChecker.cell.indexj + count].checker != null) {
                    if (board[selectedChecker.cell.indexi + count][selectedChecker.cell.indexj + count].checker.colorCode == selectedChecker.colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (board[selectedChecker.cell.indexi + j][selectedChecker.cell.indexj + j].checker != null) {
                        if (board[selectedChecker.cell.indexi + j][selectedChecker.cell.indexj + j].checker.colorCode != selectedChecker.colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                board[selectedChecker.cell.indexi+count][selectedChecker.cell.indexj+count].border=true;
                potentialCells.add(board[selectedChecker.cell.indexi+count][selectedChecker.cell.indexj+count]);
            }
            flag=true;
            if(selectedChecker.cell.indexj-count<0||selectedChecker.cell.indexi-count<0)
            {
                flag=false;
            }
            else {
                if (board[selectedChecker.cell.indexi - count][selectedChecker.cell.indexj - count].checker != null) {
                    if (board[selectedChecker.cell.indexi - count][selectedChecker.cell.indexj - count].checker.colorCode == selectedChecker.colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (board[selectedChecker.cell.indexi - j][selectedChecker.cell.indexj - j].checker != null) {
                        if (board[selectedChecker.cell.indexi - j][selectedChecker.cell.indexj - j].checker.colorCode != selectedChecker.colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                board[selectedChecker.cell.indexi-count][selectedChecker.cell.indexj-count].border=true;
                potentialCells.add(board[selectedChecker.cell.indexi-count][selectedChecker.cell.indexj-count]);
            }
            if (potentialCells.size()==0)
            {
                noLegalMove=true;
            }

        }
    }

    public void selectChecker(GameContainer gameContainer)
    {
        int xpos= Mouse.getX();
        int ypos=Mouse.getY();
        boolean flag=false;
        for (int i = 0; i < checkers.length; i++) {
            if (checkers[i].alive) {
                if (xpos >= checkers[i].cell.x && xpos <= checkers[i].cell.x + checkers[i].cell.w && ypos >= gameContainer.getHeight() - (checkers[i].cell.y + checkers[i].cell.h) && ypos <= gameContainer.getHeight() - checkers[i].cell.y&&turn==checkers[i].colorCode) {
                    checkers[i].border = true;
                    selectedChecker = checkers[i];
                    flag=true;
                    break;
                }
            }
        }
        showPotentialMove();
        if(!flag)
        {
            makeMove(gameContainer);
            selectedChecker=null;
        }
        for (int i=0;i<checkers.length;i++)
        {
            if(checkers[i]!=selectedChecker||selectedChecker==null) {
                checkers[i].border = false;
            }
        }
    }

    public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
    {
        for (int i=0;i<boardsize;i++)
        {
            for (int j=0;j<boardsize;j++)
            {
                board[i][j]=new Cell();
                board[i][j].h=cellsize;
                board[i][j].w=cellsize;
                board[i][j].x=80+cellsize*i;
                board[i][j].y=150+cellsize*j;
                board[i][j].indexi=i;
                board[i][j].indexj=j;
                if(i%2==0&&j%2==0)
                {
                    board[i][j].color=light;
                }
                else if(i%2==0&&j%2!=0)
                {
                    board[i][j].color=dark;
                }
                else if(i%2!=0&&j%2==0)
                {
                    board[i][j].color=dark;
                }
                else
                {
                    board[i][j].color=light;
                }
            }
        }
        for (int i=0;i<4;i++)
        {
            checkers[i]=new Checker();
            checkers[i].color=Color.white;
            checkers[i].colorCode=0;
            checkers[i].cell=board[0][i+1];
            board[0][i+1].checker=checkers[i];
            checkers[i].alive=true;
            checkers[i].visited=false;
        }
        for (int i=4;i<8;i++)
        {
            checkers[i]=new Checker();
            checkers[i].color=Color.white;
            checkers[i].colorCode=0;
            checkers[i].cell=board[boardsize-1][i-3];
            board[boardsize-1][i-3].checker=checkers[i];
            checkers[i].alive=true;
            checkers[i].visited=false;
        }
        for (int i=8;i<12;i++)
        {
            checkers[i]=new Checker();
            checkers[i].color=Color.black;
            checkers[i].colorCode=1;
            checkers[i].cell=board[i-7][0];
            board[i-7][0].checker=checkers[i];
            checkers[i].alive=true;
            checkers[i].visited=false;
        }
        for (int i=12;i<16;i++)
        {
            checkers[i]=new Checker();
            checkers[i].color=Color.black;
            checkers[i].colorCode=1;
            checkers[i].cell=board[i-11][boardsize-1];
            board[i-11][boardsize-1].checker=checkers[i];
            checkers[i].alive=true;
            checkers[i].visited=false;
        }
        potentialCells=new ArrayList<Cell>();

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.setFont(new TrueTypeFont(new java.awt.Font("TimesRoman", java.awt.Font.BOLD, 40), true));
        graphics.drawString(turnString,50,50);
        if(noLegalMove) {
            graphics.setFont(new TrueTypeFont(new java.awt.Font("TimesRoman", java.awt.Font.BOLD, 25), true));
            graphics.drawString("No Legal Move :(",500,55);
        }
        for (int i=0;i<boardsize;i++)
        {
            for (int j = 0; j < boardsize; j++)
            {
                graphics.setColor(board[i][j].color);
                graphics.fillRect(board[i][j].x,board[i][j].y,board[i][j].w,board[i][j].h);
                if(board[i][j].border)
                {
                    if(selectedChecker!=null)
                    {
                        graphics.setColor(selectedChecker.color);
                        graphics.setLineWidth(5);
                        graphics.drawRect(board[i][j].x,board[i][j].y,board[i][j].w,board[i][j].h);

                    }
                }
            }
        }
        for (int i=0;i<checkers.length;i++)
        {
            if(!checkers[i].alive)
            {
                continue;
            }
            graphics.setColor(checkers[i].color);
            graphics.fillOval(checkers[i].cell.x+5,checkers[i].cell.y+5,checkers[i].cell.w-10,checkers[i].cell.h-10);
            if(checkers[i].border)
            {
                if(i<12)
                {
                    graphics.setColor(Color.black);
                }
                else {
                    graphics.setColor(Color.white);
                }
                graphics.setLineWidth(5);
                graphics.drawOval(checkers[i].cell.x+5,checkers[i].cell.y+5,checkers[i].cell.w-10,checkers[i].cell.h-10);
            }
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {

        Input input=gameContainer.getInput();
        if (input.isMouseButtonDown(0)&&!gameover)
        {
            selectChecker(gameContainer);

        }
        if(!gameover) {
            if (turn == 0) {
                turnString = "Turn : White";
            } else {
                turnString = "Turn : Black";
            }
        }
    }

    @Override
    public int getID() {
        return 3;
    }
}
