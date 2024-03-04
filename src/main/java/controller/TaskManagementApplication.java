package controller;

import data.model.Task;
import dto.TaskRequest;
import dto.UserRequest;
import services.UserService;
import services.UserServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TaskManagementApplication {

    private static final Scanner input = new Scanner(System.in);
    private static final UserService userService = new UserServiceImpl();
    private static final UserRequest userRequest = new UserRequest();
    private static final TaskRequest taskRequest = new TaskRequest();

    public static void main(String[] args) {
        System.out.println("\nTask Management System:");
        register();
    }

    public static void display() {
        while (true) {
            System.out.println("Task Management Options");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. List Tasks");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1 -> addTask();
                case 2 -> removeTask();
                case 3 -> listTasks();
                case 4 -> exit();
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void exit() {
        System.out.println("Goodbye");
        System.exit(0);
    }

    public static void register() {
        System.out.println("Kindly Register");
        System.out.println("Enter your username : ");
        String username = input.nextLine();
        if (!username.matches("^\\D+$*")) {
            System.out.println("Enter valid username ");
            register();
        }
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty");
            register();
        }
        System.out.println("Enter your password : ");
        String password = input.nextLine();
        if (password.isEmpty()) {
            System.out.println("Password  is empty");
            register();
        } else {
            userRequest.setUsername(username);
            userRequest.setPassword(password);
            String message = userService.registerUser(userRequest);
            System.out.println(message);
        }
        display();
    }

    public static void addTask() {
        System.out.println("Add Task");
        System.out.println("Enter Title");
        String title = input.next();
        if (!title.matches("^\\D+$*")) {
            System.out.println("Enter a valid title");
            addTask();
            return;
        }
        taskRequest.setTitle(title);

        System.out.println("Enter Description");
        String description = input.next();
        if (!description.matches("^\\D+$*")) {
            System.out.println("Enter valid description");
            addTask();
            return;
        }
        taskRequest.setDescription(description);

        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dueDateString = input.next();
        try {
            LocalDate dueDate = LocalDate.parse(dueDateString);
            taskRequest.setDate(dueDate.atStartOfDay());
        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            addTask();
            return;
        }
        taskRequest.setUsername(userRequest.getUsername());
        String result = userService.addTask(taskRequest);
        System.out.println(result);

        System.out.println("Add another task ?");
        String answer = input.next();
        if (answer.equalsIgnoreCase("yes")) {
            addTask();
        } else {
            display();
        }
    }

    public static void removeTask() {
        System.out.println("Remove Task");
        try {
            System.out.println("Enter title of Task to be deleted");
            String value = input.next();
            if (!value.matches("^\\D+$*")) {
                System.out.println("Enter a valid title");
                removeTask();
                return;
            }
            String username = userRequest.getUsername();
            String result2 = userService.deleteTask(username, value);
            System.out.println(result2);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            display();
        }
        System.out.println("Remove another Task ?");
        String reply = input.nextLine();
        if (reply.equalsIgnoreCase("yes")) {
            removeTask();
        } else {
            display();
        }
    }

    public static void listTasks() {
        System.out.println("List of Tasks");
        try {
            String username = userRequest.getUsername();

            List<Task> tasks = userService.getTasks(username);

            if (tasks == null || tasks.isEmpty())
                System.out.println("No tasks found for user: " + username);
            else
                for (Task task : tasks)
                    System.out.println(task);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            display();

        }
            System.out.println("Go back to options? (yes/no)");
            String word = input.next();
            if (word.equalsIgnoreCase("yes")) {
                display();
            } else {
                listTasks();
            }
        }
    }

