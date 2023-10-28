package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.stream.IntStream;

public class PrinterControllerAppl {

	private static final int N_THREADS = 50;
	private static final int N_NUMBERS = 100;
	private static final int N_PARTITIONS = 10;

	public static void main(String[] args) throws InterruptedException{
	Printer.setnPartitions(N_PARTITIONS);
	Printer.setOverall(N_NUMBERS);
	Printer[] printers = new Printer[N_THREADS];
	setPrinters(printers);
	printers[0].interrupt();
	}

	private static void setPrinters(Printer[] printers) {
		printers[0] = new Printer(1);
		for(int i = 1; i < printers.length; i++) {
			printers[i] = new Printer(i+1);
			printers[i-1].setNext(printers[i]);
			printers[i-1].start();
			printers[printers.length - 1].setNext(printers[0]);
			printers[printers.length - 1].start();
		}
		
	}
		
}
