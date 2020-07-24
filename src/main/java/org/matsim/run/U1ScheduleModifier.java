package org.matsim.run;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.pt.transitSchedule.api.TransitLine;
import org.matsim.pt.transitSchedule.api.TransitSchedule;

public class U1ScheduleModifier {

    public static void main(String[] args) {

        Config config = ConfigUtils.loadConfig("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.config_without_kantstrasse.xml");
        Scenario scenario = ScenarioUtils.loadScenario(config);

        TransitSchedule transitSchedule = scenario.getTransitSchedule();
        TransitLine U1 = transitSchedule.getTransitLines().get(Id.create("U1",TransitLine.class));
        System.out.println(U1.toString());
        System.out.println("PLATZHALTER");
        System.out.println(U1.getRoutes().toString());
    }
}
