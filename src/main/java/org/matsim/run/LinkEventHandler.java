package org.matsim.run;

import org.matsim.api.core.v01.Id;
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
    private double[] volumeLink = new double[32];
    private String link;
//constructor
    public LinkEventHandler(String outputFile, String link) {
        this.link = link;
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
        if (linkEnterEvent.getLinkId().equals(Id.createLinkId(link))) {
            int hourSlot = (int) (linkEnterEvent.getTime()/3600);
            this.volumeLink[hourSlot]++;
        }
    }

    @Override
    public void handleEvent(LinkLeaveEvent linkLeaveEvent) {

    }
    public void printResult() {
        try {
            bufferedWriter.write("HOUR \t \t \t VOLUME");
            for(int i=0; i< 24; i++) {
                bufferedWriter.newLine();
                bufferedWriter.write(i+ ":00  \t \t \t" + this.volumeLink[i]);
            }
            bufferedWriter.close();
        }
        catch (IOException ee) {
            throw new RuntimeException(ee);
        }
    }
}
