package Scheme_1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Backtrack_MDD {
    public static long  number_of_nodes=0;
    public static long number_of_backtracks=0;
    public static Variable getMaxVar(int[][] state)
    {
        ArrayList<Variable> variables=new ArrayList<Variable>();
        for (int i=0;i<state.length;i++)
        {
            for (int j=0;j<state.length;j++)
            {
                if(state[i][j]!=0)
                {
                    continue;
                }
                Variable v=new Variable();
                v.indexi=i;
                v.indexj=j;
                for (int k=0;k<state.length;k++)
                {
                    if(state[i][k]==0&&k!=j)
                    {
                        v.value++;
                    }
                }
                for (int k=0;k< state.length;k++)
                {
                    if(state[k][j]==0&&k!=i)
                    {
                        v.value++;
                    }
                }
                variables.add(v);
            }
        }
        if (variables.size()==0)
        {
            return null;
        }
        return Collections.max(variables,Variable::compareVariable);
    }
    public static boolean checkConsistencyforone(int[][] state,int indexi,int indexj){
        if(state[indexi][indexj]==0)
        {
            return true;
        }

        for(int k=0;k<state.length;k++)
        {
            if(k==indexi)
            {
                continue;
            }
            if(state[indexi][indexj]==state[k][indexj]) {
                return false;
            }
        }
        for(int k=0;k<state.length;k++)
        {
            if(k==indexj)
            {
                continue;
            }
            if(state[indexi][indexj]==state[indexi][k]) {
                return false;
            }
        }
        return true;
    }

        public static boolean checkConsistency(int[][] state){
        for (int i=0;i<state.length;i++)
        {
            for (int j=0;j<state.length;j++)
            {
                if(state[i][j]==0) {
                    continue;
                }
                for(int k=0;k<state.length;k++)
                {
                    if(k==i)
                    {
                        continue;
                    }
                    if(state[i][j]==state[k][j]) {
                        return false;
                    }
                }
                for(int k=0;k<state.length;k++)
                {
                    if(k==j)
                    {
                        continue;
                    }
                    if(state[i][j]==state[i][k]) {
                        return false;
                    }
                }
            }
        }
        return true;

    }
    public static boolean backTrack(int[][] state,int indexi,int indexj)
    {
        boolean ok=checkConsistencyforone(state,indexi,indexj);
        if(!ok)
        {
            number_of_backtracks++;
            return false;
        }
        Variable variable=getMaxVar(state);
        if(variable==null)
        {
            return true;
        }
        for (int j=1;j<=state.length;j++)
        {
            number_of_nodes++;
            state[variable.indexi][variable.indexj]=j;
            ok=backTrack(state,variable.indexi,variable.indexj);
            if(ok)
            {
                return ok;
            }
            state[variable.indexi][variable.indexj]=0;
        }
        return false;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("d-10-01.txt.txt"));
        int n;
        String withsemicolon[]=scanner.next().split("=");
        n= Integer.parseInt(withsemicolon[1].split(";")[0]);
        int[][] input=new int[n][n];
        scanner.next();
        scanner.next();
        int indexi=0,indexj=0;
        for(int i=0;i<n;i++)
        {
            for (int j=0;j<n;j++)
            {
                input[i][j]= Integer.parseInt(scanner.next().split(",")[0]);
                if(input[i][j]!=0)
                {
                    indexi=i;
                    indexj=j;
                }
            }
            scanner.next();
        }
        boolean solvable=backTrack(input,indexi,indexj);
        number_of_nodes++;
        if(solvable)
        {
            System.out.println("The Quasigroup Completion Problem is solvable");
            for (int i=0;i<input.length;i++)
            {
                for (int j=0;j<input.length;j++)
                {
                    System.out.print(input[i][j]+" ");
                }
                System.out.println();
            }
        }
        else
        {
            System.out.println("The Quasigroup Completion Problem is not solvable");
        }
        System.out.println("Number of nodes explored "+number_of_nodes);
        System.out.println("Number of backtracks "+number_of_backtracks);
    }
}
