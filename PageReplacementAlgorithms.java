import java.util.*;

public class PageReplacementAlgorithms {

    // Method to simulate FIFO (First-In-First-Out) Page Replacement
    public static void fifo(int[] pages, int capacity) {
        Set<Integer> s = new HashSet<>(capacity);
        Queue<Integer> indexes = new LinkedList<>();
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            if (!s.contains(pages[i])) {
                if (s.size() == capacity) {
                    int val = indexes.poll();
                    s.remove(val);
                }
                s.add(pages[i]);
                indexes.add(pages[i]);
                pageFaults++;
            }
        }
        System.out.println("FIFO Page Faults: " + pageFaults);
    }

    // Method to simulate LRU (Least Recently Used) Page Replacement
    public static void lru(int[] pages, int capacity) {
        Set<Integer> s = new HashSet<>(capacity);
        Map<Integer, Integer> indexes = new HashMap<>();
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            if (!s.contains(pages[i])) {
                if (s.size() == capacity) {
                    int lru = Integer.MAX_VALUE, val = Integer.MIN_VALUE;
                    for (int page : s) {
                        if (indexes.get(page) < lru) {
                            lru = indexes.get(page);
                            val = page;
                        }
                    }
                    s.remove(val);
                }
                s.add(pages[i]);
                pageFaults++;
            }
            indexes.put(pages[i], i);
        }
        System.out.println("LRU Page Faults: " + pageFaults);
    }

    // Method to simulate Optimal Page Replacement
    public static void optimal(int[] pages, int capacity) {
        Set<Integer> s = new HashSet<>(capacity);
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            if (!s.contains(pages[i])) {
                if (s.size() == capacity) {
                    int val = predict(pages, s, i + 1);
                    s.remove(val);
                }
                s.add(pages[i]);
                pageFaults++;
            }
        }
        System.out.println("Optimal Page Faults: " + pageFaults);
    }

    // Method to predict the farthest page to be used in future for Optimal Page Replacement
    public static int predict(int[] pages, Set<Integer> s, int index) {
        int res = -1, farthest = index;
        for (int page : s) {
            int j;
            for (j = index; j < pages.length; j++) {
                if (page == pages[j]) {
                    if (j > farthest) {
                        farthest = j;
                        res = page;
                    }
                    break;
                }
            }
            if (j == pages.length) {
                return page;
            }
        }
        return (res == -1) ? s.iterator().next() : res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();
        int[] pages = new int[n];

        System.out.println("Enter reference string (pages): ");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        System.out.print("Enter the number of frames (capacity): ");
        int capacity = sc.nextInt();

        System.out.println("\nSimulating Page Replacement Algorithms:");
        fifo(pages, capacity);
        lru(pages, capacity);
        optimal(pages, capacity);

        sc.close();
    }
}
