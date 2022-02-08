package application.bookstore.views;

import application.bookstore.controllers.StatsController;
import application.bookstore.models.Order;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class StatsView extends View {
    private final VBox vBox = new VBox();


    public StatsView() {
        new StatsController(this);
    }

    @Override
    public Parent getView() {
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        ArrayList<Order> orders = Order.getOrders();

        class data {
            String username;
            Float total;

            @Override
            public String toString() {
                return username + ": " + total;
            }
        }

        ArrayList<data> filteredOrders = new ArrayList<>();
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

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (data d : filteredOrders) {
            pieChartData.add(new PieChart.Data(d.username, d.total));
        }

        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty(), " $"
                        )
                )
        );
        PieChart chart = new PieChart(pieChartData);

        chart.setTitle("User Based Income");

        vBox.getChildren().addAll(chart);

        return vBox;
    }


}
