package javagame;

public class PotentialState {
    public int checkerIndex;
    public int nextIndexi;
    public int nextIndexj;
    public float eval;

    public PotentialState(int checkerIndex, int nextIndexi, int nextIndexj) {
        this.checkerIndex = checkerIndex;
        this.nextIndexi = nextIndexi;
        this.nextIndexj = nextIndexj;
        eval=0;
    }
    public PotentialState(int checkerIndex, int nextIndexi, int nextIndexj,float eval) {
        this.checkerIndex = checkerIndex;
        this.nextIndexi = nextIndexi;
        this.nextIndexj = nextIndexj;
        this.eval=eval;
    }
    public PotentialState(float eval)
    {
        this.eval=eval;
    }
    public static PotentialState Max(PotentialState p1,PotentialState p2)
    {
        if(p1.eval>=p2.eval)
            return p1;
        else return p2;
    }
    public static PotentialState Min(PotentialState p1,PotentialState p2)
    {
        if(p1.eval<=p2.eval)
            return p1;
        else return p2;
    }
}
