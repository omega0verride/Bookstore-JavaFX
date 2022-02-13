package application.bookstore.views;

import application.bookstore.controllers.StatsController;
import application.bookstore.models.BookOrder;
import application.bookstore.models.Order;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class StatsView extends View {

    private final VBox vBox = new VBox();


    public StatsView() {
        new StatsController(this);
    }

    @Override
    public Parent getView() {

        BorderPane borderPane = new BorderPane();

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        ObservableList<Order> orders = Order.getOrders();

        System.out.println(orders);


        ScrollPane p = new ScrollPane();

        vBox.getChildren().addAll(new usernameBasedChart(orders));
//        vBox.getChildren().addAll(new bookBasedChart(orders));
//        vBox.getChildren().addAll(new clientBasedChart(orders));

//        HBox hBox = new HBox();
//        hBox.setAlignment(Pos.CENTER);
//        hBox.getChildren().add(p);
        p.setContent(vBox);
        p.setFitToWidth(true);
        borderPane.setCenter(p);
        return borderPane;
    }

    class usernameBasedChart extends PieChart {

        public usernameBasedChart(ObservableList<Order> orders) {
            ObservableList<data> filteredOrders = FXCollections.observableArrayList();
            for (Order o : orders) {
                boolean match = false;
                for (data d : filteredOrders) {
                    if (o.getUsername().equals(d.username)) {
                        d.total += o.getTotal();
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    data data_ = new data();
                    data_.username = o.getUsername();
                    data_.total = o.getTotal();
                    filteredOrders.add(data_);
                }
            }

            System.out.println(filteredOrders);

            ObservableList<Data> pieChartData = FXCollections.observableArrayList();

            for (data d : filteredOrders) {
                pieChartData.add(new Data(d.username, d.total));
            }

            pieChartData.forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), " ", data.pieValueProperty(), " $"
                            )
                    )
            );
            super.setData(pieChartData);
            super.setTitle("User Based Income");
        }

        class data {
            String username;
            Float total;

            @Override
            public String toString() {
                return username + ": " + total;
            }
        }
    }

    class bookBasedChart extends PieChart {

        public bookBasedChart(ArrayList<Order> orders) {
            ArrayList<data> filteredBooks = new ArrayList<>();
            ArrayList<BookOrder> allBooks = new ArrayList<>();
            for (Order o : orders) {
                boolean match = false;
                for (Order d : orders) {
                    allBooks.addAll(d.getBooksOrdered());
                }
                for (BookOrder b : allBooks) {
                    for (data d : filteredBooks) {
                        if (b.getTitle().equals(d.title)) {
                            d.total += o.getTotal();
                            match = true;
                            break;
                        }
                    }

                    if (!match) {
                        data data_ = new data();
                        data_.title = b.getTitle();
                        data_.total = b.getTotalPrice();
                        filteredBooks.add(data_);
                    }
                }
            }

            System.out.println(filteredBooks);

            ObservableList<Data> pieChartData = FXCollections.observableArrayList();

            for (data d : filteredBooks) {
                pieChartData.add(new Data(d.title, d.total));
            }

            pieChartData.forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), " ", data.pieValueProperty(), " $"
                            )
                    )
            );
            super.setData(pieChartData);
            super.setTitle("Book Based Income");
        }

        class data {
            String title;
            Float total;

            @Override
            public String toString() {
                return title + ": " + total;
            }
        }
    }

    class clientBasedChart extends PieChart {

        public clientBasedChart(ArrayList<Order> orders) {
            ArrayList<data> filteredOrders = new ArrayList<>();
            for (Order o : orders) {
                boolean match = false;
                for (data d : filteredOrders) {
                    if (o.getClientName().equals(d.name)) {
                        d.total += o.getTotal();
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    data data_ = new data();
                    data_.name = o.getClientName();
                    data_.total = o.getTotal();
                    filteredOrders.add(data_);
                }
            }

            System.out.println(filteredOrders);

            ObservableList<Data> pieChartData = FXCollections.observableArrayList();

            for (data d : filteredOrders) {
                pieChartData.add(new Data(d.name, d.total));
            }

            pieChartData.forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), " ", data.pieValueProperty(), " $"
                            )
                    )
            );
            super.setData(pieChartData);
            super.setTitle("Client Based Income");
        }

        class data {
            String name;
            Float total;

            @Override
            public String toString() {
                return name + ": " + total;
            }
        }
    }
}
