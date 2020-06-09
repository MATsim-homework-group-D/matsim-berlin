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
        File fileWithLinksToSupervise = new File("scenarios/berlin-v5.5-1pct/data/linksToRemove.xml");

        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader populationReader = new PopulationReader(scenario);
        populationReader.readFile(inputPlans.toString());

        List<Id<Link>> supervisedLinks = bufferedReader(fileWithLinksToSupervise);
        modifyPlansForAgentsOfSupervisedLinks(scenario, supervisedLinks);
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
        System.out.println(linksToSupervise.toString());
        return linksToSupervise;
    }

    public static List<Id<Person>> agentsToAnalyze(Scenario scenario, List<Id<Link>> supervisedLinks) {

        List<Id<Person>> agentsOnSupervisedLinks = new ArrayList<>();

        for (Person person : scenario.getPopulation().getPersons().values()) {
            identifier:
            for (Plan plan : person.getPlans()) {
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

    public static void writeFileWithConcernedAgents(List<Id<Person>> concernedAgents) {
        String outputConcernedAgents = "scenarios/berlin-v5.5-1pct/data/agentsOnKantstrasse.txt";
        bufferedWriter(concernedAgents, outputConcernedAgents);
    }

    private static void bufferedWriter(List<Id<Person>> list, String outputFile) {
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Id<Person> personId : list) {
                bufferedWriter.write(personId.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ee) {
            throw new RuntimeException(ee);
        }
    }

    private static void modifyPlansForAgentsOfSupervisedLinks(Scenario scenario, List<Id<Link>> supervisedLinks) {
        List<Id<Person>> agentsOnSupervisedLinks = new ArrayList<>();
        int counterNetworkLink = 0;
        int counterGenericLink = 0;
        int counterActivity = 0;
        int counter = 0;

        for (Person person : scenario.getPopulation().getPersons().values()) {
            int indicator = 0;
            for (Plan plan : person.getPlans()) {
                for (PlanElement element : plan.getPlanElements()) {
                    if (element instanceof Leg) {
                        Leg leg = (Leg) element;
                        //delete all NetworkRoutes that contains links of Kantstrasse
                        if (leg.getRoute() instanceof NetworkRoute) {
                            NetworkRoute route = (NetworkRoute) leg.getRoute();
                            for (Id<Link> testingLinks : supervisedLinks) {
                                if (route.getLinkIds().contains(testingLinks)) {
                                    leg.setRoute(null);
                                    System.out.println("CAUSE OF NETWORKLINK DONE NULL FOR " + person.getId().toString());
                                    counterNetworkLink ++;
                                    indicator = 1;
                                }
                            }
                        }
                        //delete all generic routes that start or end on links of Kantstrasse
                        if (leg.getRoute() != null) {
                            Route route = leg.getRoute();
                            for (Id<Link> testingLinks : supervisedLinks) {
                                if (route.getStartLinkId().equals(testingLinks) || route.getEndLinkId().equals(testingLinks)) {
                                    leg.setRoute(null);
                                    System.out.println("CAUSE OF START OR ENDLINK DONE NULL FOR " + person.getId().toString());
                                    counterGenericLink ++;
                                    indicator = 1;
                                }
                            }
                        }
                    }
                    //delete all activities that contain links of Kantstrasse
                    if (element instanceof Activity) {
                        Activity activity = (Activity) element;
                        for (Id<Link> testingLinks : supervisedLinks) {
                            if (activity.getLinkId() != null && activity.getLinkId().equals(testingLinks)) {
                                activity.setLinkId(null);
                                System.out.println("DONE ACTIVITY LINK NULL FOR " + person.getId().toString());
                                counterActivity ++;
                                indicator = 1;
                            }
                        }
                    }
                }
            }
            if ( indicator == 1){
                counter ++;
                agentsOnSupervisedLinks.add(person.getId());
                writeFileWithConcernedAgents(agentsOnSupervisedLinks);
            }
        }

        PopulationWriter populationWriter = new PopulationWriter(scenario.getPopulation());
        populationWriter.write("scenarios/berlin-v5.5-1pct/data/berlin-v5.5-1pct.plans_without_Kantstrasse.xml.gz");
        System.out.println("DELETED ROUTES OF " + counter + " AGENTS.");
        System.out.println("DELETED " + counterNetworkLink + " ROUTES OF AGENTS CAUSE OF NETWORKLINK.");
        System.out.println("DELETED " + counterGenericLink + " ROUTES OF AGENTS CAUSE OF GENERICLINK.");
        System.out.println("DELETED ACTIVITYLINKS OF " + counterActivity + " AGENTS CAUSE OF ACTIVITY");
    }
}