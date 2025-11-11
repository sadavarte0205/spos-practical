import java.util.*;

public class Optimal {
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
                System.out.print("Page " + page + " -> " + frame + " (Hit)");
            } else {
                faults++;
                if (frame.size() < frames) {
                    frame.add(page);
                } else {
                    int indexToReplace = findOptimal(frame, pages, i + 1);
                    frame.set(indexToReplace, page);
                }
                System.out.print("Page " + page + " -> " + frame + " (Fault)");
            }
            System.out.println();
        }

        System.out.println("\nTotal Page Faults: " + faults);
        System.out.println("Total Hits: " + hits);

        sc.close();
    }

    private static int findOptimal(ArrayList<Integer> frame, int[] pages, int start) {
        int farthest = start, indexToReplace = -1;

        for (int i = 0; i < frame.size(); i++) {
            int currentPage = frame.get(i);
            int j;
            for (j = start; j < pages.length; j++) {
                if (pages[j] == currentPage) {
                    if (j > farthest) {
                        farthest = j;
                        indexToReplace = i;
                    }
                    break;
                }
            }
            if (j == pages.length) {
                return i;
            }
        }

        return (indexToReplace == -1) ? 0 : indexToReplace;
    }
}