package org.matsim.run;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkWriter;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkModifier {

    public static void main(String[] args) {
        Path inputNetwork = Paths.get("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-network.xml");
        Path outputNetwork = Paths.get("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-network_without_kantstrasse.xml");

        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(inputNetwork.toString());

        //removing all links belonging to Kantraße:

        network.removeLink(Id.createLinkId("149698"));
        network.removeLink(Id.createLinkId("149708"));
        network.removeLink(Id.createLinkId("151264"));
        network.removeLink(Id.createLinkId("86655"));
        network.removeLink(Id.createLinkId("8569"));
        network.removeLink(Id.createLinkId("13607"));
        network.removeLink(Id.createLinkId("2987"));
        network.removeLink(Id.createLinkId("2981"));
        network.removeLink(Id.createLinkId("2982"));
        network.removeLink(Id.createLinkId("2983"));
        network.removeLink(Id.createLinkId("2984"));
        network.removeLink(Id.createLinkId("158771"));
        network.removeLink(Id.createLinkId("87484"));
        network.removeLink(Id.createLinkId("87485"));
        network.removeLink(Id.createLinkId("104157"));
        network.removeLink(Id.createLinkId("104158"));
        network.removeLink(Id.createLinkId("59286"));
        network.removeLink(Id.createLinkId("104172"));
        network.removeLink(Id.createLinkId("158759"));
        network.removeLink(Id.createLinkId("43981"));
        network.removeLink(Id.createLinkId("12239"));
        network.removeLink(Id.createLinkId("12240"));
        network.removeLink(Id.createLinkId("46358"));
        network.removeLink(Id.createLinkId("46359"));
        network.removeLink(Id.createLinkId("59274"));
        network.removeLink(Id.createLinkId("59275"));
        network.removeLink(Id.createLinkId("12213"));
        network.removeLink(Id.createLinkId("12214"));
        network.removeLink(Id.createLinkId("12215"));
        network.removeLink(Id.createLinkId("96008"));
        network.removeLink(Id.createLinkId("96009"));
        network.removeLink(Id.createLinkId("96010"));
        network.removeLink(Id.createLinkId("96011"));
        network.removeLink(Id.createLinkId("130264"));
        network.removeLink(Id.createLinkId("130256"));
        network.removeLink(Id.createLinkId("130257"));
        network.removeLink(Id.createLinkId("116879"));
        network.removeLink(Id.createLinkId("86670"));
        network.removeLink(Id.createLinkId("59278"));
        network.removeLink(Id.createLinkId("103933"));
        network.removeLink(Id.createLinkId("116882"));
        network.removeLink(Id.createLinkId("86671"));
        network.removeLink(Id.createLinkId("107752"));
        network.removeLink(Id.createLinkId("107753"));
        network.removeLink(Id.createLinkId("67659"));
        network.removeLink(Id.createLinkId("130231"));
        network.removeLink(Id.createLinkId("130252"));
        network.removeLink(Id.createLinkId("130253"));
        network.removeLink(Id.createLinkId("130254"));
        network.removeLink(Id.createLinkId("96025"));
        network.removeLink(Id.createLinkId("96026"));
        network.removeLink(Id.createLinkId("96027"));
        network.removeLink(Id.createLinkId("12218"));
        network.removeLink(Id.createLinkId("12219"));
        network.removeLink(Id.createLinkId("46348"));
        network.removeLink(Id.createLinkId("46349"));
        network.removeLink(Id.createLinkId("59270"));
        network.removeLink(Id.createLinkId("59271"));
        network.removeLink(Id.createLinkId("43978"));
        network.removeLink(Id.createLinkId("104171"));
        network.removeLink(Id.createLinkId("158751"));
        network.removeLink(Id.createLinkId("158756"));
        network.removeLink(Id.createLinkId("158762"));
        network.removeLink(Id.createLinkId("158763"));
        network.removeLink(Id.createLinkId("158764"));
        network.removeLink(Id.createLinkId("143092"));
        network.removeLink(Id.createLinkId("59287"));
        network.removeLink(Id.createLinkId("138074"));
        network.removeLink(Id.createLinkId("138075"));
        network.removeLink(Id.createLinkId("138076"));
        network.removeLink(Id.createLinkId("138077"));
        network.removeLink(Id.createLinkId("2996"));
        network.removeLink(Id.createLinkId("2985"));
        network.removeLink(Id.createLinkId("2986"));
        network.removeLink(Id.createLinkId("74678"));
        network.removeLink(Id.createLinkId("13579"));
        network.removeLink(Id.createLinkId("149700"));
        network.removeLink(Id.createLinkId("151256"));
        network.removeLink(Id.createLinkId("149699"));
        network.removeLink(Id.createLinkId("9636"));
        network.removeLink(Id.createLinkId("76232"));

        // removing all nodes that would otherwise be floating around

        network.removeNode(Id.createNodeId("27306165"));
        network.removeNode(Id.createNodeId("27306166"));
        network.removeNode(Id.createNodeId("3951898333"));
        network.removeNode(Id.createNodeId("26868106"));
        network.removeNode(Id.createNodeId("26868107"));
        network.removeNode(Id.createNodeId("3346178030"));
        network.removeNode(Id.createNodeId("3342452758"));
        network.removeNode(Id.createNodeId("3066797047"));
        network.removeNode(Id.createNodeId("3066762533"));
        network.removeNode(Id.createNodeId("2778536903"));
        network.removeNode(Id.createNodeId("341364291"));
        network.removeNode(Id.createNodeId("1122075158"));
        network.removeNode(Id.createNodeId("26787978"));
        network.removeNode(Id.createNodeId("1560791805"));
        network.removeNode(Id.createNodeId("1076683317"));
        network.removeNode(Id.createNodeId("341364294"));
        network.removeNode(Id.createNodeId("3066762534"));
        network.removeNode(Id.createNodeId("3066797048"));
        network.removeNode(Id.createNodeId("3346178031"));
        network.removeNode(Id.createNodeId("1016697526"));
        network.removeNode(Id.createNodeId("313153834"));
        network.removeNode(Id.createNodeId("26868104"));
        network.removeNode(Id.createNodeId("26868105"));
        network.removeNode(Id.createNodeId("3288675614"));
        network.removeNode(Id.createNodeId("27306167"));
        network.removeNode(Id.createNodeId("3951898334"));
        network.removeNode(Id.createNodeId("27306168"));
        network.removeNode(Id.createNodeId("365761526"));
        network.removeNode(Id.createNodeId("128391208"));

        new NetworkWriter(network).write(outputNetwork.toString());

    }
}
