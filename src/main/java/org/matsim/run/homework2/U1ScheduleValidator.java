package org.matsim.run.homework2;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.pt.transitSchedule.api.TransitSchedule;
import org.matsim.pt.utils.TransitScheduleValidator;

import java.nio.file.Path;
import java.nio.file.Paths;


public class U1ScheduleValidator {

    private final static Logger LOG = Logger.getLogger(U1ScheduleValidator.class);

    public static void main(String[] args) {

        Config config = ConfigUtils.loadConfig("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.config_U1extension_without_kantstrasse.xml");
        Scenario scenario = ScenarioUtils.loadScenario(config);

        Path inputTransitSchedule = Paths.get("scenarios/berlin-v5.5-1pct/input/modi-transit-schedule_U1_stefan.xml.gz");
        Path inputNetwork = Paths.get("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-network_U1extension_without_kantstrasse.xml");

 //       TransitScheduleReader reader = new TransitScheduleReader(scenario);
 //       reader.readFile(inputTransitSchedule.toString());
        TransitSchedule transitSchedule = scenario.getTransitSchedule();

        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(inputNetwork.toString());

        TransitScheduleValidator.ValidationResult result = TransitScheduleValidator.validateAll(transitSchedule, scenario.getNetwork());
        for (String error : result.getErrors()) {
            LOG.warn(error);
        }
        for (String warning : result.getWarnings()) {
            LOG.warn(warning);
        }
        for (TransitScheduleValidator.ValidationResult.ValidationIssue issue : result.getIssues()) {
            LOG.warn(issue.getMessage());
        }




    }
}
