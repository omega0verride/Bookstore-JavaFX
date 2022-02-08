package application.bookstore.views;

import application.bookstore.controllers.SalesController;
import application.bookstore.models.Order;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.FloatStringConverter;


public class SalesView extends View {
    private final BorderPane pane = new BorderPane();
    private final TableView<Order> tableView = new TableView<>();
    private final TableColumn<Order, String> clientNameCol = new TableColumn<>("Client Name");
    private final TableColumn<Order, String> usernameCol = new TableColumn<>("Username");
    private final TableColumn<Order, String> orderIDCol = new TableColumn<>("Order ID");
    private final TableColumn<Order, Float> totalPriceCol = new TableColumn<>("Total");
    private final SearchView searchView = new SearchView("Search for an order");


    public SalesView() {
        setTableView();
        new SalesController(this);
    }


    private void setTableView() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(false);
        tableView.setItems(FXCollections.observableArrayList(Order.getOrders()));

        clientNameCol.setCellValueFactory(
                new PropertyValueFactory<>("clientName")
        );
        clientNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        usernameCol.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );
        usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        orderIDCol.setCellValueFactory(
                new PropertyValueFactory<>("orderID")
        );
        orderIDCol.setCellFactory(TextFieldTableCell.forTableColumn());

        totalPriceCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTotal()));
        totalPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));


        tableView.getColumns().addAll(clientNameCol, usernameCol, totalPriceCol, orderIDCol);
    }


    @Override
    public Parent getView() {
        tableView.setMinHeight(200);
        pane.setTop(searchView.getSearchPane());
        pane.setCenter(tableView);
        return pane;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public TableView<Order> getTableView() {
        return tableView;
    }
}
