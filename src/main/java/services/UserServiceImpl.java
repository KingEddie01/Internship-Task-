package services;

import data.model.Task;
import data.model.User;
import data.repository.UserRepoImpl;
import data.repository.UserRepository;
import dto.TaskRequest;
import dto.UserRequest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    UserRepository userRepository = new UserRepoImpl();

    @Override
    public String registerUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(validateUser(userRequest.getUsername()));
        user.setPassword(userRequest.getPassword());
        user.setId(generateUserId());
        userRepository.saveUser(user);
        return "registration successful";
    }

    @Override
    public int generateUserId() {
        return userRepository.setId();
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = findAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new NullPointerException("User not found");
    }

    @Override
    public String addTask(TaskRequest taskRequest) {
        User user = findByUsername(taskRequest.getUsername());
        if (user != null) {
            Task task = new Task();
            task.setUsername(taskRequest.getUsername());
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setDate(taskRequest.getDate());
            List<Task> tasks = user.getTasks();
            if (tasks == null) {
                tasks = new ArrayList<>();
                user.setTasks(tasks);
            }
            tasks.add(task);
            return "Task created successfully";
        } else {
            return "User not found, task was not saved";
        }
    }


    @Override
    public String deleteTask(String username, String title) {
        User user = findByUsername(username);
        List<Task> tasks = user.getTasks();
        if (tasks == null) {
            throw new NullPointerException("No task added for " + username);
        }
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("Tasks are empty for user: " + username);
        }
        boolean taskFound = false;
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                tasks.remove(task);
                taskFound = true;
                break;
            }
        }
        if (!taskFound) {
            throw new IllegalArgumentException("Task not found");
        }
        return "Task deleted successfully";
    }


    @Override
    public List<User> findAllUsers() {
        return userRepository.findUsers();
    }

    @Override
    public String validateUser(String username) {
        List<User> users = findAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                throw new IllegalArgumentException("Username already exists");
            }
        }
        return username;
    }

    @Override
    public int count() {
        return userRepository.getUserCount();
    }

    @Override
    public Task findByTitle(String title) {
        User user = new User();
        List<Task> tasks = user.getTasks();
        for (Task task2 : tasks) {
            if (task2.getTitle().equals(title)) {
                return task2;
            }
        }
        throw new IllegalArgumentException("Task does not exist");
    }

    @Override
    public List<Task> getTasks(String username) {
        User user = findByUsername(username);
        List<Task> tasks = user.getTasks();
        if (tasks == null) {
            throw new NullPointerException("No task added for " + username);
        }
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("Tasks are empty for user: " + username);
        }
        return tasks;
    }

    @Override
    public String saveTask(Task task) {
        User user = findByUsername(task.getUsername());
        user.getTasks().add(task);
        return "Task created successfully";
    }
}
