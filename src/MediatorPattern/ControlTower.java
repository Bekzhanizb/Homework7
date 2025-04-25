package MediatorPattern;

import MediatorPattern.Planes.Aircraft;

import java.util.*;

public class ControlTower implements TowerMediator {
    private final Queue<Aircraft> landingQueue = new PriorityQueue<>(
            Comparator.comparing(Aircraft::isEmergency).reversed()
    );
    private final Queue<Aircraft> takeoffQueue = new LinkedList<>();
    private final List<Aircraft> allAircraft = new ArrayList<>();
    private Aircraft currentRunwayUser = null;
    private boolean emergencyState = false;

    private TowerDashboard dashboard;

    public void register(Aircraft a) {
        allAircraft.add(a);
        updateDashboard();
    }

    @Override
    public void broadcast(String msg, Aircraft sender) {
        logMessage("📡 " + sender.getId() + " broadcasts: " + msg);

        if (msg.equalsIgnoreCase("MAYDAY")) {
            handleEmergency(sender);
        } else {
            allAircraft.stream()
                    .filter(a -> !a.equals(sender))
                    .forEach(a -> a.receive(msg));
        }
    }

    @Override
    public boolean requestRunway(Aircraft a) {
        if (emergencyState) return false;

        if (a.isEmergency()) {
            handleEmergency(a);
            return true;
        }

        if (currentRunwayUser != null) {
            logMessage("🟥 Runway busy. " + a.getId() + " queued.");
            (a.isTakingOff() ? takeoffQueue : landingQueue).add(a);
            updateDashboard();
            return false;
        } else {
            assignRunway(a);
            return true;
        }
    }

    public void releaseRunway(Aircraft a) {
        if (currentRunwayUser == a) {
            logMessage("🟦 " + a.getId() + " cleared runway");
            currentRunwayUser = null;
            assignNextAircraft();
        }
    }

    private void assignRunway(Aircraft a) {
        currentRunwayUser = a;
        logMessage("🟩 Runway granted to " + a.getId());
        a.notifyRunwayAssigned();
        updateDashboard();
    }

    private void assignNextAircraft() {
        if (!emergencyState) {
            Aircraft next = !landingQueue.isEmpty() ? landingQueue.poll() : takeoffQueue.poll();
            if (next != null) {
                assignRunway(next);
            }
        }
        updateDashboard();
    }

    private void handleEmergency(Aircraft emergencyAircraft) {
        emergencyState = true;
        logMessage("🚨 EMERGENCY! " + emergencyAircraft.getId() + " needs immediate landing!");

        if (currentRunwayUser != null) {
            currentRunwayUser.notifyEmergency();
            currentRunwayUser = null;
        }

        landingQueue.remove(emergencyAircraft);
        landingQueue.add(emergencyAircraft);

        allAircraft.stream()
                .filter(a -> !a.equals(emergencyAircraft))
                .forEach(a -> a.receive("⚠️ HOLD POSITION! Emergency in progress."));

        emergencyState = false;
        assignNextAircraft();
    }

    public void setDashboard(TowerDashboard dashboard) {
        this.dashboard = dashboard;
    }

    private void updateDashboard() {
        if (dashboard != null) {
            dashboard.update(
                    currentRunwayUser != null ? currentRunwayUser.getId() : "Available",
                    landingQueue.size(),
                    takeoffQueue.size(),
                    emergencyState
            );
        }
    }

    private void logMessage(String message) {
        System.out.println(message);
        if (dashboard != null) {
            dashboard.addLogEntry(message);
        }
    }
    public List<Aircraft> getAllAircraft() {
        return Collections.unmodifiableList(allAircraft);
    }
}