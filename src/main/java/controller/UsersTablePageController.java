package controller;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import model.User;
import model.UserDBManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Window;
import view.AlertHelper;
import view.UsersTableUI;
import java.time.LocalDate;
import java.util.List;

public class UsersTablePageController  /*implements Initializable*/ {

    private UserDBManager userDBManager = new UserDBManager();
    private UsersTableUI usersTableUI = new UsersTableUI();


    //************************************************************************************
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
      public void initialize(){ //Invoked in RegistrationFormController
        fillUserTable();
        usersTableUI.showUserTableScene();

        //Register setOnEditCommit actions
        firstNameColumnSetOnEditCommit();
        lastNameColumnSetOnEditCommit();
        userNameColumnSetOnEditCommit();
        passwordColumnSetOnEditCommit();
        genderColumnSetOnEditCommit();
        birthdayColumnSetOnEditCommit();

        deleteButtonSetOnAction();
    }
//************************************************************************************
    public void fillUserTable() {
        List<User> users = userDBManager.getAllUsers();
        for (User u : users)
            usersTableUI.getUsersTable().getItems().add(u);
    }
//*************************************************************************************
    public void firstNameColumnSetOnEditCommit() {
        usersTableUI.getFirstNameColumn().setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String>>() {
                                                              @Override
                                                              public void handle(TableColumn.CellEditEvent<User, String> t) {
                                                                  User user = t.getRowValue();
                                                                  user.setFirstName(t.getNewValue());
                                                                  userDBManager.update(user);//Edit user in db
                                                              }
                                                          }
        );
    }
//*************************************************************************************
    public void lastNameColumnSetOnEditCommit() {
        usersTableUI.getLastNameColumn().setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String>>() {
                                                             @Override
                                                             public void handle(TableColumn.CellEditEvent<User, String> t) {
                                                                 User user = t.getRowValue();
                                                                 user.setLastName(t.getNewValue());
                                                                 userDBManager.update(user);//Edit user in db
                                                             }
                                                         }
        );
    }
//*************************************************************************************
    public void userNameColumnSetOnEditCommit() {

        Window owner = usersTableUI.getDeleteButton().getScene().getWindow();

        usersTableUI.getUserNameColumn().setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String>>() {
                                                             @Override
                                                             public void handle(TableColumn.CellEditEvent<User, String> t) {
                                                                 User user = t.getRowValue();

                                                                 // ========= Check new user name uniqueness ========
                                                                 if (userDBManager.existUserByUserName(t.getNewValue())) {
                                                                     AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                                                                             "This user name has been already existed! Please try another");
                                                                     return;
                                                                 }
                                                                 // ===================================================
                                                                 else {
                                                                     user.setUserName(t.getNewValue());
                                                                     userDBManager.update(user);//Edit user in db
                                                                 }
                                                             }
                                                         }
        );
    }
//*********************************************************************************************
    private boolean isPasswordStrong(String password) {
        return password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])[A-Za-z0-9]{8,}$");
    }
//*************************************************************************************
    public void passwordColumnSetOnEditCommit() {

        Window owner = usersTableUI.getDeleteButton().getScene().getWindow();

        usersTableUI.getPasswordColumn().setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String>>() {
                                                             @Override
                                                             public void handle(TableColumn.CellEditEvent<User, String> t) {
                                                                 User user = t.getRowValue();

                                                                 //=========== Check new password validation ===========
                                                                 if (t.getNewValue().length() < 8) {
                                                                     AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                                                                             "Password must be at least 8 character");
                                                                     return;
                                                                 }
                                                                 if (!(isPasswordStrong(t.getNewValue()))) {
                                                                     AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                                                                             "Weak password! It must contain at least one upper case letter and at least one lower case letter and at least one number");
                                                                     return;
                                                                 }
                                                                 // ===================================================
                                                                 user.setPassword(t.getNewValue());
                                                                 userDBManager.update(user);//Edit user in db
                                                             }
                                                         }
        );
    }
//*************************************************************************************
    public void genderColumnSetOnEditCommit() {
        usersTableUI.getGenderColumn().setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String >>() {
                                                        @Override
                                                        public void handle(TableColumn.CellEditEvent<User, String > t) {

                                                            // new value coming from combobox
                                                            String newGender = t.getNewValue();

                                                            // index of editing user in the tableview
                                                            int index = t.getTablePosition().getRow();

                                                            // user currently being edited
                                                            User user = (User) t.getTableView().getItems().get(index);

                                                            user.setGender(newGender);
                                                            userDBManager.update(user);//Edit user in db
                                                        }
        });
    }
//*************************************************************************************
    public void birthdayColumnSetOnEditCommit(){
        usersTableUI.getBirthdayColumn().setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, LocalDate>>() {
                                                        @Override
                                                        public void handle(TableColumn.CellEditEvent<User, LocalDate> t) {
                                                            User user = t.getRowValue();
                                                            user.setDateOfBirth(t.getNewValue());
                                                            userDBManager.update(user);//Edit user in db
                                                                }
                                                            }
                                                    );
    }
//*************************************************************************************
    public void deleteButtonSetOnAction() {
        usersTableUI.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
                                                    @Override
                                                    public void handle(ActionEvent e) {
                                                        User selectedItem = usersTableUI.getUsersTable().getSelectionModel().getSelectedItem();
                                                        usersTableUI.getUsersTable().getItems().remove(selectedItem);
                                                        userDBManager.deleteByUserName(selectedItem.getUserName()); //also delete from db
                                                    }
                                                });
    }
}
