//Implementation of dynamic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2022

//Hans Henrik Lovengreen     Oct 25, 2022

import java.util.Arrays;

class DynamicBarrier extends Barrier {
    private int currentThreshold = 9;
    int arrived = 0;
    boolean active = false;
    boolean released = false;
    boolean[] mayAdvance = { false,false,false,false,false,false,false,false,false };
    boolean[] hasArrived = { false,false,false,false,false,false,false,false,false };

    public DynamicBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {
        if (!active) return;
        released = false;
        arrived++;
        hasArrived[no] = true;
        if(arrived >= currentThreshold) {
            updateAdvancement();
            notifyAll();
            arrived = 0;
        }
        while (!mayAdvance[no]) {
            wait();
        }
        hasArrived[no] = false;
        mayAdvance[no] = false;
        released = true;
    }

    @Override
    public void on() {
        active = true;
        for (int i = 0; i < mayAdvance.length; i++) {
            mayAdvance[i] = false;
            hasArrived[i] = false;
        }
    }

    @Override
    public synchronized void off() {
        active = false;
        arrived = 0;
        updateAdvancement();
        notifyAll();
        released = true;
    }

    @Override
    /* Set barrier threshold */
    public synchronized void set(int k) {
        if(k < 1 || k > 9) return;
        if(k <= currentThreshold && active) {
            if(arrived >= k){
                updateAdvancement();
                notifyAll();
                arrived = 0;
            }
        }else if(active){
            if(!released){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        currentThreshold = k;
    }
    public void updateAdvancement(){
        System.arraycopy(hasArrived, 0, mayAdvance, 0, 9);
    }
}