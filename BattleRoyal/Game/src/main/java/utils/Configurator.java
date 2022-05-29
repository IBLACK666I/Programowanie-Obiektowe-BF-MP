package utils;

public class Configurator {

    private int mapWidth;
    private int mapHeight;
    private int initHeroCount;
    private int healthObjectCount;
    private int strengthObjectCount;
    private int initHealthValue;
    private int initStrengthValue;
    private int lowerSpeedBound;
    private int upperSpeedBound;
    private long simulationTime;
    private String fileForStats;
    private int seed;

    public Configurator() {
        this.mapWidth = 40;
        this.mapHeight = 30;
        this.initHeroCount = 20;
        this.healthObjectCount = 10;
        this.strengthObjectCount = 10;
        this.initHealthValue = 100;
        this.initStrengthValue = 20;
        this.lowerSpeedBound = 1;
        this.upperSpeedBound = 3;
        this.simulationTime = 60;
        this.fileForStats = null;
        this.seed = -1;
    }

    public Configurator(String []args) throws InterruptedException{
        this();
        processArgs(args);
    }

    private void processArgs(String []args) throws InterruptedException{
        try{
            for(int i=0; i<args.length; i+=2){
                switch (args[i]) {
                    case "-mw" ->  // -mw = map weight
                            mapWidth = Integer.parseInt(args[i + 1]);
                    case "-mh" ->  // -mh = map height
                            mapHeight = Integer.parseInt(args[i + 1]);
                    case "-ip" ->  // -ip = init hero object count
                            initHeroCount = Integer.parseInt(args[i + 1]);
                    case "-ih" ->  // -ih = init health object count
                            healthObjectCount = Integer.parseInt(args[i + 1]);
                    case "-is" ->  // -is = init strength object count
                            strengthObjectCount = Integer.parseInt(args[i + 1]);
                    case "-lb" ->  // -lb = lower bound
                            lowerSpeedBound = Integer.parseInt(args[i + 1]);
                    case "-ub" ->  // -ub = upper bound
                            upperSpeedBound = Integer.parseInt(args[i + 1]);
                    case "-sd" ->  // -sd seed
                            seed = Integer.parseInt(args[i + 1]);
                    case "-h" ->  // -h = init health value
                            initHealthValue = Integer.parseInt(args[i + 1]);
                    case "-s" ->  // -s = init strength value
                            initStrengthValue = Integer.parseInt(args[i + 1]);
                    case "-t" ->  // -t = simulation time
                            simulationTime = Integer.parseInt(args[i + 1]);
                    case "-sf" ->  // -sf = stats file
                            fileForStats = args[i + 1];
                }
            }
        }catch(NumberFormatException e){
            System.out.println("Could not load the configuration. Reason: " + e.getMessage() + ". Default configuration will be used");
            System.out.println("Please wait...");
            Thread.sleep(2000);
        }
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getInitHeroCount() {
        return initHeroCount;
    }

    public int getHealthObjectCount() {
        return healthObjectCount;
    }

    public int getStrengthObjectCount() {
        return strengthObjectCount;
    }

    public int getInitHealthValue() {
        return initHealthValue;
    }

    public int getInitStrengthValue() {
        return initStrengthValue;
    }

    public int getLowerSpeedBound() {
        return lowerSpeedBound;
    }

    public int getUpperSpeedBound() {
        return upperSpeedBound;
    }

    public long getSimulationTime() {
        return simulationTime;
    }

    public String getFileForStats() {
        return fileForStats;
    }

    public int getSeed() {
        return seed;
    }
}
