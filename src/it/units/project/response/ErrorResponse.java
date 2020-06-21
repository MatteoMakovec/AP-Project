package it.units.project.response;

import it.units.project.request.AbstractRequest;

public class ErrorResponse extends AbstractResponse {
    public ErrorResponse(AbstractRequest request, String string){
        super(request, string);
    }

    public String buildingResponse(){
        String returnFormat = "ERR";

        request.closeRequest(false);
        return returnFormat+separator+toBuild;
    }
}
