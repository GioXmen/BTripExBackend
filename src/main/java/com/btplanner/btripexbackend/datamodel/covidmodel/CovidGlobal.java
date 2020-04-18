package com.btplanner.btripexbackend.datamodel.covidmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CovidGlobal {

    @JsonProperty("NewConfirmed")
    private int NewConfirmed;

    @JsonProperty("TotalConfirmed")
    private int TotalConfirmed;

    @JsonProperty("NewDeaths")
    private int NewDeaths;

    @JsonProperty("TotalDeaths")
    private int TotalDeaths;

    @JsonProperty("NewRecovered")
    private int NewRecovered;

    @JsonProperty("TotalRecovered")
    private int TotalRecovered;

    @JsonProperty("NewConfirmed")
    public int getNewConfirmed() {
        return NewConfirmed;
    }

    @JsonCreator
    public CovidGlobal() {
    }

    @JsonProperty("NewConfirmed")
    public void setNewConfirmed(int newConfirmed) {
        NewConfirmed = newConfirmed;
    }

    @JsonProperty("TotalConfirmed")
    public int getTotalConfirmed() {
        return TotalConfirmed;
    }

    @JsonProperty("TotalConfirmed")
    public void setTotalConfirmed(int totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    @JsonProperty("NewDeaths")
    public int getNewDeaths() {
        return NewDeaths;
    }

    @JsonProperty("NewDeaths")
    public void setNewDeaths(int newDeaths) {
        NewDeaths = newDeaths;
    }

    @JsonProperty("TotalDeaths")
    public int getTotalDeaths() {
        return TotalDeaths;
    }

    @JsonProperty("TotalDeaths")
    public void setTotalDeaths(int totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    @JsonProperty("NewRecovered")
    public int getNewRecovered() {
        return NewRecovered;
    }

    @JsonProperty("NewRecovered")
    public void setNewRecovered(int newRecovered) {
        NewRecovered = newRecovered;
    }

    @JsonProperty("TotalRecovered")
    public int getTotalRecovered() {
        return TotalRecovered;
    }

    @JsonProperty("TotalRecovered")
    public void setTotalRecovered(int totalRecovered) {
        TotalRecovered = totalRecovered;
    }
}
