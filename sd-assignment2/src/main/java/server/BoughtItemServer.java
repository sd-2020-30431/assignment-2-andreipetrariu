package server;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.BoughtItem;
import model.ShopItem;

public class BoughtItemServer extends HttpServlet{

	private static WLDataAccess dataAccess = WLDataAccess.getInstance();
	
	public BoughtItemServer() {
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestUrl = request.getRequestURI();
		String username;
		List<BoughtItem> items;
		if(requestUrl.contains("unused")) {
			username = requestUrl.substring("/sd-assignment2/unuseditems/".length());
			items = dataAccess.readUnusedItems(username);
		}
		else {username = requestUrl.substring("/sd-assignment2/items/".length());
			items = dataAccess.readAllItems(username);
		}
		
		if(items != null){
			ObjectMapper mapper = new ObjectMapper();
			response.getOutputStream().println(mapper.writeValueAsString(items));
		}
		else
			response.getOutputStream().println("{}");
	}

	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		Scanner in = new Scanner(request.getInputStream());
		String json="";
		while(in.hasNext())
			json += in.nextLine();
		List<String> itemNames = mapper.readValue(json,new TypeReference<List<String>>() {});
		dataAccess.updateItems(itemNames);
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<BoughtItem> wastedItems = dataAccess.initWastedItems();
		if(wastedItems!=null) {
			ObjectMapper mapper = new ObjectMapper();
			response.getOutputStream().println(mapper.writeValueAsString(wastedItems));
		}
		else response.getOutputStream().println("{}");
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		Scanner in = new Scanner(request.getInputStream());
		String json="";
		while(in.hasNext())
			json += in.nextLine();
		System.out.println(json);
		List<BoughtItem> items = mapper.readValue(json,new TypeReference<List<BoughtItem>>() {});
		dataAccess.createItems(items);
	}
}
