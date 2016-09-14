package top.glimpse.webguide.data;

import top.glimpse.webguide.entity.User;

/**
 * Created by joyce on 16-5-11.
 */
public interface UserRepository {
    User login(User user);
    User signup(User user);

    User getUser(int uid);
    String getPassword(int uid);
    void setPassword(User user);
}
