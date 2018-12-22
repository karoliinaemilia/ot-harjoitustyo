package mathpuzzles.gui;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import mathpuzzles.dao.Database;
import mathpuzzles.dao.RecordDao;
import mathpuzzles.dao.UserDao;
import mathpuzzles.logics.ProblemLogic;
import mathpuzzles.logics.UserLogic;
import mathpuzzles.domain.Operation;
import mathpuzzles.logics.RecordLogic;

/**
 * Responsible for generating the graphic user interface
 */
public class MathPuzzlesUi extends Application {

    private Stage primaryStage;
    private Label menuLabel = new Label();
    private Scene loginScene;
    private Scene menuScene;
    private Scene newUserScene;
    private Scene problemScene;
    private Scene challengeScene;
    private Scene recordScene;
    private UserLogic userLogic;
    private ProblemLogic problemLogic;
    private RecordLogic recordLogic;
    private Label title = new Label("");
    private Label loginMessage;
    private int minValue = 2;
    private int maxValue = 20;
    private Label problemLabel = new Label("");
    private Label challengeProblemLabel = new Label("");
    private Operation operation = null;
    private Timeline timeline;
    private Integer seconds = 60;
    private Integer secondsAtStart = 0;

    @Override
    public void init() throws Exception {
        Database database = new Database("jdbc:sqlite:mathpuzzles.db");
        database.initializeDatabase();
        UserDao userDao = new UserDao(database);
        RecordDao recordDao = new RecordDao(database, userDao);
        userLogic = new UserLogic(userDao);
        problemLogic = new ProblemLogic();
        recordLogic = new RecordLogic(recordDao);
    }

    /**
     * Generates a scene in which user can login
     */
    public void setLoginScene() {
        VBox loginPane = new VBox(10);
        HBox inputPane = new HBox(10);
        loginPane.setPadding(new Insets(10));
        Label loginLabel = new Label("username");
        TextField usernameInput = new TextField();
        Label passwordLabel = new Label("password");
        PasswordField passwordInput = new PasswordField();

        inputPane.getChildren().addAll(loginLabel, usernameInput, passwordLabel, passwordInput);
        loginMessage = new Label();

        Button loginButton = new Button("login");
        Button createButton = new Button("create new user");

        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            menuLabel.setText(username + " logged in");
            try {
                if (userLogic.login(username, password)) {
                    loginMessage.setText("");
                    primaryStage.setScene(menuScene);
                    usernameInput.setText("");
                    passwordInput.setText("");
                    title.setText("Welcome " + userLogic.getCurrentUser().getName());
                    recordLogic.setCurrentUser(userLogic.getCurrentUser());
                } else {
                    loginMessage.setText("username or password incorrect");
                    loginMessage.setTextFill(Color.RED);
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

        });

        createButton.setOnAction(e -> {
            usernameInput.setText("");
            passwordInput.setText("");
            primaryStage.setScene(newUserScene);
        });

        loginPane.getChildren().addAll(loginMessage, inputPane, loginButton, createButton);

        loginScene = new Scene(loginPane, 700, 300);
    }

    /**
     * Generates a scene in which user can create login credentials
     */
    public void setNewUserScene() {
        VBox newUserPane = new VBox(10);

        HBox newUsernamePane = new HBox(10);
        newUsernamePane.setPadding(new Insets(10));
        TextField newUsernameInput = new TextField();
        Label newUsernameLabel = new Label("username");
        newUsernameLabel.setPrefWidth(200);

        newUsernamePane.getChildren().addAll(newUsernameLabel, newUsernameInput);

        HBox newPasswordPane = new HBox(10);
        newPasswordPane.setPadding(new Insets(10));
        PasswordField newPasswordInput = new PasswordField();
        Label newPasswordLabel = new Label("password");
        newPasswordLabel.setPrefWidth(200);

        newPasswordPane.getChildren().addAll(newPasswordLabel, newPasswordInput);

        HBox newConfirmationPane = new HBox(10);
        newConfirmationPane.setPadding(new Insets(10));
        PasswordField confirmationInput = new PasswordField();
        Label confirmationLabel = new Label("password confirmation");
        confirmationLabel.setPrefWidth(200);

        newConfirmationPane.getChildren().addAll(confirmationLabel, confirmationInput);

        HBox newNamePane = new HBox(10);
        newNamePane.setPadding(new Insets(10));
        TextField newNameInput = new TextField();
        Label newNameLabel = new Label("name");
        newNameLabel.setPrefWidth(200);
        newNamePane.getChildren().addAll(newNameLabel, newNameInput);

        Label userCreationMessage = new Label();

        Button createNewUserButton = new Button("create");
        createNewUserButton.setPadding(new Insets(10));

        createNewUserButton.setOnAction(e -> {
            String username = newUsernameInput.getText();
            String password = newPasswordInput.getText();
            String confirmation = confirmationInput.getText();
            String name = newNameInput.getText();

            String valid = userLogic.checkIfValidInputs(username, name, password, confirmation);

            if (valid == null) {
                try {
                    if (userLogic.createUser(name, username, password)) {
                        userCreationMessage.setText("");
                        newNameInput.setText("");
                        newPasswordInput.setText("");
                        newUsernameInput.setText("");
                        confirmationInput.setText("");
                        loginMessage.setText("new user created");
                        loginMessage.setTextFill(Color.GREEN);
                        primaryStage.setScene(loginScene);
                    } else {
                        userCreationMessage.setText("username has to be unique");
                        userCreationMessage.setTextFill(Color.RED);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } else {
                userCreationMessage.setText(valid);
                userCreationMessage.setTextFill(Color.RED);
            }

        });

        newUserPane.getChildren().addAll(userCreationMessage, newUsernamePane, newPasswordPane, newConfirmationPane, newNamePane, createNewUserButton);

        newUserScene = new Scene(newUserPane, 700, 300);
    }

    /**
     * Generates a scene in which user can choose which kind of problems to
     * solve
     */
    public void setMainScene() {
        VBox mainPane = new VBox(10);

        menuScene = new Scene(mainPane, 700, 400);

        HBox menuPane = new HBox(10);
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        Button logoutButton = new Button("logout");
        menuPane.getChildren().addAll(menuLabel, menuSpacer, logoutButton);

        logoutButton.setOnAction(e -> {
            primaryStage.setScene(loginScene);
            userLogic.logout();
        });

        HBox centerPane = new HBox(10);

        VBox newProblemPane = new VBox(10);

        Label errorMessage = new Label("");

        Label setProblems = new Label("Choose what kind of problems you want");
        setProblems.setStyle("-fx-font-size: 2em;");

        HBox newOperationPane = new HBox(10);
        newOperationPane.setPadding(new Insets(10));
        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "ALL",
                        "DIVISION",
                        "SUBTRACTION",
                        "ADDITION",
                        "MULTIPLICATION"
                );
        ComboBox comboBox = new ComboBox(options);
        Label newOperationLabel = new Label("operation");
        newOperationLabel.setPrefWidth(200);

        newOperationPane.getChildren().addAll(newOperationLabel, comboBox);

        HBox newValuesPane = new HBox(10);

        newValuesPane.setPadding(new Insets(10));
        Label minValueLabel = new Label("smallest value");
        Label maxValueLabel = new Label("largest value");
        TextField minValueInput = new TextField();
        TextField maxValueInput = new TextField();

        newValuesPane.getChildren().addAll(minValueLabel, minValueInput, maxValueLabel, maxValueInput);

        Button getProblem = new Button("get");
        
        errorMessage.setTextFill(Color.RED);

        getProblem.setOnAction(e -> {
            if (comboBox.getSelectionModel().isEmpty()) {
                errorMessage.setText("Select operation type");
            } else if (problemLogic.checkIfValidNumbers(minValueInput.getText(), maxValueInput.getText()) == null) {
                maxValue = Integer.parseInt(maxValueInput.getText());
                minValue = Integer.parseInt(minValueInput.getText());
                maxValueInput.setText("");
                minValueInput.setText("");
                this.operation = problemLogic.getOperationFromComboBox((String) comboBox.getValue());
                problemLabel.setText(problemLogic.makeProblem(minValue, maxValue, operation));
                errorMessage.setText("");
                primaryStage.setScene(problemScene);
            } else {
                errorMessage.setText(problemLogic.checkIfValidNumbers(minValueInput.getText(), maxValueInput.getText()));
                maxValueInput.setText("");
                minValueInput.setText("");  
            }

        });

        Label challengeMode = new Label("Challenge mode");
        challengeMode.setStyle("-fx-font-size: 2em;");

        HBox buttons = new HBox();

        Button thirtySeconds = new Button("30 seconds");

        Button oneMinute = new Button("1 minute");

        Button twoMinutes = new Button("2 minutes");

        thirtySeconds.setOnAction(e -> {
            secondsAtStart = 30;
            seconds = 30;
            setChallengeScene();
            errorMessage.setText("");
            primaryStage.setScene(challengeScene);
        });

        oneMinute.setOnAction(e -> {
            secondsAtStart = 60;
            seconds = 60;
            setChallengeScene();
            errorMessage.setText("");
            primaryStage.setScene(challengeScene);
        });

        twoMinutes.setOnAction(e -> {
            secondsAtStart = 120;
            seconds = 120;
            setChallengeScene();
            errorMessage.setText("");
            primaryStage.setScene(challengeScene);
        });

        buttons.getChildren().addAll(thirtySeconds, oneMinute, twoMinutes);

        Button records = new Button("records");

        records.setOnAction(e -> {
            errorMessage.setText("");
            primaryStage.setScene(recordScene);
        });

        centerPane.getChildren().addAll(title);

        newProblemPane.getChildren().addAll(newOperationPane, newValuesPane, getProblem);

        mainPane.getChildren().addAll(menuPane, errorMessage, centerPane, setProblems, newProblemPane, challengeMode, buttons, records);

    }

    /**
     * Generates a scene in which to solve the problems
     */
    public void setProblemScene() {
        VBox problemPane = new VBox(10);
        HBox centerPaneProblem = new HBox(10);
        problemLabel.setText(problemLogic.makeProblem(minValue, maxValue, operation));
        TextField problemInput = new TextField();

        Button submit = new Button("submit answer");

        Label correct = new Label("");

        Button nextProblem = new Button("next");

        nextProblem.setOnAction(e -> {
            problemLabel.setText(problemLogic.makeProblem(minValue, maxValue, operation));
            correct.setText("");
        });

        submit.setOnAction(e -> {
            String answer = problemInput.getText();
            problemInput.setText("");
            if (problemLogic.checkAnswer(answer)) {
                correct.setText("correct!!");

            } else {
                correct.setText("wrong the correct answer is " + problemLogic.getAnswer());
            }
        });

        Button back = new Button("back to menu");

        back.setOnAction(e -> {
            primaryStage.setScene(menuScene);
            correct.setText("");
        });

        centerPaneProblem.getChildren().addAll(problemLabel, problemInput, submit, correct);

        problemPane.getChildren().addAll(centerPaneProblem, nextProblem, back);

        problemScene = new Scene(problemPane, 700, 300);
    }

    /**
     * Generates a scene in which to solve the problems with a timer
     */
    public void setChallengeScene() {
        VBox mainPane = new VBox();
        VBox timer = new VBox();
        Label timerLabel = new Label(secondsAtStart.toString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em;");
        Button startChallenge = new Button("Start");

        HBox problem = new HBox();
        challengeProblemLabel.setText(problemLogic.makeProblem(minValue, maxValue, operation));
        TextField problemInput = new TextField();
        Label correct = new Label("");

        Button submit = new Button("submit answer");
        Button skip = new Button("skip");

        Label solved = new Label("");
        solved.setStyle("-fx-font-size: 2em;");
        solved.setVisible(false);

        submit.setOnAction(e -> {
            if (seconds > 0) {
                String answer = problemInput.getText();
                problemInput.setText("");
                if (problemLogic.checkAnswer(answer)) {
                    recordLogic.incrementAmount();
                    challengeProblemLabel.setText(problemLogic.makeProblem(minValue, maxValue, operation));
                    problemInput.setText("");
                    correct.setText("");
                } else {
                    correct.setText("wrong");
                }
            }
        });

        skip.setOnAction(e -> {
            if (seconds > 0) {
                challengeProblemLabel.setText(problemLogic.makeProblem(minValue, maxValue, operation));
                problemInput.setText("");
                correct.setText("");
            }
        });

        startChallenge.setOnAction(e -> {
            if (timeline != null) {
                timeline.stop();
            }
            startChallenge.setVisible(false);
            minValue = 2;
            maxValue = 20;
            operation = null;
            challengeProblemLabel.setText(problemLogic.makeProblem(minValue, maxValue, operation));
            problem.setVisible(true);
            skip.setVisible(true);
            submit.setVisible(true);
            solved.setVisible(false);
            timerLabel.setText(seconds.toString());
            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
                        seconds--;
                        timerLabel.setText(seconds.toString());
                        if (seconds <= 0) {
                            timeline.stop();
                            startChallenge.setVisible(true);
                            problem.setVisible(false);
                            submit.setVisible(false);
                            skip.setVisible(false);
                            solved.setText(Integer.toString(recordLogic.getAmountSolved()) + " solved");
                            solved.setVisible(true);
                            seconds = secondsAtStart;
                            try {
                                recordLogic.setRecord(secondsAtStart.toString());
                                setRecordsScene();
                            } catch (SQLException ex) {
                                Logger.getLogger(MathPuzzlesUi.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    })
            );
            timeline.playFromStart();
        });

        problem.getChildren().addAll(challengeProblemLabel, problemInput, correct);

        problem.setVisible(false);
        submit.setVisible(false);
        skip.setVisible(false);
        timer.getChildren().addAll(startChallenge, timerLabel);



        Button back = new Button("back to menu");

        back.setOnAction(e -> {
            primaryStage.setScene(menuScene);
            correct.setText("");
        });

        mainPane.getChildren().addAll(timer, solved, problem, submit, skip, back);
        challengeScene = new Scene(mainPane, 700, 300);
    }

    /**
     * Generates a scene in which the records are shown
     * @throws SQLException if something fails at database level
     */
    public void setRecordsScene() throws SQLException {
        VBox mainPane = new VBox();

        HBox thirtyandsixty = new HBox();

        VBox thirty = new VBox();
        

        Label titleFor30 = new Label("Records for 30 seconds");
        titleFor30.setStyle("-fx-font-size: 2em;");
        thirty.getChildren().add(titleFor30);
        List<String> records = recordLogic.getRecords("30");
        if (!records.isEmpty()) {
            for (String record : records) {
                Label recordLabel = new Label(record);
                thirty.getChildren().add(recordLabel);
            }
        }
        
        VBox sixty = new VBox();
        
        thirty.setPadding(new Insets(10));
        sixty.setPadding(new Insets(10));
        
        Label titleFor60 = new Label("Records for 1 minute");
        titleFor60.setStyle("-fx-font-size: 2em;");
        sixty.getChildren().add(titleFor60);
        records = recordLogic.getRecords("60");
        if (!records.isEmpty()) {
            for (String record : records) {
                Label recordLabel = new Label(record);
                sixty.getChildren().add(recordLabel);
            }
        }
        
        thirtyandsixty.getChildren().addAll(thirty, sixty);
        mainPane.getChildren().add(thirtyandsixty);
        
        VBox onetwenty = new VBox();
        onetwenty.setPadding(new Insets(10));
        
        Label titleFor120 = new Label("Records for 2 minutes");
        titleFor120.setStyle("-fx-font-size: 2em;");
        onetwenty.getChildren().add(titleFor120);
        mainPane.getChildren().add(titleFor120);
        records = recordLogic.getRecords("120");
        if (!records.isEmpty()) {
            for (String record : records) {
                Label recordLabel = new Label(record);
                onetwenty.getChildren().add(recordLabel);
            }
        }
        
        mainPane.getChildren().add(onetwenty);
        
        Button back = new Button("back to menu");

        back.setOnAction(e -> {
            primaryStage.setScene(menuScene);
        });

        mainPane.getChildren().add(back);

        recordScene = new Scene(mainPane, 700, 300);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {

        this.primaryStage = primaryStage;

        setLoginScene();

        setNewUserScene();

        setMainScene();

        setProblemScene();

        setChallengeScene();

        setRecordsScene();

        this.primaryStage.setTitle("Math Puzzles");
        this.primaryStage.setScene(loginScene);
        this.primaryStage.show();
    }

    /**
     * starts the application
     *
     * @param args command line arguments as String objects
     */
    public static void main(String[] args) {
        launch(args);
    }

}
