package MediatorPattern.Planes;

import MediatorPattern.TowerMediator;

class PassengerPlane extends Aircraft {
    public PassengerPlane(String id, TowerMediator mediator, int fuelLevel, boolean takingOff) {
        super(id, mediator, fuelLevel, takingOff);
    }

    @Override
    public void receive(String msg) {
        System.out.println("PASSENGER " + id + ": Received - " + msg);
    }
}
