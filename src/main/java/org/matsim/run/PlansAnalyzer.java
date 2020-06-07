package org.matsim.run;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.population.routes.NetworkRoute;

import java.util.ArrayList;
import java.util.List;

public class PlansAnalyzer {

    public List<Id<Person>> agentsToAnalyze(Scenario scenario, List<Id<Link>> supervisedLinks) {

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
                                    System.out.println(person.getId().toString());
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



     PopulationWriter populationWriter = new PopulationWriter(scenario.getPopulation());
     populationWriter.write(outputPlans.toString());
 */