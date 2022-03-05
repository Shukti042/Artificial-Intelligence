import java.util.Objects;

public class Position {
    int x,y;
    Position(int x,int y)
    {
        this.x=x;
        this.y=y;
    }
    public int distance(int x,int y)
    {
        return Math.abs((x-this.x))+Math.abs((y-this.y));
    }
    @Override
    public String toString()
    {
        return new String("("+x+" , "+y+")");

    }
    public int hashCode() {
        return Objects.hash(x,y);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        if (position.x==this.x&&position.y==this.y)
        {
            return true;
        }
        return false;

    }
}
