package com.example.Dily_serialu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartsComparator implements Comparator<String> {
    private final Pattern pattern = Pattern.compile("(\\d+)");

    @Override
    public int compare(String o1, String o2) {
        List<Integer> numbers1 = extractNumbers(o1);
        List<Integer> numbers2 = extractNumbers(o2);

        int size = Math.min(numbers1.size(), numbers2.size());
        for (int i = 0; i < size; i++) {
            int result = numbers1.get(i).compareTo(numbers2.get(i));
            if (result != 0) return result;
        }

        return Integer.compare(numbers1.size(), numbers2.size());
    }

    private List<Integer> extractNumbers(String text) {
        List<Integer> numbers = new ArrayList<>();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }
        return numbers.isEmpty() ? List.of(Integer.MAX_VALUE) : numbers;
    }
}