package org.matsim.run.homework2;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.io.PopulationReader;
import org.matsim.core.population.routes.NetworkRoute;
import org.matsim.core.scenario.ScenarioUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FindAgentsOnLinks {

    public static void main(String[] args) {

        Path baseCasePlans = Paths.get("scenarios/berlin-v5.5-1pct/data/nullfall_it.49/nullfall_berlin-v5.5-1pct.output_plans.xml.gz");
        Path policyCasePlans = Paths.get("scenarios/berlin-v5.5-1pct/data/planfall_it.49/planfall_berlin-v5.5-1pct.output_plans.xml.gz");
        File kudammLinks = new File("scenarios/berlin-v5.5-1pct/data/linksKudamm.txt");

        Scenario baseCaseScenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader baseCasePopulationReader = new PopulationReader(baseCaseScenario);
        baseCasePopulationReader.readFile(baseCasePlans.toString());

        Scenario policyCaseScenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader policyCasePopulationReader = new PopulationReader(policyCaseScenario);
        policyCasePopulationReader.readFile(policyCasePlans.toString());

        List<Id<Link>> links = bufferedReader(kudammLinks);
        List<Id<Person>> baseCaseReceivedAgents = agentsOnLinks(baseCaseScenario, links);
        List<Id<Person>> policyCaseReceivedAgents = agentsOnLinks(policyCaseScenario, links);

        System.out.println("BASE CASE");
        System.out.println(baseCaseReceivedAgents.toString());
        System.out.println("POLICY CASE");
        System.out.println(policyCaseReceivedAgents.toString());


    }

    private static List<Id<Link>> bufferedReader(File linkFile) {
        List<Id<Link>> networkLinks = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(linkFile));
            String line;
            while ((line = reader.readLine()) != null) {
                networkLinks.add(Id.createLinkId(line));
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
        return networkLinks;
    }

    private static List<Id<Person>> agentsOnLinks (Scenario scenario, List<Id<Link>> links) {
        List<Id<Person>> agents = new ArrayList<>();
        int counter = 0;

        for (Person person : scenario.getPopulation().getPersons().values()) {
            int indicator = 0;
            for (Plan plan : person.getPlans()) {
                for (PlanElement element : plan.getPlanElements()) {
                    if (element instanceof Leg) {
                        Leg leg = (Leg) element;
                        Route route = leg.getRoute();
                        if (route instanceof NetworkRoute) {
                            NetworkRoute networkRoute = (NetworkRoute) route;
                            for (Id<Link> wantedLink : links) {
                                if (networkRoute.getLinkIds().contains(wantedLink)) {
                                    System.out.println("NETWORKLINK FOUND FOR LEG.getMode " + leg.getMode() + " NETWORKROUTE.getDistance " + networkRoute.getDistance() + " FOR AGENT " + person.getId().toString());
                                    indicator = 1;
                                }
                            }
                        }
                        if (route != null) {
                            for (Id<Link> wantedLink : links) {
                                if (route.getStartLinkId().equals(wantedLink) || route.getEndLinkId().equals(wantedLink)) {
                                    System.out.println("GENERICLINK FOUND FOR LEG.getAttributes " + leg.getAttributes().toString() + " LEG.getMode " + leg.getMode() + " FOR PERSON " + person.getId().toString());
                                    indicator = 1;
                                }
                            }
                        }
                    }
                    if (element instanceof Activity) {
                        Activity activity = (Activity) element;
                        for (Id<Link> wantedLink : links) {
                            if (activity.getLinkId() != null && activity.getLinkId().equals(wantedLink)) {
                                System.out.println("ACTIVITY FOUND FOR ACTIVITY.type " + activity.getType() + " FOR PERSON " + person.getId().toString());
                                indicator = 1;
                            }
                        }
                    }
                }
            }
            if (indicator == 1) {
                agents.add(person.getId());
                counter++;
            }
        }
        System.out.println("------------------<<<<>>>>>> " + counter);
        return agents;
    }
}
