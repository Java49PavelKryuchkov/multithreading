package telran.multithreading;

public class Printer extends Thread {

private Printer next;
private int printerNumber;
static int overall;
static int nPartitions;
private int inLine;

public Printer(int printerNumber) {
	this.printerNumber = printerNumber;
	inLine = overall / nPartitions;
}

public void setNext(Printer next) {
	this.next = next;
}

public static void setOverall(int overall) {
	Printer.overall = overall;
}

public static void setnPartitions(int nPartitions) {
	Printer.nPartitions = nPartitions;
}

@Override
public void run() {
	int count = 0;
	String line = ("" + printerNumber).repeat(inLine);	
	while(count < nPartitions) {
		try {
			join();
		}catch(InterruptedException e) {
			count++;
			next.interrupt();
			System.out.println(line);
		}
	}
	
}

}
