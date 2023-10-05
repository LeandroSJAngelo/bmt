package bmt.com.api.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SealandBot {

    public List<SchedulerDetail> extractSchedules(String url)
            throws Exception
    {
        Connection.Response doc = Jsoup
                .connect(url)
                .ignoreContentType(true)
                .execute();

        JsonObject js       = (JsonObject) JsonParser.parseString(doc.body());
        JsonArray products  = js.get("products").getAsJsonArray();
        List<SchedulerDetail> schedulerDetails = new ArrayList<>();

        for(JsonElement jsa : products)
        {
            JsonArray schedules = jsa.getAsJsonObject().get("schedules").getAsJsonArray();
            for(JsonElement aux : schedules)
            {
                String departureDate    = aux.getAsJsonObject().get("fromLocation").getAsJsonObject().get("date").getAsString();
                String departureLocal   = aux.getAsJsonObject().get("fromLocation").getAsJsonObject().get("siteGeoId").getAsString();
                String arrivalData      = aux.getAsJsonObject().get("toLocation").getAsJsonObject().get("date").getAsString();
                String arrivalLocal     = aux.getAsJsonObject().get("toLocation").getAsJsonObject().get("siteGeoId").getAsString();
                String voyage           = aux.getAsJsonObject().get("scheduleDetails").getAsJsonArray().get(0).getAsJsonObject().get("transport").getAsJsonObject().get("voyageNumber").getAsString();
                String vesselVoyage     = aux.getAsJsonObject().get("vessel").getAsJsonObject().get("name").getAsString();
                String vesselVoyageCode = aux.getAsJsonObject().get("vessel").getAsJsonObject().get("code").getAsString();

                SchedulerDetail schedulerDetail = SchedulerDetail
                        .builder()
                        .departureData(parserDate(departureDate))
                        .departureLocal(departureLocal)
                        .arrivalData(parserDate(arrivalData))
                        .arrivalLocal(arrivalLocal)
                        .voyage(voyage)
                        .vesselVoyage(vesselVoyage)
                        .vesselVoyageCode(vesselVoyageCode)
                        .build();

                schedulerDetails.add(schedulerDetail);
            }
        }

        return schedulerDetails;
    }

    private LocalDate parserDate(String date)
    {
        if(date.contains("T"))
            return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate();

        return LocalDate.parse(date);
    }

    public void parserInfos(List<SchedulerDetail> schedulerDetails)
            throws Exception
    {
        for(SchedulerDetail schedulerDetail : schedulerDetails)
        {
            String facilityId = schedulerDetail.getDepartureLocal();
            setDepartureArrivalLocation(schedulerDetail, schedulerDetail.getDepartureLocal(), "departure");
            setDepartureArrivalLocation(schedulerDetail, schedulerDetail.getArrivalLocal(), "arrival");
            setVesselValues(schedulerDetail);
            setDeadLinesValues(schedulerDetail, facilityId);
        }
    }

    private void setDeadLinesValues(SchedulerDetail navio, String facilityId)
            throws Exception
    {
        String payload = "[{\"facilityId\":\""+facilityId+"\",\"vesselMaerskCode\":\""+navio.getVesselVoyageCode()+"\",\"voyageNumber\":\""+navio.getVoyage()+"\",\"deadlineGroupName\":\"DOCUMENTATION\"}]";
        Connection.Response res1 = Jsoup.connect("https://api.maersk.com/synergy/deadlines/queries")
                .header("Content-Type", "application/json")
                .header("Consumer-Key", "uXe7bxTHLY0yY0e8jnS6kotShkLuAAqG")
                .requestBody(payload)
                .ignoreContentType(true)
                .method(Connection.Method.POST)
                .execute();

        JsonArray deadlines = (JsonArray) JsonParser.parseString(res1.body());

        for(JsonElement aux : deadlines)
        {
            if(!navio.getVoyage().equalsIgnoreCase(aux.getAsJsonObject().get("voyageNumber").getAsString()))
                continue;

            JsonArray lines = aux.getAsJsonObject().get("deadlines").getAsJsonArray();
            for(JsonElement aux1 : lines)
            {
                switch (aux1.getAsJsonObject().get("deadlineCode").getAsString()){
                    case "SINONAMS" : navio.setShippingInstructions(parserDate(aux1.getAsJsonObject().get("deadline").getAsString()));
                        break;
                    case "SGIN"     : navio.setContainerGateIn(parserDate(aux1.getAsJsonObject().get("deadline").getAsString()));
                        break;
                    case "VGM"      : navio.setVerifiedGrossMass(parserDate(aux1.getAsJsonObject().get("deadline").getAsString()));
                        break;
                    case "SIAMS"    : navio.setShippingInstructionsAms(parserDate(aux1.getAsJsonObject().get("deadline").getAsString()));
                        break;
                }
            }
        }
    }

    private void setVesselValues(SchedulerDetail navio)
            throws Exception
    {
        Connection.Response res = Jsoup
                .connect("https://api.maersk.com/synergy/reference-data/vehicle/vessels?vesselCodes="+ navio.getVesselVoyageCode())
                .ignoreContentType(true)
                .execute();

        JsonArray vessel = (JsonArray) JsonParser.parseString(res.body());
        navio.setImoNumber(vessel.get(0).getAsJsonObject().get("vesselIMONumber").getAsString());
        navio.setCallSign(vessel.get(0).getAsJsonObject().get("callSign").getAsString());
    }

    private void setDepartureArrivalLocation(SchedulerDetail navio, String location, String type)
            throws Exception
    {
        Connection.Response res = Jsoup
                .connect("https://api.maersk.com/synergy/reference-data/geography/locations/"+ location)
                .header("Consumer-Key", "uXe7bxTHLY0yY0e8jnS6kotShkLuAAqG")
                .ignoreContentType(true)
                .execute();

        JsonObject local = (JsonObject) JsonParser.parseString(res.body());

        if(type.equalsIgnoreCase("departure"))
            navio.setDepartureLocal(local.get("cityName").getAsString());
        else
            navio.setArrivalLocal(local.get("cityName").getAsString());
    }
}
