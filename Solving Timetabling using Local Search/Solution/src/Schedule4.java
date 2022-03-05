import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Schedule4 {
    public static double calculatePenalty()
    {
        double penalty=0;
        for (int i=0;i<students.size();i++)
        {
            for (int j=0;j<students.get(i).enrolledCourses.size();j++)
            {
                for (int k=j+1;k<students.get(i).enrolledCourses.size();k++)
                {
                    if(Math.abs(students.get(i).enrolledCourses.get(j).color-students.get(i).enrolledCourses.get(k).color)==1)
                    {
                        penalty+=16;
                    }
                    else if(Math.abs(students.get(i).enrolledCourses.get(j).color-students.get(i).enrolledCourses.get(k).color)==2)
                    {
                        penalty+=8;
                    }
                    else if(Math.abs(students.get(i).enrolledCourses.get(j).color-students.get(i).enrolledCourses.get(k).color)==3)
                    {
                        penalty+=4;
                    }
                    else if(Math.abs(students.get(i).enrolledCourses.get(j).color-students.get(i).enrolledCourses.get(k).color)==4)
                    {
                        penalty+=2;
                    }
                    else if(Math.abs(students.get(i).enrolledCourses.get(j).color-students.get(i).enrolledCourses.get(k).color)==5)
                    {
                        penalty+=1;
                    }
                }
            }
        }
        penalty=penalty/students.size();
        return penalty;
    }
    public static ArrayList<Course> courses;
    public static ArrayList<Student> students;
    public static void main(String[] args) throws IOException {
        String filename="yor-f-83";
        BufferedReader reader =  new BufferedReader(new FileReader("src/"+filename+".crs"));

        int courseCount = 0;
        while (true) {
            if (!(reader.readLine() != null)) break;
            courseCount++;
        }
        reader.close();
        File courseFile = new File("src/"+filename+".crs");
        courses = new ArrayList<Course>();
        Scanner myReader = new Scanner(courseFile);

        for (int i = 0; i < courseCount; i++) {
            courses.add(new Course(myReader.nextInt()));
            myReader.nextInt();
        }
        myReader.close();
        students=new ArrayList<Student>();
        File studentFile = new File("src/"+filename+".stu");
        myReader = new Scanner(studentFile);
        while (myReader.hasNextLine()) {
            Student student=new Student();
            String data = myReader.nextLine();
            String[] out = data.split(" ");
            for (int i = 0; i < out.length; i++) {
                for (int j = 0; j < out.length; j++) {
                    if (i == j) {
                        continue;
                    }
                    courses.get(courses.indexOf(new Course(Integer.parseInt(out[i])))).addNeighbour(courses.get(courses.indexOf(new Course(Integer.parseInt(out[j])))));
                }
                student.enrolledCourses.add(courses.get(courses.indexOf(new Course(Integer.parseInt(out[i])))));
            }
            students.add(student);
        }
        myReader.close();


        ArrayList<Course> degreePriority=new ArrayList<Course>();
        for (int i = 0; i < courseCount; i++){
            degreePriority.add(courses.get(i));
        }
        int Color=1;
        for (int i=0;i<courseCount;i++)
        {
            boolean[] colorAvaibility=new boolean[Color];
            degreePriority.sort(Course::comparedegree);
            Course c=degreePriority.remove(0);
            for (int j=0;j<c.Neighbours.size();j++)
            {
                if(c.Neighbours.get(j).color>=0)
                {
                    colorAvaibility[c.Neighbours.get(j).color]=true;
                }
            }
            boolean colored=false;
            for (int j=0;j<Color;j++)
            {
                if(!colorAvaibility[j])
                {
                    colored=true;
                    c.color=j;
                    break;
                }
            }
            if (!colored)
            {
                c.color=Color;
                Color++;

            }
        }
        //swo
        for (int swo=0;swo<1000;swo++) {
            ArrayList<Course> swoPriority = new ArrayList<Course>();
            for (int i = 0; i < courseCount; i++) {
                swoPriority.add(courses.get(i));
            }
            swoPriority.sort(Course::compareBlame);
            int swoColor = 1;
            for (int i = 0; i < courseCount; i++) {
                boolean[] colorAvaibility = new boolean[swoColor];
                Course c = swoPriority.remove(0);
                for (int j = 0; j < c.Neighbours.size(); j++) {
                    if (c.Neighbours.get(j).swocolor >= 0) {
                        colorAvaibility[c.Neighbours.get(j).swocolor] = true;
                    }
                }
                boolean colored = false;
                for (int j = 0; j < swoColor; j++) {
                    if (!colorAvaibility[j]) {
                        colored = true;
                        c.swocolor = j;
                        break;
                    }
                }
                if (!colored) {
                    c.swocolor = swoColor;
                    swoColor++;

                }
            }
            for (int i=0;i<courseCount;i++)
            {
                courses.get(i).color=courses.get(i).swocolor;
                courses.get(i).swocolor=-1;
            }
            Color=swoColor;
        }
        System.out.println("Timeslots "+Color);
        for (int o=0;o<4;o++) {
            //kempe
            for (int num = 0; num < 3; num++) {
                for (int i = 0; i < courseCount; i++) {
                    ArrayList<Integer> track = new ArrayList<Integer>();
                    int color1 = courses.get(i).color;
                    for (int j = 0; j < courses.get(i).Neighbours.size(); j++) {
                        if (!track.contains(courses.get(i).Neighbours.get(j).color)) {
                            double initPenalty = calculatePenalty();
                            track.add(courses.get(i).Neighbours.get(j).color);
                            int color2 = courses.get(i).Neighbours.get(j).color;
                            ArrayList<Course> bfsQueue = new ArrayList<Course>();
                            bfsQueue.add(courses.get(i));
                            while (!bfsQueue.isEmpty()) {
                                Course c = bfsQueue.remove(0);
                                if (c.color == color1 && !(c.visited)) {
                                    c.visited = true;
                                    c.color = color2;
                                    for (int k = 0; k < c.Neighbours.size(); k++) {
                                        if (c.Neighbours.get(k).color == color2) {
                                            bfsQueue.add(c.Neighbours.get(k));
                                        }
                                    }
                                } else if (c.color == color2 && !(c.visited)) {
                                    c.visited = true;
                                    c.color = color1;
                                    for (int k = 0; k < c.Neighbours.size(); k++) {
                                        if (c.Neighbours.get(k).color == color1) {
                                            bfsQueue.add(c.Neighbours.get(k));
                                        }
                                    }
                                }
                            }
                            double currentpenalty = calculatePenalty();
                            if (currentpenalty > initPenalty) {
                                for (int k = 0; k < courseCount; k++) {
                                    courses.get(k).visited = false;
                                }
                                bfsQueue = new ArrayList<Course>();
                                bfsQueue.add(courses.get(i));
                                while (!bfsQueue.isEmpty()) {
                                    Course c = bfsQueue.remove(0);
                                    if (c.color == color1 && !(c.visited)) {
                                        c.visited = true;
                                        c.color = color2;
                                        for (int k = 0; k < c.Neighbours.size(); k++) {
                                            if (c.Neighbours.get(k).color == color2) {
                                                bfsQueue.add(c.Neighbours.get(k));
                                            }
                                        }
                                    } else if (c.color == color2 && !(c.visited)) {
                                        c.visited = true;
                                        c.color = color1;
                                        for (int k = 0; k < c.Neighbours.size(); k++) {
                                            if (c.Neighbours.get(k).color == color1) {
                                                bfsQueue.add(c.Neighbours.get(k));
                                            }
                                        }
                                    }
                                }
                            }
                            for (int k = 0; k < courseCount; k++) {
                                courses.get(k).visited = false;
                            }
                        }
                    }
                    track.clear();
                }
            }
            //pair swap
            for (int i = 0; i < courseCount; i++) {
                for (int j = i + 1; j < courseCount; j++) {
                    boolean flag = false;
                    for (int k = 0; k < courses.get(j).Neighbours.size(); k++) {
                        if (courses.get(j).Neighbours.get(k).color == courses.get(i).color) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        for (int k = 0; k < courses.get(i).Neighbours.size(); k++) {
                            if (courses.get(i).Neighbours.get(k).color == courses.get(j).color) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    if (!flag) {
                        double previousPenalty = calculatePenalty();
                        int temp = courses.get(i).color;
                        courses.get(i).color = courses.get(j).color;
                        courses.get(j).color = temp;
                        double postPenalty = calculatePenalty();
                        if (postPenalty > previousPenalty) {
                            temp = courses.get(i).color;
                            courses.get(i).color = courses.get(j).color;
                            courses.get(j).color = temp;
                        }
                    }
                }
            }
        }
        System.out.println("Penalty : "+calculatePenalty());

        File output = new File(filename+".sol");
        output.createNewFile();
        FileWriter myWriter = new FileWriter(filename+".sol");
        myWriter.write("");
        for (int i=0;i<courseCount;i++)
        {
            if(i!=courseCount-1)
                myWriter.append(courses.get(i).course_no+" "+courses.get(i).color+"\n");
            else
                myWriter.append(courses.get(i).course_no+" "+courses.get(i).color);
        }
        myWriter.close();

    }
}
