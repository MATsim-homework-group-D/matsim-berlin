package org.matsim.run;

import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LinkEventHandler implements LinkEnterEventHandler, LinkLeaveEventHandler {

//final variables
    private final BufferedWriter bufferedWriter;

//constructor
    public LinkEventHandler(String outputFile) {
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            bufferedWriter = new BufferedWriter(fileWriter);
        }
        catch (IOException ee) {
            throw new RuntimeException(ee);
        }
    }

    @Override
    public void handleEvent(LinkEnterEvent linkEnterEvent) {

    }

    @Override
    public void handleEvent(LinkLeaveEvent linkLeaveEvent) {

    }
}
