package application.bookstore.models;

import java.io.Serial;
import java.io.Serializable;

public class BookOrder implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;
    private final transient Book book;
    private int quantity;
    private String bookISBN;
    private String title;
    private float unitPrice;
    private Author author;

    public BookOrder(int quantity, Book b) {
        book = b;
        setQuantity(quantity);
        setBookISBN(b.getIsbn());
        setTitle(b.getTitle());
        setUnitPrice(b.getSellingPrice());
        setAuthor(b.getAuthor());
    }


    @Override
    public String toString() {
        return String.format("%-6s %-20s Unit: %-6.2f Total: %.2f", quantity + "pcs", title, unitPrice, getTotalPrice());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getTotalPrice() {
        return quantity * unitPrice;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }
}
