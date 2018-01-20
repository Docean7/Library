package db;

import db.Entity.Book;
import db.Entity.Order;
import db.Entity.User;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBManager {
    //CONSTANTS
    private static final String INSERT_USER = "INSERT INTO users " +
            "(firstname, lastname, login, password, email, telnumber) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_USER_ID_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    private static final String INSERT_BOOK_TO_CATALOG = "INSERT INTO catalog " +
            "(title, author, genre, category, publisher, country, year, rating) VALUES" +
            " (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_CATALOG = "SELECT * FROM catalog";
    private static final String FIND_BOOK_BY_ID = "SELECT * FROM catalog WHERE id=?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    private static final String ADD_BOOK_TO_USER = "INSERT INTO books_to_users (book_id, user_id) VALUES(?,?)";
    private static final String GET_BOOKS_FOR_USER = "SELECT book_id, expiration_date FROM books_to_users WHERE user_id=?";
    private static final String UPDATE_BOOK_STATUS = "UPDATE books_to_users SET delivered=? WHERE order_id=?";
    private static final String GET_ALL_ORDERS = "SELECT * FROM books_to_users";


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
            User checkUser = getUserByLogin(login);
            if (checkUser != null) {
                user.setId(checkUser.getId());
            }

            System.out.println(checkUser.getId());
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
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addBookToUser(int bookID, int userID) {
        try (Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement(ADD_BOOK_TO_USER);
            pstm.setInt(1,bookID);
            pstm.setInt(2,userID);
            //String date = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            //pstm.setString(3,date);
            pstm.executeUpdate();

        } catch (SQLException e) {
//            e.printStackTrace();
            return false;
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


    public void UpdateBookDeliveryStatus(boolean status, int orderId) {
        try(Connection conn = ds.getConnection()){
            PreparedStatement pstm = conn.prepareStatement(UPDATE_BOOK_STATUS);
            pstm.setBoolean(1,status);
            pstm.setInt(2, orderId);
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
                order.setOrderID(rs.getInt(1));
                order.setUserID(rs.getInt(2));
                order.setBookID(rs.getInt(3));
                order.setDelivered(rs.getBoolean(4));
                if(rs.getString(5) != null) {
                    order.setExpirationDate(rs.getString(5));
                }
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
