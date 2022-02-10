package application.bookstore.controllers;

import application.bookstore.models.Order;
import application.bookstore.ui.PrintWindow;
import application.bookstore.views.SalesView;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SalesController {
    private final SalesView salesView;
    private final Stage mainStage;

    public SalesController(SalesView salesView, Stage mainStage) {
        this.mainStage = mainStage;
        this.salesView = salesView;
        setSearchListener();
        setPrintListener();
    }

    private void setPrintListener() {
        salesView.getPrintButton().setOnAction(e -> {
            Order o = salesView.getTableView().getSelectionModel().getSelectedItem();
            new PrintWindow(mainStage, salesView, o);
            o.print();
        });
    }

    private void setSearchListener() {
        salesView.getSearchView().getClearBtn().setOnAction(e -> {
            salesView.getSearchView().getSearchField().setText("");
            salesView.getTableView().setItems(FXCollections.observableArrayList(Order.getOrders()));
        });
        salesView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = salesView.getSearchView().getSearchField().getText();
            ArrayList<Order> searchResults = Order.getSearchResults(searchText);
            salesView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }
}
