package application.bookstore.controllers;

import application.bookstore.ui.ChangePasswordDialog;
import application.bookstore.views.*;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;


public class MainController {
    private final MainView mainView;
    private final Stage mainStage;

    public MainController(MainView mainView, Stage mainStage) {
        this.mainView = mainView;
        this.mainStage = mainStage;

        Tab startupTab = new Tab("New Order");
        startupTab.setContent(new OrderView(mainView, mainStage, startupTab).getView());
        openTab(startupTab);

        setKeyBinds();
        setListener();
        setLogoutListener();
//        Order.getOrders();//load order on startup
    }

    private void setKeyBinds() {
        mainView.getMenuItemNewOrder().setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        mainView.getMenuItemNewAdvancedOrder().setAccelerator(new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN));
    }

    private void setListener() {

        mainView.getMenuItemViewAuthors().setOnAction((e) -> {
            Tab authorTab = new Tab("Authors");
            authorTab.setContent(new AuthorView().getView());
            openTab(authorTab);
        });

        mainView.getMenuItemViewBooks().setOnAction((e) -> {
            Tab booksTab = new Tab("Books");
            booksTab.setContent(new BookView().getView());
            openTab(booksTab);
        });
        mainView.getMenuItemNewOrder().setOnAction(e -> {
            Tab order = new Tab("New Order");
            OrderView o = new OrderView(mainView, mainStage, order);
            order.setContent(o.getView());
            mainView.getTabPane().getTabs().add(order);
            mainView.getTabPane().getSelectionModel().select(order);
        });
        mainView.getMenuItemNewAdvancedOrder().setOnAction(e -> {
            Tab order = new Tab("New Advanced Order");
            OrderView o = new OrderView(mainView, mainStage, order, true);
            order.setContent(o.getView());
            mainView.getTabPane().getTabs().add(order);
            mainView.getTabPane().getSelectionModel().select(order);
            mainStage.sizeToScene();
        });
        mainView.getMenuItemViewSales().setOnAction(e -> {
            Tab sales = new Tab("Sales");
            sales.setContent(new SalesView().getView());
            openTab(sales);
        });
        mainView.getStatsMenu().setOnAction(e -> {
            Tab stats = new Tab("Stats");
            stats.setContent(new StatsView().getView());
            openTab(stats);
        });
        mainView.getMenuItemChangePassword().setOnAction(e -> {
            new ChangePasswordDialog(mainStage, mainView);
        });
        mainView.getManageUsers().setOnAction(e -> {
            Tab users = new Tab("Users");
            users.setContent(new UsersView().getView());
            openTab(users);
        });

    }

    private void setLogoutListener() {
        mainView.getMenuItemLogout().setOnAction(e -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView, mainStage);
            Scene scene = new Scene(loginView.getView(), LoginView.width, LoginView.height);
            mainStage.setScene(scene);
        });
        mainView.getLogoutButton().setOnMouseClicked(e -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView, mainStage);
            Scene scene = new Scene(loginView.getView(), LoginView.width, LoginView.height);
            mainStage.setScene(scene);
        });
    }

    private Tab openTab(Tab tab) {
        // check if tab is open
        for (Tab t : mainView.getTabPane().getTabs()) {
            if (t.getText().equals(tab.getText())) {
                mainView.getTabPane().getSelectionModel().select(t);// if it is open, navigate to it (bring it up front)
                mainStage.sizeToScene();
                return t;
            }
        }
        mainView.getTabPane().getTabs().add(tab); // add the tab
        mainView.getTabPane().getSelectionModel().select(tab); // bring it up front
        return tab;
    }

}
