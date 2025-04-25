package MediatorPattern;

import MediatorPattern.Planes.Aircraft;
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

public class DashBoard extends Application implements TowerDashboard {
    private Label runwayStatusLabel;
    private Label landingQueueLabel;
    private Label takeoffQueueLabel;
    private TextArea logArea;
    private ListView<String> aircraftListView;
    private ObservableList<String> aircraftList = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Air Traffic Control Dashboard (JavaFX)");

        BorderPane root = new BorderPane();
        HBox statusBar = new HBox(10);
        VBox centerPanel = new VBox(10);
        VBox rightPanel = new VBox(10);

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

        root.setTop(statusBar);
        root.setCenter(centerPanel);
        root.setRight(rightPanel);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
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
}