package thread.ThreadPool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ShaoJiale
 * @create 2019/10/22
 * @function 模拟火箭发射倒计时
 */
public class Rocket implements Runnable{
    private final int RocketID;

    public Rocket(int rocketID) {
        this.RocketID = rocketID;
    }

    public void run(){
        for(int i = 10; i > 0; i--)
            System.out.println("Rocket[" + RocketID + "] -- counting " + i);
        System.out.println("Rocket[" + RocketID + "] finished!");
    }
}
