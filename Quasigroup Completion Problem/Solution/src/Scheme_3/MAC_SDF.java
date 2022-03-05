package Scheme_3;
import Scheme_1.Backtrack_MDD;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class MAC_SDF {
    public static int number_of_nodes=0;
    public static int number_of_backtracks=0;
    public static ArrayList<Integer>[][] domains;
    public static boolean mac(int[][] state)
    {
        int indexi=-1,indexj=-1,smallest_domain=1000;
        for (int i=0;i<state.length;i++)
        {
            for (int j=0;j<state.length;j++)
            {
                if(state[i][j]==0)
                {
                    if(domains[i][j].size()<smallest_domain)
                    {
                        smallest_domain=domains[i][j].size();
                        indexi=i;
                        indexj=j;
                    }
                }
            }
        }
        if(smallest_domain==1000)
        {
            return true;
        }
        for (int i=0;i<domains[indexi][indexj].size();i++)
        {
            ArrayList<Integer>[][] prevdomains=new ArrayList[state.length][state.length];
            for (int m=0;m<state.length;m++)
            {
                for (int n=0;n<state.length;n++)
                {
                    prevdomains[m][n]=new ArrayList<Integer>();
                    for (int o=0;o<domains[m][n].size();o++)
                    {
                        prevdomains[m][n].add(domains[m][n].get(o));
                    }
                }
            }
            boolean ok=true;
            number_of_nodes++;
            state[indexi][indexj]=domains[indexi][indexj].get(i);
            LinkedList<Node> queue=new LinkedList<Node>();
            for(int j=0;j<state.length;j++)
            {
                if (j==indexi||state[j][indexj]!=0)
                {
                    continue;
                }
                boolean flag=domains[j][indexj].remove(Integer.valueOf(state[indexi][indexj]));
                if(domains[j][indexj].size()==0)
                {
                    number_of_backtracks++;
                    ok=false;
                    break;
                }
                else if(flag)
                {
                    for (int k=0;k<state.length;k++)
                    {
                        if(k==j||state[k][indexj]!=0)
                        {
                            continue;
                        }
                        queue.addLast(new Node(j,indexj,k,indexj));
                    }
                    for (int k=0;k<state.length;k++)
                    {
                        if(k==indexj||state[j][k]!=0)
                        {
                            continue;
                        }
                        queue.addLast(new Node(j,indexj,j,k));
                    }
                }
            }
            if(ok)
            {
                for(int j=0;j<state.length;j++)
                {
                    if (j==indexj||state[indexi][j]!=0)
                    {
                        continue;
                    }
                    boolean flag=domains[indexi][j].remove(Integer.valueOf(state[indexi][indexj]));
                    if(domains[indexi][j].size()==0)
                    {
                        number_of_backtracks++;
                        ok=false;
                        break;
                    }
                    else if(flag)
                    {
                        for (int k=0;k<state.length;k++)
                        {
                            if(k==j||state[indexi][k]!=0)
                            {
                                continue;
                            }
                            queue.addLast(new Node(indexi,j,indexi,k));
                        }
                        for (int k=0;k<state.length;k++)
                        {
                            if(k==indexi||state[k][j]!=0)
                            {
                                continue;
                            }
                            queue.addLast(new Node(indexi,j,k,j));
                        }
                    }

                }
            }
            if(ok) {
                while (!queue.isEmpty()) {
                    Node node = queue.removeFirst();
                    if (domains[node.parenti][node.parentj].size() == 1)
                    {
                        boolean flag=domains[node.indexi][node.indexj].remove(domains[node.parenti][node.parentj].get(0));
                        if(flag)
                        {
                            if(domains[node.indexi][node.indexj].size()==0)
                            {
                                number_of_backtracks++;
                                ok=false;
                                break;
                            }
                            else
                            {
                                for (int j=0;j<state.length;j++)
                                {
                                    if(j==node.indexj||state[node.indexi][j]!=0)
                                    {
                                        continue;
                                    }
                                    queue.addLast(new Node(node.indexi,node.indexj,node.indexi,j));
                                }
                                for (int j=0;j<state.length;j++)
                                {
                                    if(j==node.indexi||state[j][indexj]!=0)
                                    {
                                        continue;
                                    }
                                    queue.addLast(new Node(node.indexi,node.indexj,j,node.indexj));
                                }
                            }
                        }
                    }
                }
            }

           if(ok)
           {
               ok=mac(state);
               if(ok)
               {
                   return true;
               }
           }
            if(!ok)
            {
                for (int m=0;m<state.length;m++)
                {
                    for (int n=0;n<state.length;n++)
                    {
                        domains[m][n]=new ArrayList<Integer>();
                        for (int o=0;o<prevdomains[m][n].size();o++)
                        {
                            domains[m][n].add(prevdomains[m][n].get(o));
                        }
                    }
                }
                state[indexi][indexj]=0;
            }


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
        for(int i=0;i<n;i++)
        {
            for (int j=0;j<n;j++)
            {
                input[i][j]= Integer.parseInt(scanner.next().split(",")[0]);
            }
            scanner.next();
        }
        domains=new ArrayList[n][n];
        for (int i=0;i<n;i++)
        {
            for (int j=0;j<n;j++)
            {
                domains[i][j]=new ArrayList<Integer>();
                if(input[i][j]==0) {
                    for (int k = 1; k <= n; k++) {
                        domains[i][j].add(k);
                    }
                }
            }
        }
        for (int i=0;i<n;i++)
        {
            for (int j=0;j<n;j++)
            {
                if(input[i][j]!=0)
                {
                    for (int k=0;k<n;k++)
                    {
                        if(k==i)
                        {
                            continue;
                        }
                        domains[k][j].remove(Integer.valueOf(input[i][j]));
                    }
                    for (int k=0;k<n;k++)
                    {
                        if(k==j)
                        {
                            continue;
                        }
                        domains[i][k].remove(Integer.valueOf(input[i][j]));
                    }
                }
            }
        }

        boolean solvable=mac(input);
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
            System.out.println(Backtrack_MDD.checkConsistency(input));
        }
        else
        {
            System.out.println("The Quasigroup Completion Problem is not solvable");
        }
        System.out.println("Number of nodes explored "+number_of_nodes);
        System.out.println("Number of backtracks "+number_of_backtracks);
    }
}
