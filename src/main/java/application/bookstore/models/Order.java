package application.bookstore.models;

import application.bookstore.auxiliaries.FileHandler;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Order extends BaseModel implements Serializable {
    public static final String FILE_PATH = "data/orders.ser";
    public static final File DATA_FILE = new File(FILE_PATH);
    @Serial
    private static final long serialVersionUID = 1234567L;
    private static final ArrayList<Order> orders = new ArrayList<>();
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter idFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
    private String clientName;
    private String username;
    private String orderID;
    private ArrayList<BookOrder> booksOrdered;
    private String date;


    public Order() {
        booksOrdered = new ArrayList<>();
    }

    public static ArrayList<Order> getSearchResults(String searchText) {
        ArrayList<Order> searchResults = new ArrayList<>();
        for (Order order : getOrders())
            if (order.getClientName().matches(".*" + searchText + ".*"))
                searchResults.add(order);
        return searchResults;
    }

    public static ArrayList<Order> getOrders() {
        if (orders.size() == 0) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
                while (true) {
                    Order temp = (Order) inputStream.readObject();
                    if (temp != null)
                        orders.add(temp);
                    else
                        break;
                }
                inputStream.close();
            } catch (EOFException eofException) {
                System.out.println("End of orders file reached!");
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return orders;
    }

    public void completeOrder(String username, String clientName) {
        setUsername(username);
        setClientName(clientName);
        LocalDateTime now = LocalDateTime.now();
        setDate(dtf.format(now));
        setOrderID("Order_" + idFormatter.format(now));
    }

    @Override
    public String toString() {
        String s = "Order: " + orderID + "\nDate: " + date + "\nClient: " + clientName + "\nBooks Ordered: \n";
        for (BookOrder b : booksOrdered)
            s += b + "\n";
        s += String.format("\n-----------\nTotal: %.2f", getTotal());
        return s;
    }

    public float getTotal() {
        float sum = 0;
        for (BookOrder b : booksOrdered)
            sum += b.getTotalPrice();
        return sum;
    }

    public void print() {
        try {
            PrintWriter p = new PrintWriter(new File("bills/" + orderID + ".txt"));
            p.println(this);
            p.close();
        } catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    public ArrayList<BookOrder> getBooksOrdered() {
        return booksOrdered;
    }

    public void setBooksOrdered(ArrayList<BookOrder> booksOrdered) {
        this.booksOrdered = booksOrdered;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean saveInFile() {
        Order.getOrders();
        boolean saved = super.save(Order.DATA_FILE);
        if (saved)
            orders.add(this);
        return saved;
    }

    @Override
    public boolean updateFile() {
        try {
            FileHandler.overwriteCurrentListToFile(DATA_FILE, orders);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }

    public boolean isValid() {
        return clientName.matches("([a-zA-Z0-9_]{1,30}\\s*)+");
    }

    @Override
    public boolean deleteFromFile() {
        orders.remove(this);
        try {
            FileHandler.overwriteCurrentListToFile(DATA_FILE, orders);
        } catch (Exception e) {
            orders.add(this);
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }
}
