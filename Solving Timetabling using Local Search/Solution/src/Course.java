import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Course{
    boolean visited;
    int course_no;
    int color,swocolor;
    ArrayList<Course> Neighbours=new ArrayList<Course>();
    Course(int course_no)
    {
        this.course_no=course_no;
    }
    public void addNeighbour(Course course)
    {
        if(!Neighbours.contains(course))
        {
            Neighbours.add(course);
        }
        color=-1;
        swocolor=-1;

        visited=false;
    }
    @Override
    public boolean equals(Object o)
    {
        if (o==this)
        {
            return true;
        }
        if (!(o instanceof Course))
        {
            return false;
        }
        Course course=(Course)o;
        return this.course_no==course.course_no;
    }
    public int calculateSatur()
    {
        int mySatur=0;
        ArrayList<Integer> myTrack=new ArrayList<>();
        for (int i=0;i<this.Neighbours.size();i++)
        {
            if (this.Neighbours.get(i).color>=0&&!(myTrack.contains(this.Neighbours.get(i).color)))
            {
                mySatur++;
                myTrack.add(this.Neighbours.get(i).color);
            }
        }
        return mySatur;
    }
    @Override
    public int hashCode()
    {
       return Objects.hash(course_no);
    }
    public int compareSaturation(Course c) {
        if(this.calculateSatur()!=c.calculateSatur())
        {
            return c.calculateSatur()-this.calculateSatur();
        }
        else
        {
            return c.Neighbours.size()-this.Neighbours.size();
        }
    }
    public int comparedegree(Course c) {
            return c.Neighbours.size()-this.Neighbours.size();
    }
    public int compareBlame(Course c)
    {
        return c.color-this.color;
    }
}
