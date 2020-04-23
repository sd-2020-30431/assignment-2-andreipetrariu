package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import model.BoughtItem;

public class WeeklyReport implements Report{
	public String createFile(List<BoughtItem> wastedItems,String filePath) {
		String line = null, wDate = null;
		StringBuilder sb = new StringBuilder();
		Date today = new Date();
		int i = 0, wDateIndex;
		BufferedReader br;
		try {
			 br = new BufferedReader(new FileReader(filePath));
			//reading the file to find the last written date
			while(br.ready()) {
				line = br.readLine();
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
				if(line.contains("Week")) {
					wDateIndex = i;
					wDate = line.substring(18,28);
				}
				i++;
			}
			br.close();
			
			Calendar c = Calendar.getInstance();
			
			if(wastedItems.isEmpty()) {
				return sb.toString();
			}

			//sorting the expired items by expiration date to write them in the file in order
			Collections.sort(wastedItems, new Comparator<BoughtItem>() {
				public int compare(BoughtItem i1, BoughtItem i2) {
					return i1.getExpirationDate().compareTo(i2.getExpirationDate());
				}
			});
			if(wDate!=null)
				c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(wDate));
			//writing this date in the file if it is empty
			else {c.setTime(wastedItems.get(0).getExpirationDate());
				sb.append("\nWeek " + String.format("%02d",c.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d",c.get(Calendar.MONTH)+1)+ "/" + c.get(Calendar.YEAR));
				sb.append(" - ");
				c.add(Calendar.DAY_OF_MONTH,7);
				sb.append(String.format("%02d",c.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d",c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR)+" : ");
			}
			
			//writing the items' names, quantities and calories in the file
			for(BoughtItem item : wastedItems) {
				if(item.getExpirationDate().after(c.getTime())) {
					c.setTime(item.getExpirationDate());
					sb.append("\nWeek " + String.format("%02d",c.get(Calendar.DAY_OF_MONTH)+1) + "/" + String.format("%02d",c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR));
					sb.append(" - ");
					c.add(Calendar.DAY_OF_MONTH,7);
					sb.append(String.format("%02d",c.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d",c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR)+" : ");
				}
				sb.append(item.getName()+" x "+item.getQuantity()+"("+item.getCalories()+")"+" | ");				
				}		
			
		}catch (Exception e) {
			System.out.println("(WeeklyReport)"+e.toString());
		}
		return sb.toString();
		}
}
