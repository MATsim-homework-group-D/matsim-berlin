package org.matsim.run.homework1;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stepperstep
 */

public class RunEventHandler {

    public static void main(String[] args) {

        String eventInputFile = "scenarios/berlin-v5.5-1pct/data/nullfall_it.49/nullfall_berlin-v5.5-1pct.49.events.xml.gz";
        File fileWithLinksToAnalyze = new File("scenarios/berlin-v5.5-1pct/data/linksToAnalyze.xml");
        List<Id<Link>> linksToAnalyze = bufferedReader(fileWithLinksToAnalyze);

        EventsManager eventsManager = EventsUtils.createEventsManager();

        LinkEventHandler linkEventHandler = new LinkEventHandler(linksToAnalyze);
        eventsManager.addHandler(linkEventHandler);

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
        eventsReader.readFile(eventInputFile);

        linkEventHandler.printResult();
    }

    private static List<Id<Link>> bufferedReader (File fileWithLinksToAnalyze) {
        List<Id<Link>> linksToAnalyze = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fileWithLinksToAnalyze));
            String line;
            while ((line = reader.readLine()) != null) {
                linksToAnalyze.add(Id.createLinkId(line));
            }
        } catch (IOException ee) {
            ee.printStackTrace();
        } finally {
            try {
                assert reader != null;
                reader.close();
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }
        System.out.println(linksToAnalyze.toString());
        return linksToAnalyze;
    }

}
