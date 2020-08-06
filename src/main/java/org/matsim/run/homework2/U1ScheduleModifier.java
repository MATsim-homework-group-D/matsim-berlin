package org.matsim.run.homework2;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.routes.NetworkRoute;
import org.matsim.core.scenario.ScenarioUtils;
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

//<stopFacilitiy>
        //coordinates
        Coord u_Bleibtreustr = new Coord(4589751.257219473,5819551.3163263835);
        Coord u_Adenauerplatz = new Coord(4588884.259735091,5819352.321299794);
        Coord u_Kracauerplatz = new Coord(4588155.194292864,5819492.335208873);
        Coord u_Westkreuz = new Coord(4587231.222792794,5819441.303777937);
        Coord u_Masurenallee = new Coord(4586931.214653902,5820057.284003928);
        Coord u_Theodor_Heuss_Platz = new Coord(4586531.200568646,5820391.249467583);

        //facilities
        TransitStopFacility u1_stop_001 = tsf.createTransitStopFacility(Id.create("U1_stop_001", TransitStopFacility.class), u_Bleibtreustr, false);
        TransitStopFacility u1_stop_002 = tsf.createTransitStopFacility(Id.create("U1_stop_002", TransitStopFacility.class), u_Adenauerplatz, false);
        TransitStopFacility u1_stop_003 = tsf.createTransitStopFacility(Id.create("U1_stop_003", TransitStopFacility.class), u_Kracauerplatz, false);
        TransitStopFacility u1_stop_004 = tsf.createTransitStopFacility(Id.create("U1_stop_004", TransitStopFacility.class), u_Westkreuz, false);
        TransitStopFacility u1_stop_005 = tsf.createTransitStopFacility(Id.create("U1_stop_005", TransitStopFacility.class), u_Masurenallee, false);
        TransitStopFacility u1_stop_006 = tsf.createTransitStopFacility(Id.create("U1_stop_006", TransitStopFacility.class), u_Theodor_Heuss_Platz, false);
        TransitStopFacility u1_stop_010 = tsf.createTransitStopFacility(Id.create("U1_stop_010", TransitStopFacility.class), u_Theodor_Heuss_Platz, false);
        TransitStopFacility u1_stop_020 = tsf.createTransitStopFacility(Id.create("U1_stop_020", TransitStopFacility.class), u_Masurenallee, false);
        TransitStopFacility u1_stop_030 = tsf.createTransitStopFacility(Id.create("U1_stop_030", TransitStopFacility.class), u_Westkreuz, false);
        TransitStopFacility u1_stop_040 = tsf.createTransitStopFacility(Id.create("U1_stop_040", TransitStopFacility.class), u_Kracauerplatz, false);
        TransitStopFacility u1_stop_050 = tsf.createTransitStopFacility(Id.create("U1_stop_050", TransitStopFacility.class), u_Adenauerplatz, false);
        TransitStopFacility u1_stop_060 = tsf.createTransitStopFacility(Id.create("U1_stop_060", TransitStopFacility.class), u_Bleibtreustr, false);

        //names
        u1_stop_001.setName("U Bleibtreustr.");
        u1_stop_002.setName("U Adenauerplatz (Berlin) [U1]");
        u1_stop_003.setName("U Kracauerplatz (Berlin)");
        u1_stop_004.setName("S+U Westkreuz [U1]");
        u1_stop_005.setName("U Masurenallee/ZOB (Berlin)");
        u1_stop_006.setName("U Theodor-Heuss-Platz [U1]");
        u1_stop_010.setName("U Theodor-Heuss-Platz [U1]");
        u1_stop_020.setName("U Masurenallee/ZOB (Berlin)");
        u1_stop_030.setName("S+U Westkreuz [U1]");
        u1_stop_040.setName("U Kracauerplatz (Berlin)");
        u1_stop_050.setName("U Adenauerplatz (Berlin) [U1]");
        u1_stop_060.setName("U Bleibtreustr.");

        //referenced LinkId
        u1_stop_001.setLinkId(Id.createLinkId("U1_001"));
        u1_stop_002.setLinkId(Id.createLinkId("U1_002"));
        u1_stop_003.setLinkId(Id.createLinkId("U1_003"));
        u1_stop_004.setLinkId(Id.createLinkId("U1_004"));
        u1_stop_005.setLinkId(Id.createLinkId("U1_005"));
        u1_stop_006.setLinkId(Id.createLinkId("U1_006"));
        u1_stop_010.setLinkId(Id.createLinkId("pt_43069"));
        u1_stop_020.setLinkId(Id.createLinkId("U1_010"));
        u1_stop_030.setLinkId(Id.createLinkId("U1_020"));
        u1_stop_040.setLinkId(Id.createLinkId("U1_030"));
        u1_stop_050.setLinkId(Id.createLinkId("U1_040"));
        u1_stop_060.setLinkId(Id.createLinkId("U1_050"));
        //Uhlandstraßeu1_stop_001.setLinkId(Id.createLinkId("U1_060"));

        //add to TransitSchedule
        transitSchedule.addStopFacility(u1_stop_001);
        transitSchedule.addStopFacility(u1_stop_002);
        transitSchedule.addStopFacility(u1_stop_003);
        transitSchedule.addStopFacility(u1_stop_004);
        transitSchedule.addStopFacility(u1_stop_005);
        transitSchedule.addStopFacility(u1_stop_006);
        transitSchedule.addStopFacility(u1_stop_010);
        transitSchedule.addStopFacility(u1_stop_020);
        transitSchedule.addStopFacility(u1_stop_030);
        transitSchedule.addStopFacility(u1_stop_040);
        transitSchedule.addStopFacility(u1_stop_050);
        transitSchedule.addStopFacility(u1_stop_060);
//</stopFacilitiy>
//<routes>
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
/*
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

                U1.addRoute();
                transitRoutes[i].getRoute().setLinkIds();
                transitSchedule.addStopFacility();
                transitSchedule.addTransitLine();
                tsf.create

            }



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
