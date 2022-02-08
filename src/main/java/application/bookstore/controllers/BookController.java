package application.bookstore.controllers;

import application.bookstore.models.Author;
import application.bookstore.models.Book;
import application.bookstore.views.BookView;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;

public class BookController {
    private final BookView bookView;

    public BookController(BookView bookView) {
        this.bookView = bookView;
        setSaveListener();
        setDeleteListener();
        if (bookView.isAllowEdit()) {
            setEditListener();
            bookView.getTableView().setEditable(true);
        }
        setComboBoxListener();
        setSearchListener();
    }

    private void setSearchListener() {
        bookView.getSearchView().getClearBtn().setOnAction(e -> {
            bookView.getSearchView().getSearchField().setText("");
            bookView.getTableView().setItems(FXCollections.observableArrayList(Book.getBooks()));
        });
        bookView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = bookView.getSearchView().getSearchField().getText();
            ArrayList<Book> searchResults = Book.getSearchResults(searchText);
            bookView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }

    private void setComboBoxListener() {
        bookView.getAuthorsComboBox().setOnMouseClicked(e -> {

            bookView.getAuthorsComboBox().getItems().setAll(Author.getAuthors());
            // set default selected the first author
            if (!Author.getAuthors().isEmpty())
                bookView.getAuthorsComboBox().setValue(Author.getAuthors().get(0));
        });
    }

    private void setSaveListener() {
        bookView.getSaveBtn().setOnAction(e -> {
            String isbn = bookView.getIsbnField().getText();
            String title = bookView.getTitleField().getText();
            float purchasedPrice = Float.parseFloat(bookView.getPurchasedPriceField().getText());
            float sellingPrice = Float.parseFloat(bookView.getSellingPriceField().getText());
            Author author = bookView.getAuthorsComboBox().getValue();
            Book book = new Book(isbn, title, purchasedPrice, sellingPrice, author);

            if (!book.exists()) {
                if (book.saveInFile()) {
                    bookView.getTableView().getItems().add(book);
                    ControllerCommon.showSuccessMessage(bookView.getResultLabel(), "Book created successfully");
                    resetFields();
                } else {
                    ControllerCommon.showErrorMessage(bookView.getResultLabel(), "Book creation failed");
                }
            } else {
                ControllerCommon.showErrorMessage(bookView.getResultLabel(), "Book with this ISBN exists.");
            }
        });
    }

    private void setDeleteListener() {
        bookView.getDeleteBtn().setOnAction(e -> {
            List<Book> itemsToDelete = List.copyOf(bookView.getTableView().getSelectionModel().getSelectedItems());
            for (Book b : itemsToDelete) {
                if (b.deleteFromFile()) {
                    bookView.getTableView().getItems().remove(b);
                    ControllerCommon.showSuccessMessage(bookView.getResultLabel(), "Book removed successfully");
                } else {
                    ControllerCommon.showErrorMessage(bookView.getResultLabel(), "Book deletion failed");
                    break;
                }
            }
        });
    }

    private void setEditListener() {
        bookView.getIsbnCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            String oldVal = bookToEdit.getIsbn();
            bookToEdit.setIsbn(e.getNewValue());
            if (bookToEdit.isValid()) {
                bookToEdit.updateFile();
            } else {
                bookToEdit.setIsbn(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                ControllerCommon.showErrorMessage(bookView.getResultLabel(), "Edit value invalid!");
            }
        });

        bookView.getTitleCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            String oldVal = bookToEdit.getTitle();
            bookToEdit.setTitle(e.getNewValue());
            if (bookToEdit.isValid()) {
                bookToEdit.updateFile();
            } else {
                bookToEdit.setTitle(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                ControllerCommon.showErrorMessage(bookView.getResultLabel(), "Edit value invalid!");
            }
        });

        bookView.getQuantityCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            int oldVal = bookToEdit.getQuantity();
            bookToEdit.setQuantity(e.getNewValue());
            if (bookToEdit.isValid()) {
                bookToEdit.updateFile();
            } else {
                bookToEdit.setQuantity(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                ControllerCommon.showErrorMessage(bookView.getResultLabel(), "Edit value invalid!");
            }
        });

        bookView.getPurchasedPriceCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            float oldVal = bookToEdit.getPurchasedPrice();
            bookToEdit.setPurchasedPrice(e.getNewValue());
            if (bookToEdit.isValid()) {
                bookToEdit.updateFile();
            } else {
                bookToEdit.setPurchasedPrice(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                ControllerCommon.showErrorMessage(bookView.getResultLabel(), "Edit value invalid!");
            }
        });

        bookView.getSellingPriceCol().setOnEditCommit(e -> {
            Book bookToEdit = e.getRowValue();
            float oldVal = bookToEdit.getSellingPrice();
            bookToEdit.setSellingPrice(e.getNewValue());
            if (bookToEdit.isValid()) {
                bookToEdit.updateFile();
            } else {
                bookToEdit.setSellingPrice(oldVal);
                bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                ControllerCommon.showErrorMessage(bookView.getResultLabel(), "Edit value invalid!");
            }
        });


    }

    private void resetFields() {
        bookView.getIsbnField().setText("");
        bookView.getTitleField().setText("");
        bookView.getPurchasedPriceField().setText("");
        bookView.getSellingPriceField().setText("");
        bookView.getQuantityField().setText("");
    }
}
