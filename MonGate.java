//Monitor implementation of Gate (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2022

//Hans Henrik Lovengreen     Oct 25, 2022


public class MonGate extends Gate {

    boolean isOpen = false;

    public synchronized void pass() throws InterruptedException {
        while(!isOpen) wait();
    }

    public synchronized void open() {
        if(!isOpen) isOpen = true;
        notifyAll();
    }

    public synchronized void close() {
        if(isOpen) isOpen = false;
    }
}
