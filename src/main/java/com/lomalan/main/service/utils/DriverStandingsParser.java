package com.lomalan.main.service.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lomalan.main.rest.model.f1.DriverStandings;
import java.util.ArrayList;
import java.util.List;

public class DriverStandingsParser {

    private DriverStandingsParser() {
    }

    public static List<DriverStandings> parseToDriverStandings(String json) {
        json = json.replace("Driver", "driver");
        Gson gson = new Gson();
        JsonArray jarray = getJsonArray(json);
        List<DriverStandings> entities = new ArrayList<>();

        for (int i = 0; i < jarray.size(); ++i) {
            entities.add(gson.fromJson(jarray.get(i).getAsJsonObject(), DriverStandings.class));
        }

        return entities;
    }

    private static JsonArray getJsonArray(String json) {
        JsonElement jelement = JsonParser.parseString(json);
        JsonObject jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonObject("MRData");
        jobject = jobject.getAsJsonObject("StandingsTable");
        jobject = jobject.getAsJsonArray("StandingsLists").get(0).getAsJsonObject();
        return jobject.getAsJsonArray("driverStandings");
    }
}
