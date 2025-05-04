package accounts;

import jdbc.JDBCConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
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

    public boolean addNewUser(UserProfile userProfile) throws SQLException, ClassNotFoundException {
        loginToProfile.put(userProfile.getLogin(), userProfile);
        JDBCConnection connection = new JDBCConnection();
        if (!isUserRegistered(userProfile)){
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
