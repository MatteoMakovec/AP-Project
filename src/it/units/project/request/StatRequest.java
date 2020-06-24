package it.units.project.request;

import it.units.project.exception.CommandException;
import it.units.project.response.SuccessfulResponse;

import static it.units.project.server.ProcessingServer.DECIMAL_PRECISION;


public class StatRequest extends AbstractRequest {
    public StatRequest(String regex, double[] stats){
        super(regex, stats);
    }

    public String process() throws CommandException{
        String result;

        switch (request){
            case "STAT_REQS":
                result = new SuccessfulResponse(Double.toString(stats[0]), time).buildingResponse();
                break;

            case "STAT_AVG_TIME":
                result = new SuccessfulResponse(String.format("%." + DECIMAL_PRECISION + "f", (stats[1] / stats[0])), time).buildingResponse();
                break;

            case "STAT_MAX_TIME":
                result = new SuccessfulResponse(String.format("%." + DECIMAL_PRECISION + "f", stats[2]), time).buildingResponse();
                break;

            default:
                throw new CommandException("Command not in protocol specification");
        }
        return result;
    }
}
