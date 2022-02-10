package application.bookstore.views;

import application.bookstore.controllers.ControllerCommon;
import application.bookstore.controllers.OrderController;
import application.bookstore.models.Book;
import application.bookstore.models.BookOrder;
import application.bookstore.models.Order;
import application.bookstore.ui.ClearButton;
import application.bookstore.ui.CreateButton;
import application.bookstore.ui.ProfileButton;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class OrderView extends View {
    private final BorderPane mainPane = new BorderPane();

    private final Order order;
    private final Tab tab;

    private final TableView<BookOrder> tableView = new TableView<>();
    private final TableColumn<BookOrder, Integer> noCol = new TableColumn<>("Quantity");
    private final TableColumn<BookOrder, String> isbnCol = new TableColumn<>("ISBN");
    private final TableColumn<BookOrder, String> titleCol = new TableColumn<>("Title");
    private final TableColumn<BookOrder, Float> priceCol = new TableColumn<>("Unit Price");
    private final TableColumn<BookOrder, Float> totalPriceCol = new TableColumn<>("Total Price");
    private final TableColumn<BookOrder, String> authorCol = new TableColumn<>("Author");
    private final TableColumn<Book, Integer> selectorCol = new TableColumn<>("");
    private final TableColumn<BookOrder, Integer> selectorCol_ = new TableColumn<>("");

    private final HBox formPane = new HBox();
    private final TextField nameField = new TextField();
    private final Button createBtn = new CreateButton();
    private final Button clearBtn = new ClearButton();
    private final Label totalValueLabel = new Label("0");
    private final Label totalLabel = new Label("Total: ", totalValueLabel);
    private final Label messageLabel = new Label("");

    private final BookView existingBooksView;
    private final Parent existingBooksViewPane;
    private final boolean advanced; // if the bookview allows editing

    public OrderView(MainView mainView, Stage mainStage, Tab tab) {
        this(mainView, mainStage, tab, false);
    }

    public OrderView(MainView mainView, Stage mainStage, Tab tab, boolean advanced) {
        this.tab = tab;
        this.advanced = advanced;

        order = new Order();
        existingBooksView = new BookView(advanced);
        existingBooksViewPane = existingBooksView.getView();
        new OrderController(this, mainStage);
    }

    @Override
    public Parent getView() {
        setForm();
        setTableView();

        ControllerCommon.showSuccessMessage(messageLabel, "Double click on a row to add/remove it.");

        VBox tables = new VBox();
        tables.setAlignment(Pos.CENTER);
        VBox.setVgrow(existingBooksViewPane, Priority.ALWAYS);
        VBox.setVgrow(tableView, Priority.ALWAYS); // make the tables expand
        tables.getChildren().add(existingBooksViewPane);
        tables.getChildren().add(tableView);

        VBox controls = new VBox();
        controls.setAlignment(Pos.CENTER);
        controls.setSpacing(5);
        controls.getChildren().addAll(formPane, messageLabel);

        mainPane.setCenter(tables);
        mainPane.setBottom(controls);

        return mainPane;
    }

    private void setForm() {
        formPane.setPadding(new Insets(20));
        formPane.setSpacing(20);
        formPane.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Client Full Name: ", nameField);
        nameLabel.setContentDisplay(ContentDisplay.TOP);
        totalLabel.setContentDisplay(ContentDisplay.RIGHT);
        Pane spacer = new Pane();
        spacer.setMinWidth(totalLabel.getWidth() + 40);
        formPane.getChildren().addAll(spacer, clearBtn, nameLabel, createBtn, totalLabel);
    }

    private void setTableView() {
        selectorCol.setGraphic(new ImageView(String.valueOf(ProfileButton.class.getResource("/images/selector.png"))));
        selectorCol.setMinWidth(30);
        selectorCol.setMaxWidth(30);;
        selectorCol_.setGraphic(new ImageView(String.valueOf(ProfileButton.class.getResource("/images/selector.png"))));
        selectorCol_.setMinWidth(30);
        selectorCol_.setMaxWidth(30);
        existingBooksView.getTableView().getColumns().add(0, selectorCol);

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
        titleCol.setEditable(false);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        priceCol.setEditable(false);
        priceCol.setCellValueFactory(
                new PropertyValueFactory<>("unitPrice")
        );
        totalPriceCol.setEditable(false);
        totalPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("totalPrice")
        );
        authorCol.setEditable(false);
        authorCol.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );
        tableView.getColumns().addAll(selectorCol_, noCol, isbnCol, titleCol, priceCol, totalPriceCol, authorCol);
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
        return messageLabel;
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

    public Parent getExistingBooksViewPane() {
        return existingBooksViewPane;
    }

    public Tab getTab() {
        return tab;
    }


    public TextField getNameField() {
        return nameField;
    }

    public TableView<BookOrder> getTableView() {
        return tableView;
    }

    public TableColumn<Book, Integer> getSelectorCol() {
        return selectorCol;
    }

    public TableColumn<BookOrder, Integer> getSelectorCol_() {
        return selectorCol_;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}
