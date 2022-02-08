package application.bookstore.controllers;

import application.bookstore.models.BookOrder;
import application.bookstore.views.OrderView;

import java.util.List;

public class OrderController {
    private final OrderView orderView;

    public OrderController(OrderView bookView) {
        this.orderView = bookView;
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

    private void clearOrder() {
        List<BookOrder> elementsToRemove = List.copyOf(orderView.getTableView().getItems());
        for (BookOrder b : elementsToRemove) {
            orderView.getOrder().getBooksOrdered().remove(b);
            orderView.getExistingBooksView().getTableView().getItems().add(b.getBook());
            orderView.getTableView().getItems().remove(b);
            orderView.getTotalValueLabel().setText(((Float) orderView.getOrder().getTotal()).toString());
        }
    }

    private void setClearListener() {
        orderView.getClearBtn().setOnMousePressed(e -> clearOrder());
    }

    private void setRemoveBookListener() {
        orderView.getTableView().setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                BookOrder b = orderView.getTableView().getSelectionModel().getSelectedItem();
                orderView.getOrder().getBooksOrdered().remove(b);
                orderView.getExistingBooksView().getTableView().getItems().add(b.getBook());
                orderView.getTableView().getItems().remove(b);
                orderView.getTotalValueLabel().setText(((Float) orderView.getOrder().getTotal()).toString());
            }
        });
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
            if (orderView.getOrder().saveInFile()) {
                for (BookOrder b : orderView.getOrder().getBooksOrdered()) {
                    b.getBook().setQuantity(b.getBook().getQuantity() - b.getQuantity());
                } // change stock quantity
                orderView.getOrder().print();
                ControllerCommon.showSuccessMessage(orderView.getResultLabel(), "Order created successfully");
                resetFields();
//                orderView.getMainView().getTabPane().getTabs().remove(orderView.getTab());
            } else {
                ControllerCommon.showErrorMessage(orderView.getResultLabel(), "Client name cannot be empty!");
            }

        });
    }

    private void resetFields() {
        orderView.getNameField().setText("");
        clearOrder();
    }
}
