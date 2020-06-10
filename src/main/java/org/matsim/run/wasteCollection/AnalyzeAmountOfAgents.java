package org.matsim.run.wasteCollection;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeAmountOfAgents {

    public static void main(String[] args) {
        File inputAgents = new File("scenarios/berlin-v5.5-1pct/data/agentsOnKantstrasse.txt");
        List<Id<Person>> agents = bufferedReader(inputAgents);
        analyzeAmountOFxxx(agents);

    }

    private static List<Id<Person>> bufferedReader(File inputAgents) {
        List<Id<Person>> agents = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(inputAgents));
            String line;
            while ((line = reader.readLine()) != null) {
                agents.add(Id.createPersonId(line));
            }
        }
        catch (IOException ee) {
            ee.printStackTrace();
        }
        finally {
            try {
                assert reader != null;
                reader.close();
            }
            catch (IOException ee) {
                ee.printStackTrace();
            }
        }
        System.out.println(agents.toString());
        return agents;
    }

    private static void analyzeAmountOFxxx(List<Id<Person>> agents) {
    }
}
