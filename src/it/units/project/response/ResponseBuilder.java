package it.units.project.response;

import it.units.project.request.Request;

public class ResponseBuilder {
    private String toBuild;

    public ResponseBuilder(String string){
        toBuild = string;
    }

    static public String buildingResponse(Request request, String string){
        String[] returnFormat = {"OK", "ERR"};
        char separator = ';';

        return returnFormat[0]+separator+request.closeRequest(true)+separator+string;
    }
}
