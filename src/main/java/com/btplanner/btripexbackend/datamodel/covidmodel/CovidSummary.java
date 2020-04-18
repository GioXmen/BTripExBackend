package com.btplanner.btripexbackend.datamodel.covidmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CovidSummary {

    @JsonProperty("Global")
    private CovidGlobal Global;

    @JsonProperty("Countries")
    private List<CovidCountries> Countries = new ArrayList<CovidCountries>();

    @JsonProperty("Date")
    private Date Date;

    @JsonCreator
    CovidSummary(){
        Countries = new ArrayList<CovidCountries>();
    }

    @JsonProperty("Countries")
    public List<CovidCountries> getCountries() {
        return Countries;
    }

    @JsonProperty("Countries")
    public void setCountries(List<CovidCountries> countries) {
        Countries = countries;
    }

    @JsonProperty("Date")
    public java.util.Date getDate() {
        return Date;
    }

    @JsonProperty("Date")
    public void setDate(java.util.Date date) {
        Date = date;
    }

    @JsonProperty("Global")
    public CovidGlobal getGlobal() {
        return Global;
    }

    @JsonProperty("Global")
    public void setGlobal(CovidGlobal global) {
        Global = global;
    }
}
