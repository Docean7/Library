package db;

import db.Entity.Book;
import db.Entity.Order;
import db.Entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DBManager {
    private static final Logger LOG = LogManager.getLogger(DBManager.class);
    //CONSTANTS
    private static final String INSERT_USER = "INSERT INTO users " +
            "(firstname, lastname, login, password, email, telnumber) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_USER_ID_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE BINARY login=?";
    private static final String INSERT_BOOK_TO_CATALOG = "INSERT INTO catalog " +
            "(title, author, genre, category, publisher, country, year, rating, quantity) VALUES" +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_CATALOG = "SELECT * FROM catalog";
    private static final String FIND_BOOK_BY_ID = "SELECT * FROM catalog WHERE id=?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    private static final String ADD_BOOK_TO_USER = "INSERT INTO books_to_users (book_id, user_id) VALUES(?,?)";
    private static final String GET_BOOKS_FOR_USER = "SELECT book_id, expiration_date FROM books_to_users WHERE user_id=?";
    private static final String UPDATE_BOOK_STATUS = "UPDATE books_to_users SET delivered=?, expiration_date=? WHERE order_id=?";
    private static final String GET_ALL_ORDERS = "SELECT order_id, user_id, book_id, delivered, expiration_date, title, login " +
            "FROM books_to_users JOIN catalog ON books_to_users.book_id = catalog.id JOIN users ON books_to_users.user_id = users.id";
    private static final String DELETE_ORDER = "DELETE FROM books_to_users WHERE user_id=? AND book_id=?";
    private static final String ADD_DAY_ORDER = "INSERT INTO books_to_users (user_id, book_id, delivered, expiration_date) VALUES (?,?,TRUE ,?)" ;
    private static final String SET_NEW_QUANTITY = "UPDATE catalog SET quantity=? WHERE id=?" ;
    private static final String DELETE_BOOK_BY_ID = "DELETE FROM catalog WHERE id=?";
    private static final String UPDATE_BOOK_INFO = "UPDATE catalog SET title=?, author=?, genre=?," +
            " category=?, publisher=?, country=?, year=?, rating=?, quantity=? WHERE id=?";
    private static final String UPDATE_USER_TYPE = "UPDATE users SET user_type=? WHERE BINARY login=?";


    private static DBManager instance;
    private static Context ctx;

    @Resource(name = "jdbc/library")
    private DataSource ds;

    public static DBManager getInstance() {
        if (instance == null) {
            try {
                ctx = new InitialContext();
            } catch (NamingException e) {
                e.printStackTrace();
            }
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
        try {
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/library");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void addNewUser(User user) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement pstm = conn.prepareStatement(INSERT_USER);
            pstm.setString(1, user.getFirstName());
            pstm.setString(2, user.getLastName());
            String login = user.getLogin();
            pstm.setString(3, login);
            String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            pstm.setString(4, hash);
            pstm.setString(5, user.getEmail());
            pstm.setString(6, user.getTelNumber());
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addBookToCatalog(Book book) {

        try (Connection conn = ds.getConnection()) {
            PreparedStatement pstm = conn.prepareStatement(INSERT_BOOK_TO_CATALOG);
            pstm.setString(1, book.getTitle());
            pstm.setString(2, book.getAuthor());
            pstm.setString(3, book.getGenre());
            pstm.setString(4, book.getCategory());
            pstm.setString(5, book.getPublisher());
            pstm.setString(6, book.getCountry());
            pstm.setInt(7, book.getYear());
            pstm.setDouble(8, book.getRating());
            pstm.setInt(9, book.getQuantity());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Book> getCatalog() {
        List<Book> catalog = new ArrayList<>();
        try (Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_CATALOG);
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setGenre(rs.getString(4));
                book.setCategory(rs.getString(5));
                book.setPublisher(rs.getString(6));
                book.setCountry(rs.getString(7));
                book.setYear(rs.getInt(8));
                book.setRating(rs.getDouble(9));
                catalog.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalog;
    }

    public User getUserByLogin(String login) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(FIND_USER_BY_LOGIN);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setLogin(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setTelNumber(rs.getString(7));
                user.setUserType(rs.getInt(8));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkEmail(String email) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(FIND_BY_EMAIL);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Book getBookById(int id) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement pstm = conn.prepareStatement(FIND_BOOK_BY_ID);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setGenre(rs.getString(4));
                book.setCategory(rs.getString(5));
                book.setPublisher(rs.getString(6));
                book.setCountry(rs.getString(7));
                book.setYear(rs.getInt(8));
                book.setRating(rs.getDouble(9));
                book.setQuantity(rs.getInt(10));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addBookToUser(int bookID, int userID) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            //adding book
            PreparedStatement pstm = conn.prepareStatement(ADD_BOOK_TO_USER);
            pstm.setInt(1,bookID);
            pstm.setInt(2,userID);
            pstm.executeUpdate();
            //updating quantity
            pstm = conn.prepareStatement(SET_NEW_QUANTITY);
            int quantity = getBookById(bookID).getQuantity() - 1;
            pstm.setInt(1,quantity);
            pstm.setInt(2,bookID);
            pstm.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if(conn != null){
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        } finally {
            //conn.setAutoCommit(true);
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public List<Book> getBooksForUser(int id ){
        ArrayList<Book> books = new ArrayList<>();
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement(GET_BOOKS_FOR_USER);
            pstm.setInt(1,id);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                Book book = getBookById(rs.getInt(1));
                String expiration = rs.getString(2);
                if(expiration != null) {
                    book.setExpiration(rs.getString(2));
                }
                if(book != null) {
                    books.add(book);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
          return books;
    }


    public void markDelivered(boolean status, int orderId) {
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement(UPDATE_BOOK_STATUS);
            pstm.setBoolean(1,status);
            String date = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            pstm.setString(2,date);
            pstm.setInt(3, orderId);
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders(){
        List<Order> orders = new ArrayList<>();
        try(Connection conn = ds.getConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_ORDERS);
            while (rs.next()){
                Order order = new Order();
                order.setId(rs.getInt(1));
                order.setUserID(rs.getInt(2));
                order.setBookID(rs.getInt(3));
                order.setDelivered(rs.getBoolean(4));
                if(rs.getString(5) != null) {
                    order.setExpirationDate(rs.getString(5));
                }
                order.setBookTitle(rs.getString(6));
                order.setUsername(rs.getString(7));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void deleteOrder(int userId, int bookId) {
        Connection conn = null;
        try{
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstm = conn.prepareStatement(DELETE_ORDER);
            pstm.setInt(1, userId);
            pstm.setInt(2, bookId);
            pstm.executeUpdate();

            pstm = conn.prepareStatement(SET_NEW_QUANTITY);
            int quantity = getBookById(bookId).getQuantity()+1;
            pstm.setInt(1, quantity);
            pstm.setInt(2,bookId);
            pstm.executeUpdate();

            conn.commit();
        } catch (SQLException e){
           // e.printStackTrace();
            if(conn != null){
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean addOneDayOrder(String login, int bookId){
        Connection conn = null;
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstm = conn.prepareStatement(ADD_DAY_ORDER);
            int userId = getUserByLogin(login).getId();
            String expirationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            pstm.setInt(1,userId);
            pstm.setInt(2,bookId);
            pstm.setString(3,expirationDate);
            pstm.executeUpdate();

            pstm = conn.prepareStatement(SET_NEW_QUANTITY);
            int quantity = getBookById(bookId).getQuantity() - 1;
            pstm.setInt(1,quantity);
            pstm.setInt(2,bookId);
            pstm.executeUpdate();

            conn.commit();
        }  catch (SQLException e){
            // e.printStackTrace();
            if(conn != null){
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public void deleteBook(int book_id) {
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement(DELETE_BOOK_BY_ID);
            pstm.setInt(1,book_id);
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {
        System.out.println("Updating " + book);
        try(Connection conn = ds.getConnection()){
           PreparedStatement pstm = conn.prepareStatement(UPDATE_BOOK_INFO);
            pstm.setString(1,book.getTitle());
            pstm.setString(2,book.getAuthor());
            pstm.setString(3,book.getGenre());
            pstm.setString(4,book.getCategory());
            pstm.setString(5,book.getPublisher());
            pstm.setString(6,book.getCountry());
            pstm.setInt(7,book.getYear());
            pstm.setDouble(8,book.getRating());
            pstm.setInt(9,book.getQuantity());
            pstm.setInt(10,book.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeUserType(int type, String login){
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement(UPDATE_USER_TYPE);
            pstm.setInt(1,type);
            pstm.setString(2,login);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
