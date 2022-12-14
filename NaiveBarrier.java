//Naive implementation of Barrier class
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2022

//Hans Henrik Lovengreen     Oct 25, 2022

class NaiveBarrier extends Barrier {
    
    int arrived = 0;
    boolean active = false;
   
    public NaiveBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public void sync(int no) throws InterruptedException {
        if (!active) return;
        arrived++;
        //Thread.sleep(5000);
        //if(no == 5) Thread.sleep(100);
        synchronized(this) {
            //arrived++;
            System.out.println("arrived = "+ arrived + " no = " + no);
            if (arrived < 9) {//arrived might not reach 9 because of arrived++ in 1.
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
    public void off()  {
        active = false;
        arrived = 0;
        synchronized(this) {
            notifyAll();
        }
    }


    @Override
    // May be (ab)used for robustness testing
    public void set(int k) { //4.
        synchronized (this){
            notify();
        }
    }
}
