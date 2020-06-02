package org.matsim.run;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.io.PopulationReader;
import org.matsim.core.scenario.ScenarioUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PlansModifierHomework1 {

    public static void main(String[] args) {
        Path inputPlans = Paths.get(args[0]); //add first program argument in dropdown menu
        Path outputPlans = Paths.get(args[1]); //add second program argument in dropdown menu as output file (potentially .gz)

        Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        PopulationReader populationReader = new PopulationReader(scenario);
        populationReader.readFile(inputPlans.toString());

        // modify plans here, no idea yet

        PopulationWriter populationWriter = new PopulationWriter(scenario.getPopulation());
        populationWriter.write(outputPlans.toString());
    }
}
