package client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	private Stage loginGUI;
	private static ClassPathXmlApplicationContext context;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		loginGUI = context.getBean("wasteLessLoginUI",LoginGUI.class);
		loginGUI.show();
	}
	public static AbstractApplicationContext getContext() {
		return context;
	}
}
