package client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.BoughtItem;
import model.ShopItem;

public class MainGUI extends Stage {
	
	private WLClient client;
	private BorderPane gLayout,iLayout,rLayout,dLayout;
	private Scene groceriesScene;
	private Scene itemsScene;
	private Scene reportsScene;
	private Scene donationScene;
	private VBox[] items;
	private HBox[] menuBar;
	private String username;
	private Label ideal,user;
	private ItemObserver obs;
	private boolean notified;
	
	public MainGUI(WLClient client,String username,String password) {
		this.client = client;
		this.username = username;
		notified = false;
		obs = new ItemObserver();
		
		ideal = new Label();
		user = new Label();
		ideal.setFont(new Font(15));
		ideal.setTextFill(Color.web("#FF1111"));
		ideal.setText("     Ideal burndown rate: ");
		user.setFont(new Font(15));
		user.setTextFill(Color.web("#FF1111"));
		user.setText("     Your burndown rate: ");
		
		menuBar = new HBox[4];
		
		gLayout = new BorderPane();
		iLayout = new BorderPane();
		rLayout = new BorderPane();
		dLayout = new BorderPane();
		
		for(int i = 0; i < 4; i++) {
			Label menuTitle = new Label("WASTE LESS");
			menuTitle.setFont(new Font(17));
			Label userLabel = new Label(username+"\t\t");
			userLabel.setFont(new Font(15));
			Button gBtn = new Button("Groceries");
			Button iBtn = new Button("Bought Items");
			Button rBtn = new Button("Reports");
			Button dBtn = new Button("Donate");
			Button logoutBtn = new Button("Logout");
			
			gBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setScene(groceriesScene);
				this.show();
				((HBox)gLayout.getChildren().get(0)).getChildren().get(2).requestFocus();
				});
			iBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setScene(itemsScene);
				this.show();
				((HBox)iLayout.getChildren().get(0)).getChildren().get(3).requestFocus();
			});
			rBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setScene(reportsScene);
				this.show();
				((HBox)((VBox)rLayout.getChildren().get(0)).getChildren().get(0)).getChildren().get(4).requestFocus();
			});
			dBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setScene(donationScene);
				this.show();
				((HBox)dLayout.getChildren().get(0)).getChildren().get(5).requestFocus();
			});
			logoutBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, e-> {
				client.logout(username,password);
			});
			
			menuBar[i] = new HBox();
			menuBar[i].setTranslateX(36);
			menuBar[i].getChildren().add(menuTitle);
			menuBar[i].getChildren().add(new Label("\t\t\t\t\t\t\t"));
			menuBar[i].getChildren().add(gBtn);
			menuBar[i].getChildren().add(iBtn);
			menuBar[i].getChildren().add(rBtn);
			menuBar[i].getChildren().add(dBtn);
			menuBar[i].getChildren().add(new Label("\t\t\t\t\t"));
			menuBar[i].getChildren().add(userLabel);
			menuBar[i].getChildren().add(logoutBtn);
			menuBar[i].setStyle("-fx-padding: 25px;-fx-border-insets:25px");
		}

		gLayout.setTop(menuBar[0]);
		iLayout.setTop(menuBar[1]);
		rLayout.setTop(new VBox(menuBar[2]));
		dLayout.setTop(menuBar[3]);
		
		VBox places = new VBox(5);
		ScrollPane placesSP = new ScrollPane(places);
		placesSP.setStyle("-fx-padding: 15px");
		List<String> donationPlaces = client.getDonationPlacesContents();
		for(String place : donationPlaces)
			places.getChildren().add(new Label(place));
		dLayout.setCenter(placesSP);

		groceriesScene = new Scene(gLayout,1150,590);
		itemsScene = new Scene(iLayout,940,590);
		reportsScene = new Scene(rLayout,1010,590);
		donationScene = new Scene(dLayout,940,590);
		
		this.setScene(groceriesScene);
		this.show();
	}
	
	public void setRates(int idealRate, int userRate) {
		ideal.setText("     Ideal burndown rate: "+idealRate);
		user.setText("     Your burndown rate: "+userRate);
	}
	
	public void initGroceries(List<ShopItem> groceries) {
		items = new VBox[groceries.size()];
		HBox gBottomBox = new HBox();
		ScrollPane gSP = new ScrollPane();
		GridPane gLayout1 = new GridPane();
		Button gBtn = new Button("Purchase");
		
		Date today = new Date();
		Calendar c = Calendar.getInstance();

		gBottomBox.getChildren().addAll(gBtn,new Label("        \t"));
		gBottomBox.setStyle("-fx-padding: 20px;-fx-border-insets:20px");
		gBottomBox.setAlignment(Pos.BOTTOM_RIGHT);
		gBtn.setStyle("-fx-background-color: lightgreen");
		gBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			List<BoughtItem> boughtItems = new ArrayList<BoughtItem>();
			for(int i = 0; i < items.length; i++) {
				HBox topItem = (HBox) items[i].getChildren().get(0);
				HBox botItem = (HBox) items[i].getChildren().get(1);
				String theQuantity = ((TextField) topItem.getChildren().get(1)).getText();
				if(!(theQuantity.isBlank()))
					try {
						boughtItems.add(new BoughtItem(((Label) topItem.getChildren().get(0)).getText(),
														Integer.parseInt(theQuantity),
														Integer.parseInt(((Label) botItem.getChildren().get(4)).getText()),
														new Date(),
														new SimpleDateFormat("dd/MM/yyyy").parse(((Label) botItem.getChildren().get(1)).getText()),
														null));
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					
			}
			client.createItems(boughtItems,username);
		});
		
		for(int i = 0; i < groceries.size(); i++) {
			c.setTime(today);
			c.add(Calendar.DATE, groceries.get(i).getDays());
			
			String month;
			TextField quantityTF = new TextField();
			Button plus = new Button("+");
			Button minus = new Button("-");
			plus.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
				int q;
				try{q = Integer.parseInt(quantityTF.getText());}
				catch(NumberFormatException ex) {
					q=0;
				}
				quantityTF.setText(String.valueOf(q+1));
			});
			minus.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
				int q;
				try{q = Integer.parseInt(quantityTF.getText());}
				catch(NumberFormatException ex) {
					q = 0;
				}quantityTF.setText(String.valueOf(q - 1));
			});
			items[i] = new VBox(10);
			items[i].setTranslateX(15);
			//items[i].setTranslateY((i/3)*35 + 60);
			items[i].setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;"
			        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
			        + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
			HBox hbox = new HBox(10);
			HBox hbox1 = new HBox(10);
		
			
			if(c.get(Calendar.MONTH)+1 <10)
				month = "0" + String.valueOf(c.get(Calendar.MONTH)+1);
			else month = String.valueOf(c.get(Calendar.MONTH)+1);
			
			hbox.getChildren().addAll(new Label(groceries.get(i).getName()),quantityTF,plus,minus);
			hbox1.getChildren().addAll(new Label("Expiration date: "),
						new Label(String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "/" + month + "/" + String.valueOf(c.get(Calendar.YEAR))),
						new Label("\t"),new Label("Calories: "),
						new Label(String.valueOf(groceries.get(i).getCalories())));
			items[i].getChildren().add(hbox);
			items[i].getChildren().add(hbox1);
			gLayout1.add(items[i],(i%3),i/3,1,1);
		}
		
		gSP.setContent(gLayout1);
		gLayout.setCenter(gSP);
		gLayout.setBottom(gBottomBox);
		
		this.setX(-5);
		this.setY(5);
		this.setScene(groceriesScene);
		this.setTitle("WasteLess");
	}
	
	public void initItems(List<BoughtItem> items) {
		int i = 0, j = 0, expiringItems = 0;
		Date lastDate, theDate;
		lastDate = new Date(0,0,0);
		GridPane iLayout1 = new GridPane();
		ScrollPane iSP = new ScrollPane();
		HBox iBottomBox = new HBox();
		Button iBtn = new Button("Remove selected items");
		iLayout1.setVgap(30);
		iLayout1.setHgap(10);
		iBottomBox.getChildren().addAll(iBtn,new Label("        \t"));
		iBottomBox.setStyle("-fx-padding: 20px;-fx-border-insets:20px");
		iBottomBox.setAlignment(Pos.BOTTOM_RIGHT);
		iBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			List<String> removedItems = new ArrayList<>();
			for(Node node : iLayout1.getChildren()) {
				if(node instanceof HBox)
					if( ((CheckBox) (((HBox) node).getChildren().get(4))).isSelected())
						removedItems.add(((Label) (((HBox) node).getChildren().get(1))).getText());
			client.consumeItems(removedItems,username);
			}
		});
		
		try {
			Collections.sort(items, new Comparator<BoughtItem>() {
				@Override
				public int compare(BoughtItem i1, BoughtItem i2) {
					return i1.getPurchaseDate().compareTo(i2.getPurchaseDate());
				}
			});
			for(BoughtItem item : items) {
				Label itemName = new Label(item.getName());
				HBox hbox = new HBox(3);
				float days = ((float) (item.getExpirationDate().getTime() - new Date().getTime())/(1000*60*60*24));
				theDate = item.getPurchaseDate();
				if(days >0 && days <=1) {
					itemName.setTextFill(Color.web("FF1111"));
					expiringItems++;
				}
				else {if (days < 4)
						itemName.setTextFill(Color.web("DDDD11"));
				else itemName.setTextFill(Color.web("11EE11"));
				}
				if(!(theDate.getYear() == lastDate.getYear() && theDate.getMonth() == lastDate.getMonth()
						&& theDate.getDay() == lastDate.getDay())) {
					i++;
					j = 0;
					iLayout1.add(new Label(item.getPurchaseDate().toLocaleString()),0,i,1,1);
				}
				hbox.getChildren().addAll(new Label("\t"), itemName, new Label(" x "), new Label(item.getQuantity()+"  "));
				hbox.getChildren().add((new CheckBox()));
				iLayout1.add(hbox, ++j, i);
				//iLayout1.add(new Label("\t" + item.getName() + " x " + item.getQuantity() + "  "),++j,i);
				//iLayout1.add(new CheckBox(),++j,i);
				lastDate = theDate;
			}
		
			iLayout1.setStyle("-fx-padding:15px");
			iSP.setContent(iLayout1);
			iLayout.setCenter(iSP);
			iLayout.setBottom(iBottomBox);
			
			obs.update(null, expiringItems);
		
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	}

	public void initReports(String weeklyReport, String monthlyReport) {
		TextArea weeklyTA = new TextArea(weeklyReport);
		TextArea monthlyTA = new TextArea(monthlyReport);
		VBox wvbox = new VBox(10);
		VBox mvbox = new VBox(10);
		
		((VBox) rLayout.getChildren().get(0)).getChildren().addAll(ideal,user);
		weeklyTA.setPrefHeight(600);
		monthlyTA.setPrefHeight(600);
		wvbox.getChildren().addAll(new Label("Weekly report of expired items"),weeklyTA);
		wvbox.setStyle("-fx-padding:15px");
		mvbox.getChildren().addAll(new Label("Monthly report of expired items"),monthlyTA);
		mvbox.setStyle("-fx-padding:15px");
		rLayout.setLeft(wvbox);
		rLayout.setRight(mvbox);
	}
}
