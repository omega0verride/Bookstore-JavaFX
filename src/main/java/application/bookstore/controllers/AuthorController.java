package application.bookstore.controllers;

import application.bookstore.models.Author;
import application.bookstore.models.Role;
import application.bookstore.views.AuthorView;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;

public class AuthorController {
    private final AuthorView authorView;

    public AuthorController(AuthorView authorView) {
        this.authorView = authorView;
        setSaveListener();
        setDeleteListener();
        Role currentRole = authorView.getCurrentUser().getRole();
        if (currentRole == Role.MANAGER || currentRole == Role.ADMIN) {
            setEditListener();
            authorView.getTableView().setEditable(true);
        }
        setSearchListener();
    }

    private void setEditListener() {
        authorView.getFirstNameCol().setOnEditCommit(e -> {
            Author authorToEdit = e.getRowValue();
            String oldVal = authorToEdit.getFirstName();
            authorToEdit.setFirstName(e.getNewValue());
            if (authorToEdit.isValid().matches("1")) {
                authorToEdit.updateFile();
            } else {
                ControllerCommon.showErrorMessage(authorView.getResultLabel(), "Edit value invalid!\n" + authorToEdit.isValid());
                authorToEdit.setFirstName(oldVal);
                authorView.getTableView().getItems().set(authorView.getTableView().getItems().indexOf(authorToEdit), authorToEdit);
            }
        });

        authorView.getLastNameCol().setOnEditCommit(e -> {
            Author authorToEdit = e.getRowValue();
            String oldVal = authorToEdit.getLastName();
            authorToEdit.setLastName(e.getNewValue());
            if (authorToEdit.isValid().matches("1")) {
                authorToEdit.updateFile();
            } else {
                ControllerCommon.showErrorMessage(authorView.getResultLabel(), "Edit value invalid!\n" + authorToEdit.isValid());
                authorToEdit.setLastName(oldVal);
                authorView.getTableView().getItems().set(authorView.getTableView().getItems().indexOf(authorToEdit), authorToEdit);
            }
        });
    }

    private void setSearchListener() {
        authorView.getSearchView().getClearBtn().setOnAction(e -> {
            authorView.getSearchView().getSearchField().setText("");
            authorView.getTableView().setItems(FXCollections.observableArrayList(Author.getAuthors()));
        });
        authorView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = authorView.getSearchView().getSearchField().getText();
            ArrayList<Author> searchResults = Author.getSearchResults(searchText);
            authorView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }

    private void setDeleteListener() {
        authorView.getDeleteBtn().setOnAction(e -> {
            List<Author> itemsToDelete = List.copyOf(authorView.getTableView().getSelectionModel().getSelectedItems());
            for (Author a : itemsToDelete) {
                if (a.deleteFromFile()) {
                    authorView.getTableView().getItems().remove(a);
                    ControllerCommon.showSuccessMessage(authorView.getResultLabel(), "Author removed successfully");
                } else {
                    ControllerCommon.showErrorMessage(authorView.getResultLabel(), "Author deletion failed");
                    break;
                }
            }
        });
    }

    private void setSaveListener() {
        authorView.getSaveBtn().setOnAction(e -> {
            String firstName = authorView.getFirstNameField().getText();
            String lastName = authorView.getLastNameField().getText();
            Author author = new Author(firstName, lastName);
            if (!author.exists()) {
                String saveResult = author.saveInFile();
                if (saveResult.matches("1")) {
                    authorView.getTableView().getItems().add(author);
                    ControllerCommon.showSuccessMessage(authorView.getResultLabel(), "Author created successfully!");
                    authorView.getFirstNameField().setText("");
                    authorView.getLastNameField().setText("");
                } else {
                    ControllerCommon.showErrorMessage(authorView.getResultLabel(), "Author creation failed!\n" + saveResult);
                }
            } else {
                ControllerCommon.showErrorMessage(authorView.getResultLabel(), "Author with this Full Name exists.");
            }
        });
    }

}
