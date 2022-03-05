import java.util.Objects;

public class Node implements Comparable<Node> {
    int cost;
    Node parent;
    int[][] grid;
    int manhattanDistance=0;
    Position zeroPosition;

    public Node(Node parent, int[][] grid, Position zeroPosition, int cost) {
        this.grid=new int[4][4];
        for(int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                this.grid[i][j]=grid[i][j];
            }
        }
        this.zeroPosition=new Position(zeroPosition.x,zeroPosition.y);
        this.parent=parent;
        calculateManhattanDistance();
        this.cost=cost;
    }
    public void calculateManhattanDistance()
    {
        manhattanDistance=0;
        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                if (grid[i][j]!=0)
                {
                    manhattanDistance+=Manhattan.goalPosition.get(grid[i][j]).distance(i,j);
                }
            }
        }
    }

    @Override
    public String toString()
    {
        String array="";
        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                array=array+" "+grid[i][j];
            }
            array=array+"\n";
        }
        return array;
    }

    @Override
    public int compareTo(Node node) {
        if((this.cost+this.manhattanDistance)>(node.cost+node.manhattanDistance))
        {
            return 1;
        }
        else if((this.cost+this.manhattanDistance)<(node.cost+node.manhattanDistance))
        {
            return -1;
        }
        else
        {
            if (this.manhattanDistance>node.manhattanDistance)
                return -1;
            else if (this.manhattanDistance<node.manhattanDistance)
                return 1;
            else
                return 0;
        }

    }

//    public int hashCode() {
//        return Objects.hash(grid);
//    }
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Node node = (Node) o;
//        for (int i=0;i<4;i++)
//        {
//            for (int j=0;j<4;j++)
//            {
//                if (this.grid[i][j]!=node.grid[i][j])
//                {
//                    return false;
//                }
//            }
//        }
//        return true;
    // }
}
