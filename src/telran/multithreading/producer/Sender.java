package telran.multithreading.producer;

import telran.multithreading.messaging.MessageBox;

public class Sender extends Thread{
private MessageBox messageBoxEven;
private MessageBox messageBoxOdd;
private int nMessages;
public Sender(MessageBox messageBoxEven, MessageBox messageBoxOdd, int nMessages) {
	this.messageBoxOdd = messageBoxOdd;
	this.messageBoxEven = messageBoxEven;
	this.nMessages = nMessages;
}
@Override
public void run() {
	for(int i = 1; i <= nMessages; i++) {
		try {
			MessageBox message = i % 2 == 0 ? messageBoxEven : messageBoxOdd;
			message.put("message" + i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

}
