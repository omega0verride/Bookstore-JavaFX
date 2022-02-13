# Bookstore-JavaFX

On the last update changed all lists into ObservableList which made the software more responsive. Data and stats are updated imidiatelly on all tabs.

Made a lot of changes on the base models and generalized file handling using generics.

Added a dialog to ask if the user wants to delete all books related to the author he is deleting or just the author.

Added checboxes in the OrderView table to choose the books.

Fixed the search in the OrderView table so that it does not show books that are already in the order.

Added custom error messages that describe the errors better. I.e "Edit value invalid! ISBN must contain exactlly 13 numbers."

Added a ControllerCommon class that handles showing error messages, which now have a default timer of 5 seconds to dissapear.

Added a tablegenerator to show the books ordered as a table in the receipt. (This looks good in the console and txt file but when shown inside the dialog is a bit strange)

Cleaned up the code and added logging.

"Profile" and "Settings" have not been implemented yet. 

Was going to add line charts based on time intervals chosen by the user but closed the project because of deadlines.
https://docs.oracle.com/javafx/2/charts/line-chart.htm
