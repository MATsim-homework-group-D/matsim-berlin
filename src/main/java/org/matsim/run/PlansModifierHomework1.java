package org.matsim.run;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.io.PopulationReader;
import org.matsim.core.scenario.ScenarioUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PlansModifierHomework1 {

    public static void main(String[] args) {
        Path inputPlans = Paths.get(args[0]); //add first program argument in dropdown menu
        Path outputPlans = Paths.get(args[1]); //add second program argument in dropdown menu as output file (potentially .gz)

        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader populationReader = new PopulationReader(scenario);
        populationReader.readFile(inputPlans.toString());

        //creating array with 80 strings read from linksToRemove.xml

        String[] RemovedLinks = new String [80];

        BufferedReader reader = null;

        try {
            File file = new File("scenarios/berlin-v5.5-1pct/input/linksToRemove.xml");
            reader = new BufferedReader(new FileReader(file));

            for (int i = 0; i < RemovedLinks.length; i++) {
                RemovedLinks[i]=reader.readLine();
                System.out.println(RemovedLinks[i]); //printing array to check result
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

        //modify plans with this array here, no idea yet...

        PopulationWriter populationWriter = new PopulationWriter(scenario.getPopulation());
        populationWriter.write(outputPlans.toString());
    }
}
