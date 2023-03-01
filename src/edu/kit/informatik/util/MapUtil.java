package edu.kit.informatik.util;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.kit.informatik.config.ErrorMessages;
import edu.kit.informatik.game.type.VegetableType;

/**
 * Utility class that adds some additional functionality to the default java map
 * 
 * @author uiljo
 * @version 1.0
 */
public final class MapUtil {
    private MapUtil() {
        throw new AssertionError(ErrorMessages.UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * Sets all vegetables in a map to the given value.
     * 
     * @param map   to set values
     * @param value to set
     */
    public static void setVegetablesToValue(final Map<VegetableType, Integer> map, final int value) {
        for (final VegetableType vegetable : VegetableType.values()) {
            map.put(vegetable, value);
        }
    }

    /**
     * Sorts the map by the integer value, starting from the lowest to the highest.
     * If the values are the same, the order will be random. This can be prevented
     * by using an enum map, which results in same values being sorted the way the
     * enum constants have been declared.
     * 
     * @param map The map which should be sorted by the values.
     * @return a sorted map.
     */
    public static Map<VegetableType, Integer> sortByInt(final Map<VegetableType, Integer> map) {
        final List<Entry<VegetableType, Integer>> listOfEntries = new ArrayList<>(map.entrySet());

        Collections.sort(listOfEntries, (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()));

        final Map<VegetableType, Integer> sortedMap = new LinkedHashMap<>();

        // copying entries from List to Map
        for (Entry<VegetableType, Integer> entry : listOfEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    private static String formatStringMap(Map<String, Integer> data) {
        // get highest length of keys & values
        int highestLengthKey = 0;
        int highestLengthValue = 0;
        for (Entry<String, Integer> entry : data.entrySet()) {
            highestLengthKey = Math.max(highestLengthKey, entry.getKey().length());
            highestLengthValue = Math.max(highestLengthValue, String.valueOf(entry.getValue()).length());
        }

        final String format = "%-" + (highestLengthKey + 1) + "s %" + highestLengthValue + "d";

        final StringBuilder output = new StringBuilder();
        for (Entry<String, Integer> entry : data.entrySet()) {
            if (entry.getKey().equals("")) { // line break
                output.append(System.lineSeparator());
            } else if (entry.getKey().equals("-")) { // hyphens
                for (int i = 0; i < (highestLengthKey + 1) + 1 + highestLengthValue; i++) {
                    output.append('-');
                }
                output.append(System.lineSeparator());
            } else { // normal value
                output.append(String.format(format, entry.getKey() + ":", entry.getValue()));
                output.append(System.lineSeparator());
            }
        }

        return output.toString().strip();
    }

    private static Map<String, Integer> convertToStringMap(Map<VegetableType, Integer> data) {
        final Map<String, Integer> formattedData = new LinkedHashMap<>();
        for (Entry<VegetableType, Integer> entry : data.entrySet()) {
            if (entry.getValue() == 0) { // skips every line where the value is zero
                continue;
            }

            formattedData.put(entry.getKey().getPlural(), entry.getValue());
        }

        return formattedData;
    }

    /**
     * Formats a map of vegetables and their corresponding quantity into a table
     * format.
     * The table has two columns: The vegetable and their quantity. The vegetable is
     * left aligned, the quantity is right aligned.
     * 
     * @param data The map to be formatted into a table.
     * @return a string representing the table.
     */
    public static String formatToTable(Map<VegetableType, Integer> data) {
        return formatStringMap(convertToStringMap(data));
    }

    /**
     * Formats a map of vegetables and their corresponding quantity into a table
     * format with an additional row for the total quantity and the players gold.
     * The table has two columns: The vegetable and their quantity. The vegetable is
     * left aligned, the quantity is right aligned.
     * The sum is separated from the quantity by hyphens. The sum and the gold are
     * separated by a line break.
     * 
     * @param data The map to be formatted into a table
     * @param gold The amount of gold the player has.
     * @return a string representing the table.
     */
    public static String formatToTable(Map<VegetableType, Integer> data, int gold) {
        // calculate sum
        int sum = 0;
        for (Integer value : data.values()) {
            sum += value;
        }

        // format to string map
        final Map<String, Integer> formattedData = convertToStringMap(data);

        // separator (---)
        formattedData.put("-", 0);
        // sum of all values
        formattedData.put("Sum", sum);
        // line break
        formattedData.put("", 0);
        // gold of plyer
        formattedData.put("Gold", gold);

        return formatStringMap(formattedData);
    }
}