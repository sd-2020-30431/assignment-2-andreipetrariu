package client;

public class WeeklyReportFactory implements ReportFactory{
	
	@Override
	public WeeklyReport getReport() {
		return new WeeklyReport();
	}
}
