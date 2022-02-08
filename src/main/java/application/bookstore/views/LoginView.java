package application.bookstore.views;

import application.bookstore.ui.CreateButton;
import application.bookstore.ui.LoginButton;
import application.bookstore.ui.LogoutButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginView extends View {
    public static int width = 500;
    public static int height = 500;
    private final BorderPane borderPane = new BorderPane();
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final LoginButton loginBtn = new LoginButton();
    private final Label errorLabel = new Label("");

    public LoginView() {
        setView();
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginBtn() {
        return loginBtn;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    private void setView() {
        HBox usernameHBox = new HBox();
        Text usernameLabel = new Text("Username");
        usernameHBox.getChildren().addAll(usernameLabel, usernameField);
        usernameHBox.setAlignment(Pos.CENTER);
        usernameHBox.setSpacing(10);
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
        usernameLabel.setFill(Color.WHITE);

        HBox passwordHBox = new HBox();
        Text passwordLabel = new Text("Password");
        passwordHBox.getChildren().addAll(passwordLabel, passwordField);
        passwordHBox.setSpacing(10);
        passwordHBox.setAlignment(Pos.CENTER);
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
        passwordLabel.setFill(Color.WHITE);

        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setPadding(new Insets(20));
        Pane spacer = new Pane();
        spacer.setMinWidth(20);
        vBox.getChildren().addAll(usernameHBox, passwordHBox, loginBtn, errorLabel, spacer);

        borderPane.setCenter(vBox);

        Image image = new Image(String.valueOf(CreateButton.class.getResource("/images/adrion.png")));
        BackgroundImage bImg = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background bGround = new Background(bImg);

        borderPane.setBackground(bGround);
    }

    @Override
    public Parent getView() {
        return borderPane;
    }
}
