package com.lomalan.main.service.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lomalan.main.rest.model.f1.Race;
import com.lomalan.main.rest.model.f1.RaceResults;

public class RaceResultsParser {

    private RaceResultsParser() {
    }

    public static Race parseToRace(String json) {
        json = json.replace("Driver", "driver");
        Gson gson = new Gson();
        return gson.fromJson(getAsJsonObject(json), Race.class);
    }

    public static RaceResults parseToRaceResults(String json) {
        json = json.replace("Driver", "driver");
        Gson gson = new Gson();
        return gson.fromJson(getAsJsonObject(json), RaceResults.class);
    }

    private static JsonObject getAsJsonObject(String json) {
        JsonElement jelement = JsonParser.parseString(json);
        JsonObject jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonObject("MRData");
        jobject = jobject.getAsJsonObject("RaceTable");
        return jobject.getAsJsonArray("Races").get(0).getAsJsonObject();
    }
}
