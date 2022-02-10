package application.bookstore.controllers;

import application.bookstore.models.BookOrder;
import application.bookstore.ui.PrintWindow;
import application.bookstore.views.OrderView;
import javafx.stage.Stage;

import java.util.List;

public class OrderController {
    private final OrderView orderView;
    private final Stage mainStage;

    public OrderController(OrderView orderView, Stage mainStage) {
        this.mainStage = mainStage;
        this.orderView = orderView;

//        Order.getOrders();// get data from file
        setEditListener();
        setChooseBookListener();
        setRemoveBookListener();
        setCreateListener();
        setClearListener();
    }

    private void setChooseBookListener() {
        orderView.getExistingBooksView().getTableView().setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {

                BookOrder b = new BookOrder(1, orderView.getExistingBooksView().getTableView().getSelectionModel().getSelectedItem());
                if (b.getQuantity() > b.getBook().getQuantity()) {
                    ControllerCommon.showErrorMessage(orderView.getResultLabel(), "There are not enough books in stock! Currently there are " + b.getBook().getQuantity() + " available.");
                    return;
                }
                orderView.getOrder().getBooksOrdered().add(b);
                orderView.getExistingBooksView().getTableView().getItems().remove(orderView.getExistingBooksView().getTableView().getSelectionModel().getSelectedItem());
                orderView.getTableView().getItems().add(b);
                orderView.getTotalValueLabel().setText(((Float) orderView.getOrder().getTotal()).toString());
            }
        });
    }

    private void removeFromOrder(BookOrder b){
        orderView.getOrder().getBooksOrdered().remove(b);
        orderView.getExistingBooksView().getTableView().getItems().add(b.getBook());
        orderView.getTableView().getItems().remove(b);
        orderView.getTotalValueLabel().setText(((Float) orderView.getOrder().getTotal()).toString());
    }
    private void clearOrder() {
        List<BookOrder> elementsToRemove = List.copyOf(orderView.getTableView().getItems());
        for (BookOrder b : elementsToRemove) {
            removeFromOrder(b);
        }
    }


    private void setRemoveBookListener() {
        orderView.getTableView().setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                BookOrder b = orderView.getTableView().getSelectionModel().getSelectedItem();
                removeFromOrder(b);
            }
        });
    }

    private void setClearListener() {
        orderView.getClearBtn().setOnMousePressed(e -> clearOrder());
    }

    private void setEditListener() {
        orderView.getNoCol().setOnEditCommit(e -> {
            BookOrder orderToEdit = e.getRowValue();
            int oldVal = orderToEdit.getQuantity();
            orderToEdit.setQuantity(e.getNewValue());
            if (orderToEdit.getQuantity() > 0) {
                if (orderToEdit.getQuantity() <= orderToEdit.getBook().getQuantity()) {
                    orderView.getTotalValueLabel().setText(((Float) orderView.getOrder().getTotal()).toString());
                } else {
                    orderToEdit.setQuantity(oldVal);
                    orderView.getTableView().getItems().set(orderView.getTableView().getItems().indexOf(orderToEdit), orderToEdit);
                    ControllerCommon.showErrorMessage(orderView.getResultLabel(), "There are not enough books in stock! Currently there are " + orderToEdit.getBook().getQuantity() + " available.");
                }
            } else {
                orderToEdit.setQuantity(oldVal);
                orderView.getTableView().getItems().set(orderView.getTableView().getItems().indexOf(orderToEdit), orderToEdit);
                ControllerCommon.showErrorMessage(orderView.getResultLabel(), "Edit value invalid!");
            }
        });

    }

    private void setCreateListener() {
        orderView.getCreateBtn().setOnMousePressed(e -> {
            orderView.getOrder().completeOrder(orderView.getCurrentUser().getUsername(), orderView.getNameField().getText());
            String saveResult = orderView.getOrder().saveInFile();
            if (saveResult.matches("1")) {
                for (BookOrder b : orderView.getOrder().getBooksOrdered()) {
                    b.getBook().setQuantity(b.getBook().getQuantity() - b.getQuantity());
                } // change stock quantity
                new PrintWindow(mainStage, orderView, orderView.getOrder(), this);
                ControllerCommon.showSuccessMessage(orderView.getResultLabel(), "Order created successfully");
            } else {
                ControllerCommon.showErrorMessage(orderView.getResultLabel(), "Order  creation failed!\n" + saveResult);
            }

        });
    }

    public void resetFields() {
        orderView.getNameField().setText("");
        clearOrder();
    }
}
