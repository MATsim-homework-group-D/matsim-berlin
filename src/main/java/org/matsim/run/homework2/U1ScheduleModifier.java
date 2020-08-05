package org.matsim.run.homework2;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.routes.NetworkRoute;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.misc.OptionalTime;
import org.matsim.pt.transitSchedule.TransitRouteStopImpl;
import org.matsim.pt.transitSchedule.TransitStopFacilityImpl;
import org.matsim.pt.transitSchedule.api.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class U1ScheduleModifier {

    public static void main(String[] args) {

        Config config = ConfigUtils.loadConfig("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.config_without_kantstrasse.xml");
        Scenario scenario = ScenarioUtils.createScenario(config);

        Path inputTransitSchedule = Paths.get("scenarios/berlin-v5.5-1pct/data/TestFiles/berlin-v5.5-transit-schedule.xml.gz");
        Path outputTransitSchedule = Paths.get("scenarios/berlin-v5.5-1pct/data/TestFiles/modi-transit-schedule.xml.gz");

        TransitScheduleReader reader = new TransitScheduleReader(scenario);
        reader.readFile(inputTransitSchedule.toString());

        TransitSchedule transitSchedule = scenario.getTransitSchedule();
        TransitScheduleFactory tsf = transitSchedule.getFactory();

        TransitLine U1 = transitSchedule.getTransitLines().get(Id.create("U1---17512_400",TransitLine.class));
        Map<Id<TransitRoute>, TransitRoute> routes = U1.getRoutes();


        int size = routes.size();
        TransitRoute[] transitRoutes = new TransitRoute[size];

        for (int i=0; i<size;i++) {
            String coreRoute = "U1---17512_400_" + i;
            transitRoutes[i] = routes.get(Id.create(coreRoute, TransitRoute.class));
            List<TransitRouteStop> stops = transitRoutes[i].getStops();
            NetworkRoute networkRoute = transitRoutes[i].getRoute();
            List<Id<Link>> liste = new ArrayList<>();
            if (i<2) {
                transitRoutes[i].getRoute().setStartLinkId(Id.createLinkId("pt_43069"));
                List<Id<Link>> routeLinkIds = transitRoutes[i].getRoute().getLinkIds();


                liste.add(Id.createLinkId("U1_010"));
                liste.add(Id.createLinkId("U1_020"));
                liste.add(Id.createLinkId("U1_030"));
                liste.add(Id.createLinkId("U1_040"));
                liste.add(Id.createLinkId("U1_050"));
                liste.add(Id.createLinkId("U1_060"));
                liste.addAll(routeLinkIds);

                String endLink = networkRoute.getEndLinkId().toString();
                networkRoute.setLinkIds(Id.createLinkId("pt_43069"),liste,Id.createLinkId(endLink));
            }

            if (i>1) {
                List<Id<Link>> routeLinkIds = transitRoutes[i].getRoute().getLinkIds();
                liste.addAll(routeLinkIds);
                liste.add(Id.createLinkId("pt_43027"));
                liste.add(Id.createLinkId("U1_001"));
                liste.add(Id.createLinkId("U1_002"));
                liste.add(Id.createLinkId("U1_003"));
                liste.add(Id.createLinkId("U1_004"));
                liste.add(Id.createLinkId("U1_005"));

                String startLink = networkRoute.getStartLinkId().toString();
                networkRoute.setLinkIds(Id.createLinkId(startLink),liste,Id.createLinkId("U1_006"));
            }


/*
                List<TransitRouteStop> oldStops = transitRoutes[i].getStops();
                List<TransitRouteStop> newStops = new ArrayList<>();
                TransitStopFacility stopFacility = null;
                Coord theo = new Coord(4586531.20056, 5820391.249467);
                stopFacility.setCoord(theo);
                stopFacility.setName("U Theodor-Heuss-Platz");
                tsf.createTransitRoute(Id.create(coreRoute,TransitRoute.class),networkRoute,stops,"rail");



*/

            System.out.println(transitRoutes[i].getRoute().getStartLinkId().toString());
            System.out.println(transitRoutes [i].getRoute().getLinkIds().toString());
            System.out.println("----------------------------");
        }

        new TransitScheduleWriter(transitSchedule).writeFile(outputTransitSchedule.toString());

    }
}
