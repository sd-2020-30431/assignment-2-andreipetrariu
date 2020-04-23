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


public class UserServer  extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static WLDataAccess dataAccess = WLDataAccess.getInstance();
	
	public UserServer(){
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, 
	ServletException {
		String requestUrl = request.getRequestURI();
		String username = requestUrl.substring("/sd-assignment2/user/".length()).split("_")[0];
		String password = requestUrl.substring("/sd-assignment2/user/".length()).split("_")[1];
		boolean result = dataAccess.login(username,password);
		String json = "{\n";
		if(result){
			json += "\"result\": " + 1 + "\n";
			json += "}";
		}
		else {
			json += "\"result\": " + 0 + "\n";
			json += "}";
		}
			response.getOutputStream().println(json);
	}
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, 
	ServletException {
		String requestUrl = request.getRequestURI();
		String username = requestUrl.substring("/sd-assignment2/user/".length()).split("_")[0];
		String password = requestUrl.substring("/sd-assignment2/user/".length()).split("_")[1];
		dataAccess.logout(username, password);
	}
}
