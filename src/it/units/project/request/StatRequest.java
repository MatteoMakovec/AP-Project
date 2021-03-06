package it.units.project.request;

import it.units.project.exception.CommandException;
import it.units.project.response.SuccessfulResponse;

import java.util.Locale;

import static it.units.project.server.ProcessingServer.DECIMAL_PRECISION;


public class StatRequest extends AbstractRequest {
    public StatRequest(String regex, double[] stats){
        super(regex, stats);
    }

    public String process() throws CommandException{
        String result;

        switch (request){
            case "STAT_REQS":
                result = new SuccessfulResponse(Double.toString(stats[0]), this).buildingResponse();
                break;

            case "STAT_AVG_TIME":
                result = new SuccessfulResponse(String.format(Locale.US, "%." + DECIMAL_PRECISION + "f", (stats[1] / stats[0])), this).buildingResponse();
                break;

            case "STAT_MAX_TIME":
                result = new SuccessfulResponse(String.format(Locale.US, "%." + DECIMAL_PRECISION + "f", stats[2]), this).buildingResponse();
                break;

            default:
                throw new CommandException("Command not in protocol specification: " + request);
        }
        return result;
    }
}
