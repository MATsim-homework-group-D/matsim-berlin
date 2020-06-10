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
    private int[] volumeLink = new int[30];
    private String link;
    private int summe;
//constructor
    LinkEventHandler(String outputFile, String link) {
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
    void printResult() {
        summe = 0;
        try {
            bufferedWriter.write("HOUR;VOLUME;");
            for(int i=0; i< 27; i++) {
                bufferedWriter.newLine();
                bufferedWriter.write(i+ ":00;" + this.volumeLink[i] + ";");
                summe = summe + this.volumeLink[i];
            }
            bufferedWriter.newLine();
            bufferedWriter.write("SUMME;"+ summe + ";");
            bufferedWriter.close();
        }
        catch (IOException ee) {
            throw new RuntimeException(ee);
        }
    }
}
