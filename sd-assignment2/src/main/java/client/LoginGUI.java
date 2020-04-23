package client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginGUI extends Stage{
	
	private WLClient client;
	private Button loginBtn;
	private TextField usernameTF;
	private PasswordField passwordTF;
	private Label usernameLabel,passwordLabel;
	private Scene loginScene;
	
	public LoginGUI() {
		usernameLabel = new Label("Username");
		usernameLabel.setTranslateX(-100);
		usernameLabel.setTranslateY(-50);
		usernameTF = new TextField();
		usernameTF.setText("Andrei98");
		usernameTF.setTranslateX(40);
		usernameTF.setTranslateY(-50);
		usernameTF.setMaxWidth(150);
		passwordLabel = new Label("Password");
		passwordLabel.setTranslateX(-100);
		passwordLabel.setTranslateY(-10);
		passwordTF = new PasswordField();
		passwordTF.setText("andrei18");
		passwordTF.setTranslateX(40);
		passwordTF.setTranslateY(-10);
		passwordTF.setMaxWidth(150);
		
		loginBtn=new Button("Login");
		loginBtn.setTranslateY(60);
		loginBtn.setOnAction(e -> {
			if(usernameTF.getText().length()>0 && passwordTF.getText().length()>0) {
				client.login(usernameTF.getText(),passwordTF.getText());
				this.close();
			}
		});
		
		StackPane layout = new StackPane();
		layout.getChildren().addAll(usernameLabel,usernameTF,passwordLabel,passwordTF,loginBtn);
		
		loginScene = new Scene(layout,300,200);
		this.setScene(loginScene);
		this.setTitle("WasteLess Login");
		this.show();
	}
	
	public void setClient(WLClient client) {
		this.client = client;
	}
	
}
