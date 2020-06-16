package it.units.project.response;

import it.units.project.request.Request;

public class ErrorResponse extends ResponseBuilder{
    public ErrorResponse(Request request, String string){
        super(request, string);
    }

    public String buildingResponse(){
        String returnFormat = "ERR";

        request.closeRequest(false);
        return returnFormat+separator+toBuild;
    }
}
