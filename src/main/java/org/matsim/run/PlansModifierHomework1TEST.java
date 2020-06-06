package org.matsim.run;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
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

public class PlansModifierHomework1TEST {

    public static void main(String[] args) {
        Path inputPlans = Paths.get("D:/Git/MATsim/matsim-berlin-group/scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.plans.xml.gz");
//        Path outputPlans = Paths.get(args[1]); //add second program argument in dropdown menu as output file (potentially .gz)

        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader populationReader = new PopulationReader(scenario);
        populationReader.readFile(inputPlans.toString());

        //creating array with 80 strings read from linksToRemove.xml

        String[] removedLinks = new String [80];
        // es bietet sich auch eine ArrayList an, diese ist unabhängig von der Anzahl der inputLinks
        BufferedReader reader = null;

        try {
            File file = new File("scenarios/berlin-v5.5-1pct/input/linksToRemove.xml");
            reader = new BufferedReader(new FileReader(file));

            for (int i = 0; i < removedLinks.length; i++) {
                removedLinks[i]=reader.readLine();
                System.out.println(removedLinks[i]); //printing array to check result
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert reader != null;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<Id<Person>> agentsOnLinks = new ArrayList<>();


            for(Person person : scenario.getPopulation().getPersons().values()) {
                System.out.println(person.getId().toString());
                Plan plan = person.getPlans(); //ERGEBNIS SIND MEHRERE PLANS!!!!
                PlanElement element = (PlanElement) plan.getPlanElements();
                System.out.println("funzt");
                if (element instanceof Leg) {
                    Leg leg = (Leg) element;
                    if (leg instanceof NetworkRoute) {
                        NetworkRoute route = (NetworkRoute) leg;
                        if ( route.getLinkIds().contains(removedLinks[4])) {
                            agentsOnLinks.add(person.getId());
                            System.out.println("AGENT GEFUNDEN");
                        }
                    }
                }

                //if
/*                NetworkRoute route = (NetworkRoute)((Plan) person.getPlans()).getPlanElements();
                if (route.getLinkIds().contains(removedLinks[2])) {
                    Id<Person> personId = person.getId();
                    agentsOnLinks.add(personId);
                    System.out.println(personId.toString());*/
                }

/*
            scenario.getPopulation().getPersons().values().parallelStream()
                    .flatMap(person -> person.getPlans().stream())
                    .flatMap(plan -> plan.getPlanElements().stream())
                    //.filter(element -> element instanceof Leg)
                    //.map(element -> (Leg) element)
                    .filter(element -> element instanceof NetworkRoute)
                    .map(element -> (NetworkRoute) element)
                    .filter(networkRoute -> networkRoute.getLinkIds().contains(linkId))
                    .map(element -> (Leg) element)
                    .forEach(leg -> leg.setRoute(null));
                    //.forEach(Leg::setRoute(null));
                    //.forEach(networkRoute -> networkRoute.getLinkIds().contains(linkId))
                    //.forEach(leg -> leg.setRoute(null));
        */


//        PopulationWriter populationWriter = new PopulationWriter(scenario.getPopulation());
//        populationWriter.write(outputPlans.toString());
    }
}
