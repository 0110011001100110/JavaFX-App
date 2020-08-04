package view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StageFactory {

    private static final StageFactory singletonStageFactory = new StageFactory();
    private StageFactory(){}
    public static StageFactory getSingletonStageFactory(){
        return singletonStageFactory;
    }
    private static final ObservableList<Stage> openStages = FXCollections.observableArrayList();

    public static ObservableList<Stage> getOpenStages() {
        return openStages ;
    }

    private static final ObjectProperty<Stage> currentStage = new SimpleObjectProperty<>(null);
    public  final ObjectProperty<Stage> currentStageProperty() {
        return this.currentStage;
    }
    public  final javafx.stage.Stage getCurrentStage() {
        return this.currentStageProperty().get();
    }
    public  final void setCurrentStage(final javafx.stage.Stage currentStage) {
        this.currentStageProperty().set(currentStage);
    }

    public static void registerStage(Stage stage) {
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, e ->
                openStages.add(stage));
        stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, e ->
                openStages.remove(stage));
        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                currentStage.set(stage);
            } else {
                currentStage.set(null);
            }
        });
    }
//
//    public Stage createStage() {
//        Stage stage = new Stage();
//        registerStage(stage);
//        return stage ;
//    }

}
