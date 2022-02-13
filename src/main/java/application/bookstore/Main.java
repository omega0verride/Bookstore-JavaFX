package application.bookstore;

import application.bookstore.controllers.ControllerCommon;
import application.bookstore.controllers.LoginController;
import application.bookstore.models.*;
import application.bookstore.views.LoginView;
import application.bookstore.views.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Main extends Application {

    public static void main(String[] args) {
        FileHandler fh;
        try {
            fh = new FileHandler("data/bookstoreLOG.log");
            ControllerCommon.LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            ControllerCommon.LOGGER.info("Starting APP...");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        loadData();
//        createAdminAndData();
        launch(args);
    }

    public static void createAdminAndData(){
        User u = new User("admin", "admin", Role.ADMIN);
        ControllerCommon.LOGGER.log(Level.INFO, u.saveInFile());
        Author a = new Author("Albert", "Camus");
        a.saveInFile();
        Book b = new Book("1234567890120", "The Stranger", 20, 5, 5.2f, a);
        b.saveInFile();
        b = new Book("1234567890121", "The Plague", 8, 7, 7.5f, a);
        b.saveInFile();
        b = new Book("1234567890122", "The Myth of Sisyphus", 10, 6, 6.4f, a);
        b.saveInFile();
        b = new Book("1234567890123", "The Fall", 14, 5, 5.2f, a);
        b.saveInFile();
        b = new Book("1234567890123", "The Fall", 12, 5, 5.2f, a);
        b.saveInFile();
        a=new Author("Fyodor", "Dostoevsky");
        a.saveInFile();
        b = new Book("1234567890124", "Crime and Punishment", 15, 5, 5.2f, a);
        b.saveInFile();
        b = new Book("1234567890125", "The Brothers Karamazov", 10, 6, 6.3f, a);
        b.saveInFile();
    }

    private static void loadData(){
        ControllerCommon.LOGGER.info("Loading data Files...");
        User.getUsers();
        Author.getAuthors();
        Book.getBooks();
        Order.getOrders();
    }

    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();
        new LoginController(loginView, stage);
        Scene scene = new Scene(loginView.getView(), MainView.width, MainView.height);
        stage.setTitle("Bookstore");
        stage.setScene(scene);
        stage.show();
    }

}
