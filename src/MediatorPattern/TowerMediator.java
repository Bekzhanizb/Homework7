package MediatorPattern;

import MediatorPattern.Planes.Aircraft;

public interface TowerMediator {
    void broadcast(String msg, Aircraft sender);
    boolean requestRunway(Aircraft a);
    void releaseRunway(Aircraft a);
}
