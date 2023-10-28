package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.IntStream;

import telran.view.console.ConsoleInputOutput;
import telran.view.console.InputOutput;
import telran.view.console.Item;
import telran.view.console.Menu;

public class RaceControllerApp {

	private static final int MIN = 3;
	private static final int MAX = 10;
	private static final int MIN_DIST = 100;
	private static final int MAX_DIST = 3500;
	private static final int MIN_SLEEP = 2;
	private static final int MAX_SLEEP = 5;
	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		Item[] items = getItems();
		Menu menu = new Menu("Race game", items);
		menu.perform(io);
}
	private static Item[] getItems() {
		Item[] res = {Item.of("Start game", RaceControllerApp::startGame),
				Item.ofExit()};
		return res;
		}
		

	static void startGame(InputOutput io) {
		int nThreads = io.readInt("Enter number of threads", "Wrong number", MIN, MAX);
		int distance = io.readInt("Enter distance", "Wrong distance", MIN_DIST, MAX_DIST);
		Race race = new Race(distance, MIN_SLEEP, MAX_SLEEP, new ArrayList<Runner>(), Instant.now());
		Runner[] runners = new Runner[nThreads];
		startRunners(runners, race);
		joinRunners(runners);
		displayTable(race);
	}
	
	private static void displayTable(Race race) {
		System.out.println("place\tracer number\ttime");
		ArrayList<Runner> resultsTable = race.getResultsTable();
		IntStream.range(0, resultsTable.size()).mapToObj(i ->  toPrintedString(i, race))
		.forEach(System.out::println);
	}	
	private static String toPrintedString(int index, Race race) {
		Runner runner = race.getResultsTable().get(index);
		return String.format("%3d\t%7d\t\t%d", index + 1, runner.getRunnerId(),
				ChronoUnit.MILLIS.between(race.getTimePoint(), runner.getFinishTime()));
	}
	private static void joinRunners(Runner[] runners) {
		IntStream.range(0, runners.length).forEach(i -> {
			try {
				runners[i].join();
			} catch (InterruptedException e) {
			}
		});
		
	}
	private static void startRunners(Runner[] runners, Race race) {
		IntStream.range(0, runners.length).forEach(i -> {
			runners[i] = new Runner(i+1, race);
			runners[i].start();
		});
	}
}
