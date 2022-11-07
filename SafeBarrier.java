//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2022

//Hans Henrik Lovengreen     Oct 25, 2022

class SafeBarrier extends Barrier {

    int arrived = 0;
    boolean active = false;
    boolean allAdvanced = false; // Everyone has passed.


    public SafeBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public void sync(int no) throws InterruptedException {
        if (!active) return;
        synchronized(this) {
            arrived++;
            while (arrived < 9) {
                wait();
                if(allAdvanced) break;
            }
            allAdvanced = true;
            arrived--;
            if(arrived == 0) {
                allAdvanced = false;
            }
            notifyAll();
        }
    }

    @Override
    public void on() {
        active = true;
    }

    @Override
    public void off()  {
        active = false;
        arrived = 0;
        synchronized(this) {
            notifyAll();
        }
    }


    @Override
    public void set(int k) {
        synchronized (this){
            notify();
        }
    }
}