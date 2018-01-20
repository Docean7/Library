package Server.Tags;

import db.Entity.Book;

import java.util.List;

public class CheckPresenceFunc {
    public static boolean isPresent(Object bookList, String Id){
        //System.out.println("Figure out if present");
        int bookId = Integer.parseInt(Id);
        boolean present = false;
        List<Book> usersBooks = (List<Book>)bookList;
        if(usersBooks != null) {
            for (Book book : usersBooks) {
                if (book.getId() == bookId){
                    present = true;
                }
            }
        }

        return present;
    }
}
