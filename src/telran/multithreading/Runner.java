package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;

public class Runner extends Thread {
private int runnerId;
private Race race;
private Instant finishTime;
public Runner(int runnerId, Race race) {
	this.runnerId = runnerId;
	this.race = race;
}

@Override
public void run() {
	int sleepRange = race.getMaxSleep() - race.getMinSleep();
	int minSleep = race.getMinSleep();
	int distance = race.getDistance();
	for(int i = 0; i < distance; i++) {
	try {
		sleep((long)(Math.random() * sleepRange + minSleep));
	}catch (InterruptedException e) {
		throw new IllegalStateException();
	}
	System.out.println(runnerId);
}
	synchronized (race) {
		finishTime = Instant.now();
		finishRace();
	}
	
	
}
public int getRunnerId() {
	return runnerId;
}
private void finishRace() {
	race.getResultsTable().add(this);
}
public Instant getFinishTime() {
	return finishTime;
}
}
