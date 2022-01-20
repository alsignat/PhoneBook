package phonebook;

import java.io.*;
import java.util.ArrayList;
import static phonebook.Search.*;
import static phonebook.Sort.*;
import static phonebook.Hashtable.*;

public class Main {

    public static ArrayList<String> createListFromFile(File file) throws FileNotFoundException, IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String s;
        while ((s = reader.readLine()) != null) {
            list.add(s);
        }
        return list;
    }

    public static long searchLinearSearch(File phonebook, File search) throws Exception {
        long startTime = System.currentTimeMillis();
        ArrayList<String> names = createListFromFile(search);
        ArrayList<String> data = createListFromFile(phonebook);
        int found = 0;
        for(String name : names) {
            if (linearSearch(data, name) != -1) {
                found++;
            }
        }
        System.out.printf("Found %d / %d entries. ", found, names.size());
        return System.currentTimeMillis() - startTime;
    }

    public static void searchJumpAfterBubbleSort(File phonebook, File search, long linearSearchTime) throws Exception {
        ArrayList<String> names = createListFromFile(search);
        ArrayList<String> data = createListFromFile(phonebook);
        long startSortTime = System.currentTimeMillis();
        long searchTime;
        try {
            ArrayList<Object> result = bubbleSort(data, linearSearchTime * 10);
            long sortTime = System.currentTimeMillis() - startSortTime;
            boolean sortingSuccessful = (boolean) result.get(1);
            if (!sortingSuccessful) {
                searchTime = searchLinearSearch(phonebook, search);
            } else {
                data = (ArrayList<String>) result.get(0);
                long startSearchTime = System.currentTimeMillis();
                int found = 0;
                for(String name : names) {
                    if (jumpSearch(data, name) != -1) {
                        found++;
                    }
                }
                searchTime = System.currentTimeMillis() - startSearchTime;
                System.out.printf("Found %d / %d entries. ", found, names.size());
            }
            long totalTime = searchTime + sortTime;
            System.out.printf("Time taken: %d min. %d sec. %d ms", totalTime / 60000, totalTime / 1000 % 60, totalTime % 1000);
            System.out.printf("\nSorting time: %d min. %d sec. %d ms.",sortTime / 60000, sortTime / 1000 % 60, sortTime % 1000);
            if (!sortingSuccessful) {
                System.out.println(" - STOPPED, moved to linear search");
            }
            System.out.printf("Searching time: %d min. %d sec. %d ms.", searchTime / 60000, searchTime / 1000 % 60, searchTime % 1000);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void searchBinaryAfterQuickSort(File phonebook, File search) throws Exception {
        ArrayList<String> names = createListFromFile(search);
        ArrayList<String> data = createListFromFile(phonebook);
        long startSortTime = System.currentTimeMillis();
        ArrayList<String> sortedData = quickSort(data);
        long sortTime = System.currentTimeMillis() - startSortTime;
        long startSearchTime = System.currentTimeMillis();
        long found = 0;
        for(String name : names) {
            if (binarySearch(sortedData, name, 0, sortedData.size() - 1) != -1) {
                found++;
            }
        }
        long searchTime = System.currentTimeMillis() - startSearchTime;
        System.out.printf("Found %d / %d entries. ", found, names.size());
        long totalTime = searchTime + sortTime;
        System.out.printf("Time taken: %d min. %d sec. %d ms", totalTime / 60000, totalTime / 1000 % 60, totalTime % 1000);
        System.out.printf("\nSorting time: %d min. %d sec. %d ms.",sortTime / 60000, sortTime / 1000 % 60, sortTime % 1000);
        System.out.printf("\nSearching time: %d min. %d sec. %d ms.", searchTime / 60000, searchTime / 1000 % 60, searchTime % 1000);
    }

    public static void searchWithHashtable(File phonebook, File search) throws Exception {
        ArrayList<String> names = createListFromFile(search);
        ArrayList<String> data = createListFromFile(phonebook);
        long startHashTime = System.currentTimeMillis();
        Hashtable hashtable = new Hashtable(data.size());
        for (String item : data) {
            hashtable.add(removeDigits(item));
        }
        long createTime = System.currentTimeMillis() - startHashTime;
        long startSearchTime = System.currentTimeMillis();
        long found = 0;
        for(String name : names) {
            if (hashtable.contains(name)) {
                found++;
            }
        }
        System.out.printf("Found %d / %d entries. ", found, names.size());
        long searchTime = System.currentTimeMillis() - startSearchTime;
        long totalTime = searchTime + createTime;
        System.out.printf("Time taken: %d min. %d sec. %d ms", totalTime / 60000, totalTime / 1000 % 60, totalTime % 1000);
        System.out.printf("\nCreating time: %d min. %d sec. %d ms.",createTime / 60000, createTime / 1000 % 60, createTime % 1000);
        System.out.printf("\nSearching time: %d min. %d sec. %d ms.", searchTime / 60000, searchTime / 1000 % 60, searchTime % 1000);
    }

    public static void main(String[] args) {
        File phonebook = new File("C:\\Users\\Alexander\\IdeaProjects\\DATA\\directory.txt");
        File search = new File("C:\\Users\\Alexander\\IdeaProjects\\DATA\\find.txt");


        try {
            System.out.println("Start searching (linear search)...");
            long linearSearchTime = searchLinearSearch(phonebook, search);
            System.out.printf("Time taken: %d min. %d sec. %d ms.\n", linearSearchTime / 60000, linearSearchTime / 1000 % 60, linearSearchTime % 1000);
            System.out.println("\nStart searching (bubble sort + jump search)...");
            searchJumpAfterBubbleSort(phonebook, search, linearSearchTime);
            System.out.println("\n\nStart searching (quick sort + binary search)...");
            searchBinaryAfterQuickSort(phonebook, search);
            System.out.println("\n\nStart searching (hash table)...");
            searchWithHashtable(phonebook, search);
        } catch (Exception e) {
            System.out.println("Ooups");
        }

    }

}
