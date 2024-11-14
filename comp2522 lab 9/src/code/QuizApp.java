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
import java.util.*;

/**
 * QuizApp represents an Application Object.
 * @author Jason Lau
 * @author Vincent Fung
 * @version 2024
 */
public class QuizApp extends Application {

    private static final int QUESTION_KEYS_INDEX = 0;
    private static final int ANSWER_VALUES_INDEX = 1;
    private static final int ZERO_INDEX = 0;

    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;
    private static final int ROOT_LAYOUT_SPACING = 10;
    private static final int QUESTIONS_CONTAINER_LAYOUT_SPACING= 1;

    private static final String INPUT_PATH = "src/resources/quiz.txt";
    private static final Path INPUT_FILE_PATH;

    private static final Map<String, String> quizQuestionsMap;
    private static final List<String> questionList;

    private static final int NUM_RAND_QUESTIONS = 10;
    private static final int INITIAL_NUM_CORRECT_ANSWERS = 0;

    private static int currentQuestionIndexNum = ZERO_INDEX;

    private static int correctAnswerCount = INITIAL_NUM_CORRECT_ANSWERS;

    static {
        quizQuestionsMap = new HashMap<>();
        INPUT_FILE_PATH = Paths.get(INPUT_PATH);

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
        // Root parent component
        final VBox rootLayout;

        // Components
        final VBox questionsContainer;
        final Button startQuizBtn;
        final Button submitBtn;

        // Questions
        List<String> randomQuestionsList;

        randomQuestionsList     = new ArrayList<>(NUM_RAND_QUESTIONS);

        questionsContainer      = new VBox(QUESTIONS_CONTAINER_LAYOUT_SPACING);
        submitBtn               = new Button("Submit");
        startQuizBtn            = new Button("Start Quiz");

        startQuizBtn.setOnAction(event ->
                startNewGame(
                        randomQuestionsList,
                        questionsContainer,
                        submitBtn
                )
        );

        rootLayout = new VBox(ROOT_LAYOUT_SPACING);
        rootLayout
                .getChildren()
                .addAll(
                        startQuizBtn,
                        questionsContainer
//                        currQuestionLabel
//                        submitBtn
//                        answerLabel
                );

        return rootLayout;
    }

//        Label currQuestionLabel;
//        currQuestionLabel = getAndDisplayRandomQuestionLabel();
//        submitBtn = new Button("Submit");

    private static void startNewGame(List<String> randomQuestionsList,
                                     final VBox questionsContainer,
                                     final Button submitBtn)
    {
        final String firstQuestion;
        final List<String> tempRandomQuestionsList;

        randomQuestionsList = getRandomQuestions();

        firstQuestion = randomQuestionsList.getFirst();

        displayCurrentQnAndUpdateIndex(randomQuestionsList, questionsContainer);

        tempRandomQuestionsList = randomQuestionsList;

        // Add submit button event handler
        submitBtn.setOnAction(event ->
                handleQuestionSubmit(
                        tempRandomQuestionsList,
                        questionsContainer
                )
        );

        questionsContainer
                .getChildren()
                .add(submitBtn);
    }

    private static void endGameAndReset(final VBox questionsContainer)
    {
        correctAnswerCount = INITIAL_NUM_CORRECT_ANSWERS;
        currentQuestionIndexNum = ZERO_INDEX;

        questionsContainer.getChildren().clear();
    }

    private static void handleQuestionSubmit(final List<String> randomQuestionsList,
                                            final VBox questionsContainer)
    {
        if (currentQuestionIndexNum < NUM_RAND_QUESTIONS)
        {
            displayCurrentQnAndUpdateIndex(
                    randomQuestionsList,
                    questionsContainer
            );
        }
        else
        {
            System.out.println("HAVE REACHED THE MAXIMUM NUMBER, ENDING GAME");
            endGameAndReset(questionsContainer);
        }
    }

    /*
     * Get the current question from randomQuestionsList and add to Label control component to display,
     * then increment currentQuestionIndexNum by 1.
     */
    private static void displayCurrentQnAndUpdateIndex(final List<String> randomQuestionsList,
                                                    final VBox questionsContainer)
    {
        final String currentQuestion;
        final Label questionLabel;

        currentQuestion = randomQuestionsList.get(currentQuestionIndexNum);


        questionLabel = new Label(currentQuestion);

        questionsContainer
                .getChildren()
                .add(questionLabel);

        currentQuestionIndexNum++;
    }

    private static List<String> getRandomQuestions()
    {
        final List<String> randomQuestions;

        Collections.shuffle(questionList);

        // Get the first NUM_RAND_QUESTIONS questions from the shuffled questionList
        randomQuestions = questionList.subList(ZERO_INDEX, NUM_RAND_QUESTIONS);

        return randomQuestions;
    }

    //////////////////////////////////

//    /**
//     * Displays Ten Random Questions
//     * @return
//     */
//    private static Label getAndDisplayRandomQuestions()
//    {
//        final Random randIndexGen;
//        final int randInt;
//
//        final Label questionLabel;
//        final String randomQuestion;
//
//        randIndexGen = new Random();
//        randInt = randIndexGen.nextInt(questionList.size());
//
//        randomQuestion = questionList.get(randInt);
//        questionLabel = new Label(randomQuestion);
//
//        return questionLabel;
//    }
//
//    private static Label displayAnswer(final String question)
//    {
//        final Label answerLabel;
//        final String answer;
//
//        answer = quizQuestionsMap.get(question);
//        answerLabel = new Label(answer);
//
//        return answerLabel;
//    }

//    private static String displayAnswerString(final String question)
//    {
//        final String answer;
//
//        answer = quizQuestionsMap.get(question);
//
//        return answer;
//    }

    // ------------------------------------------

    //TODO: Make below method run on separate thread
    /**
     * Helper method that helps reads a file specified.
     * <p>
     *     Initializes quizQuestionsMap
     * </p>
     */
    private static void readFile()
    {
        if (Files.exists(INPUT_FILE_PATH)) {

            // Using try-with-resources (large file) - to be auto-closed, resource needs to be declared inside try block
            try (final BufferedReader br = Files.newBufferedReader(INPUT_FILE_PATH))
            {
                String currentLine;
                String[] questionAndAnswer;

                currentLine = br.readLine();

                // Check if the first and subsequent lines are not null.
                while (currentLine != null)
                {
                    questionAndAnswer = currentLine.split("\\|");
                    quizQuestionsMap.put(questionAndAnswer[QUESTION_KEYS_INDEX], questionAndAnswer[ANSWER_VALUES_INDEX]);

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
