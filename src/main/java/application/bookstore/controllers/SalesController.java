package application.bookstore.controllers;

import application.bookstore.models.Order;
import application.bookstore.views.SalesView;
import javafx.collections.FXCollections;

import java.util.ArrayList;

public class SalesController {
    private final SalesView salesView;

    public SalesController(SalesView salesView) {
        this.salesView = salesView;
        setSearchListener();
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
