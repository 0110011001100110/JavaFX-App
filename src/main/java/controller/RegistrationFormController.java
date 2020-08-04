package controller;

import model.User;
import model.UserDBManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;
import view.AlertHelper;
import view.UsersTableUI;
import java.io.IOException;

public class RegistrationFormController {

    //field injection
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private RadioButton male;

    @FXML
    private RadioButton female;

    @FXML
    private Button submitButton;

    private UsersTableUI userTableUI = new UsersTableUI();
    private UserDBManager userDBManager = new UserDBManager();
    private ToggleGroup group = new ToggleGroup();
    private User newUser = new User();
    private String  gender;

//**************************************************************************************
    //Sign UP
    public void insert(User user){

        newUser.setFirstName(firstNameField.getText());
        newUser.setLastName(lastNameField.getText());
        newUser.setUserName(userNameField.getText());
        newUser.setPassword(passwordField.getText());
        newUser.setGender(gender);
        newUser.setDateOfBirth(dateOfBirth.getValue());

        userDBManager.insert(newUser);//Insert to db
    }
//**************************************************************************************
    @FXML
    private void onRadioButtonClicked(ActionEvent event) {
        male.setToggleGroup(group);
        female.setToggleGroup(group);
        if(group.getSelectedToggle() == male)
            gender = "male";
        else
            gender = "female";
    }
//*********************************************************************************************
    private boolean isPasswordStrong(String password){
        return password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])[A-Za-z0-9]{8,}$");
    }
//**************************************************************************************
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        Window owner = submitButton.getScene().getWindow();
        if(firstNameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your first name");
            return;
        }
        if(lastNameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your last name");
            return;
        }
        if(userNameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your user name");
            return;
        }
        if(userDBManager.existUserByUserName(userNameField.getText())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "This user name has been already existed! Please try another");
            return;
        }
        if(passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }
        if((passwordField.getText()).length() < 8 ) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Password must be at least 8 character");
            return;
        }
        if(!(isPasswordStrong(passwordField.getText()))) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Weak password! It must contain at least one upper case letter and at least one lower case letter and at least one number");
            return;
        }
        if(dateOfBirth.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your birthday");
            return;
        }
        if(group.getSelectedToggle() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please select your gender");
            return;
        }

        insert(newUser); //Insert new user into db and sign up

        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
                "Welcome " + userNameField.getText());

        UsersTablePageController usersTablePageController = new UsersTablePageController();
        usersTablePageController.initialize();
    }
}
