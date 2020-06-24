package it.units.project.response;


public abstract class AbstractResponse {
    protected String toBuild;
    protected char separator = ';';
    protected String totalTime = "totalTime";
    private double time;

    public abstract String buildingResponse();

    public AbstractResponse(String string){
        toBuild = string;
        time = System.nanoTime();
    }
}
