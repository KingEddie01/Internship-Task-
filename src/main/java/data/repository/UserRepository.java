package data.repository;

import data.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository {

    List<User> users = new ArrayList<User>();


    String saveUser(User user);

    int getUserCount();



    int setId();

    List<User> findUsers();

    }

