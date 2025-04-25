package MediatorPattern;

import MediatorPattern.Planes.Aircraft;

import java.util.List;

public interface TowerDashboard {
    void update(String runwayStatus, int landingQueueSize, int takeoffQueueSize, boolean emergency);
    void addLogEntry(String message);
    void updateAircraftList(List<Aircraft> aircraft);
}
