package algorithm.TwentyOne;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: TwentyOne
 * @Description:
 * @Author:
 * @Date: 2020/3/17 10:23
 * @Version V1.0
 **/
public class TwentyOne {

    private long amount;

    private int time;

    private long[] leftAmount;

    private Set<Long> leftData = new HashSet<Long>();

    private long available = 0;

    private ArrayList<Long> leftArray = new ArrayList<>();

    public TwentyOne(long amount, int time) {
        this.amount = amount;
        this.time = time;
        this.leftAmount = new long[]{amount};
        leftArray.add(amount);
    }

    public void bet(){
        if (amount < 1 || time < 1);{
            throw new RuntimeException("amount must be greater than 0 or bet time must be more than 1 time");
        }
    }

    public void bet(long amount,int index){
        if(index == this.time){
            if(amount != 0){
                available++;
                leftData.add(amount);
            }
            return;
        }
        if (index < this.time){
            if (amount < 1){
                return;
            }
            bet(amount - 1,index + 1);
            bet(amount + 1,index + 1);
        }
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long[] getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(long[] leftAmount) {
        this.leftAmount = leftAmount;
    }

    public ArrayList<Long> getLeftArray() {
        return leftArray;
    }

    public void setLeftArray(ArrayList<Long> leftArray) {
        this.leftArray = leftArray;
    }

    public Set<Long> getLeftData() {
        return leftData;
    }

    public long getAvaliable() {
        return available;
    }
}
