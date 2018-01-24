package db;

import db.Entity.Book;
import db.Entity.Order;
import db.Entity.User;
import exception.DBException;
import exception.Messages;
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
    private static final String GET_ORDER = "SELECT * FROM books_to_users WHERE user_id=? AND book_id=?";


    private static DBManager instance;
    private static Context ctx;

    @Resource(name = "jdbc/library")
    private DataSource ds;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            try {
                ctx = new InitialContext();
                instance = new DBManager();
            } catch (NamingException e) {
               LOG.error(Messages.ERR_LOOKUP, e);
            }
            catch (DBException e) {
                LOG.error(Messages.ERR_DBMANAGER_INSTANCE, e);
            }
        }
        return instance;
    }

    private DBManager() throws DBException{
        try {
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/library");
        } catch (NamingException e) {
            LOG.error(Messages.ERR_DATA_SOURCE, e);
            throw new DBException(Messages.ERR_DATA_SOURCE, e);
        }
    }

    public void addNewUser(User user) throws DBException{
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
            LOG.error(Messages.ERR_ADD_USER, e);
            throw new DBException(Messages.ERR_ADD_USER, e);
        }
    }

    public boolean addBookToCatalog(Book book) throws DBException {

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
            LOG.error(Messages.ERR_ADD_BOOK_TO_CATALOG, e);
            throw new DBException(Messages.ERR_ADD_BOOK_TO_CATALOG, e);

        }
        return true;
    }

    public List<Book> getCatalog() throws DBException {
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
            LOG.error(Messages.ERR_GET_CATALOG, e);
            throw new DBException(Messages.ERR_GET_CATALOG, e);
        }
        return catalog;
    }

    public User getUserByLogin(String login) throws DBException {
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
            LOG.error(Messages.ERR_GET_USER, e);
            throw new DBException(Messages.ERR_GET_USER, e);
        }
        return null;
    }

    public boolean checkEmail(String email) throws DBException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(FIND_BY_EMAIL);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CHECK_EMAIL, e);
            throw new DBException(Messages.ERR_CHECK_EMAIL, e);
        }
        return true;
    }

    public Book getBookById(int id) throws DBException {
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
            LOG.error(Messages.ERR_GET_BOOK, e);
            throw new DBException(Messages.ERR_GET_BOOK, e);
        }
        return null;
    }

    public boolean addBookToUser(int bookID, int userID) throws DBException {
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
                    LOG.error(Messages.ERR_ROLLBACK, e);
                }
            }
            LOG.error(Messages.ERR_ADD_BOOK_TO_USER,e);
            throw new DBException(Messages.ERR_ADD_BOOK_TO_USER,e);
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                   LOG.error(Messages.ERR_CLOSE_CONNECTION, e);
                }
            }
        }
        return true;
    }

    public List<Book> getBooksForUser(int id ) throws DBException {
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
            LOG.error(Messages.ERR_GET_USERS_BOOKS, e);
            throw new DBException(Messages.ERR_GET_USERS_BOOKS, e);
        }
          return books;
    }


    public void markDelivered(boolean status, int orderId) throws DBException {
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement(UPDATE_BOOK_STATUS);
            pstm.setBoolean(1,status);
            String date = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            pstm.setString(2,date);
            pstm.setInt(3, orderId);
            pstm.executeUpdate();

        } catch (SQLException e) {
            LOG.error(Messages.ERR_MARK_DELIVERED, e);
            throw new DBException(Messages.ERR_MARK_DELIVERED, e);
        }
    }

    public boolean isBookDelivered (int userId, int bookId) throws DBException{
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement(GET_ORDER);
            pstm.setInt(1,userId);
            pstm.setInt(2,bookId);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getBoolean("delivered");
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_MARK_DELIVERED, e);
            throw new DBException(Messages.ERR_GET_ORDER, e);
        }
        return false;
    }

    public List<Order> getAllOrders() throws DBException {
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
            LOG.error(Messages.ERR_GET_ALL_ORDERS,e);
            throw new DBException(Messages.ERR_GET_ALL_ORDERS,e);
        }
        return orders;
    }

    public void deleteOrder(int userId, int bookId) throws DBException {
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
                    conn.rollback();
                } catch (SQLException e1) {
                    LOG.error(Messages.ERR_ROLLBACK, e);
                }
            }
            LOG.error(Messages.ERR_DELETE_ORDER,e);
            throw new DBException(Messages.ERR_DELETE_ORDER,e);
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error(Messages.ERR_CLOSE_CONNECTION,e);
                }
            }
        }
    }

    public boolean addOneDayOrder(String login, int bookId) throws DBException {
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
            if(conn != null){
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    LOG.error(Messages.ERR_ROLLBACK,e);
                }
            }
            LOG.error(Messages.ERR_ADD_ONE_DAY_ORDER,e);
            throw new DBException(Messages.ERR_ADD_ONE_DAY_ORDER,e);
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error(Messages.ERR_CLOSE_CONNECTION,e);
                }
            }
        }
        return true;
    }

    public void deleteBook(int book_id) throws DBException {
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement(DELETE_BOOK_BY_ID);
            pstm.setInt(1,book_id);
            pstm.executeUpdate();

        } catch (SQLException e) {
            LOG.error(Messages.ERR_DELETE_BOOK,e);
            throw new DBException(Messages.ERR_DELETE_BOOK,e);
        }
    }

    public void updateBook(Book book) throws DBException {
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
            LOG.error(Messages.ERR_UPDATE_BOOK,e);
            throw new DBException(Messages.ERR_UPDATE_BOOK,e);
        }
    }

    public void changeUserType(int type, String login) throws DBException {
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement(UPDATE_USER_TYPE);
            pstm.setInt(1,type);
            pstm.setString(2,login);
            pstm.executeUpdate();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CHANGE_USER_TYPE,e );
            throw new DBException(Messages.ERR_CHANGE_USER_TYPE,e);
        }
    }
}
