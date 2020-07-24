package org.matsim.run;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.network.Link;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author stepperstep
 */

public class LinkEventHandler implements LinkEnterEventHandler, LinkLeaveEventHandler {

//final variables
    private final BufferedWriter bufferedWriter;
    private List<Id<Link>> linkIds;
    private int numberOfLinks;
    private int[] [] volumeLink;

    //constructor
    LinkEventHandler(List<Id<Link>> linkIds) {
        this.linkIds = linkIds;
        this.numberOfLinks = this.linkIds.size();
        this.volumeLink = new int[30][numberOfLinks];
        try {
            String outputFile = "scenarios/berlin-v5.5-1pct/data/TestFiles/TestAuswertungAlle.csv";
            FileWriter fileWriter = new FileWriter(outputFile);
            bufferedWriter = new BufferedWriter(fileWriter);
        }
        catch (IOException ee) {
            throw new RuntimeException(ee);
        }
    }

//methods
    @Override
    public void handleEvent(LinkEnterEvent linkEnterEvent) {    //LinkEnterEventHandler
        for ( int i=0; i<numberOfLinks; i++) {
            if (linkEnterEvent.getLinkId().equals(linkIds.get(i))) {
                int hourSlot = (int) (linkEnterEvent.getTime() / 3600);
                this.volumeLink[hourSlot] [i]++;
            }
        }
    }

    @Override
    public void handleEvent(LinkLeaveEvent linkLeaveEvent) {    //LinkLeaveEventHandler
        //possibility to analyze traffic jam with time differences
    }

    void printResult() {    //Printer for results of Analysis with EventHandler
        int [] summe = new int[numberOfLinks];
        try {
            bufferedWriter.write("HOUR;");
            for (Id<Link> links : linkIds) {
                String linkId = links.toString();
                bufferedWriter.write(linkId + ";");
            }
            for (int i=0; i< 27; i++) {
                bufferedWriter.newLine();
                bufferedWriter.write(i+ ":00;");
                for (int j=0; j<numberOfLinks; j++) {
                    bufferedWriter.write( this.volumeLink[i] [j] + ";");
                    summe [j]= summe [j] + this.volumeLink[i] [j];
                }
            }
            bufferedWriter.newLine();
            bufferedWriter.write("SUMME;");
            for (int j=0; j<numberOfLinks; j++) {
                bufferedWriter.write(summe[j] + ";");
            }
            bufferedWriter.close();
        }
        catch (IOException ee) {
            throw new RuntimeException(ee);
        }
    }
}
