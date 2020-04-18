package com.btplanner.btripexbackend.util;

import com.btplanner.btripexbackend.datamodel.accountmodel.Event;

import java.util.List;
import java.util.Objects;

public class EventBean {
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventBean eventBean = (EventBean) o;
        return Objects.equals(events, eventBean.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events);
    }

    @Override
    public String toString() {
        return "EventBean{" +
                "events=" + events +
                '}';
    }
}
