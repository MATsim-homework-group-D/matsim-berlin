package org.matsim.run.homework2;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkWriter;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkModifier_homework2 {

    public static void main(String[] args) {
        Path inputNetwork = Paths.get("scenarios/berlin-v5.5-1pct/input/berlin-v5.5-network_without_kantstrasse.xml");
        Path outputNetwork = Paths.get("berlin-v5.5-network_U1extension_without_kantstrasse.xml");

        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(inputNetwork.toString());

        //do sth here:



        new NetworkWriter(network).write(outputNetwork.toString());

    }
}
