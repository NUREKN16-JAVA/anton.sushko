 package ua.nure.kn.anton_sushko.usermanagement.db;

 
 import ua.nure.kn.anton_sushko.usermanagement.User;

 import java.sql.*;
 import java.util.Collection;
 import java.util.LinkedList;

 class HsqlUserDao implements UserDao {

     private static final String SELECT_ALL_QUERY = "SELECT id, first_name, last_name, date_of_birth FROM users";
     private static final String SELECT_ONE_QUERY = "SELECT id, first_name, last_name, date_of_birth FROM users WHERE id = (?)";
     private static final String INSERT_QUERY = "INSERT INTO users (first_name, last_name, date_of_birth) VALUES (?, ?, ?)";
     private static final String UPDATE_QUERY = "UPDATE users SET first_name = (?), last_name = (?), date_of_birth = (?) WHERE id = (?)";
     private static final String DELETE_QUERY = "DELETE FROM users WHERE id = (?)";
     private ConnectionFactory connectionFactory;

     public HsqlUserDao() {
     }

     public HsqlUserDao(ConnectionFactory connectionFactory) {
         this.connectionFactory = connectionFactory;
     }

     public void setConnectionFactory(ConnectionFactory connectionFactory) {
         this.connectionFactory = connectionFactory;
     }

     public ConnectionFactory getConnectionFactory() {
         return connectionFactory;
     }

     @Override
     public User create(User user) throws DatabaseException {
         try {
             Connection connection = this.connectionFactory.createConnection();

             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
             statement.setString(1, user.get_firstname());
             statement.setString(2, user.get_lastname());
             statement.setDate(3, new Date(user.get_birthdate().getTime()));

             int n = statement.executeUpdate();
             if (n != 1) {
                 throw new DatabaseException("Num of inserted rows: " + n);
             }

             CallableStatement callableStatement = connection.prepareCall("call IDENTITY ()");
             ResultSet keys = callableStatement.executeQuery();

             if (keys.next()) {
                 user.set_id(keys.getLong(1));
             }

             keys.close();
             callableStatement.close();
             statement.close();
             connection.close();

             return user;

         } catch (SQLException e) {
             throw new DatabaseException(e);
         }
     }

     @Override
     public User find(Long id) throws DatabaseException {
         try {
             Connection connection = this.connectionFactory.createConnection();

             PreparedStatement statement = connection.prepareStatement(SELECT_ONE_QUERY);
             statement.setLong(1, id);
             ResultSet resultSet = statement.executeQuery();

             if (resultSet.next()) {
                 Long recordId = resultSet.getLong("id");
                 String firstName = resultSet.getString("first_name");
                 String lastName = resultSet.getString("last_name");
                 Date dateOfBirth = resultSet.getDate("date_of_birth");

                 if (resultSet.next()) {
                     throw new DatabaseException("There are more than one user with specified id in the database.");
                 }

                 return new User(recordId, firstName, lastName, dateOfBirth);

             }

             throw new DatabaseException("User not found.");

         } catch (SQLException e) {
             throw new DatabaseException(e);
         }
     }

     @Override
     public Collection<User> findAll() throws DatabaseException {
         try {
             Collection<User> result = new LinkedList<>();

             Connection connection = this.connectionFactory.createConnection();

             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);

             while (resultSet.next()) {
                 Long id = resultSet.getLong("id");
                 String firstName = resultSet.getString("first_name");
                 String lastName = resultSet.getString("last_name");
                 Date dateOfBirth = resultSet.getDate("date_of_birth");

                 User user = new User(id, firstName, lastName, dateOfBirth);
                 result.add(user);
             }

             return result;

         } catch (SQLException e) {
             throw new DatabaseException(e);
         }
     }

     @Override
     public void update(User user) throws DatabaseException {
         try {
             Connection connection = this.connectionFactory.createConnection();

             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
             statement.setString(1, user.get_firstname());
             statement.setString(2, user.get_lastname());
             statement.setDate(3, new Date(user.get_birthdate().getTime()));
             statement.setLong(4, user.get_id());

             int n = statement.executeUpdate();

             if (n != 1) {
                 throw new DatabaseException("Num of updated rows: " + n);
             }

         } catch (SQLException e) {
             throw new DatabaseException(e);
         }
     }

     @Override
     public void delete(User user) throws DatabaseException {
         try {
             Connection connection = this.connectionFactory.createConnection();

             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
             statement.setLong(1, user.get_id());

             int n = statement.executeUpdate();

             if (n != 1) {
                 throw new DatabaseException("Num of deleted rows: " + n);
             }

         } catch (SQLException e) {
             throw new DatabaseException(e);
         }
     }
 }