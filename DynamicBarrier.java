//Implementation of dynamic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2022

//Hans Henrik Lovengreen     Oct 25, 2022

class DynamicBarrier extends Barrier {
    private int currentThreshold = 9;
    int arrived = 0;
    boolean active = false;
    
    public DynamicBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public void sync(int no) throws InterruptedException {
        if (!active) return;
        arrived++;
        synchronized(this) {
            if (arrived < currentThreshold) {
                wait();
            } else {
                arrived = 0;
                notifyAll();
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
        }
    }

    @Override
    /* Set barrier threshold */
    public void set(int k) {
        if(k <= currentThreshold) {
            currentThreshold = k;
            return;
        }
        if(k <= arrived) {
            arrived = 0;
            notifyAll();
        }
        currentThreshold = k;
    }

}
