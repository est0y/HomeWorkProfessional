package ru.est0y.testing_app.stats;

public class TestStats extends Stats {
    @Override
    public String toString() {
        String statsString = "Passed tests " + passed.size() + " of " + countMethods;
        if (passed.size() + failed.size() != countMethods) {
            statsString = statsString.concat("\nInvoked " + (passed.size() + failed.size()));
        }
        for (Exception e : failed) {
            statsString = statsString.concat("\n" + e.getMessage());
        }
        return statsString;
    }
}
