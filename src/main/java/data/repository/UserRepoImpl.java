package data.repository;

import data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepoImpl implements UserRepository{

    List<User> users = new ArrayList<User>();
    @Override
    public String saveUser(User user) {
        users.add(user);
        return "Successfully saved";
    }

    @Override
    public int getUserCount() {
        return users.size();
    }

    @Override
    public int setId() {
        return getUserCount() + 1;
    }

    @Override
    public List<User> findUsers() {
        return users;
    }








}
