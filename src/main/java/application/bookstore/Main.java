package application.bookstore;

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

public class Main extends Application {

    public static void main(String[] args) {
        loadData();
        createAdminAndData();
        launch(args);
    }

    public static void createAdminAndData(){
        User u = new User("admin", "admin", Role.ADMIN);
        System.out.println(u.saveInFile());
        Author a = new Author("Albert", "Camus");
        a.saveInFile();
        Book b = new Book("1234567890120", "The Stranger", 20, 500, 520, a);
        b.saveInFile();
        b = new Book("1234567890121", "The Plague", 8, 700, 750, a);
        b.saveInFile();
        b = new Book("1234567890122", "The Myth of Sisyphus", 10, 600, 640, a);
        b.saveInFile();
        b = new Book("1234567890123", "The Fall", 14, 500, 520, a);
        b.saveInFile();
        b = new Book("1234567890123", "The Fall", 12, 500, 520, a);
        b.saveInFile();
        a=new Author("Fyodor", "Dostoevsky");
        a.saveInFile();
        b = new Book("1234567890124", "Crime and Punishment", 15, 500, 520, a);
        b.saveInFile();
        b = new Book("1234567890125", "The Brothers Karamazov", 10, 600, 630, a);
        b.saveInFile();
    }

    private static void loadData(){
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
