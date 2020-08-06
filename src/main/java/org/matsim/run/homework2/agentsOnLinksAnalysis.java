package org.matsim.run.homework2;

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

public class agentsOnLinksAnalysis {

    public static void main(String[] args) {

//inputFiles
        Path baseCasePlans = Paths.get("scenarios/berlin-v5.5-1pct/data/homework 2/Nullfall/berlin-v5.5-1pct.output_plans_base.xml.gz"); // <-- fill in relative path of basecase_plans
        Path policyCasePlans = Paths.get("scenarios/berlin-v5.5-1pct/data/homework 2/Planfall/berlin-v5.5-1pct.output_plans_policy.xml.gz"); // <-- fill in relative path of policycase_plans
        File AgentsOnLinks = new File("scenarios/berlin-v5.5-1pct/data/homework 2/linksKudamm.txt"); // <-- fill in relative path of txt-File with links

//outputFiles
        File baseCaseSummary = new File("scenarios/berlin-v5.5-1pct/data/homework 2/agentsOnKudamm_base.txt"); // <-- fill in relative Path for new txt-File
        File policyCaseSummary = new File("scenarios/berlin-v5.5-1pct/data/homework 2/agentsOnKudamm_policy.txt"); // <-- fill in relative Path for new txt-File
        File baseCaseSummary_extended = new File ("scenarios/berlin-v5.5-1pct/data/homework 2/agentsOnKudammSummary_base.csv"); // <-- fill in relative Path for new csv-File
        File policyCaseSummary_extended = new File ("scenarios/berlin-v5.5-1pct/data/homework 2/agentsOnKudammSummary_policy.csv"); // <-- fill in relative Path for new csv-File

        List<Id<Link>> links = bufferedReader(AgentsOnLinks);

//base case
/*
        Scenario baseCaseScenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader baseCasePopulationReader = new PopulationReader(baseCaseScenario);
        baseCasePopulationReader.readFile(baseCasePlans.toString());

        List<Id<Person>> baseCaseReceivedAgents = agentsOnLinks(baseCaseScenario, links);

        System.out.println("BASE CASE");
        System.out.println(baseCaseReceivedAgents.toString());
        bufferedWriterToTxt(baseCaseReceivedAgents, baseCaseSummary);
*/
// es ist immer nur eine Auswertung möglich bei geringem Arbeitsspeicher!!! policy oder base case je nach Ausklammerung.

//policy case
        Scenario policyCaseScenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader policyCasePopulationReader = new PopulationReader(policyCaseScenario);
        policyCasePopulationReader.readFile(policyCasePlans.toString());

        List<Id<Person>> policyCaseReceivedAgents = agentsOnLinks(policyCaseScenario, links);

        System.out.println("POLICY CASE");
        System.out.println(policyCaseReceivedAgents.toString());
        bufferedWriterToTxt(policyCaseReceivedAgents, policyCaseSummary);
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

    private static void bufferedWriterToTxt(List<Id<Person>> list, File outputFile) {
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
/*
    private static void bufferedWriterToCsv(List<Id<Person>> baseCaseAgents, List<Id<Person>> policyCaseAgents) {
        File outputFile = new File("scenarios/berlin-v5.5-1pct/data/homework 2/agentsOnKudamm_both.txt"); // <-- fill in relative Path for new csv-File
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("base case;policy case");
            for (Id<Person> personId : list) {
                bufferedWriter.write(personId.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ee) {
            throw new RuntimeException(ee);
        }
    }
*/
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
                        //received agents of NetworkRoute
                        if (route instanceof NetworkRoute) {
                            NetworkRoute networkRoute = (NetworkRoute) route;
                            for (Id<Link> wantedLink : links) {
                                if (networkRoute.getLinkIds().contains(wantedLink)) {
                                    System.out.println("NETWORKLINK FOUND FOR LEG.getMode " + leg.getMode() + " NETWORKROUTE.getDistance " + networkRoute.getDistance() + " FOR AGENT " + person.getId().toString());
                                    indicator = 1;
                                }
                            }
                        }
                        //received agents of generic routes
                        if (route != null) {
                            for (Id<Link> wantedLink : links) {
                                if (route.getStartLinkId().equals(wantedLink) || route.getEndLinkId().equals(wantedLink)) {
                                    System.out.println("GENERICLINK FOUND FOR LEG.getAttributes " + leg.getAttributes().toString() + " LEG.getMode " + leg.getMode() + " FOR PERSON " + person.getId().toString());
                                    indicator = 1;
                                }
                            }
                        }
                    }
                    //received agents of activities
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
        System.out.println(counter);
        return agents;
    }
}
