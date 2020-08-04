package view;

import controller.DatePickerTableCell;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.time.LocalDate;

import static view.StageFactory.getSingletonStageFactory;

public class UsersTableUI {

    private TableView<User> usersTable = new TableView<User>();

    private TableColumn<User, String> firstNameColumn = new TableColumn<>("First Name");

    private TableColumn<User, String> lastNameColumn = new TableColumn<>("Last Name");

    private TableColumn<User, String> userNameColumn = new TableColumn<>("User Name");

    private TableColumn<User, String> passwordColumn = new TableColumn<>("Password");

    private TableColumn<User, LocalDate> birthdayColumn = new TableColumn<>("Birthday");

    private TableColumn<User, String> genderColumn = new TableColumn<>("Gender");

    private Button deleteButton = new Button("Delete Record");

    public TableView<User> getUsersTable() {
        return usersTable;
    }

    public TableColumn<User, String> getFirstNameColumn() {
        return firstNameColumn;
    }

    public TableColumn<User, String> getLastNameColumn() {
        return lastNameColumn;
    }

    public TableColumn<User, String> getUserNameColumn() {
        return userNameColumn;
    }

    public TableColumn<User, String> getPasswordColumn() {
        return passwordColumn;
    }

    public TableColumn<User, LocalDate> getBirthdayColumn() {
        return birthdayColumn;
    }

    public TableColumn<User, String> getGenderColumn() {
        return genderColumn;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

//*************************************************************************************
    public void showUserTableScene(){

        VBox vBox = new VBox();
        Scene scene = new Scene(vBox ,800 ,500);

        ObservableList<Stage> openStages = getSingletonStageFactory().getOpenStages();
        Stage stage;

        if(openStages.isEmpty()) {
            stage = new Stage();
            getSingletonStageFactory().registerStage(stage);

            stage.setTitle("J2SE Application");
            stage.setWidth(800);
            stage.setHeight(500);

        }
        else
            stage = openStages.get(0); //Previous stage


        // ======================= Text Node =================
        InnerShadow is = new InnerShadow();
        is.setOffsetX(2.0f);
        is.setOffsetY(2.0f);
        Text text = new Text();
        text.setEffect(is);
        text.setText("Users Informations Table");
        text.setFill(Color.BLUE);
        text.setFont(Font.font(null, FontWeight.BOLD, 30));
        // ======================= Text Node =====================================


        // ======================= User Table Node =====================================
        usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);//Delete last empty column
        vBox.setVgrow(usersTable, Priority.ALWAYS);//Maximise with other nodes
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);
        usersTable.setStyle("-fx-alignment: CENTER;");
        firstNameColumn.setStyle("-fx-alignment: CENTER;");
        lastNameColumn.setStyle("-fx-alignment: CENTER;");
        userNameColumn.setStyle("-fx-alignment: CENTER;");
        passwordColumn.setStyle("-fx-alignment: CENTER;");
        genderColumn.setStyle("-fx-alignment: CENTER;");
        birthdayColumn.setStyle("-fx-alignment: CENTER;");

        usersTable.getColumns().addAll(firstNameColumn, lastNameColumn, userNameColumn,
                                       passwordColumn, genderColumn, birthdayColumn);

        makeTableEditable();
        generateFirstNameColumnTextField();
        generateLastNameColumnTextField();
        generateUserNameColumnTextField();
        generatePasswordColumnTextField();
        generateGenderColumnComboBox();
        generateBirthdayColumnTextField();
        // ======================= User Table Node =====================================

        ((VBox) scene.getRoot()).getChildren().addAll(text ,usersTable , deleteButton);

        stage.setScene(scene);
        stage.show();
    }
//*************************************************************************************
    public void makeTableEditable(){

        usersTable.setEditable(true);
    }
//*************************************************************************************
    public void generateFirstNameColumnTextField(){

        //Generate fist name text field and make it editable
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }
//*************************************************************************************
    public void generateLastNameColumnTextField(){

        //Generate last name text field and make it editable
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }
//*************************************************************************************
    public void generateUserNameColumnTextField(){

        //Generate user name text field and make it editable
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userNameColumn.setMinWidth(100);
        userNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }
//*************************************************************************************
    public void generatePasswordColumnTextField(){

        //Generate password text field and make it editable
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setMinWidth(100);
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }
//*************************************************************************************
    public void generateGenderColumnComboBox(){

        //Generate gender combo box and make it editable
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setMinWidth(100);
        genderColumn.setCellFactory(ComboBoxTableCell.forTableColumn("female", "male"));
    }
//*************************************************************************************
    public void generateBirthdayColumnTextField(){

        //Generate birthday window field and make it editable
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        birthdayColumn.setMinWidth(100);
        birthdayColumn.setCellFactory(DatePickerTableCell.forTableColumn());
    }
}
