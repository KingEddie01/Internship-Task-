package services;

import data.model.Task;
import data.model.User;
import dto.TaskRequest;
import dto.UserRequest;

import java.util.List;

public interface UserService {

   String registerUser(UserRequest userRequest);

   int generateUserId();

   User findByUsername(String username);

   String addTask(TaskRequest taskRequest);

   String deleteTask(String username, String title);

   List<User> findAllUsers();

   String validateUser(String username);

   int count();

   Task findByTitle(String title);

   List<Task> getTasks(String username);

   String saveTask(Task task);
}
