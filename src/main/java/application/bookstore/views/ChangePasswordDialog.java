package application.bookstore.views;

import application.bookstore.models.User;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class ChangePasswordDialog {
    private final Scene secondScene;
    private final Button okButton;
    private final Label usernameLabel;
    private final PasswordField oldPassField;
    private final PasswordField newPassField;
    private final PasswordField newPassField1;
    private final Label newPassLabel;
    private final Label messageLabel;
    private final Stage newWindow;
    private final Stage mainStage;
    private final View mainView;
    private final VBox v;

    public ChangePasswordDialog(Stage mainStage, View mainView) {
        this.mainStage = mainStage;
        this.mainView = mainView;

        usernameLabel = new Label("User: " + mainView.getCurrentUser().getUsername());
        oldPassField = new PasswordField();
        newPassField = new PasswordField();
        newPassField1 = new PasswordField();
        Label oldPassLabel = new Label("Old Password: ", oldPassField);
        oldPassLabel.setContentDisplay(ContentDisplay.RIGHT);
        newPassLabel = new Label("New Password: ", newPassField);
        newPassLabel.setContentDisplay(ContentDisplay.RIGHT);
        Label newPassLabel1 = new Label("New Password: ", newPassField1);
        newPassLabel1.setContentDisplay(ContentDisplay.RIGHT);

        HBox h = new HBox();
        h.setAlignment(Pos.CENTER);
        h.setSpacing(20);
        okButton = new Button("Ok");
        Button cancelButton = new Button("Cancel");
        h.getChildren().addAll(okButton, cancelButton);

        messageLabel = new Label("");

        v = new VBox();
        v.setAlignment(Pos.CENTER);
        v.getChildren().addAll(usernameLabel, oldPassLabel, newPassLabel, newPassLabel1, messageLabel, h);
        v.setSpacing(25);

        secondScene = new Scene(v, 350, 350);

        newWindow = new Stage();
        newWindow.setTitle("Change Password");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(mainStage);

        cancelButton.setOnMousePressed(ev -> newWindow.close());
        okButton.setOnMousePressed(ev -> {
            changePassword();
        });

        addKeyBind();

        newWindow.show();
    }

    private void addKeyBind() {
        v.setOnKeyPressed(e -> {
            System.out.println("iyhkdsja");
            if (e.getCode().equals(KeyCode.ENTER)) {
                changePassword();
                System.out.println("asdad");
            }
        });

    }

    private void changePassword() {
        if (oldPassField.getText().equals(mainView.getCurrentUser().getPassword())) {
            if (newPassField.getText().matches(newPassField1.getText())) {
                User u = new User(mainView.getCurrentUser().getUsername(), newPassField.getText(), mainView.getCurrentUser().getRole());
                if (u.isValid()) {
                    mainView.getCurrentUser().deleteFromFile();
                    mainView.setCurrentUser(u);
                    u.saveInFile();
                    ConfirmationDialog d = new ConfirmationDialog("Password Changed Successfully");
                    d.okButton.setOnMousePressed(e -> {
                        d.close();
                        newWindow.close();
                    });
                    d.setOnCloseRequest(e -> newWindow.close());
                } else {
                    showErrorMessage(messageLabel, "New Password Invalid!");
                }
            } else {
                showErrorMessage(messageLabel, "New Passwords do not match!");
            }
        } else {
            showErrorMessage(messageLabel, "Old Password Incorrect!");
        }
    }

    private void showErrorMessage(Label l, String t) {
        l.setText(t);
        l.setTextFill(Color.DARKRED);
        scheduleErrorReset(l, 5000);
    }

    private void showErrorMessage(Label l, String t, int time) {
        l.setText(t);
        l.setTextFill(Color.DARKRED);
        scheduleErrorReset(l, time);
    }

    private void scheduleErrorReset(Label l, int time) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    l.setText("");
                });
            }
        }, time);
    }

    class ConfirmationDialog extends Stage {
        Button okButton;

        ConfirmationDialog(String message) {
            super.initModality(Modality.WINDOW_MODAL);
            okButton = new Button("OK");
            VBox vbox = new VBox(new Text(message), okButton);
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(20);
            vbox.setPadding(new Insets(20));
            super.setScene(new Scene(vbox));
            super.show();
        }
    }
}
