package phonebook;

import java.util.ArrayList;
import java.util.Collections;

import static phonebook.Search.removeDigits;

public class Sort {

    public static ArrayList<Object> bubbleSort(ArrayList<String> phonebook, long timeLimit) {
        ArrayList<Object> returnValues = new ArrayList<>(phonebook.size());
        long startTime = System.currentTimeMillis();
        boolean isSorted = true;
        do {
            for (int i = 0; i < phonebook.size() - 1; i++) {
                if (System.currentTimeMillis() - startTime > timeLimit) {
                    returnValues.add(phonebook);
                    returnValues.add(false);
                    return returnValues;
                }
                String entry1 = phonebook.get(i);
                String person1 = removeDigits(entry1);
                String entry2 = phonebook.get(i+1);
                String person2 = removeDigits(entry2);
                if (person1.compareTo(person2) > 0) {
                    String temp = entry1;
                    phonebook.set(i, entry2);
                    phonebook.set(i+1, entry1);
                    isSorted = false;
                }
            }
        } while (!isSorted);

        returnValues.add(phonebook);
        returnValues.add(true);
        return returnValues;
    }

    public static ArrayList<String> quickSort(ArrayList<String> phonebook) {
        ArrayList<String> rsl = new ArrayList<>(phonebook.size());
        if (phonebook.size() <= 1) return phonebook;
        if (phonebook.size() == 2 && removeDigits(phonebook.get(0)).compareTo(removeDigits(phonebook.get(1))) <= 0) return phonebook;
        String pivot = phonebook.get(0);
        ArrayList<String> left = new ArrayList<>();
        ArrayList<String> right = new ArrayList<>();
        for (int i = 1; i < phonebook.size(); i++) {
            String checked = phonebook.get(i);
            if (removeDigits(checked).compareTo(removeDigits(pivot)) <= 0) {
                left.add(checked);
            } else {
                right.add(checked);
            }
        }
        rsl.addAll(quickSort(left));
        rsl.add(pivot);
        rsl.addAll(quickSort(right));
        return rsl;
    }

}
