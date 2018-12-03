package mathpuzzles.gui;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mathpuzzles.dao.Database;
import mathpuzzles.dao.UserDao;
import mathpuzzles.logics.ProblemLogic;
import mathpuzzles.logics.UserLogic;
import mathpuzzles.problem.Operation;

public class MathPuzzlesUi extends Application {

    private Stage primaryStage;
    private Label menuLabel = new Label();
    private Scene loginScene;
    private Scene menuScene;
    private Scene newUserScene;
    private Scene problemScene;
    private UserLogic userLogic;
    private ProblemLogic problemLogic;
    private Label title = new Label("");
    private Label loginMessage;
    private int minValue = 2;
    private int maxValue = 20;
    private Label problemLabel = new Label("");
    private Operation operation = null;

    @Override
    public void init() throws Exception {
        Database database = new Database("jdbc:sqlite:mathpuzzles.db");

        UserDao userDao = new UserDao(database);
        userLogic = new UserLogic(userDao);
        problemLogic = new ProblemLogic();
    }
    
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
    
    public void setMainScene() {
        VBox mainPane = new VBox(10);

        menuScene = new Scene(mainPane, 700, 300);

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

        HBox newOperationPane = new HBox(10);
        newOperationPane.setPadding(new Insets(10));
        ObservableList<Operation> options
                = FXCollections.observableArrayList(
                        null,
                        Operation.DIVIDE,
                        Operation.MINUS,
                        Operation.PLUS,
                        Operation.MULTIPLY
                );
        final ComboBox comboBox = new ComboBox(options);
        Label newOperationLabel = new Label("operation (empty means all)");
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

        getProblem.setOnAction(e -> {
            if (problemLogic.checkIfValidNumbers(minValueInput.getText(), maxValueInput.getText())) {
                this.maxValue = Integer.parseInt(maxValueInput.getText());
                this.minValue = Integer.parseInt(minValueInput.getText());
                maxValueInput.setText("");
                minValueInput.setText("");
                this.operation = (Operation) comboBox.getValue();
                problemLabel.setText(problemLogic.makeProblem(minValue, maxValue, operation));
                primaryStage.setScene(problemScene);
            } else {
                maxValueInput.setText("");
                minValueInput.setText("");
                errorMessage.setText("please input positive integers only");
                errorMessage.setTextFill(Color.RED);
            }
            
        });

        centerPane.getChildren().addAll(title);

        newProblemPane.getChildren().addAll(newOperationPane, newValuesPane, getProblem);

        mainPane.getChildren().addAll(menuPane, errorMessage, centerPane, setProblems, newProblemPane);

    }
    
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
        });

        centerPaneProblem.getChildren().addAll(problemLabel, problemInput, submit, correct);

        problemPane.getChildren().addAll(centerPaneProblem, nextProblem, back);

        problemScene = new Scene(problemPane, 700, 300);
    }

    @Override
    public void start(Stage primaryStage) {
        
        this.primaryStage = primaryStage;

        setLoginScene();
        
        setNewUserScene();

        setMainScene();
       
        setProblemScene();

        this.primaryStage.setTitle("Math Puzzles");
        this.primaryStage.setScene(loginScene);
        this.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
