package application.bookstore.models;

import application.bookstore.auxiliaries.FileHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Author extends BaseModel implements Serializable, Cloneable {
    public static final String FILE_PATH = "data/authors.ser";
    public static final File DATA_FILE = new File(FILE_PATH);
    @Serial
    private static final long serialVersionUID = 1234567L;
    private static final ArrayList<Author> authors = new ArrayList<>();
    private String firstName;
    private String lastName;

    public Author(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    public static ArrayList<Author> getSearchResults(String searchText) {
        // this only works if users searches by:
        // firstname/lastname only (even if truncated)
        // firstname (full) *space/s* lastname (even if truncated)
        searchText = ".*" + searchText.toLowerCase().replaceAll(" ", "") + ".*";
        ArrayList<Author> searchResults = new ArrayList<>();
        for (Author author : getAuthors())
            if (author.getFullName().toLowerCase().replaceAll(" ", "").matches(searchText))
                searchResults.add(author);
        return searchResults;
    }

    public static ArrayList<Author> getAuthors() {
        if (authors.size() == 0) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
                while (true) {
                    Author temp = (Author) inputStream.readObject();
                    if (temp != null)
                        authors.add(temp);
                    else
                        break;
                }
                inputStream.close();
            } catch (EOFException eofException) {
                System.out.println("End of authors file reached!");
                // TODO: log
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                // TODO: log
            }
        }
        return authors;
    }

    @Override
    public Author clone() {
        return new Author(firstName, lastName);
    }

    public boolean exists() {
        for (Author a : authors) {
            if (a.getFullName().equals(this.getFullName()))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    // toString for logging
    // since Author is widely used as an object in combo-boxes/table-views the original toString is used by them
    public String toString_() {
        return "\nAuthor{" +
                "\n\t\"firstName\": " + getFirstName() +
                ",\n\t\"lastName\": " + getLastName() +
                "\n}";
    }

    public String isValid() {
        // firstname and last name must contain only letters
        if (!getFirstName().matches("[a-zA-Z]{1,30}"))
            return "First Name must contain only letters.";
        if (!getLastName().matches("[a-zA-Z]{1,30}")) {
            return "Last Name must contain only letters.";
        }
        return "1";
    }

    public String saveInFile() {
        String saved = super.save(Author.DATA_FILE);
        if (saved.matches("1"))
            authors.add(this);
        return saved;
    }

    @Override
    public boolean updateFile() {
        try {
            FileHandler.overwriteCurrentListToFile(DATA_FILE, authors);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteFromFile() {
        // TODO: handle books
        authors.remove(this);
        try {
            FileHandler.overwriteCurrentListToFile(DATA_FILE, authors);
        } catch (Exception e) {
            authors.add(this);
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

}
