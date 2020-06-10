package org.matsim.run;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.io.PopulationReader;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.misc.OptionalTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestAnalyze {

    public static void main(String[] args) {

        Path inputPlansBaseCase = Paths.get("scenarios/berlin-v5.5-1pct/data/nullfall_it.49/nullfall_berlin-v5.5-1pct.output_plans.xml.gz");
        Path inputPlansPolicyCase = Paths.get("scenarios/berlin-v5.5-1pct/data/planfall_it.49/planfall_berlin-v5.5-1pct.output_plans.xml.gz");
        File inputAgents = new File("scenarios/berlin-v5.5-1pct/data/agentsOnKantstrasse.txt");
        File testAgents = new File ("scenarios/berlin-v5.5-1pct/data/agentsTEST.txt");

//        List<Id<Person>> agents = bufferedReader(inputAgents);
        List<Id<Person>> test = bufferedReader(testAgents);

        Scenario base = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader basePopulation = new PopulationReader(base);
        basePopulation.readFile(inputPlansBaseCase.toString());

//        analyzeHomeOfAgents(base, test);
//        analyzeTravelTime(base, test);
        analyzeTravelDistanceOfLimitedAgents(base, test);

//       Scenario policy = ScenarioUtils.createScenario(ConfigUtils.createConfig());
//       PopulationReader policyPopulation = new PopulationReader(policy);
//       policyPopulation.readFile(inputPlansPolicyCase.toString());

    }

    private static List<Id<Person>> bufferedReader(File inputAgents) {
        List<Id<Person>> agents = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(inputAgents));
            String line;
            while ((line = reader.readLine()) != null) {
                agents.add(Id.createPersonId(line));
            }
        }
        catch (IOException ee) {
            ee.printStackTrace();
        }
        finally {
            try {
                assert reader != null;
                reader.close();
            }
            catch (IOException ee) {
                ee.printStackTrace();
            }
        }
        return agents;
    }

    private static void analyzeHomeOfLimitedAgents(Scenario scenario, List<Id<Person>> agents) {
        for (Person person : scenario.getPopulation().getPersons().values()) {
            if (agents.contains(person.getId())) {
                System.out.println(person.getAttributes().getAttribute("home-activity-zone").toString());
            }
        }
    }

    private static void analyzeTravelTimeOfLimitedAgents(Scenario scenario, List<Id<Person>> agents) {
        for (Person person : scenario.getPopulation().getPersons().values()) {
            int travelTime = 0;
            if (agents.contains(person.getId())) {
                for (Plan plan : person.getPlans()) {
                    for (PlanElement element : plan.getPlanElements()) {
                        if ( element instanceof Leg) {
                            Leg leg = (Leg) element;
                            Route route = leg. getRoute();
                            if (route.getTravelTime() != null) {
                                OptionalTime time =route.getTravelTime();
                                System.out.println(time.seconds());
                            }
                        }
                    }
                }
                System.out.println(person.getId().toString());
            }
        }
    }

    private static void analyzeTravelDistanceOfLimitedAgents(Scenario scenario, List<Id<Person>> agents) {
        for (Person person : scenario.getPopulation().getPersons().values()) {
            double distance = 0;
            if (agents.contains(person.getId())) {
                for (Plan plan : person.getPlans()) {
                    for (PlanElement element : plan.getPlanElements()) {
                        if (element instanceof Leg) {
                            Leg leg = (Leg) element;
                            Route route = leg.getRoute();
                            System.out.println(route.getDistance());
                        }
                    }
                }
            }
        }
    }
}