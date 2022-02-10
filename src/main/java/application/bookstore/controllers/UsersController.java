package application.bookstore.controllers;

import application.bookstore.models.Role;
import application.bookstore.models.User;
import application.bookstore.views.UsersView;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;

public class UsersController {
    private final UsersView usersView;

    public UsersController(UsersView usersView) {
        this.usersView = usersView;
        setSaveListener();
        setDeleteListener();
        setEditListener();
        setSearchListener();
    }

    private void setEditListener() {
        usersView.getUsernameCol().setOnEditCommit(e -> {
            User userToEdit = e.getRowValue();
            String oldVal = userToEdit.getUsername();
            userToEdit.setUsername(e.getNewValue());
            if (!userToEdit.usernameExists()) {
                if (userToEdit.isValid().matches("1")) {
                    userToEdit.updateFile();
                } else {
                    ControllerCommon.showErrorMessage(usersView.getMessageLabel(), "Edit value invalid!\n" + userToEdit.isValid());
                    userToEdit.setUsername(oldVal);
                    usersView.getTableView().getItems().set(usersView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                }
            } else {
                ControllerCommon.showErrorMessage(usersView.getMessageLabel(), "Username Exists!");
                userToEdit.setUsername(oldVal);
                usersView.getTableView().getItems().set(usersView.getTableView().getItems().indexOf(userToEdit), userToEdit);
            }
        });

        usersView.getPasswordCol().setOnEditCommit(e -> {
            User userToEdit = e.getRowValue();
            String oldVal = userToEdit.getPassword();
            userToEdit.setPassword(e.getNewValue());
            if (userToEdit.isValid().matches("1")) {
                userToEdit.updateFile();
            } else {
                ControllerCommon.showErrorMessage(usersView.getMessageLabel(), "Edit value invalid!\n" + userToEdit.isValid());
                userToEdit.setPassword(oldVal);
                usersView.getTableView().getItems().set(usersView.getTableView().getItems().indexOf(userToEdit), userToEdit);
            }
        });

        usersView.getRoleCol().setOnEditCommit(e -> {
            User userToEdit = e.getRowValue();
            Role oldVal = userToEdit.getRole();
            userToEdit.setRole(e.getNewValue());
            if (userToEdit.isValid().matches("1")) {
                userToEdit.updateFile();
            } else {
                ControllerCommon.showErrorMessage(usersView.getMessageLabel(), "Edit value invalid!\n" + userToEdit.isValid());
                userToEdit.setRole(oldVal);
                usersView.getTableView().getItems().set(usersView.getTableView().getItems().indexOf(userToEdit), userToEdit);
            }
        });
    }

    private void setSearchListener() {
        usersView.getSearchView().getClearBtn().setOnAction(e -> {
            usersView.getSearchView().getSearchField().setText("");
            usersView.getTableView().setItems(FXCollections.observableArrayList(User.getUsers()));
        });
        usersView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = usersView.getSearchView().getSearchField().getText();
            ArrayList<User> searchResults = User.getSearchResults(searchText);
            usersView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }

    private void setDeleteListener() {
        usersView.getDeleteBtn().setOnAction(e -> {
            List<User> itemsToDelete = List.copyOf(usersView.getTableView().getSelectionModel().getSelectedItems());
            for (User a : itemsToDelete) {
                if (a.deleteFromFile()) {
                    usersView.getTableView().getItems().remove(a);
                    ControllerCommon.showSuccessMessage(usersView.getMessageLabel(), "User removed successfully");
                } else {
                    ControllerCommon.showErrorMessage(usersView.getMessageLabel(), "User deletion failed");
                    break;
                }
            }
        });
    }

    private void setSaveListener() {
        usersView.getSaveBtn().setOnAction(e -> {
            String username = usersView.getUserNameField().getText();
            String password = usersView.getPasswordField().getText();
            Role role = usersView.getRoleComboBox().getValue();
            User user = new User(username, password, role);
            if (!user.usernameExists()) {
                String saveResult = user.saveInFile();
                if (saveResult.matches("1")) {
                    usersView.getTableView().getItems().add(user);
                    ControllerCommon.showSuccessMessage(usersView.getMessageLabel(), "User created successfully!");
                    usersView.getUsernameCol().setText("");
                    usersView.getPasswordField().setText("");
                } else {
                    ControllerCommon.showErrorMessage(usersView.getMessageLabel(), "User creation failed!\n" + saveResult);

                }
            } else {
                ControllerCommon.showErrorMessage(usersView.getMessageLabel(), "User with this username exists.");
            }
        });
    }

}
