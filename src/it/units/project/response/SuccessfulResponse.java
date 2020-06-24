package it.units.project.response;


import static it.units.project.server.ProcessingServer.DECIMAL_PRECISION;

public class SuccessfulResponse extends AbstractResponse {
    private double time;

    public SuccessfulResponse(String string, double time){
        super(string);
        this.time = time;
    }

    public String buildingResponse(){
        String returnFormat = "OK";
        double totalTime = (System.nanoTime() - time) / 1_000_000_000.0;

        return returnFormat+separator+String.format("%." + DECIMAL_PRECISION + "f", totalTime)+separator+toBuild;
    }
}
