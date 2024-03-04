

import dto.TaskRequest;
import dto.UserRequest;
import org.junit.jupiter.api.Test;
import services.UserService;
import services.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private final UserService userService = new UserServiceImpl();

    @Test
    public void testWeCanRegisterUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Edmund");
        userRequest.setPassword("password");
        String response = userService.registerUser(userRequest);
        assertEquals("registration successful", response);
    }

    @Test
    public void testWeCanValidateUsername() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Edmund");
        userRequest.setPassword("password");
        userService.registerUser(userRequest);
        assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUser("Edmund");
        });
    }

    @Test
    public void testWeCanAddTask() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Edmund");
        userRequest.setPassword("password");
        userService.registerUser(userRequest);
        TaskRequest request = new TaskRequest();
        request.setTitle("Read");
        request.setDescription("I am reading today");
        request.setDate(LocalDateTime.of(2024, Month.FEBRUARY, 10, 12, 30));
        request.setUsername(userRequest.getUsername());
        String response = userService.addTask(request);
        assertEquals("Task created successfully", response);
    }

    @Test
    public void testWeCanDeleteTask() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Edmund");
        userRequest.setPassword("password");
        userService.registerUser(userRequest);
        TaskRequest request = new TaskRequest();
        request.setTitle("Read");
        request.setDescription("I am reading today");
        request.setDate(LocalDateTime.of(2024, Month.FEBRUARY, 10, 12, 30));
        request.setUsername(userRequest.getUsername());
        userService.addTask(request);
        String result = userService.deleteTask("Edmund", "Read");
        assertEquals("Task deleted successfully", result);
    }

    @Test
    public void testWeCanGetListOfTask() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Edmund");
        userRequest.setPassword("password");
        userService.registerUser(userRequest);
        TaskRequest request = new TaskRequest();
        request.setUsername(userRequest.getUsername());
        request.setTitle("Read");
        request.setDescription("I am reading today");
        request.setDate(LocalDateTime.of(2024, Month.FEBRUARY, 10, 12, 30));
        userService.addTask(request);
        assertEquals(1, userService.getTasks("Edmund").size());
    }
}
