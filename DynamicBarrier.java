//Implementation of dynamic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2022

//Hans Henrik Lovengreen     Oct 25, 2022

class DynamicBarrier extends Barrier {
    private int currentThreshold = 9;
    int arrived = 0;
    boolean active = false;
    boolean released = false;

    public DynamicBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public void sync(int no) throws InterruptedException {
        if (!active) return;
        synchronized(this) {
            released = false;
            arrived++;
            if (arrived < currentThreshold) {
                wait();
            } else {
                arrived = 0;
                notifyAll();
                released = true;
            }
        }
    }

    @Override
    public void on() {
        active = true;
    }

    @Override
    public void off() {
        active = false;
        arrived = 0;
        synchronized(this) {
            notifyAll();
            released = true;
        }
    }

    @Override
    /* Set barrier threshold */
    public void set(int k) {
        if(k < 1 || k > 9) return;
        if(k <= currentThreshold && active) {
            if(arrived >= k){
                synchronized (this) {
                    notifyAll();
                }
                arrived = 0;
            }
        }else if(active){
            //while(!released);
            if(!released){
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //while(!released) System.out.print("");
        }
        currentThreshold = k;
    }
}