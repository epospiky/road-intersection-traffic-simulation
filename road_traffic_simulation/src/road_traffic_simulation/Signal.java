package road_traffic_simulation;

class Signal {
 private String Light;

 public Signal(String light) {
     Light = light;
 }
 public synchronized void setLight(String light) {
     Vehicle_logs.getInstance().addEntry(String.format("Signal light has now turned %s", light));
     Light = light;
     notifyAll();
 }
 public synchronized String getLight() {
     return Light;
 }
}
