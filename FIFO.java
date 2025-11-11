import java.util.Scanner;
public class FIFO {
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

        int[] frame = new int[frames];
        for (int i = 0; i < frames; i++) {
            frame[i] = -1;   
        }

        int pointer = 0, faults = 0, hits = 0;
        System.out.println("\nPage Replacement Process:");
        for (int i = 0; i < n; i++) {
            int page = pages[i];
            boolean found = false;

            for (int j = 0; j < frames; j++) {
                if (frame[j] == page) {
                    found = true;
                    hits++;
                    break;
                }
            }

            if (!found) {
                frame[pointer] = page;
                pointer = (pointer + 1) % frames;
                faults++;
            }

            System.out.print("Page " + page + " -> ");
            for (int j = 0; j < frames; j++) {
                if (frame[j] != -1)
                    System.out.print(frame[j] + " ");
                else
                    System.out.print("- ");
            }
            System.out.println(found ? "(Hit)" : "(Fault)");
        }
        System.out.println("\nTotal Page Faults: " + faults);
        System.out.println("Total Hits: " + hits);
        sc.close();
    }
}