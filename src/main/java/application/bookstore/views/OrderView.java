package application.bookstore.views;

import application.bookstore.controllers.OrderController;
import application.bookstore.models.BookOrder;
import application.bookstore.models.Order;
import application.bookstore.ui.ClearButton;
import application.bookstore.ui.CreateButton;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class OrderView extends View {
    private final BorderPane borderPane = new BorderPane();
    private final TableView<BookOrder> tableView = new TableView<>();
    private final HBox formPane = new HBox();

    private final TextField nameField = new TextField();
    private final Button createBtn = new CreateButton();
    private final Button clearBtn = new ClearButton();
    private final Label totalValueLabel = new Label("0");
    private final Label totalLabel = new Label("Total: ", totalValueLabel);


    private final Order order;
    private final Tab tab;
    private final MainView mainView;
    private final Stage mainStage;

    private final TableColumn<BookOrder, Integer> noCol = new TableColumn<>("Quantity");
    private final TableColumn<BookOrder, String> isbnCol = new TableColumn<>("ISBN");
    private final TableColumn<BookOrder, String> titleCol = new TableColumn<>("Title");
    private final TableColumn<BookOrder, Float> priceCol = new TableColumn<>("Unit Price");
    private final TableColumn<BookOrder, Float> totalPriceCol = new TableColumn<>("Total Price");
    private final TableColumn<BookOrder, String> authorCol = new TableColumn<>("Author");

    private final Label resultLabel = new Label("");


    private final BookView existingBooksView;
    private final Parent existingBooksViewView;
    private final boolean advanced;

    public OrderView(MainView mainView, Stage mainStage, Tab tab) {
        this(mainView, mainStage, tab, false);
    }

    public OrderView(MainView mainView, Stage mainStage, Tab tab, boolean advanced) {
        this.mainView = mainView;
        this.mainStage=mainStage;
        this.tab = tab;
        this.advanced = advanced;
        order = new Order();

        existingBooksView = new BookView(advanced);
        existingBooksViewView = existingBooksView.getView();

        setForm();
        setTableView();

        new OrderController(this, mainStage);
    }

    private void setForm() {
        formPane.setPadding(new Insets(20));
        formPane.setSpacing(20);
        formPane.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Client Full Name: ", nameField);
        nameLabel.setContentDisplay(ContentDisplay.TOP);
        totalLabel.setContentDisplay(ContentDisplay.RIGHT);
        Pane spacer = new Pane();
        spacer.setMinWidth(totalLabel.getWidth()+40);
        formPane.getChildren().addAll(spacer, clearBtn, nameLabel, createBtn, totalLabel);
    }

    private void setTableView() {

        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setEditable(true);
        tableView.setMinHeight(200);
        tableView.setItems(FXCollections.observableArrayList(order.getBooksOrdered()));

        noCol.setEditable(true);
        noCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );
        noCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        isbnCol.setEditable(false);
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("bookISBN")
        );
        // to edit the value inside the table view
        isbnCol.setCellFactory(TextFieldTableCell.forTableColumn());

        titleCol.setEditable(false);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());

        priceCol.setEditable(false);
        priceCol.setCellValueFactory(
                new PropertyValueFactory<>("unitPrice")
        );
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        totalPriceCol.setEditable(false);
        totalPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("totalPrice")
        );
        totalPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        authorCol.setEditable(false);
        authorCol.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );

        tableView.getColumns().addAll(noCol, isbnCol, titleCol, priceCol, totalPriceCol, authorCol);
    }

    public TextField getNameField() {
        return nameField;
    }

    public TableView<BookOrder> getTableView() {
        return tableView;
    }

    @Override
    public Parent getView() {

        VBox tables = new VBox();
        tables.setAlignment(Pos.CENTER);
        VBox.setVgrow(existingBooksViewView, Priority.ALWAYS);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        tables.getChildren().add(existingBooksViewView);
        tables.getChildren().add(tableView);
        borderPane.setCenter(tables);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        vBox.getChildren().addAll(formPane, resultLabel);
        borderPane.setBottom(vBox);

        return borderPane;
    }

    public Label getTotalValueLabel() {
        return totalValueLabel;
    }

    public Button getCreateBtn() {
        return createBtn;
    }

    public Order getOrder() {
        return order;
    }

    public TableColumn<BookOrder, Integer> getNoCol() {
        return noCol;
    }

    public TableColumn<BookOrder, String> getIsbnCol() {
        return isbnCol;
    }

    public TableColumn<BookOrder, String> getTitleCol() {
        return titleCol;
    }

    public TableColumn<BookOrder, Float> getPriceCol() {
        return priceCol;
    }

    public TableColumn<BookOrder, Float> getTotalPriceCol() {
        return totalPriceCol;
    }

    public TableColumn<BookOrder, String> getAuthorCol() {
        return authorCol;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public Label getTotalLabel() {
        return totalLabel;
    }

    public BookView getExistingBooksView() {
        return existingBooksView;
    }

    public Button getClearBtn() {
        return clearBtn;
    }

    public Parent getExistingBooksViewView() {
        return existingBooksViewView;
    }

    public Tab getTab() {
        return tab;
    }

    public MainView getMainView() {
        return mainView;
    }

}
