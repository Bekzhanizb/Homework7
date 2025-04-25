package MediatorPattern.Planes;

import MediatorPattern.TowerMediator;

public class Helicopter extends Aircraft {
    public Helicopter(String id, TowerMediator mediator, int fuelLevel, boolean takingOff) {
        super(id, mediator, fuelLevel, takingOff);
    }

    @Override
    public void receive(String msg) {
        System.out.println("HELICOPTER " + id + ": Received - " + msg);
    }
}
