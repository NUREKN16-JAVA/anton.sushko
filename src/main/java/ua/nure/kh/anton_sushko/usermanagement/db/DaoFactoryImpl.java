package ua.nure.kh.anton_sushko.usermanagement.db;

public class DaoFactoryImpl extends DaoFactory {

	@SuppressWarnings("deprecation")
	@Override
	public UserDao getUserDao() {
		{
			UserDao result = null;
			try {
				Class classs = Class.forName(properties.getProperty(USER_DAO));
				result = (UserDao)classs.newInstance();
				result.setConnectionFactory(getConnectionFactory());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return result;
		}
	}

}