package org.matsim.run;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
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

public class PlanModifier {

    public static void main(String[] args) {
        Path inputPlans = Paths.get("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.plans.xml.gz");
        File fileWithLinksToSupervise = new File("scenarios/berlin-v5.5-1pct/input/linksToRemove.xml");
        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader populationReader = new PopulationReader(scenario);
        populationReader.readFile(inputPlans.toString());

        List<Id<Link>> supervisedLinks = bufferedReader (fileWithLinksToSupervise);
        List<Id<Person>> agentsOnSupervisedLinks = agentsToAnalyze(scenario, supervisedLinks);
        System.out.println(agentsOnSupervisedLinks.toString());
    }

    private static List<Id<Link>> bufferedReader(File fileWithLinks) {
        List<Id<Link>> linksToSupervise = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fileWithLinks));
            String line;
            while ((line = reader.readLine()) != null) {
                linksToSupervise.add(Id.createLinkId(line));
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
        return linksToSupervise;
    }

    public static List<Id<Person>> agentsToAnalyze(Scenario scenario, List<Id<Link>> supervisedLinks) {

        List<Id<Person>> agentsOnSupervisedLinks = new ArrayList<>();

        for (Person person : scenario.getPopulation().getPersons().values()) {
            identifier: for (Plan plan : person.getPlans()) {
                for (PlanElement element : plan.getPlanElements()) {
                    if (element instanceof Leg) {
                        Leg leg = (Leg) element;
                        if (leg.getRoute() instanceof NetworkRoute) {
                            NetworkRoute route = (NetworkRoute) leg.getRoute();
                            for (Id<Link> testingLinks : supervisedLinks) {
                                if (route.getLinkIds().contains(testingLinks)) {
                                    agentsOnSupervisedLinks.add(person.getId());
                                    break identifier;
                                }
                            }
                        }
                    }
                }
            }
        }
        return agentsOnSupervisedLinks;
    }

    public void writeFileWithConcernedAgents (List<Id<Person>> concernedAgents) {
        System.out.println("NOT IMPLEMENTED YET");
    }

    public void bufferedWriter (ArrayList list){
        System.out.println("NOT IMPLEMENTED YET");
    }
}
