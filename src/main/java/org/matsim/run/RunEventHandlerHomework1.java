package org.matsim.run;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

public class RunEventHandlerHomework1 {

    public static void main(String[] args) {

        String eventInputFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct/ITERS/it.0/berlin-v5.5-1pct.0.events.xml.gz";
        String linkInputFile = "";
        String outputFile = "scenarios/berlin-v5.5-1pct/output-berlin-v5.5-1pct/ITERS/testHandledEvents.txt";

        EventsManager eventsManager = EventsUtils.createEventsManager();

        LinkEventHandlerHomework1 linkEventHandler = new LinkEventHandlerHomework1(outputFile);
        eventsManager.addHandler(linkEventHandler);

        MatsimEventsReader eventsReader = new MatsimEventsReader(eventsManager);
        eventsReader.readFile(eventInputFile);

        linkEventHandler.printResult();


    }

}
