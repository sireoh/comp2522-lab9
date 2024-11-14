package code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import javafx.scene.control.Button;

public class QuizApp extends Application {

    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;

    @Override
    public void start(final Stage primaryStage)
    {
        primaryStage.setTitle("Quiz App!");

        // Create root layout node with child components
        VBox layout = createLayout();

        // Create a 'container' for content within window
        // passing in the root content node
        // There can be more than one scene
        Scene scene = new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);

        // Add the scene to the stage
        // Add content within window to the window
        primaryStage.setScene(scene);

        // Show the stage / make Window visible
        primaryStage.show();
    }

    private VBox createLayout()
    {
        VBox vBox = new VBox();
//        Button btn = new Button();
//        vBox.
        return vBox;
    }

    public static void main(final String[] args)
    {
        // Creates an instance of the class extending Application,
        // and calls that instance's start method (passing in a Stage object to set the Window)

        // Initializes JavaFX runtime environment and JavaFX app thread
        // Always required for JavaFX apps
        // Analogy - npm run
        Application.launch(args);
    }
}
