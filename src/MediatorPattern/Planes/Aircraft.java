package MediatorPattern.Planes;

import MediatorPattern.TowerMediator;

public abstract class Aircraft {
    protected String id;
    protected TowerMediator mediator;
    protected int fuelLevel;
    protected boolean takingOff;

    public Aircraft(String id, TowerMediator mediator, int fuelLevel, boolean takingOff) {
        this.id = id;
        this.mediator = mediator;
        this.fuelLevel = fuelLevel;
        this.takingOff = takingOff;
    }

    public String getId() { return id; }
    public boolean isTakingOff() { return takingOff; }

    public boolean isEmergency() {
        return fuelLevel <= 5;
    }

    public abstract void receive(String msg);

    public void send(String msg) {
        mediator.broadcast(msg, this);
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public void requestRunway() {
        if (mediator.requestRunway(this)) {
            System.out.println(id + ": Runway access granted");
        } else {
            System.out.println(id + ": Added to queue");
        }
    }

    public void releaseRunway() {
        mediator.releaseRunway(this);
    }

    public void notifyRunwayAssigned() {
        System.out.println(id + ": Received runway assignment");
    }

    public void notifyEmergency() {
        System.out.println(id + ": Received emergency notification - aborting operation");
    }

    public void consumeFuel() {
        if (!takingOff && fuelLevel > 0) {
            fuelLevel--;
        }
    }
}

