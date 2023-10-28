package telran.multithreading;

import telran.multithreading.consumer.Receiver;
import telran.multithreading.messaging.MessageBox;
import telran.multithreading.producer.Sender;

public class SenderReceiverAppl {
	private static final int N_MESSAGES = 20;
	private static final int N_RECEIVERS = 10;	
	
	public static void main(String[] args) throws InterruptedException {
		MessageBox messageBoxOdd = new MessageBox();
		MessageBox messageBoxEven = new MessageBox();
		Sender sender = new Sender(messageBoxEven, messageBoxOdd,N_MESSAGES);
		sender.start();
		Receiver[] receivers = new Receiver[N_RECEIVERS];
		startReceivers(messageBoxEven, messageBoxOdd);
		sender.join();
		stopReceivers(receivers);
	}

	private static void stopReceivers(Receiver[] receivers) {
		for(Receiver receiver: receivers) {
			receiver.interrupt();
		}
	}

	private static void startReceivers(MessageBox messageBoxEven, MessageBox messageBoxOdd) {
		
		for(int i = 0; i < N_RECEIVERS; i++) {
			Receiver receiver = new Receiver();
			MessageBox message = receiver.getId() % 2 == 0 ? messageBoxEven : messageBoxOdd;
			receiver.setMessageBox(message);
			receiver.start();
		}
	}
}
