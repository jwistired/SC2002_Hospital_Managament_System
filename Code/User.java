import java.io.Serializable;

abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String hospitalId;
    protected String name;
    protected String password;
    protected String role;

    public User(String hospitalId, String name, String password, String role) {
        this.hospitalId = hospitalId;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public abstract void showMenu(UserManager userManager); // Added userManager reference to save the changes
}