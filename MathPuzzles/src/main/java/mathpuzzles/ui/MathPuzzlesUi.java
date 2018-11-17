package mathpuzzles.ui;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mathpuzzles.database.Database;
import mathpuzzles.database.UserDao;
import mathpuzzles.domain.Service;

public class MathPuzzlesUi extends Application {
    
    private Label menuLabel = new Label();
    private Scene loginScene;
    private Scene menuScene;
    private Scene newUserScene;
    private Service service;
    
    @Override
    public void init() throws Exception {
        Database database = new Database("jdbc:sqlite:mathpuzzles.db");
        
        UserDao userDao = new UserDao(database);
        service = new Service(userDao);
    }

    @Override
    public void start(Stage primaryStage) {
        
        //login scene
        VBox loginPane = new VBox(10);
        HBox inputPane = new HBox(10);
        loginPane.setPadding(new Insets(10));
        Label loginLabel = new Label("username");
        TextField usernameInput = new TextField();
        Label passwordLabel = new Label("password");
        PasswordField passwordInput = new PasswordField();
        
        inputPane.getChildren().addAll(loginLabel, usernameInput, passwordLabel, passwordInput);
        Label loginMessage = new Label();
        
        Button loginButton = new Button("login");
        Button createButton = new Button("create new user");
        
        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            menuLabel.setText( username + " logged in");
            try {
                if (service.login(username, password)) {
                    loginMessage.setText("");
                    primaryStage.setScene(menuScene);
                    usernameInput.setText("");
                    passwordInput.setText("");
                } else {
                    loginMessage.setText("username or password incorrect");
                    loginMessage.setTextFill(Color.RED);
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            
        });
        
        createButton.setOnAction(e->{
            usernameInput.setText("");
            passwordInput.setText("");
            primaryStage.setScene(newUserScene);   
        }); 
        
        loginPane.getChildren().addAll(loginMessage, inputPane, loginButton, createButton);       
        
        loginScene = new Scene(loginPane, 700, 300);  
        
        // new createNewUserScene
        
        VBox newUserPane = new VBox(10);
        
        HBox newUsernamePane = new HBox(10);
        newUsernamePane.setPadding(new Insets(10));
        TextField newUsernameInput = new TextField(); 
        Label newUsernameLabel = new Label("username");
        newUsernameLabel.setPrefWidth(200);
        
        newUsernamePane.getChildren().addAll(newUsernameLabel, newUsernameInput);
        
        HBox newPasswordPane = new HBox(10);
        newPasswordPane.setPadding( new Insets(10));
        PasswordField newPasswordInput = new PasswordField(); 
        Label newPasswordLabel = new Label("password");
        newPasswordLabel.setPrefWidth(200);
        
        newPasswordPane.getChildren().addAll(newPasswordLabel, newPasswordInput );
        
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

        createNewUserButton.setOnAction(e->{
            String username = newUsernameInput.getText();
            String password = newPasswordInput.getText();
            String confirmation = confirmationInput.getText();
            String name = newNameInput.getText();
   
            if ( username.length()<3 || name.length()<2 ) {
                userCreationMessage.setText("username or name too short");
                userCreationMessage.setTextFill(Color.RED);              
            } else if (password.length() < 6) {
                userCreationMessage.setText("password too short");
                userCreationMessage.setTextFill(Color.RED);   
            } else if (!password.equals(confirmation)) {
                userCreationMessage.setText("password and password confirmation do not match");
                userCreationMessage.setTextFill(Color.RED);
            } else try {
                if ( service.createUser(name, username, password) ){
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
 
        }); 
        
        newUserPane.getChildren().addAll(userCreationMessage, newUsernamePane, newPasswordPane, newConfirmationPane, newNamePane, createNewUserButton); 
       
        newUserScene = new Scene(newUserPane, 700, 300);
        
        // main scene
        
        VBox mainPane = new VBox(10);
        
        menuScene = new Scene(mainPane, 700, 300);
        
        HBox menuPane = new HBox(10);
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        Button logoutButton = new Button("logout");      
        menuPane.getChildren().addAll(menuLabel, menuSpacer, logoutButton);
        
        HBox centerPane = new HBox(10);
        Label title = new Label("");
        if (service.getLoggedIn() != null) {
            title = new Label("Welcome " + service.getLoggedIn().getName());
        }
        
        centerPane.getChildren().add(title);
        
        mainPane.getChildren().addAll(menuPane, centerPane);
        
        
        // 
        
        primaryStage.setTitle("Math Puzzles");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
