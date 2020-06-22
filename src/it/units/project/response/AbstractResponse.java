package it.units.project.response;


public abstract class AbstractResponse {
    protected String toBuild;
    protected char separator = ';';
    protected String totalTime = "totalTime";

    public abstract String buildingResponse();

    public AbstractResponse(String string){
        toBuild = string;
    }
}
