package client;

public class AbstractReportFactory {
	public static ReportFactory getFactory(String factory) {
		switch(factory) {
			case "weekly" : return new WeeklyReportFactory();
			case "monthly" : return new MonthlyReportFactory();
			default : throw new IllegalArgumentException("unknown factory" + factory);
		}
	}
}
