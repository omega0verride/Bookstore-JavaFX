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
                if (userToEdit.isValid()) {
                    userToEdit.updateFile();
                } else {
                    System.out.println("Edit value invalid! " + e.getNewValue());
                    userToEdit.setUsername(oldVal);
                    usersView.getTableView().getItems().set(usersView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                    ControllerCommon.showErrorMessage(usersView.getResultLabel(), "Edit value invalid!");
                }
            } else {
                userToEdit.setUsername(oldVal);
                usersView.getTableView().getItems().set(usersView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                ControllerCommon.showErrorMessage(usersView.getResultLabel(), "Username Exists!");
            }
        });

        usersView.getPasswordCol().setOnEditCommit(e -> {
            User userToEdit = e.getRowValue();
            String oldVal = userToEdit.getPassword();
            userToEdit.setPassword(e.getNewValue());
            if (userToEdit.isValid()) {
                userToEdit.updateFile();
            } else {
                userToEdit.setPassword(oldVal);
                usersView.getTableView().getItems().set(usersView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                ControllerCommon.showErrorMessage(usersView.getResultLabel(), "Edit value invalid!");
            }
        });

        usersView.getRoleCol().setOnEditCommit(e -> {
            User userToEdit = e.getRowValue();
            Role oldVal = userToEdit.getRole();
            userToEdit.setRole(e.getNewValue());
            if (userToEdit.isValid()) {
                userToEdit.updateFile();
            } else {
                userToEdit.setRole(oldVal);
                usersView.getTableView().getItems().set(usersView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                ControllerCommon.showErrorMessage(usersView.getResultLabel(), "Edit value invalid!");
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
                    ControllerCommon.showSuccessMessage(usersView.getResultLabel(), "User removed successfully");
                } else {
                    ControllerCommon.showErrorMessage(usersView.getResultLabel(), "User deletion failed");
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
                if (user.saveInFile()) {
                    usersView.getTableView().getItems().add(user);
                    ControllerCommon.showSuccessMessage(usersView.getResultLabel(), "User created successfully!");
                    usersView.getUsernameCol().setText("");
                    usersView.getPasswordField().setText("");
                } else {
                    ControllerCommon.showErrorMessage(usersView.getResultLabel(), "User creation failed!");

                }
            } else {
                ControllerCommon.showErrorMessage(usersView.getResultLabel(), "User with this username exists.");
            }
        });
    }

}
