package client;

import java.util.Observable;
import java.util.Observer;

import eu.hansolo.enzo.notification.Notification;
import eu.hansolo.enzo.notification.Notification.Notifier;


public class ItemObserver implements Observer{

	private boolean notified = false;
	
	@Override
	public void update(Observable o, Object expiringItems) {
		if((int)expiringItems > 0 && !notified) {
			Notification notification = new Notification("WasteLess Application","You have "+(int)expiringItems+" item(s) expiring soon!\n Click the donations tab to find charities.",Notification.INFO_ICON);
			Notifier.INSTANCE.notify(notification);
			notified = true;
		}
	}

}
