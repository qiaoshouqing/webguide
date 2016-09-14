package top.glimpse.webguide.entity;


/**
 * Created by joyce on 16-8-5.
 */
public class User {

    private int uid;
    private String name;
    private String password;
    private String email;
    private String avatar;
    private String created_at;

    public User() {
        super();
    }

    public User(String email, String password) {
        this(0, null, password, email, null, null);
    }

    public User(int uid) {
        this(uid, null, null, null, null, null);
    }

    public User(int uid, String password) {
        this(uid, null, password, null, null, null);
    }


    public User(int uid, String name, String password, String email, String avatar, String created_at) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.created_at = created_at;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
