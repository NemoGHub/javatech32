package accounts;

import ORMEntities.MySessionFactory;
import ORMEntities.User;
import ORMEntities.UserDAO;
import jdbc.JDBCConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    UserDAO userDAO = new UserDAO();
    private  final Map<String, UserProfile> loginToProfile;
//    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        this.loginToProfile = new HashMap<>();
//        this.sessionIdToProfile = new HashMap<>();
    }

    public boolean isUserRegistered(UserProfile userProfile) throws SQLException, ClassNotFoundException {
        JDBCConnection connection = new JDBCConnection();
        ResultSet resultSet = connection.SQLRequest(
                "SELECT * FROM `csulab6`.`users` WHERE `login` = '"
                        + userProfile.getLogin() + "' AND `password` = '"+ userProfile.getPassword() + "'"
        );
        if (resultSet.next()) return true;
        else return false;
    }

    public boolean isUserRegistered(UserProfile userProfile, boolean usingORM){
        User user = userDAO.findByLogin(userProfile.getLogin());
        if (user != null){
            if (user.getLogin().equals(userProfile.getLogin()) && user.getPassword().equals(userProfile.getPassword())){
                return true;
            }
        }
        return false;
    }

    public boolean addNewUser(UserProfile userProfile){
        loginToProfile.put(userProfile.getLogin(), userProfile);
        if (userDAO.findByLogin(userProfile.getLogin()) == null){
            User user = new User();
            user.setLogin(userProfile.getLogin());
            user.setPassword(userProfile.getPassword());
            userDAO.registerNewUser(user);
            return true;
        } else return false;
    }

    public boolean addNewUser(UserProfile userProfile, boolean usingORM) throws SQLException, ClassNotFoundException {
        loginToProfile.put(userProfile.getLogin(), userProfile);
        JDBCConnection connection = new JDBCConnection();
        if (!isUserRegistered(userProfile, true)){
            String sql =
                    "INSERT INTO `csulab6`.`users` (`login`,`password`) VALUES ('"
                            + userProfile.getLogin() + "', '"+ userProfile.getPassword() + "')";
            connection.SQLVoidRequest(sql);
            return true;
        } else return false;
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }


}
