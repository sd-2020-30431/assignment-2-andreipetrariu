package client;

import java.util.List;

import model.BoughtItem;

public interface Report {
	public String createFile(List<BoughtItem> wastedItems,String filePath);
}
