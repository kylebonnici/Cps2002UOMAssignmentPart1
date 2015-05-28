package um.edu.mt.cps2002.assignment.part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by kylebonnici on 12/05/15.
 */
public class CompoundTransaction extends AbstractTransaction {

    ArrayList<AbstractTransaction> childTransaction = new ArrayList<AbstractTransaction>();


    public boolean process(){
        boolean ok = true;
        for (int loops = 0; loops < childTransaction.size(); loops ++){
            if (childTransaction.get(loops).checkFailOnTime()){
                // show error
                ok = false;
            }
        }

        if (ok){
            ArrayList<AbstractTransaction> successList = new ArrayList<AbstractTransaction>();

            for (int loops = 0; loops < childTransaction.size(); loops ++){
                if(childTransaction.get(loops).process()){
                    successList.add(childTransaction.get(loops));
                }
            }

            if (successList.size() != childTransaction.size()){
                for (int loops = 0; loops < successList.size(); loops ++){
                    successList.get(loops).rollback();
                }
                return false;
            }else {
                return true;
            }
        }

        return ok;
    }

    public boolean check() {
        for (int loops = 0; loops < childTransaction.size(); loops ++){
            if (childTransaction.get(loops).checkFailOnTime()){
               return false;
            }
        }

        return true;
    }

    public boolean checkFailOnTime(){
        for (int loops = 0; loops < childTransaction.size(); loops ++){
            if (childTransaction.get(loops).checkFailOnTime()){
                return true;
            }
        }

        return false;
    }

    public void updateTime(){
        for (int loops = 0; loops < childTransaction.size(); loops ++){
            childTransaction.get(loops).updateTime();
        }
    }

    public void rollback() {
        for (int loops = 0; loops < childTransaction.size(); loops ++){
            childTransaction.get(loops).rollback();
        }
    }

    public void addChildTransaction(AbstractTransaction transaction){
        childTransaction.add(transaction);
    }

    public ArrayList<Transaction> getFilteredTransactions(int srcAcc){
        ArrayList<Transaction> out = new ArrayList<Transaction>();

        for (int loops = 0 ; loops < childTransaction.size(); loops ++){
            if (childTransaction.get(loops) instanceof Transaction){
                if ( ((Transaction)childTransaction.get(loops)).getSourceAccountNumber() == srcAcc ){
                    out.add((Transaction)childTransaction.get(loops) );
                }
            }else {
                out.addAll(((CompoundTransaction)childTransaction.get(loops)).getFilteredTransactions(srcAcc));
            }
        }

        return out;
    }

    public ArrayList<Transaction> getTransactionsInAscending(){
        ArrayList<Transaction> out = new ArrayList<Transaction>();

        for (int loops = 0 ; loops < childTransaction.size(); loops ++){
            if (childTransaction.get(loops) instanceof Transaction){
                out.add((Transaction)childTransaction.get(loops));
            }else {
                out.addAll(((CompoundTransaction) childTransaction.get(loops)).getTransactionsInAscending());
            }
        }

        Collections.sort(out, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction z1, Transaction z2) {
                if (z1.getAmount() > z2.getAmount())
                    return 1;
                if (z1.getAmount() < z2.getAmount())
                    return -1;
                return 0;
            }
        });

        return out;
    }

    public ArrayList<Transaction> getTransactionsInDescending(){
        ArrayList<Transaction> out = getTransactionsInAscending();

        Collections.reverse(out);

        return out;
    }
}


