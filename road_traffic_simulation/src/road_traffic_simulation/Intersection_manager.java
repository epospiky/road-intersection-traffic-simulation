package road_traffic_simulation;

public class Intersection_manager {
	    private int phases;
	    private int duration;
	    private char curSegment;
	    private char currentPos;
	    private char nextDir;

	    public Intersection_manager(int phase, int dur, char segmnt, char currentPos, char nextDir) {
	        phases = phase;
	        duration = dur;
	        curSegment = segmnt;
	        this.currentPos = currentPos;
	        this.nextDir = nextDir;
	    }

	    public int getPhases() {
	        return phases;
	    }

	    public int getDuration() {
	        return duration;
	    }

	    public char getSegment() {
	        return curSegment;
	    }

	    public char getCurrentPos() {
	        return currentPos;
	    }

	    public char getNextDir() {
	        return nextDir;
	    }

	    public boolean crossPossible(char dto, char cfm) {
	        if (dto == curSegment && currentPos == cfm || nextDir == cfm) {
	            return true;
	        }

	        return false;
	    }

}
