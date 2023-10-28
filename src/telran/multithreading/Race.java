package telran.multithreading;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class Race {
LinkedHashMap<Integer, Long> winners = new LinkedHashMap<>();
private int distance;
private int minSleep;
private int maxSleep;
private Instant timePoint;
private ArrayList<Runner> resultsTable;
public Race(int distance, int minSleep, int maxSleep, ArrayList<Runner> resultsTable, Instant timePoint) {
	this.distance = distance;
	this.minSleep = minSleep;
	this.maxSleep = maxSleep;
	this.timePoint = timePoint;
	this.resultsTable = resultsTable;
}
public int getDistance() {
	return distance;
}
public void setDistance(int distance) {
	this.distance = distance;
}
public int getMinSleep() {
	return minSleep;
}
public void setMinSleep(int minSleep) {
	this.minSleep = minSleep;
}
public int getMaxSleep() {
	return maxSleep;
}
public void setMaxSleep(int maxSleep) {
	this.maxSleep = maxSleep;
}
public ArrayList<Runner> getResultsTable() {
	return resultsTable;
}

public Instant getTimePoint() {
	return timePoint;
}
public void setTimePoint() {
	this.timePoint = Instant.now();
}

}
