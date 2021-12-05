package core.entity;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String role;
    private String status;

    public User() {
    }

    public User(
            String username,
            String password,
            String email,
            String phone,
            String address,
            String role,
            String status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
