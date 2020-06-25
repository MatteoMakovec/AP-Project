package it.units.project.response;


import it.units.project.request.AbstractRequest;
import static it.units.project.server.ProcessingServer.DECIMAL_PRECISION;

public class SuccessfulResponse extends AbstractResponse {
    private AbstractRequest request;

    public SuccessfulResponse(String string, AbstractRequest request){
        super(string);
        this.request = request;
    }

    public String buildingResponse(){
        String returnFormat = "OK";

        return returnFormat+separator+String.format("%." + DECIMAL_PRECISION + "f", request.closeRequest())+separator+toBuild;
    }
}
