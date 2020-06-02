package org.matsim.run;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

public class RunEventHandler {

    public static void main(String[] args) {

        String inputFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct/ITERS/it.0/berlin-5.5-1pct.0.events.xml.gz";
        String outputFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct/ITERS/testHandledEvents.txt";

        EventsManager eventsManager = EventsUtils.createEventsManager();

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
        eventsReader.readFile(inputFile);

        LinkEventHandler linkEventHandler = new LinkEventHandler(outputFile);
        eventsManager.addHandler(linkEventHandler);


    }

}
