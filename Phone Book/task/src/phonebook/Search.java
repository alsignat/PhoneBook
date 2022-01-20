package phonebook;

import java.util.ArrayList;

public class Search {

    public static long linearSearch (ArrayList<String> phonebook, String name) {
        long index = -1;
        for (int i = 0; i < phonebook.size(); i++) {
            String entry = phonebook.get(i);
            String person = removeDigits(entry);
            if (name.equals(person)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static long binarySearch (ArrayList<String> phonebook, String name, int left, int right) {
      //  System.out.print("looking for " + name + " between " + removeDigits(phonebook.get(left)) + " and " + removeDigits(phonebook.get(right)));
      //  System.out.println(". Mid value = " + removeDigits(phonebook.get((left + right) / 2)));
        if (name.compareTo(removeDigits(phonebook.get(left))) < 0) return -1;
        if (name.compareTo(removeDigits(phonebook.get(right))) > 0) return -1;
        if (left == right) return name.equals(removeDigits(phonebook.get(left))) ? left : -1;
        if (right - left == 1) {
            if (name.equals(removeDigits(phonebook.get(left)))) {
                return left;
            } else if (name.equals(removeDigits(phonebook.get(right)))) {
                return right;
            } else return -1;
        }

        int mid = (left + right) / 2;
        String value = removeDigits(phonebook.get(mid));
        int order = name.compareTo(value);
        if (order == 0) {
            return mid;
        }
        int nextLeft = order < 0 ? left : mid + 1;
        int nextRight = order < 0 ? mid - 1  : right;
        return binarySearch(phonebook, name, nextLeft, nextRight);
    }

    public static long jumpSearch (ArrayList<String> phonebook, String name) {
        long length = phonebook.size();
        int block = (int) Math.sqrt(length);
        int jump = (int) Math.ceil((double) length / block);
        for (int i = 0; i < phonebook.size(); i += jump) {
            String entry = phonebook.get(i);
            String person = removeDigits(entry);
            if (name.equals(person)) return i;
            else if (name.compareTo(person) < 0) {
                if (i == 0) return -1;
                for (int j = 1; j < block; j++) {
                    entry = phonebook.get(i - j);
                    person = removeDigits(entry);
                    if (name.equals(person)) {
                        return i - j;
                    }
                }
            }
        }
        return -1;
    }

    public static String removeDigits(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                result = result + str.charAt(i);
            }
        }
        return result.trim();
    }

}
