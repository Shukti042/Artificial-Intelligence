import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Manhattan {
    static int [][] goal;
    static Hashtable<Integer,Position> goalPosition;
    static Hashtable<List<Integer>, Boolean> repeatGrid;
    static PriorityQueue<Node> priorityQueue;
    public static void Manhattanmain(int [][] input) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Stack<Node> finalResult=new Stack<>();
        int it=0,inputZeroPosition=0,goalZeroPosition = 0;
        int inputInversionCount=0,goalInversionCount=0;
        int[] inputArr,goalArr;
        int resultCost=0,queEntrey=1;
        inputArr=new int[16];
        goalArr=new int[16];
        Position zeroPosition;
        zeroPosition=new Position(0,0);
        //input=new int[4][4];
        //goal=new int[4][4];
        repeatGrid=new Hashtable<List<Integer>, Boolean>();
        priorityQueue=new PriorityQueue<Node>();
        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                //input[i][j]=scanner.nextInt();
                if (input[i][j]==0)
                {
                    zeroPosition=new Position(i,j);
                }
            }
        }
        //scanner.close();
        int index=0;
        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                inputArr[index]=input[i][j];
                if (input[i][j]==0)
                {
                    inputZeroPosition=i;
                }
                goalArr[index]=goal[i][j];
                if (goal[i][j]==0)
                {
                    goalZeroPosition=i;
                }
                index++;
            }
        }
        for (int i=0;i<16;i++)
        {
            for (int j=i+1;j<16;j++)
            {
                if (goalArr[i]!=0&&goalArr[j]!=0&&goalArr[i]>goalArr[j])
                {
                    goalInversionCount++;
                }
                if (inputArr[i]!=0&&inputArr[j]!=0&&inputArr[i]>inputArr[j])
                {
                    inputInversionCount++;
                }
            }
        }
        goalZeroPosition=3-goalZeroPosition;
        inputZeroPosition=3-inputZeroPosition;
        if (((goalInversionCount+goalZeroPosition)%2)!=((inputInversionCount+inputZeroPosition)%2))
        {
            System.out.println();
            System.out.println("The puzzle is not Solvable");
            System.out.println();
            return;
        }
        else
        {
            System.out.println();
            System.out.println("The Solve in Manhattan is given below :");
            System.out.println();
        }
        Node node=new Node(null,input,zeroPosition,0);
        Node inputNode=node;
        Node temp;
        List<Integer> items= new ArrayList<Integer>();
        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                items.add(node.grid[i][j]);
            }
        }
        repeatGrid.put(items,true);
        while (node.manhattanDistance!=0)
        {
            queEntrey++;
            temp=new Node(node,node.grid,node.zeroPosition,node.cost+1);

            if (temp.zeroPosition.x+1<4)
            {
                temp.grid[temp.zeroPosition.x][temp.zeroPosition.y]= temp.grid[temp.zeroPosition.x+1][temp.zeroPosition.y];
                temp.grid[temp.zeroPosition.x+1][temp.zeroPosition.y]=0;
                temp.zeroPosition.x=temp.zeroPosition.x+1;
                temp.calculateManhattanDistance();
                items.clear();
                for (int i=0;i<4;i++)
                {
                    for (int j=0;j<4;j++)
                    {
                        items.add(temp.grid[i][j]);
                    }
                }
                if (repeatGrid.get(items)==null)
                {
                    priorityQueue.add(temp);
                    //queEntrey++;
                    repeatGrid.put(items,true);
                }

            }
            temp=new Node(node,node.grid,node.zeroPosition,node.cost+1);
            if (temp.zeroPosition.y+1<4)
            {
                temp.grid[temp.zeroPosition.x][temp.zeroPosition.y]= temp.grid[temp.zeroPosition.x][temp.zeroPosition.y+1];
                temp.grid[temp.zeroPosition.x][temp.zeroPosition.y+1]=0;
                temp.zeroPosition.y=temp.zeroPosition.y+1;
                temp.calculateManhattanDistance();
                items.clear();
                for (int i=0;i<4;i++)
                {
                    for (int j=0;j<4;j++)
                    {
                        items.add(temp.grid[i][j]);
                    }
                }
                if (repeatGrid.get(items)==null)
                {
                    priorityQueue.add(temp);
                    //queEntrey++;
                    repeatGrid.put(items,true);
                }

            }
            temp=new Node(node,node.grid,node.zeroPosition,node.cost+1);
            if (temp.zeroPosition.y-1>=0)
            {
                temp.grid[temp.zeroPosition.x][temp.zeroPosition.y]= temp.grid[temp.zeroPosition.x][temp.zeroPosition.y-1];
                temp.grid[temp.zeroPosition.x][temp.zeroPosition.y-1]=0;
                temp.zeroPosition.y=temp.zeroPosition.y-1;
                temp.calculateManhattanDistance();
                items.clear();
                for (int i=0;i<4;i++)
                {
                    for (int j=0;j<4;j++)
                    {
                        items.add(temp.grid[i][j]);
                    }
                }
                if (repeatGrid.get(items)==null)
                {
                    priorityQueue.add(temp);
                   // queEntrey++;
                    repeatGrid.put(items,true);
                }

            }
            temp=new Node(node,node.grid,node.zeroPosition,node.cost+1);
            if (temp.zeroPosition.x-1>=0)
            {
                temp.grid[temp.zeroPosition.x][temp.zeroPosition.y]= temp.grid[temp.zeroPosition.x-1][temp.zeroPosition.y];
                temp.grid[temp.zeroPosition.x-1][temp.zeroPosition.y]=0;
                temp.zeroPosition.x=temp.zeroPosition.x-1;
                temp.calculateManhattanDistance();
                items.clear();
                for (int i=0;i<4;i++)
                {
                    for (int j=0;j<4;j++)
                    {
                        items.add(temp.grid[i][j]);
                    }
                }
                if (repeatGrid.get(items)==null)
                {
                    priorityQueue.add(temp);
                   // queEntrey++;
                    repeatGrid.put(items,true);
                }

            }
            node=priorityQueue.peek();
            priorityQueue.poll();
            it++;
//            if(it>50)
//                break;
        }
        resultCost=node.cost;
        while (node!=inputNode)
        {
            finalResult.push(node);
            node=node.parent;
        }
        System.out.println(inputNode);
        while (!finalResult.empty())
        {
            System.out.println(finalResult.pop());
        }
        System.out.println("Total cost is :"+resultCost);
        System.out.println("Total Explored Nodes: "+queEntrey);
        long endTime   = System.currentTimeMillis();;
        long totalTime = endTime - startTime;
        //System.out.println("Total taken by A* Search : "+totalTime/1000.0+" seconds");

        priorityQueue.clear();
        repeatGrid.clear();
    }
}
