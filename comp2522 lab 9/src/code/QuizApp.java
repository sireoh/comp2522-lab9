package code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * QuizApp represents an Application Object.
 * @author Jason Lau
 * @author Vincent Fung
 * @version 2024
 */
public class QuizApp extends Application {

    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;

    private static final int ROOT_LAYOUT_SPACING = 10;

    private static final int QUESTION_KEYS = 0;
    private static final int ANSWER_VALUES = 1;
    private static final String INPUT_PATH = "src/resources/quiz.txt";

    private static int currentQuestionNumber = 1;

    private static final Path inputFilePath;
    private static final Map<String, String> quizQuestionsMap;
    private static final List<String> questionList;

    static {
        quizQuestionsMap = new HashMap<>();
        inputFilePath = Paths.get(INPUT_PATH);

//        if (!Files.exists(inputFilePath))
//        {
//            throw new IOException("Input file does not exist");
//        }

        // Get and store questions and answers from file
        readFile();
        questionList = new ArrayList<>(quizQuestionsMap.keySet());
    }

    public static void main(final String[] args) throws IOException
    {
//        questionList.forEach(System.out::println);

//        for (final String key : keyList)
//        {
//            final String answer = quizQuestions.get(key);
//            System.out.println("Question: " + key + "\nAnswer: " + answer + "\n");
//        }

        //---------------------------------------

        // Creates an instance of the class extending Application,
        // and calls that instance's start method (passing in a Stage object to set the Window)

        // Initializes JavaFX runtime environment and JavaFX app thread
        // Always required for JavaFX apps
        // Analogy - npm run
        Application.launch(args);
    }

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

    /**
     * Create a Root Layout for the Application
     * @return the Root Layout as a Vbox.
     */
    private static VBox createLayout()
    {
        final VBox rootLayout;
        final Button submitBtn;
        final Label questionLabel;
        final Label answerLabel;

        rootLayout = new VBox(ROOT_LAYOUT_SPACING);
        questionLabel = getAndDisplayRandomQuestionLabel();
        submitBtn = new Button("Submit Question");

        answerLabel = new Label("");
//        answerLabel = displayAnswer(questionLabel.getText());

        submitBtn.setOnAction(e -> answerLabel.setText(displayAnswerString(questionLabel.getText())));

        rootLayout
                .getChildren()
                .addAll(
                        questionLabel,
                        submitBtn,
                        answerLabel
                );

        return rootLayout;
    }

    //-----------------------------------

    private static Label getAndDisplayRandomQuestionLabel()
    {
        final Random randIndexGen;
        final int randInt;

        final Label questionLabel;
        final String randomQuestion;

        randIndexGen = new Random();
        randInt = randIndexGen.nextInt(questionList.size());

        randomQuestion = questionList.get(randInt);

        System.out.println(randomQuestion);

        questionLabel = new Label(randomQuestion);

        return questionLabel;
    }

    private static Label displayAnswer(final String question)
    {
        final Label answerLabel;
        final String answer;

        answer = quizQuestionsMap.get(question);
        answerLabel = new Label(answer);

        return answerLabel;
    }

    private static String displayAnswerString(final String question)
    {
        final String answer;

        answer = quizQuestionsMap.get(question);

        return answer;
    }

    // ------------------------------------------

    //TODO: Make below method run on separate thread
    /**
     * Helper method that helps reads a file specified.
     */
    private static void readFile()
    {
        if (Files.exists(inputFilePath)) {

            // Using try-with-resources (large file) - to be auto-closed, resource needs to be declared inside try block
            try (final BufferedReader br = Files.newBufferedReader(inputFilePath))
            {
                String currentLine;
                String[] questionAndAnswer;

                currentLine = br.readLine();

                // Check if the first and subsequent lines are not null.
                while (currentLine != null)
                {
                    questionAndAnswer = currentLine.split("\\|");
                    quizQuestionsMap.put(questionAndAnswer[QUESTION_KEYS], questionAndAnswer[ANSWER_VALUES]);

                    currentLine = br.readLine();
                }
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
