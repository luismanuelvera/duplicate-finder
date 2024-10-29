package com.dpfinder.app.mappers;

public class Match {
	private int sourceID;
    private int matchID;
    private String accuracy;

    public Match(int sourceID, int matchID, String accuracy) {
        this.sourceID = sourceID;
        this.matchID = matchID;
        this.accuracy = accuracy;
    }

    public int getSourceID() {
		return sourceID;
	}

	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
	}

	public int getMatchID() {
		return matchID;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	@Override
    public String toString() {
        return "Source ID: " + sourceID + ", Match ID: " + matchID + ", Accuracy: " + accuracy;
    }
}
