package org.matsim.run;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RunEventHandler {

    public static void main(String[] args) {

        String eventInputFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct/ITERS/it.0/berlin-v5.5-1pct.0.events.xml.gz";
        String[] linksToAnalyze = new String [3];
        BufferedReader linkReader = null;

        try {
            File file = new File("scenarios/berlin-v5.5-1pct/input/drt/linksToAnalyze.xml");
            linkReader = new BufferedReader(new FileReader(file));

            for (int i = 0; i < linksToAnalyze.length; i++) {
                linksToAnalyze[i]=linkReader.readLine();
                System.out.println(linksToAnalyze[i]); //printing array to check result
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert linkReader != null;
                linkReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i =0; i< linksToAnalyze.length; i++) {
            String link = linksToAnalyze[i];

            String outputFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct/ITERS/volume_" + link + ".txt";

            EventsManager eventsManager = EventsUtils.createEventsManager();

            LinkEventHandler linkEventHandler = new LinkEventHandler(outputFile, link);
            eventsManager.addHandler(linkEventHandler);

            MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
            eventsReader.readFile(eventInputFile);

            linkEventHandler.printResult();
        }


    }

}
