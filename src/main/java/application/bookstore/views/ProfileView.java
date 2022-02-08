package application.bookstore.views;

import application.bookstore.controllers.ProfileController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ProfileView extends View {

    public ProfileView() {

        new ProfileController(this);
    }

    @Override
    public Parent getView() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(vBox);
        return borderPane;
    }


}
