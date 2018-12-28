package ua.nure.kh.anton_sushko.usermanagement.db;

import ua.nure.kh.anton_sushko.usermanagement.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockUserDao implements UserDao {

    private static Long id = 0L;
    private static Map<Long, User> users = new HashMap<>();

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }

    @Override
    public User create(User user) throws DatabaseException {
        Long currentId = ++id;
        user.setId(currentId);
        users.put(currentId, user);

        return user;
    }

    @Override
    public User find(Long id) throws DatabaseException {
        return users.get(id);
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        return users.values();
    }

    @Override
    public void update(User user) throws DatabaseException {
        Long id = user.getId();
        users.put(id, user);
    }

    @Override
    public void delete(User user) throws DatabaseException {
        Long id = user.getId();
        users.remove(id);
    }

    public void reset() {
        users.clear();
    }
    
    @Override
	public Collection<User> find(String firstName, String lastName) throws DatabaseException {
    throw new UnsupportedOperationException();
	}
}