import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner=new Scanner(new File("src/input.txt"));
        int testCase=scanner.nextInt();
        Hamington.goal=new int[4][4];
        Manhattan.goal=new int[4][4];
        Manhattan.goalPosition=new Hashtable<Integer,Position>();
        Hamington.goalPosition=new Hashtable<Integer,Position>();
        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                Manhattan.goal[i][j]=scanner.nextInt();
                Manhattan.goalPosition.put(Manhattan.goal[i][j],new Position(i,j));
                Hamington.goal[i][j]= Manhattan.goal[i][j];
                Hamington.goalPosition.put(Hamington.goal[i][j],new Position(i,j));
            }
        }
        int [][] input;
        input=new int[4][4];
        for (int t=1;t<testCase;t++)
        {
            System.out.println();
            System.out.println("Problem : "+t);
            for (int i=0;i<4;i++)
            {
                for (int j=0;j<4;j++)
                {
                    input[i][j]=scanner.nextInt();
                }
            }
            Manhattan.Manhattanmain(input);
            Hamington.Hamingtonmain(input);
        }
    }
}
