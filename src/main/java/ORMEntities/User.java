package ORMEntities;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "login", nullable = false)
    private String login; // varchar 255

    @Column(name = "password")
    private String password; // varchar 255

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
