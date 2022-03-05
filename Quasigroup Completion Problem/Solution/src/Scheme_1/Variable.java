package Scheme_1;

public class Variable {
    public int indexi;
    public int indexj;
    public int value;
    public Variable()
    {
        value=0;
    }
    public int compareVariable(Variable v)
    {
        return v.value-this.value;
    }
}
