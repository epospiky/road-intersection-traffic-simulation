package road_traffic_simulation;

class Signal {
 private String Light;

 public Signal(String light) {
     Light = light;
 }
 public synchronized void setLight(String light) {
     Vehicle_logs.getInstance().addToLog(String.format("Signal light is now %s", light));
     Light = light;
     notifyAll();
 }
 public synchronized String getLight() {
     return Light;
 }
}
