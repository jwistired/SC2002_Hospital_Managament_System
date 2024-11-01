public abstract class UserController {
    protected User user;
    protected UserView userView;

    public UserController(User user, UserView userView) {
        this.user = user;
        this.userView = userView;
    }

    public abstract void ShowMenu();
}
