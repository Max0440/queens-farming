package edu.kit.informatik.util;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.kit.informatik.game.type.VegetableType;

public class MapUtil {
    private MapUtil() {
        // TODO error
    }

    public static Map<VegetableType, Integer> sortByInt(Map<VegetableType, Integer> map) {
        List<Entry<VegetableType, Integer>> listOfEntries = new ArrayList<>(map.entrySet());

        Collections.sort(listOfEntries, (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()));

        Map<VegetableType, Integer> sortedMap = new LinkedHashMap<>();

        // copying entries from List to Map
        for (Entry<VegetableType, Integer> entry : listOfEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static String formatToTable(Map<VegetableType, Integer> data) {
        // get highest length of keys & values
        int highestLengthKey = 0;
        int highestLengthValue = 0;
        for (VegetableType key : data.keySet()) {
            int value = data.get(key);
            if (value == 0) {
                continue;
            }

            highestLengthKey = Math.max(highestLengthKey, key.getPlural().length());
            highestLengthValue = Math.max(highestLengthValue, String.valueOf(value).length());
        }

        String format = "%-" + (highestLengthKey + 1) + "s %" + highestLengthValue + "d";

        StringBuilder output = new StringBuilder();
        for (VegetableType key : data.keySet()) {
            int value = data.get(key);
            if (value == 0) {
                continue;
            }

            output.append(String.format(format, key.getPlural() + ":", value));
            output.append(System.lineSeparator());
        }

        return output.toString().strip();
    }

    public static String formatToTable(Map<VegetableType, Integer> data, int gold) {
        // calculate sum
        int sum = 0;
        for (Integer value : data.values()) {
            sum += value;
        }

        // get highest length of keys & values
        int highestLengthKey = 0;
        int highestLengthValue = 0;
        for (VegetableType key : data.keySet()) {
            int value = data.get(key);
            if (value == 0) {
                continue;
            }

            highestLengthKey = Math.max(highestLengthKey, key.getPlural().length());
            highestLengthValue = Math.max(highestLengthValue, String.valueOf(value).length());
        }

        highestLengthKey = Math.max(highestLengthKey, "Gold".length());
        highestLengthValue = Math.max(highestLengthValue, String.valueOf(gold).length());

        highestLengthValue = Math.max(highestLengthValue, String.valueOf(sum).length());

        String format = "%-" + (highestLengthKey + 1) + "s %" + highestLengthValue + "d";

        StringBuilder output = new StringBuilder();
        for (VegetableType key : data.keySet()) {
            int value = data.get(key);
            if (value == 0) {
                continue;
            }

            output.append(String.format(format, key.getPlural() + ":", value));
            output.append(System.lineSeparator());
        }

        // ----
        for (int i = 0; i < (highestLengthKey + 1) + 1 + highestLengthValue; i++) {
            output.append('-');
        }
        output.append(System.lineSeparator());

        // sum
        output.append(String.format(format, "Sum:", sum));
        output.append(System.lineSeparator());

        // gold
        output.append(System.lineSeparator());
        output.append(String.format(format, "Gold:", gold));
        output.append(System.lineSeparator());

        return output.toString().strip();
    }
}
