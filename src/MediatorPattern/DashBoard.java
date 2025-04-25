package MediatorPattern;

import MediatorPattern.Planes.Aircraft;
import MediatorPattern.Planes.CargoPlane;
import MediatorPattern.Planes.Helicopter;
import MediatorPattern.Planes.PassengerPlane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;
import java.util.Random;

public class DashBoard extends Application implements TowerDashboard {
    private Label runwayStatusLabel;
    private Label landingQueueLabel;
    private Label takeoffQueueLabel;
    private TextArea logArea;
    private ListView<String> aircraftListView;
    private ObservableList<String> aircraftList = FXCollections.observableArrayList();
    private ControlTower controlTower;
    private ComboBox<String> aircraftTypeCombo;
    private TextField aircraftIdField;
    private TextField fuelLevelField;
    private ComboBox<String> operationTypeCombo;
    private ComboBox<Aircraft> aircraftSelector;
    private TextField messageField;
    private ObservableList<Aircraft> aircraftObservableList = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        controlTower = new ControlTower();
        controlTower.setDashboard(this);

        primaryStage.setTitle("Air Traffic Control Dashboard (JavaFX)");

        BorderPane root = new BorderPane();
        HBox statusBar = new HBox(10);
        VBox centerPanel = new VBox(10);
        VBox rightPanel = new VBox(10);
        VBox leftPanel = new VBox(10);

        runwayStatusLabel = createStatusLabel("Runway: Available", Color.LIGHTGREEN);
        landingQueueLabel = createStatusLabel("Landing Queue: 0", Color.LIGHTGRAY);
        takeoffQueueLabel = createStatusLabel("Takeoff Queue: 0", Color.LIGHTGRAY);

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);

        aircraftListView = new ListView<>(aircraftList);
        aircraftListView.setPrefWidth(250);

        Label aircraftTitle = new Label("Registered Aircraft:");
        aircraftTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        Label controlTitle = new Label("Aircraft Control:");
        controlTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        Label createLabel = new Label("Create New Aircraft:");
        aircraftTypeCombo = new ComboBox<>(FXCollections.observableArrayList(
                "Passenger Plane", "Cargo Plane", "Helicopter"));
        aircraftTypeCombo.getSelectionModel().selectFirst();

        aircraftIdField = new TextField();
        aircraftIdField.setPromptText("Aircraft ID");
        aircraftIdField.setText("FL-" + new Random().nextInt(1000));

        fuelLevelField = new TextField();
        fuelLevelField.setPromptText("Fuel Level");
        fuelLevelField.setText("20");

        operationTypeCombo = new ComboBox<>(FXCollections.observableArrayList(
                "Landing", "Takeoff"));
        operationTypeCombo.getSelectionModel().selectFirst();

        Button createButton = new Button("Create Aircraft");
        createButton.setOnAction(e -> createAircraft());

        Label controlLabel = new Label("Control Aircraft:");
        aircraftSelector = new ComboBox<>(aircraftObservableList);
        aircraftSelector.setCellFactory(param -> new ListCell<Aircraft>() {
            @Override
            protected void updateItem(Aircraft item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getId() + " (" + (item.isTakingOff() ? "Takeoff" : "Landing") + ")");
                }
            }
        });
        aircraftSelector.setButtonCell(new ListCell<Aircraft>() {
            @Override
            protected void updateItem(Aircraft item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getId() + " (" + (item.isTakingOff() ? "Takeoff" : "Landing") + ")");
                }
            }
        });

        Button requestRunwayButton = new Button("Request Runway");
        requestRunwayButton.setOnAction(e -> requestRunway());

        Button releaseRunwayButton = new Button("Release Runway");
        releaseRunwayButton.setOnAction(e -> releaseRunway());

        Button consumeFuelButton = new Button("Consume Fuel");
        consumeFuelButton.setOnAction(e -> consumeFuel());

        Button maydayButton = new Button("MAYDAY!");
        maydayButton.setStyle("-fx-base: #ff5555;");
        maydayButton.setOnAction(e -> sendMayday());

        messageField = new TextField();
        messageField.setPromptText("Message to broadcast");

        Button sendMessageButton = new Button("Send Message");
        sendMessageButton.setOnAction(e -> sendMessage());

        VBox createBox = new VBox(5, createLabel, aircraftTypeCombo, aircraftIdField,
                fuelLevelField, operationTypeCombo, createButton);
        createBox.setPadding(new Insets(5));
        createBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1;");

        VBox controlBox = new VBox(5, controlLabel, aircraftSelector, requestRunwayButton,
                releaseRunwayButton, consumeFuelButton, maydayButton, messageField, sendMessageButton);
        controlBox.setPadding(new Insets(5));
        controlBox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1;");

        leftPanel.getChildren().addAll(controlTitle, createBox, controlBox);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setSpacing(10);
        leftPanel.setStyle("-fx-background-color: #f8f8f8;");

        statusBar.getChildren().addAll(landingQueueLabel, runwayStatusLabel, takeoffQueueLabel);
        statusBar.setPadding(new Insets(10));
        statusBar.setStyle("-fx-background-color: #f0f0f0;");

        centerPanel.getChildren().addAll(
                new Label("Communication Log:"),
                new ScrollPane(logArea)
        );
        centerPanel.setPadding(new Insets(10));

        rightPanel.getChildren().addAll(aircraftTitle, aircraftListView);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setStyle("-fx-background-color: #f8f8f8;");

        root.setLeft(leftPanel);
        root.setTop(statusBar);
        root.setCenter(centerPanel);
        root.setRight(rightPanel);

        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createAircraft() {
        try {
            String type = aircraftTypeCombo.getValue();
            String id = aircraftIdField.getText();
            int fuel = Integer.parseInt(fuelLevelField.getText());
            boolean takingOff = operationTypeCombo.getValue().equals("Takeoff");

            Aircraft aircraft;
            switch (type) {
                case "Passenger Plane":
                    aircraft = new PassengerPlane(id, controlTower, fuel, takingOff);
                    break;
                case "Cargo Plane":
                    aircraft = new CargoPlane(id, controlTower, fuel, takingOff);
                    break;
                case "Helicopter":
                    aircraft = new Helicopter(id, controlTower, fuel, takingOff);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown aircraft type");
            }

            controlTower.register(aircraft);
            aircraftObservableList.add(aircraft);
            updateAircraftList(controlTower.getAllAircraft());
            logArea.appendText("Created new " + type + ": " + id + "\n");
        } catch (NumberFormatException e) {
            logArea.appendText("Error: Invalid fuel level\n");
        } catch (Exception e) {
            logArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    private void requestRunway() {
        Aircraft aircraft = aircraftSelector.getValue();
        if (aircraft != null) {
            aircraft.requestRunway();
        } else {
            logArea.appendText("Error: No aircraft selected\n");
        }
    }

    private void releaseRunway() {
        Aircraft aircraft = aircraftSelector.getValue();
        if (aircraft != null) {
            aircraft.releaseRunway();
        } else {
            logArea.appendText("Error: No aircraft selected\n");
        }
    }

    private void consumeFuel() {
        Aircraft aircraft = aircraftSelector.getValue();
        if (aircraft != null) {
            aircraft.consumeFuel();
            updateAircraftList(controlTower.getAllAircraft());
            logArea.appendText(aircraft.getId() + " consumed fuel. Remaining: " + aircraft.getFuelLevel() + "\n");
        } else {
            logArea.appendText("Error: No aircraft selected\n");
        }
    }

    private void sendMayday() {
        Aircraft aircraft = aircraftSelector.getValue();
        if (aircraft != null) {
            aircraft.send("MAYDAY");
        } else {
            logArea.appendText("Error: No aircraft selected\n");
        }
    }

    private void sendMessage() {
        Aircraft aircraft = aircraftSelector.getValue();
        String message = messageField.getText();
        if (aircraft != null && !message.isEmpty()) {
            aircraft.send(message);
            messageField.clear();
        } else if (message.isEmpty()) {
            logArea.appendText("Error: Message is empty\n");
        } else {
            logArea.appendText("Error: No aircraft selected\n");
        }
    }

    private Label createStatusLabel(String text, Color bgColor) {
        Label label = new Label(text);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        label.setPadding(new Insets(10));
        label.setBorder(new Border(new BorderStroke(
                Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        label.setBackground(new Background(new BackgroundFill(
                bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
        HBox.setHgrow(label, Priority.ALWAYS);
        return label;
    }

    @Override
    public void update(String runwayStatus, int landingQueueSize, int takeoffQueueSize, boolean emergency) {
        Platform.runLater(() -> {
            runwayStatusLabel.setText("Runway: " + runwayStatus);

            Color statusColor = emergency ? Color.INDIANRED :
                    runwayStatus.equals("Available") ? Color.LIGHTGREEN : Color.KHAKI;

            runwayStatusLabel.setBackground(new Background(new BackgroundFill(
                    statusColor, CornerRadii.EMPTY, Insets.EMPTY)));

            landingQueueLabel.setText("Landing Queue: " + landingQueueSize);
            takeoffQueueLabel.setText("Takeoff Queue: " + takeoffQueueSize);
        });
    }

    @Override
    public void addLogEntry(String message) {
        Platform.runLater(() -> {
            logArea.appendText(message + "\n");
        });
    }

    @Override
    public void updateAircraftList(List<Aircraft> aircraft) {
        Platform.runLater(() -> {
            aircraftList.clear();
            aircraft.stream()
                    .map(a -> String.format("%s\nFuel: %d | %s | %s",
                            a.getId(),
                            a.getFuelLevel(),
                            a.isEmergency() ? "EMERGENCY" : "Normal",
                            a.isTakingOff() ? "Taking Off" : "Landing"))
                    .forEach(aircraftList::add);
        });
    }

    private List<Aircraft> getAllAircraft() {
        return controlTower.getAllAircraft();
    }
}