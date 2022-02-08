package application.bookstore.controllers;

import application.bookstore.models.User;
import application.bookstore.views.LoginView;
import application.bookstore.views.MainView;
import application.bookstore.views.View;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LoginController {
    private final Stage primaryStage;
    private View nextView;
    private User currentUser;

    public LoginController(LoginView view, Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        addListener(view);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void addListener(LoginView view) {
        view.getView().setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                view.getLoginBtn().fire();
            }
        });

        view.getLoginBtn().setOnAction(e -> {
            String password = view.getPasswordField().getText();
            String username = view.getUsernameField().getText();
            User potentialUser = new User(username, password);
            if ((currentUser = User.getIfExists(potentialUser)) != null) {
                view.setCurrentUser(currentUser);
                primaryStage.setResizable(true);
                nextView = new MainView(primaryStage);
                primaryStage.setScene(new Scene(nextView.getView(), MainView.width, MainView.height));
                primaryStage.centerOnScreen();
            } else
                ControllerCommon.showErrorMessage(view.getErrorLabel(), "Wrong username or password");
        });
    }
}
