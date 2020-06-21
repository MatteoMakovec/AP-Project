package it.units.project.response;

import it.units.project.request.AbstractRequest;

public class SuccessfulResponse extends AbstractResponse {
    public SuccessfulResponse(AbstractRequest request, String string){
        super(request, string);
    }

    public String buildingResponse(){
        String returnFormat = "OK";

        return returnFormat+separator+String.format("%.5f", request.closeRequest(true))+separator+toBuild;
    }
}
