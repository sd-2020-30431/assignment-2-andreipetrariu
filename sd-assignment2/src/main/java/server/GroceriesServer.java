package server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.ShopItem;

public class GroceriesServer extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static WLDataAccess dataAccess = WLDataAccess.getInstance();
	
	public  GroceriesServer(){
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, 
	ServletException {
		List<ShopItem> groceries = dataAccess.readAllGroceries();
		if(groceries!=null) {
			ObjectMapper mapper = new ObjectMapper();
			response.getOutputStream().println(mapper.writeValueAsString(groceries));
		}
		else response.getOutputStream().println("{}");
	}
}