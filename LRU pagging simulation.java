import java.util.*;
public class LRU {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();
        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();

        int[] pages = new int[n];
        System.out.println("Enter the page reference string:");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        ArrayList<Integer> frame = new ArrayList<>(frames);
        int hits = 0, faults = 0;

        System.out.println("\nPage Replacement Process:");
        for (int i = 0; i < n; i++) {
            int page = pages[i];

            if (frame.contains(page)) {
                hits++;
                frame.remove((Integer) page);
                frame.add(page);
                System.out.print("Page " + page + " -> " + frame + " (Hit)");
            } else {
                faults++;
                if (frame.size() < frames) {
                    frame.add(page);
                } else {
                    frame.remove(0);
                    frame.add(page);
                }
                System.out.print("Page " + page + " -> " + frame + " (Fault)");
            }
            System.out.println();
        }
        System.out.println("\nTotal Page Faults: " + faults);
        System.out.println("Total Hits: " + hits);
        sc.close();
    }
}