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
import java.util.*;

public class U1ScheduleModifier {

    public static void main(String[] args) {

//base
        Config config = ConfigUtils.loadConfig("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-1pct.config_without_kantstrasse.xml");
        Scenario scenario = ScenarioUtils.createScenario(config);

        Path inputTransitSchedule = Paths.get("scenarios/berlin-v5.5-1pct/data/TestFiles/berlin-v5.5-transit-schedule.xml.gz"); //relative path to input transitScheduleFile
        Path outputTransitSchedule = Paths.get("scenarios/berlin-v5.5-1pct/data/TestFiles/new_transit-schedule.xml.gz"); //relative path to output transitScheduleFile

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
        transitSchedule.getFacilities().get(Id.create("070201013302",TransitStopFacility.class)).setLinkId(Id.createLinkId("U1_060"));

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

//<transitroute>
        TransitLine u1 = transitSchedule.getTransitLines().get(Id.create("U1---17512_400",TransitLine.class));
        int size = u1.getRoutes().size();
        TransitRoute[] transitRoutes = new TransitRoute[size];


        for (int i=0; i<size;i++) {
            String coreRoute = "U1---17512_400_" + i;
            transitRoutes[i] = u1.getRoutes().get(Id.create(coreRoute, TransitRoute.class));

//safing all elements of transitroute
            //transportMode
            String transportMode = transitRoutes[i].getTransportMode();
            //Departures
            Map<Id<Departure>, Departure> departureMap = transitRoutes[i].getDepartures();
            Iterator<Id<Departure>> iterator = departureMap.keySet().iterator();
            List<Departure> departures = new ArrayList<>();
            while (iterator.hasNext()) {
                Id<Departure> next = iterator.next();
                departures.add(departureMap.get(next));
            }
            //routeProfile
            List<TransitRouteStop> oldstops = transitRoutes[i].getStops();
            List<TransitRouteStop> newstops = new ArrayList<>();

            if (i<2) {
                TransitRouteStop east_01 = tsf.createTransitRouteStop(u1_stop_010, 0, 0);
                TransitRouteStop east_02 = tsf.createTransitRouteStop(u1_stop_020, 90, 90);
                TransitRouteStop east_03 = tsf.createTransitRouteStop(u1_stop_030, 210, 210);
                TransitRouteStop east_04 = tsf.createTransitRouteStop(u1_stop_040, 330, 330);
                TransitRouteStop east_05 = tsf.createTransitRouteStop(u1_stop_050, 420, 420);
                TransitRouteStop east_06 = tsf.createTransitRouteStop(u1_stop_060, 510, 510);

                newstops.add(east_01);
                newstops.add(east_02);
                newstops.add(east_03);
                newstops.add(east_04);
                newstops.add(east_05);
                newstops.add(east_06);

                for (TransitRouteStop routeStop : oldstops) {
                    TransitStopFacility facility = routeStop.getStopFacility();
                    double oldarrival = routeStop.getArrivalOffset().seconds();
                    double olddeparture = routeStop.getDepartureOffset().seconds();
                    double newarrival = oldarrival + 570;
                    double newdeparture = olddeparture + 570;
                    TransitRouteStop east_xx = tsf.createTransitRouteStop(facility, newarrival, newdeparture);
                    newstops.add(east_xx);
                }
            }

            if (i>1) {
                for (TransitRouteStop routeStop : oldstops) {
                    TransitStopFacility facility = routeStop.getStopFacility();
                    TransitRouteStop west_xx = tsf.createTransitRouteStop(facility, routeStop.getArrivalOffset().seconds(), routeStop.getDepartureOffset().seconds());
                    newstops.add(west_xx);
                }
                double time_uhland = oldstops.get(12).getDepartureOffset().seconds();

                TransitRouteStop west_01 = tsf.createTransitRouteStop(u1_stop_001, time_uhland + 60, time_uhland + 60);
                TransitRouteStop west_02 = tsf.createTransitRouteStop(u1_stop_002, time_uhland + 150, time_uhland + 150);
                TransitRouteStop west_03 = tsf.createTransitRouteStop(u1_stop_003, time_uhland + 240, time_uhland + 240);
                TransitRouteStop west_04 = tsf.createTransitRouteStop(u1_stop_004, time_uhland + 360, time_uhland + 360);
                TransitRouteStop west_05 = tsf.createTransitRouteStop(u1_stop_005, time_uhland + 480, time_uhland + 480);
                TransitRouteStop west_06 = tsf.createTransitRouteStop(u1_stop_006, time_uhland + 570, time_uhland + 570);

                newstops.add(west_01);
                newstops.add(west_02);
                newstops.add(west_03);
                newstops.add(west_04);
                newstops.add(west_05);
                newstops.add(west_06);
            }

            //route
            NetworkRoute oldRouteBody = transitRoutes[i].getRoute();
            List<Id<Link>> newRouteBody = new ArrayList<>();

            List<Id<Link>> routeLinkIds = oldRouteBody.getLinkIds();
            String startLink = oldRouteBody.getStartLinkId().toString();
            String endLink = oldRouteBody.getEndLinkId().toString();
            
//deleting transitroute and creating new transitroute with same id, same transport mode and new stops
            u1.removeRoute(u1.getRoutes().get(Id.create(coreRoute, TransitRoute.class)));
            TransitRoute newTransitRoute = tsf.createTransitRoute(Id.create(coreRoute, TransitRoute.class), oldRouteBody, newstops, transportMode);

            for (Departure departure : departures) {
                newTransitRoute.addDeparture(departure);
            }

            u1.addRoute(newTransitRoute);

//modification of route
            if (i<2) {
                newRouteBody.add(Id.createLinkId("U1_010"));
                newRouteBody.add(Id.createLinkId("U1_020"));
                newRouteBody.add(Id.createLinkId("U1_030"));
                newRouteBody.add(Id.createLinkId("U1_040"));
                newRouteBody.add(Id.createLinkId("U1_050"));
                newRouteBody.add(Id.createLinkId("U1_060"));
                newRouteBody.addAll(routeLinkIds);
                oldRouteBody.setLinkIds(Id.createLinkId("pt_43069"),newRouteBody,Id.createLinkId(endLink));
            }

            if (i>1) {
                newRouteBody.addAll(routeLinkIds);
                newRouteBody.add(Id.createLinkId(endLink));
                newRouteBody.add(Id.createLinkId("U1_001"));
                newRouteBody.add(Id.createLinkId("U1_002"));
                newRouteBody.add(Id.createLinkId("U1_003"));
                newRouteBody.add(Id.createLinkId("U1_004"));
                newRouteBody.add(Id.createLinkId("U1_005"));
                oldRouteBody.setLinkIds(Id.createLinkId(startLink),newRouteBody,Id.createLinkId("U1_006"));
            }
        }
//</transitroute>

        new TransitScheduleWriter(transitSchedule).writeFile(outputTransitSchedule.toString());
    }
}
