package org.matsim.run;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.io.PopulationReader;
import org.matsim.core.population.routes.NetworkRoute;
import org.matsim.core.scenario.ScenarioUtils;

import java.io.*;
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
        writeFileWithConcernedAgents(agentsOnSupervisedLinks);
//      planDeleterForConcernedAgents(scenario, agentsOnSupervisedLinks); //use only one PlanDeleter!!
        planDeleterForAgentsOfSupervisedLinks(scenario, supervisedLinks);
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

    public static void writeFileWithConcernedAgents (List<Id<Person>> concernedAgents) {
        String outputConcernedAgents = "scenarios/berlin-v5.5-1pct/input/agentsOnKantstrasse.xml";
        bufferedWriter(concernedAgents, outputConcernedAgents);
    }

    public static void bufferedWriter (List<Id<Person>> list, String outputFile){
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i=0; i< list.size(); i++) {
                bufferedWriter.write(list.get(i).toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        catch (IOException ee) {
            throw new RuntimeException(ee);
        }
    }

    public static void planDeleterForConcernedAgents(Scenario scenario, List<Id<Person>> concernedAgents) {
        int counter = 0;
        for (Person person : scenario.getPopulation().getPersons().values()) {
            identifier : for (int i=0; i<concernedAgents.size(); i++) {
                if (person.getId().equals(concernedAgents.get(i))) {
                    for (Plan plan : person.getPlans()) {
                        for (PlanElement element : plan.getPlanElements()) {
                            if (element instanceof Leg) {
                                Leg leg = (Leg) element;
                                leg.setRoute(null);
                                counter ++;
                                System.out.println("DONE ROUTE NULL FOR " + person.getId().toString());
                                break identifier;
                            }
                        }
                    }
                }
            }
        }
        PopulationWriter populationWriter = new PopulationWriter(scenario.getPopulation());
        populationWriter.write("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.plans_without_kantstrasse.xml");
        System.out.println("DELETED ROUTE OF "+ counter +"AGENTS");
    }

    public static void planDeleterForAgentsOfSupervisedLinks(Scenario scenario, List<Id<Link>> supervisedLinks) {
        int counter = 0;
        for (Person person : scenario.getPopulation().getPersons().values()) {
            identifier: for (Plan plan : person.getPlans()) {
                for (PlanElement element : plan.getPlanElements()) {
                    if (element instanceof Leg) {
                        Leg leg = (Leg) element;
                        if (leg.getRoute() instanceof NetworkRoute) {
                            NetworkRoute route = (NetworkRoute) leg.getRoute();
                            for (Id<Link> testingLinks : supervisedLinks) {
                                if (route.getLinkIds().contains(testingLinks)) {
                                    leg.setRoute(null);
                                    System.out.println("DONE ROUTE NULL FOR " + person.getId().toString());
                                    counter ++;
                                    break identifier;
                                }
                            }
                        }
                    }
                }
            }
        }
        PopulationWriter populationWriter = new PopulationWriter(scenario.getPopulation());
        populationWriter.write("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.plans_without_kantstrasse.xml");
        System.out.println("DELETED ROUTE OF "+ counter +"AGENTS");
    }
}
