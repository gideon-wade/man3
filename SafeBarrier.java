//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2022

//Hans Henrik Lovengreen     Oct 25, 2022

class SafeBarrier extends Barrier {

    int arrived = 0;
    boolean active = false;
    //boolean allAdvanced = false; // Everyone has passed.
    boolean mayAdvance[] = { false,false,false,false,false,false,false,false,false };


    public SafeBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {
        if (!active) return;
        arrived++;
        if(arrived>8) { for(int i = 0; i < 9; i++) { mayAdvance[i] = true; }; notifyAll(); }
        while (!mayAdvance[no]) {
            wait();
        }
        arrived--;
        mayAdvance[no] = false;
    }

    @Override
    public void on() {
        active = true;
    }

    @Override
    public synchronized void off()  {
        active = false;
        arrived = 0;
        notifyAll();
    }


    @Override
    public void set(int k) {
        synchronized (this){
            notify();
        }
    }
}