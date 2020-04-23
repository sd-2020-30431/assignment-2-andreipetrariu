package client;

import java.util.List;

import model.BoughtItem;

public class MonthlyReportFactory implements ReportFactory{

	@Override
	public Report getReport() {
		return new MonthlyReport();
	}

}
