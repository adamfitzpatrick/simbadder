package net.muneris.simbadder.testutils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class TestLoggingAppender extends AppenderSkeleton {

    private List<LoggingEvent> eventsList = new ArrayList<>();

    
    @Override
    public void close() {
        // Not Implemented
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    protected void append(LoggingEvent event) {
        eventsList.add(event);
    }
    
    public List<LoggingEvent> getEventsList() {
        return eventsList;
    }
    
    public List<String> getMessages() {
        return eventsList.stream()
                .map(event -> event.getMessage().toString())
                .collect(Collectors.toList());
    }
    
    public List<LoggingEvent> getEventByLoggerName(String name) {
        return this.eventsList.stream().filter(loggerNamePredicate(name))
                .collect(Collectors.toList());
    }
    
    public List<LoggingEvent> getEventByLoggerName(Class<?> clazz) {
        return getEventByLoggerName(clazz.getName());
    }
    
    private Predicate<LoggingEvent> loggerNamePredicate(String name) {
        return event -> event.getLogger().getName().equals(name);
    }
}
