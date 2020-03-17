package algorithm;

import java.util.ArrayList;

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

    private ArrayList<Long> leftArray = new ArrayList<>();

    public TwentyOne(long amount, int time, long[] leftAmount) {
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
}
