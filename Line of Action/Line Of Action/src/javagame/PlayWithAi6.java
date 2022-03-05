package javagame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlayWithAi6 extends BasicGameState {
    float INFINITY=500000;
    float piece=10,area=1,connectedness=20,quad=5,density=20,mobility=1;
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
    float[][] pieceSquareTable=new float[boardsize][boardsize];

    public PlayWithAi6(int state) {
    }
    public int checkGameover(Checker[] currentcheckers,Cell[][] currentboard,int currentturn)
    {
        int mover=currentturn;
        for (int check=0;check<=1;check++) {
            ArrayList<Checker> bfs = new ArrayList<Checker>();
            Checker currentChecker = null;
            for (int i = 0; i < currentcheckers.length; i++) {
                if (currentcheckers[i].alive && currentcheckers[i].colorCode == mover) {
                    currentChecker = currentcheckers[i];
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
                    if (currentboard[i + 1][j].checker != null) {
                        if (currentboard[i + 1][j].checker.alive && currentboard[i + 1][j].checker.colorCode == mover && !currentboard[i + 1][j].checker.visited) {
                            currentboard[i + 1][j].checker.visited = true;
                            bfs.add(currentboard[i + 1][j].checker);
                        }
                    }
                }
                if (j + 1 < boardsize) {
                    if (currentboard[i][j + 1].checker != null) {
                        if (currentboard[i][j + 1].checker.alive && currentboard[i][j + 1].checker.colorCode == mover && !currentboard[i][j + 1].checker.visited) {
                            currentboard[i][j + 1].checker.visited = true;
                            bfs.add(currentboard[i][j + 1].checker);
                        }
                    }
                }
                if (i - 1 >= 0) {
                    if (currentboard[i - 1][j].checker != null) {
                        if (currentboard[i - 1][j].checker.alive && currentboard[i - 1][j].checker.colorCode == mover && !currentboard[i - 1][j].checker.visited) {
                            currentboard[i - 1][j].checker.visited = true;
                            bfs.add(currentboard[i - 1][j].checker);
                        }
                    }
                }
                if (j - 1 >= 0) {
                    if (currentboard[i][j - 1].checker != null) {
                        if (currentboard[i][j - 1].checker.alive && currentboard[i][j - 1].checker.colorCode == mover && !currentboard[i][j - 1].checker.visited) {
                            currentboard[i][j - 1].checker.visited = true;
                            bfs.add(currentboard[i][j - 1].checker);
                        }
                    }
                }
                if (i + 1 < boardsize && j + 1 < boardsize) {
                    if (currentboard[i + 1][j + 1].checker != null) {
                        if (currentboard[i + 1][j + 1].checker.alive && currentboard[i + 1][j + 1].checker.colorCode == mover && !currentboard[i + 1][j + 1].checker.visited) {
                            currentboard[i + 1][j + 1].checker.visited = true;
                            bfs.add(currentboard[i + 1][j + 1].checker);
                        }
                    }
                }
                if (i - 1 >= 0 && j + 1 < boardsize) {
                    if (currentboard[i - 1][j + 1].checker != null) {
                        if (currentboard[i - 1][j + 1].checker.alive && currentboard[i - 1][j + 1].checker.colorCode == mover && !currentboard[i - 1][j + 1].checker.visited) {
                            currentboard[i - 1][j + 1].checker.visited = true;
                            bfs.add(currentboard[i - 1][j + 1].checker);
                        }
                    }
                }
                if (i + 1 < boardsize && j - 1 >= 0) {
                    if (currentboard[i + 1][j - 1].checker != null) {
                        if (currentboard[i + 1][j - 1].checker.alive && currentboard[i + 1][j - 1].checker.colorCode == mover && !currentboard[i + 1][j - 1].checker.visited) {
                            currentboard[i + 1][j - 1].checker.visited = true;
                            bfs.add(currentboard[i + 1][j - 1].checker);
                        }
                    }
                }
                if (i - 1 >= 0 && j - 1 >= 0) {
                    if (currentboard[i - 1][j - 1].checker != null) {
                        if (currentboard[i - 1][j - 1].checker.alive && currentboard[i - 1][j - 1].checker.colorCode == mover && !currentboard[i - 1][j - 1].checker.visited) {
                            currentboard[i - 1][j - 1].checker.visited = true;
                            bfs.add(currentboard[i - 1][j - 1].checker);
                        }
                    }
                }

            }
            boolean flag = true;
            for (int i = 0; i < currentcheckers.length; i++) {
                if (currentcheckers[i].colorCode == mover && currentcheckers[i].alive) {
                    if (!currentcheckers[i].visited) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag)
            {
                return mover;
            }
            for (int i=0;i<currentcheckers.length;i++)
            {
                currentcheckers[i].visited=false;
            }
            mover=1-currentturn;
        }
        return -1;
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
        pieceSquareTable[0][0]=-80;
        pieceSquareTable[0][boardsize-1]=-80;
        pieceSquareTable[boardsize-1][0]=-80;
        pieceSquareTable[boardsize-1][boardsize-1]=-80;
        pieceSquareTable[0][1]=-25;
        pieceSquareTable[1][0]=-25;
        pieceSquareTable[0][boardsize-2]=-25;
        pieceSquareTable[boardsize-2][0]=-25;
        pieceSquareTable[1][boardsize-1]=-25;
        pieceSquareTable[boardsize-1][1]=-25;
        pieceSquareTable[boardsize-2][boardsize-1]=-25;
        pieceSquareTable[boardsize-1][boardsize-2]=-25;
        for (int i=2;i<boardsize-2;i++)
        {
            pieceSquareTable[i][0]=-20;
        }
        for (int i=2;i<boardsize-2;i++)
        {
            pieceSquareTable[0][i]=-20;
        }
        for (int i=2;i<boardsize-2;i++)
        {
            pieceSquareTable[boardsize-1][i]=-20;
        }
        for (int i=2;i<boardsize-2;i++)
        {
            pieceSquareTable[i][boardsize-1]=-20;
        }

        for (int i=1;i<boardsize-1;i++)
        {
            pieceSquareTable[i][1]=10;
        }
        for (int i=1;i<boardsize-1;i++)
        {
            pieceSquareTable[1][i]=10;
        }
        for (int i=1;i<boardsize-1;i++)
        {
            pieceSquareTable[boardsize-2][i]=10;
        }
        for (int i=1;i<boardsize-1;i++)
        {
            pieceSquareTable[i][boardsize-2]=10;
        }

        for (int i=2;i<boardsize-2;i++)
        {
            pieceSquareTable[i][2]=25;
        }
        for (int i=2;i<boardsize-2;i++)
        {
            pieceSquareTable[2][i]=25;
        }
        for (int i=2;i<boardsize-2;i++)
        {
            pieceSquareTable[boardsize-3][i]=25;
        }
        for (int i=2;i<boardsize-2;i++)
        {
            pieceSquareTable[i][boardsize-3]=25;
        }
        pieceSquareTable[boardsize/2][boardsize/2]=50;
        pieceSquareTable[boardsize/2-1][boardsize/2]=50;
        pieceSquareTable[boardsize/2][boardsize/2-1]=50;
        pieceSquareTable[boardsize/2-1][boardsize/2-1]=50;
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
        if(turn==1) {
            if (input.isMouseButtonDown(0) && !gameover) {
                selectChecker(gameContainer);

            }
        }
        else if(turn==0&& !gameover){
            makeMoveWithAi();
        }
        if(!gameover) {
            if (turn == 0) {
                turnString = "Turn : White (AI)";
            } else {
                turnString = "Turn : Black";
            }
        }
    }

    public void makeMoveWithAi()
    {
//        ArrayList<PotentialState> potentialStates=new ArrayList<PotentialState>();
//        potentialStates=getPotentialStates(checkers,board,0);
//        if (potentialStates.size()==0)
//        {
//            gameover=true;
//            turnString="No move for White!!!";
//            return;
//        }
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Random random=new Random(System.currentTimeMillis());
//        int index=Math.abs(random.nextInt())%potentialStates.size();
//        if( board[potentialStates.get(index).nextIndexi][potentialStates.get(index).nextIndexj].checker!=null)
//        {
//            board[potentialStates.get(index).nextIndexi][potentialStates.get(index).nextIndexj].checker.alive=false;
//            System.out.println("Captured");
//        }

//        System.out.println(checkers[potentialStates.get(index).checkerIndex].cell.indexi+","+checkers[potentialStates.get(index).checkerIndex].cell.indexj+" to "+potentialStates.get(index).nextIndexi+","+potentialStates.get(index).nextIndexj);
//        checkers[potentialStates.get(index).checkerIndex].cell.checker=null;
//        checkers[potentialStates.get(index).checkerIndex].cell=board[potentialStates.get(index).nextIndexi][potentialStates.get(index).nextIndexj];
//        board[potentialStates.get(index).nextIndexi][potentialStates.get(index).nextIndexj].checker=checkers[potentialStates.get(index).checkerIndex];
//        decideGameOver();
//        System.out.println("White: "+centerOfMass(checkers,0).indexi+","+centerOfMass(checkers,0).indexj+" Quads: "+countQuads(checkers,board,0)+" Density "+calculateDensity(checkers,0));
//        System.out.println("Black: "+centerOfMass(checkers,1).indexi+" "+centerOfMass(checkers,1).indexj+" Quads: "+countQuads(checkers,board,1)+" Density "+calculateDensity(checkers,1));
        PotentialState bestmove=null;
        long start = System.currentTimeMillis(),end = 0;
            bestmove=minimax(checkers,board,4,-1*INFINITY,INFINITY,true);
            end = System.currentTimeMillis();
        System.out.println(end-start);

        if( board[bestmove.nextIndexi][bestmove.nextIndexj].checker!=null)
        {
            board[bestmove.nextIndexi][bestmove.nextIndexj].checker.alive=false;
            System.out.println("Captured");
        }
        System.out.println(checkers[bestmove.checkerIndex].cell.indexi+","+checkers[bestmove.checkerIndex].cell.indexj+" to "+bestmove.nextIndexi+","+bestmove.nextIndexj);
        checkers[bestmove.checkerIndex].cell.checker=null;
        checkers[bestmove.checkerIndex].cell=board[bestmove.nextIndexi][bestmove.nextIndexj];
        board[bestmove.nextIndexi][bestmove.nextIndexj].checker=checkers[bestmove.checkerIndex];
        decideGameOver();
        turn=1;
    }
    public ArrayList<PotentialState> getPotentialStates(Checker[] currentcheckers,Cell[][] currentboard,int currentturn)
    {
        ArrayList<PotentialState> potentialStates=new ArrayList<PotentialState>();
        for (int o=0;o<currentcheckers.length;o++)
        {
            if(!currentcheckers[o].alive||currentcheckers[o].colorCode!=currentturn)
            {
                continue;
            }
            int count=0;
            for (int i=0;i<boardsize;i++)
            {
                if (currentboard[i][currentcheckers[o].cell.indexj].checker!=null)
                {
                    count++;
                }
            }
            boolean flag=true;
            if(currentcheckers[o].cell.indexi+count>=boardsize)
            {
                flag=false;
            }
            else {
                if (currentboard[currentcheckers[o].cell.indexi + count][currentcheckers[o].cell.indexj].checker != null) {
                    if (currentboard[currentcheckers[o].cell.indexi + count][currentcheckers[o].cell.indexj].checker.colorCode == currentcheckers[o].colorCode) {
                        flag = false;
                    }
                }
                for (int i = 1; i < count; i++) {
                    if (currentboard[currentcheckers[o].cell.indexi + i][currentcheckers[o].cell.indexj].checker != null) {
                        if (currentboard[currentcheckers[o].cell.indexi + i][currentcheckers[o].cell.indexj].checker.colorCode != currentcheckers[o].colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                potentialStates.add(new PotentialState(o,currentcheckers[o].cell.indexi+count,currentcheckers[o].cell.indexj));
            }
            flag=true;
            if(currentcheckers[o].cell.indexi-count<0)
            {
                flag=false;
            }
            else {
                if (currentboard[currentcheckers[o].cell.indexi - count][currentcheckers[o].cell.indexj].checker != null) {
                    if (currentboard[currentcheckers[o].cell.indexi - count][currentcheckers[o].cell.indexj].checker.colorCode == currentcheckers[o].colorCode) {
                        flag = false;
                    }
                }
                for (int i = 1; i < count; i++) {
                    if (currentboard[currentcheckers[o].cell.indexi - i][currentcheckers[o].cell.indexj].checker != null) {
                        if (currentboard[currentcheckers[o].cell.indexi - i][currentcheckers[o].cell.indexj].checker.colorCode != currentcheckers[o].colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                potentialStates.add(new PotentialState(o,currentcheckers[o].cell.indexi-count,currentcheckers[o].cell.indexj));
            }
            count=0;

            for (int j=0;j<boardsize;j++)
            {
                if (currentboard[currentcheckers[o].cell.indexi][j].checker!=null)
                {
                    count++;
                }
            }
            flag=true;
            if(currentcheckers[o].cell.indexj+count>=boardsize)
            {
                flag=false;
            }
            else {
                if (currentboard[currentcheckers[o].cell.indexi][currentcheckers[o].cell.indexj + count].checker != null) {
                    if (currentboard[currentcheckers[o].cell.indexi][currentcheckers[o].cell.indexj + count].checker.colorCode == currentcheckers[o].colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (currentboard[currentcheckers[o].cell.indexi][currentcheckers[o].cell.indexj + j].checker != null) {
                        if (currentboard[currentcheckers[o].cell.indexi][currentcheckers[o].cell.indexj + j].checker.colorCode != currentcheckers[o].colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                potentialStates.add(new PotentialState(o,currentcheckers[o].cell.indexi,currentcheckers[o].cell.indexj+count));
            }
            flag=true;
            if(currentcheckers[o].cell.indexj-count<0)
            {
                flag=false;
            }
            else {
                if (currentboard[currentcheckers[o].cell.indexi][currentcheckers[o].cell.indexj - count].checker != null) {
                    if (currentboard[currentcheckers[o].cell.indexi][currentcheckers[o].cell.indexj - count].checker.colorCode == currentcheckers[o].colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (currentboard[currentcheckers[o].cell.indexi][currentcheckers[o].cell.indexj - j].checker != null) {
                        if (currentboard[currentcheckers[o].cell.indexi][currentcheckers[o].cell.indexj - j].checker.colorCode != currentcheckers[o].colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                potentialStates.add(new PotentialState(o,currentcheckers[o].cell.indexi,currentcheckers[o].cell.indexj-count));
            }

            count=0;
            for (int i=currentcheckers[o].cell.indexi,j=currentcheckers[o].cell.indexj;i<boardsize&&j>=0;i++,j--)
            {
                if (currentboard[i][j].checker!=null)
                {
                    count++;
                }
            }
            for (int i=currentcheckers[o].cell.indexi-1,j=currentcheckers[o].cell.indexj+1;j<boardsize&&i>=0;i--,j++)
            {
                if (currentboard[i][j].checker!=null)
                {
                    count++;
                }
            }
            flag=true;
            if(currentcheckers[o].cell.indexj+count>=boardsize||currentcheckers[o].cell.indexi-count<0)
            {
                flag=false;
            }
            else {
                if (currentboard[currentcheckers[o].cell.indexi - count][currentcheckers[o].cell.indexj + count].checker != null) {
                    if (currentboard[currentcheckers[o].cell.indexi - count][currentcheckers[o].cell.indexj + count].checker.colorCode == currentcheckers[o].colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (currentboard[currentcheckers[o].cell.indexi - j][currentcheckers[o].cell.indexj + j].checker != null) {
                        if (currentboard[currentcheckers[o].cell.indexi - j][currentcheckers[o].cell.indexj + j].checker.colorCode != currentcheckers[o].colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                potentialStates.add(new PotentialState(o,currentcheckers[o].cell.indexi-count,currentcheckers[o].cell.indexj+count));
            }

            flag=true;
            if(currentcheckers[o].cell.indexi+count>=boardsize||currentcheckers[o].cell.indexj-count<0)
            {
                flag=false;
            }
            else {
                if (currentboard[currentcheckers[o].cell.indexi + count][currentcheckers[o].cell.indexj - count].checker != null) {
                    if (currentboard[currentcheckers[o].cell.indexi + count][currentcheckers[o].cell.indexj - count].checker.colorCode == currentcheckers[o].colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (currentboard[currentcheckers[o].cell.indexi + j][currentcheckers[o].cell.indexj - j].checker != null) {
                        if (currentboard[currentcheckers[o].cell.indexi + j][currentcheckers[o].cell.indexj - j].checker.colorCode != currentcheckers[o].colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }

            if(flag)
            {
                potentialStates.add(new PotentialState(o,currentcheckers[o].cell.indexi+count,currentcheckers[o].cell.indexj-count));
            }

            count=0;
            for (int i=currentcheckers[o].cell.indexi,j=currentcheckers[o].cell.indexj;i>=0&&j>=0;i--,j--)
            {
                if (currentboard[i][j].checker!=null)
                {
                    count++;
                }
            }
            for (int i=currentcheckers[o].cell.indexi+1,j=currentcheckers[o].cell.indexj+1;j<boardsize&&i<boardsize;i++,j++)
            {
                if (currentboard[i][j].checker!=null)
                {
                    count++;
                }
            }
            flag=true;
            if(currentcheckers[o].cell.indexj+count>=boardsize||currentcheckers[o].cell.indexi+count>=boardsize)
            {
                flag=false;
            }
            else {
                if (currentboard[currentcheckers[o].cell.indexi + count][currentcheckers[o].cell.indexj + count].checker != null) {
                    if (currentboard[currentcheckers[o].cell.indexi + count][currentcheckers[o].cell.indexj + count].checker.colorCode == currentcheckers[o].colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (currentboard[currentcheckers[o].cell.indexi + j][currentcheckers[o].cell.indexj + j].checker != null) {
                        if (currentboard[currentcheckers[o].cell.indexi + j][currentcheckers[o].cell.indexj + j].checker.colorCode != currentcheckers[o].colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                potentialStates.add(new PotentialState(o,currentcheckers[o].cell.indexi+count,currentcheckers[o].cell.indexj+count));
            }
            flag=true;
            if(currentcheckers[o].cell.indexj-count<0||currentcheckers[o].cell.indexi-count<0)
            {
                flag=false;
            }
            else {
                if (currentboard[currentcheckers[o].cell.indexi - count][currentcheckers[o].cell.indexj - count].checker != null) {
                    if (currentboard[currentcheckers[o].cell.indexi - count][currentcheckers[o].cell.indexj - count].checker.colorCode == currentcheckers[o].colorCode) {
                        flag = false;
                    }
                }
                for (int j = 1; j < count; j++) {
                    if (currentboard[currentcheckers[o].cell.indexi - j][currentcheckers[o].cell.indexj - j].checker != null) {
                        if (currentboard[currentcheckers[o].cell.indexi - j][currentcheckers[o].cell.indexj - j].checker.colorCode != currentcheckers[o].colorCode) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(flag)
            {
                potentialStates.add(new PotentialState(o,currentcheckers[o].cell.indexi-count,currentcheckers[o].cell.indexj-count));
            }
        }
        return potentialStates;
    }

    public float pieceSquareScore(Checker[] currentCheckers,int currentTurn)
    {
        float score=0;
        if(currentTurn==0) {
            for (int i = 0; i < currentCheckers.length / 2; i++) {
                if (currentCheckers[i].alive) {
                    score += pieceSquareTable[currentCheckers[i].cell.indexi][currentCheckers[i].cell.indexj];
                }
            }
        }
        else
        {
            for (int i = currentCheckers.length/2; i < checkers.length; i++) {
                if (currentCheckers[i].alive) {
                    score += pieceSquareTable[currentCheckers[i].cell.indexi][currentCheckers[i].cell.indexj];
                }
            }
        }
        return score;

    }
    public float calculateArea(Checker[] currentCheckers,int currentTurn)
    {
        int imax=-100,imin=100,jmax=-100,jmin=100;
        if(currentTurn==0) {
            for (int i = 0; i < currentCheckers.length / 2; i++) {
                if(currentCheckers[i].alive) {
                    if (currentCheckers[i].cell.indexi < imin) {
                        imin = currentCheckers[i].cell.indexi;
                    }
                    if (currentCheckers[i].cell.indexi > imax) {
                        imax = currentCheckers[i].cell.indexi;
                    }
                    if (currentCheckers[i].cell.indexj < jmin) {
                        jmin = currentCheckers[i].cell.indexj;
                    }
                    if (currentCheckers[i].cell.indexj > jmax) {
                        jmax = currentCheckers[i].cell.indexj;
                    }
                }
            }
        }
        else
        {
            for (int i = currentCheckers.length/2; i < checkers.length; i++) {
                if(currentCheckers[i].alive) {
                    if (currentCheckers[i].cell.indexi < imin) {
                        imin = currentCheckers[i].cell.indexi;
                    }
                    if (currentCheckers[i].cell.indexi > imax) {
                        imax = currentCheckers[i].cell.indexi;
                    }
                    if (currentCheckers[i].cell.indexj < jmin) {
                        jmin = currentCheckers[i].cell.indexj;
                    }
                    if (currentCheckers[i].cell.indexj > jmax) {
                        jmax = currentCheckers[i].cell.indexj;
                    }
                }

            }
        }
        return (imax-imin+1)*(jmax-jmin+1);

    }
    public float calculateConnectedNess(Checker[] currentCheckers,Cell[][] currentBoard,int currentTurn)
    {
        float aliveCheckersCount=0,connectedness=0;
        for (int i = 0; i < currentCheckers.length; i++) {
            if(currentCheckers[i].colorCode==currentTurn) {
                if (currentCheckers[i].alive) {
                    aliveCheckersCount++;
                    if (currentCheckers[i].cell.indexi + 1 < boardsize) {
                        if (board[currentCheckers[i].cell.indexi + 1][currentCheckers[i].cell.indexj].checker != null) {
                            if (board[currentCheckers[i].cell.indexi + 1][currentCheckers[i].cell.indexj].checker.alive && board[currentCheckers[i].cell.indexi + 1][currentCheckers[i].cell.indexj].checker.colorCode == currentTurn) {
                                connectedness++;
                            }
                        }
                    }
                    if (currentCheckers[i].cell.indexj + 1 < boardsize) {
                        if (board[currentCheckers[i].cell.indexi][currentCheckers[i].cell.indexj+1].checker != null) {
                            if (board[currentCheckers[i].cell.indexi][currentCheckers[i].cell.indexj+1].checker.alive && board[currentCheckers[i].cell.indexi][currentCheckers[i].cell.indexj+1].checker.colorCode == currentTurn) {
                                connectedness++;
                            }
                        }
                    }
                    if (currentCheckers[i].cell.indexj + 1 < boardsize&&currentCheckers[i].cell.indexi + 1 < boardsize) {
                        if (board[currentCheckers[i].cell.indexi+1][currentCheckers[i].cell.indexj+1].checker != null) {
                            if (board[currentCheckers[i].cell.indexi+1][currentCheckers[i].cell.indexj+1].checker.alive && board[currentCheckers[i].cell.indexi+1][currentCheckers[i].cell.indexj+1].checker.colorCode == currentTurn) {
                                connectedness++;
                            }
                        }
                    }
                    if (currentCheckers[i].cell.indexi - 1 >=0) {
                        if (board[currentCheckers[i].cell.indexi - 1][currentCheckers[i].cell.indexj].checker != null) {
                            if (board[currentCheckers[i].cell.indexi - 1][currentCheckers[i].cell.indexj].checker.alive && board[currentCheckers[i].cell.indexi - 1][currentCheckers[i].cell.indexj].checker.colorCode == currentTurn) {
                                connectedness++;
                            }
                        }
                    }
                    if (currentCheckers[i].cell.indexj - 1 >=0) {
                        if (board[currentCheckers[i].cell.indexi][currentCheckers[i].cell.indexj-1].checker != null) {
                            if (board[currentCheckers[i].cell.indexi][currentCheckers[i].cell.indexj-1].checker.alive && board[currentCheckers[i].cell.indexi][currentCheckers[i].cell.indexj-1].checker.colorCode == currentTurn) {
                                connectedness++;
                            }
                        }
                    }
                    if (currentCheckers[i].cell.indexj - 1 >=0 &&currentCheckers[i].cell.indexi - 1 >=0) {
                        if (board[currentCheckers[i].cell.indexi-1][currentCheckers[i].cell.indexj-1].checker != null) {
                            if (board[currentCheckers[i].cell.indexi-1][currentCheckers[i].cell.indexj-1].checker.alive && board[currentCheckers[i].cell.indexi-1][currentCheckers[i].cell.indexj-1].checker.colorCode == currentTurn) {
                                connectedness++;
                            }
                        }
                    }
                    if (currentCheckers[i].cell.indexj - 1 >=0 &&currentCheckers[i].cell.indexi + 1 <boardsize) {
                        if (board[currentCheckers[i].cell.indexi+1][currentCheckers[i].cell.indexj-1].checker != null) {
                            if (board[currentCheckers[i].cell.indexi+1][currentCheckers[i].cell.indexj-1].checker.alive && board[currentCheckers[i].cell.indexi+1][currentCheckers[i].cell.indexj-1].checker.colorCode == currentTurn) {
                                connectedness++;
                            }
                        }
                    }
                    if (currentCheckers[i].cell.indexi - 1 >=0 &&currentCheckers[i].cell.indexj + 1 <boardsize) {
                        if (board[currentCheckers[i].cell.indexi-1][currentCheckers[i].cell.indexj+1].checker != null) {
                            if (board[currentCheckers[i].cell.indexi-1][currentCheckers[i].cell.indexj+1].checker.alive && board[currentCheckers[i].cell.indexi-1][currentCheckers[i].cell.indexj+1].checker.colorCode == currentTurn) {
                                connectedness++;
                            }
                        }
                    }
                }
            }
        }
        return connectedness/aliveCheckersCount;
    }
    public Coordinate centerOfMass(Checker[] currentCheckers,int currentTurn)
    {
        float x=0,y=0;
        int indexi,indexj;
        int aliveCheckers=0;
        for (int i=0;i<currentCheckers.length;i++)
        {
            if(currentCheckers[i].colorCode==currentTurn)
            {
                if(currentCheckers[i].alive)
                {
                    aliveCheckers++;
                    x+=currentCheckers[i].cell.indexi;
                    y+=currentCheckers[i].cell.indexj;
                }
            }
        }
        indexi=Math.round(x/aliveCheckers);
        indexj=Math.round(y/aliveCheckers);
        return new Coordinate(indexi,indexj);
    }
    public int countQuads(Checker[] currentCheckers,Cell[][] currentBoard,int currentTurn)
    {
        int quads=0;
        Coordinate com=centerOfMass(currentCheckers,currentTurn);
        int mini=Math.max(com.indexi-2,0);
        int minj=Math.max(com.indexj-2,0);
        int maxi=Math.min(com.indexi+2,boardsize-1);
        int maxj=Math.min(com.indexj+2,boardsize-1);
        for(int i=mini;i<maxi;i++)
        {
            for (int j=minj;j<maxj;j++)
            {
                int temp=0;
                if(currentBoard[i][j].checker!=null)
                {
                    if(currentBoard[i][j].checker.colorCode==currentTurn&&currentBoard[i][j].checker.alive)
                    {
                        temp++;
                    }
                }
                if(currentBoard[i+1][j].checker!=null)
                {
                    if(currentBoard[i+1][j].checker.colorCode==currentTurn&&currentBoard[i+1][j].checker.alive)
                    {
                        temp++;
                    }
                }
                if(currentBoard[i][j+1].checker!=null)
                {
                    if(currentBoard[i][j+1].checker.colorCode==currentTurn&&currentBoard[i][j+1].checker.alive)
                    {
                        temp++;
                    }
                }
                if(currentBoard[i+1][j+1].checker!=null)
                {
                    if(currentBoard[i+1][j+1].checker.colorCode==currentTurn&&currentBoard[i+1][j+1].checker.alive)
                    {
                        temp++;
                    }
                }
                if(temp>=3)
                {
                    quads++;
                }
            }
        }
        return quads;

    }
    public float calculateDensity(Checker[] currentCheckers,int currentTurn)
    {
        float density=0;
        float aliveCheckersCount=0;
        Coordinate com=centerOfMass(currentCheckers,currentTurn);
        for (int i=0;i<currentCheckers.length;i++)
        {
            if(currentCheckers[i].alive&&currentCheckers[i].colorCode==currentTurn)
            {
                aliveCheckersCount++;
                density+=Math.max(Math.abs(currentCheckers[i].cell.indexi-com.indexi),Math.abs(currentCheckers[i].cell.indexj-com.indexj));
            }
        }
        return density/aliveCheckersCount;

    }
    public float calculateUtility(Checker[] currentCheckers,Cell[][] currentBoard,int currentTurn)
    {
        float utility=0;
        if(piece!=0)
        {
            utility+=(piece*pieceSquareScore(currentCheckers,currentTurn));
        }
        if(area!=0)
        {
            utility-=area*calculateArea(currentCheckers,currentTurn);
        }
        if(connectedness!=0)
        {
            utility+=(connectedness*calculateConnectedNess(currentCheckers,currentBoard,currentTurn));
        }
        if(quad!=0)
        {
            utility+=(quad*countQuads(currentCheckers,currentBoard,currentTurn));
        }
        if(density!=0)
        {
            utility-=(density*calculateDensity(currentCheckers,currentTurn));
        }
        if(mobility!=0)
        {
            utility+=(mobility*getPotentialStates(currentCheckers,currentBoard,currentTurn).size());
        }
        return utility;
    }
    public PotentialState minimax(Checker[] currentCheckers,Cell[][] currentBoard,int depth,float alpha,float beta,boolean maximizingPlayer)
    {
        int mover;
        if(maximizingPlayer)
        {
            mover=0;
        }
        else
        {
            mover=1;
        }
        int decision=checkGameover(currentCheckers,currentBoard,1-mover);
        if(decision==0)
        {
            return new PotentialState(INFINITY);
        }
        if(decision==1)
        {
            return new PotentialState(-1*INFINITY);
        }
        if(depth==0)
        {
            return new PotentialState(calculateUtility(currentCheckers,currentBoard,0)-calculateUtility(currentCheckers,currentBoard,1));
        }
        if(maximizingPlayer)
        {
            PotentialState maxEval=new PotentialState(-1*INFINITY),eval;
            ArrayList<PotentialState> potentialStates=getPotentialStates(currentCheckers,currentBoard,0);
            Collections.shuffle(potentialStates);
            for (int i=0;i<potentialStates.size();i++)
            {
                Cell[][] nextBoard=new Cell[boardsize][boardsize];
                for(int j=0;j<boardsize;j++)
                {
                    for(int k=0;k<boardsize;k++)
                    {
                        nextBoard[j][k]=new Cell();
                        nextBoard[j][k].indexi=currentBoard[j][k].indexi;
                        nextBoard[j][k].indexj=currentBoard[j][k].indexj;
                    }
                }
                Checker nextCheckers[]=new Checker[currentCheckers.length];
                for (int j=0;j<nextCheckers.length;j++)
                {
                    nextCheckers[j]=new Checker();
                    nextCheckers[j].alive=currentCheckers[j].alive;
                    nextCheckers[j].colorCode=currentCheckers[j].colorCode;
                    nextCheckers[j].visited=false;
                    nextCheckers[j].cell=nextBoard[currentCheckers[j].cell.indexi][currentCheckers[j].cell.indexj];
                    nextCheckers[j].cell.checker=nextCheckers[j];
                }
                if(nextBoard[potentialStates.get(i).nextIndexi][potentialStates.get(i).nextIndexj].checker!=null)
                {
                    nextBoard[potentialStates.get(i).nextIndexi][potentialStates.get(i).nextIndexj].checker.alive=false;
                }
                nextCheckers[potentialStates.get(i).checkerIndex].cell.checker=null;
                nextCheckers[potentialStates.get(i).checkerIndex].cell=nextBoard[potentialStates.get(i).nextIndexi][potentialStates.get(i).nextIndexj];
                nextBoard[potentialStates.get(i).nextIndexi][potentialStates.get(i).nextIndexj].checker=nextCheckers[potentialStates.get(i).checkerIndex];
                eval=minimax(nextCheckers,nextBoard,depth-1,alpha,beta,false);
                eval.nextIndexi=potentialStates.get(i).nextIndexi;
                eval.nextIndexj=potentialStates.get(i).nextIndexj;
                eval.checkerIndex=potentialStates.get(i).checkerIndex;
                maxEval=PotentialState.Max(eval,maxEval);
                alpha=Math.max(alpha,maxEval.eval);
                if(beta<=alpha)
                {
                    break;
                }
            }
            return maxEval;
        }
        else
        {
            PotentialState minEval=new PotentialState(INFINITY),eval;
            ArrayList<PotentialState> potentialStates=getPotentialStates(currentCheckers,currentBoard,1);
            Collections.shuffle(potentialStates);
            for (int i=0;i<potentialStates.size();i++)
            {
                Cell[][] nextBoard=new Cell[boardsize][boardsize];
                for(int j=0;j<boardsize;j++)
                {
                    for(int k=0;k<boardsize;k++)
                    {
                        nextBoard[j][k]=new Cell();
                        nextBoard[j][k].indexi=currentBoard[j][k].indexi;
                        nextBoard[j][k].indexj=currentBoard[j][k].indexj;
                    }
                }
                Checker nextCheckers[]=new Checker[currentCheckers.length];
                for (int j=0;j<nextCheckers.length;j++)
                {
                    nextCheckers[j]=new Checker();
                    nextCheckers[j].alive=currentCheckers[j].alive;
                    nextCheckers[j].colorCode=currentCheckers[j].colorCode;
                    nextCheckers[j].visited=false;
                    nextCheckers[j].cell=nextBoard[currentCheckers[j].cell.indexi][currentCheckers[j].cell.indexj];
                    nextCheckers[j].cell.checker=nextCheckers[j];
                }
                if(nextBoard[potentialStates.get(i).nextIndexi][potentialStates.get(i).nextIndexj].checker!=null)
                {
                    nextBoard[potentialStates.get(i).nextIndexi][potentialStates.get(i).nextIndexj].checker.alive=false;
                }
                nextCheckers[potentialStates.get(i).checkerIndex].cell.checker=null;
                nextCheckers[potentialStates.get(i).checkerIndex].cell=nextBoard[potentialStates.get(i).nextIndexi][potentialStates.get(i).nextIndexj];
                nextBoard[potentialStates.get(i).nextIndexi][potentialStates.get(i).nextIndexj].checker=nextCheckers[potentialStates.get(i).checkerIndex];
                eval=minimax(nextCheckers,nextBoard,depth-1,alpha,beta,true);
                eval.nextIndexi=potentialStates.get(i).nextIndexi;
                eval.nextIndexj=potentialStates.get(i).nextIndexj;
                eval.checkerIndex=potentialStates.get(i).checkerIndex;
                minEval=PotentialState.Min(eval,minEval);
                beta=Math.min(beta,minEval.eval);
                if(beta<=alpha)
                {
                    break;
                }
            }
            return minEval;
        }

    }




    @Override
    public int getID() {
        return 4;
    }
}
