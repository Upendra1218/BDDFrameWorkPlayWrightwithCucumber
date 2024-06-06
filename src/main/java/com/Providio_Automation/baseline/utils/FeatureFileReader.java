package com.Providio_Automation.baseline.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeatureFileReader {

    public static int countScenariosWithTag(String featureDirectoryPath, String tag) {
        int scenarioCount = 0;
        try {
            List<File> featureFiles = Files.walk(Paths.get(featureDirectoryPath))
                    .filter(Files::isRegularFile)
                    .map(path -> path.toFile())
                    .toList();

            Pattern scenarioPattern = Pattern.compile("Scenario(?: Outline)?:");
            Pattern tagPattern = Pattern.compile("@" + tag);

            for (File file : featureFiles) {
                List<String> lines = Files.readAllLines(file.toPath());
                boolean tagFound = false;
                for (String line : lines) {
                    Matcher tagMatcher = tagPattern.matcher(line);
                    if (tagMatcher.find()) {
                        tagFound = true;
                    }

                    Matcher scenarioMatcher = scenarioPattern.matcher(line);
                    if (scenarioMatcher.find() && tagFound) {
                        scenarioCount++;
                        tagFound = false; // Reset tagFound for the next scenario
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scenarioCount;
    }
}
