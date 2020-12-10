import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ArgumentHandler {

//    Command line arguments:
//            -l   Lists all the tasks
//            -a   Adds a new task
//            -r   Removes an task
//            -c   Completes an task

    public void handleArgument(String[] arguments) {
        if (arguments.length == 0) {
            System.out.println(getInstructions());
        } else {
            switch (arguments[0]) {
                case "-l": {
                    getTasks();
                    break;
                }

                case "-a": {
                    addTasks(arguments);
                    break;
                }

                case "-r": {
                    try {
                        removeTasks(arguments[1]);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Unable to remove: no index provided");
                    }
                    break;
                }

                default: {
                    System.out.println("Unsupported argument");
                    break;
                }
            }
        }
    }

    private void removeTasks(String removeIndexString) {

        try {
            int removeIndex = Integer.parseInt(removeIndexString) - 1;

            Path instructionPath = tasksPath();
            List<String> taskList = new ArrayList<>();
            try {
                taskList = Files.readAllLines(instructionPath);
            } catch (IOException e) {
                System.out.println("Sry, task list not available");
            }
            if (taskList.size() == 0) {
                System.out.println("No todos to delete");
            }
            try {
                taskList.remove(removeIndex);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Unable to remove: index is out of bound");
            }
            try {
                Path filePath = tasksPath();
                Files.write(filePath, taskList);
            } catch (Exception e) {
                System.out.println("Uh-oh, could not write the file!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to remove: index is not a number");
        }
    }

    private String getInstructions() {
        Path instructionPath = Paths.get("C:\\greenfox\\Toth-P_handle-todo-app\\TODOApplication\\src\\instructions");
        List<String> content = new ArrayList<>();
        try {
            content = Files.readAllLines(instructionPath);
        } catch (IOException e) {
            System.out.println("Sry, instructions not available, have fun experimenting");
        }

        StringBuilder contentAsString = new StringBuilder();
        for (String line : content) {
            contentAsString.append(line);
            contentAsString.append("\n");
        }
        return contentAsString.toString();

    }

    private void getTasks() {
        Path instructionPath = tasksPath();
        List<String> taskList = new ArrayList<>();
        try {
            taskList = Files.readAllLines(instructionPath);
        } catch (IOException e) {
            System.out.println("Sry, task list not available");
        }
        if (taskList.size() == 0) {
            System.out.println("No todos for today! :)");
        }
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(i + 1 + " - " + taskList.get(i));
        }
    }

    private void addTasks(String[] arguments) {


        if (arguments.length == 1) {
            System.out.println("Unable to add: no task provided");
        }
        ArrayList<String> tasks = new ArrayList<>();

        for (int i = 0; i < arguments.length - 1; i++) {
            tasks.add(arguments[i + 1]);
        }


        try {
            Path filePath = tasksPath();
            Files.write(filePath, tasks, StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("Uh-oh, could not write the file!");
        }
    }

    private Path tasksPath() {
        return Paths.get("C:\\greenfox\\Toth-P_handle-todo-app\\TODOApplication\\src\\instructions");
    }

}