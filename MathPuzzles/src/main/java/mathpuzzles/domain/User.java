
package mathpuzzles.domain;

/**
 * Represents the user of the application
 */

public class User {
    
    private Integer id;
    private String name;
    private String username;
    private String password;
    
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    
    
}
