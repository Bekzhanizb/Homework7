package MediatorPattern.Planes;

import MediatorPattern.TowerMediator;

public class CargoPlane extends Aircraft {
    public CargoPlane(String id, TowerMediator mediator, int fuelLevel, boolean takingOff) {
        super(id, mediator, fuelLevel, takingOff);
    }

    @Override
    public void receive(String msg) {
        System.out.println("CARGO " + id + ": Received - " + msg);
    }
}
