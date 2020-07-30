package org.matsim.run.homework2;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.NetworkWriter;
import org.matsim.api.core.v01.network.Node;
import org.matsim.contrib.accessibility.utils.NetworkUtil;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.utils.collections.CollectionUtils;
import org.matsim.pt.router.TransitRouterNetwork;
import org.matsim.pt.transitSchedule.api.TransitStopFacility;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkModifier_homework2 {

    public static void main(String[] args) {
        Path inputNetwork = Paths.get("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-network_without_kantstrasse.xml");
        Path outputNetwork = Paths.get("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-network_U1extension_without_kantstrasse.xml");

        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(inputNetwork.toString());

        //add 12 links according to table in documentation

        //---direction west---
        //U1_001
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_001"), network.getNodes().get(Id.createNodeId("pt_070201013302")), network.getNodes().get(Id.createNodeId("pt_070101001403")), 431., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));
        //U1_002
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_002"), network.getNodes().get(Id.createNodeId("pt_070101001403")), network.getNodes().get(Id.createNodeId("pt_070101000864")), 910., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));
        //U1_003
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_003"), network.getNodes().get(Id.createNodeId("pt_070101000864")), network.getNodes().get(Id.createNodeId("26642092")), 777., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));
        //U1_004
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_004"), network.getNodes().get(Id.createNodeId("26642092")), network.getNodes().get(Id.createNodeId("pt_060024100801")), 919., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));
        //U1_005
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_005"), network.getNodes().get(Id.createNodeId("pt_060024100801")), network.getNodes().get(Id.createNodeId("pt_070101001449")), 852., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));
        //U1_006
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_006"), network.getNodes().get(Id.createNodeId("pt_070101001449")), network.getNodes().get(Id.createNodeId("pt_070201024502")), 660., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));

        //---direction east---
        //U1_010
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_010"), network.getNodes().get(Id.createNodeId("pt_070201024502")), network.getNodes().get(Id.createNodeId("pt_070101001449")), 660., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));
        //U1_020
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_020"), network.getNodes().get(Id.createNodeId("pt_070101001449")), network.getNodes().get(Id.createNodeId("pt_060024100801")), 852., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));
        //U1_030
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_030"), network.getNodes().get(Id.createNodeId("pt_060024100801")), network.getNodes().get(Id.createNodeId("26642092")), 919., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));
        //U1_040
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_040"), network.getNodes().get(Id.createNodeId("26642092")), network.getNodes().get(Id.createNodeId("pt_070101000864")), 777., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));
        //U1_050
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_050"), network.getNodes().get(Id.createNodeId("pt_070101000864")), network.getNodes().get(Id.createNodeId("pt_070101001403")), 910., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));
        //U1_060
        NetworkUtils.createAndAddLink(network, Id.createLinkId("U1_060"), network.getNodes().get(Id.createNodeId("pt_070101001403")), network.getNodes().get(Id.createNodeId("pt_070201013302")), 431., 12.,100000., 1.).setAllowedModes(CollectionUtils.stringToSet("pt"));

        new NetworkWriter(network).write(outputNetwork.toString());

    }
}
