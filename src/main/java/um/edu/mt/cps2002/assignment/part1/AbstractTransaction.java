package um.edu.mt.cps2002.assignment.part1;

/**
 * Created by kylebonnici on 12/05/15.
 */
public abstract class AbstractTransaction {

    public abstract boolean check();
    public abstract boolean checkFailOnTime();
    public abstract boolean process();
    public abstract void updateTime();
    public abstract void rollback();
}
