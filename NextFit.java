import java.util.Arrays;
import java.util.Scanner;

public class NextFit {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of memory blocks: ");
        int nBlocks = sc.nextInt();
        int[] blocks = new int[nBlocks];
        for (int i = 0; i < nBlocks; i++)
            blocks[i] = sc.nextInt();

        System.out.print("Enter number of jobs: ");
        int nJobs = sc.nextInt();
        int[] jobs = new int[nJobs];
        for (int i = 0; i < nJobs; i++)
            jobs[i] = sc.nextInt();

        int[] allocation = new int[nJobs];
        Arrays.fill(allocation, -1);
        int[] remaining = Arrays.copyOf(blocks, blocks.length);
        int lastIndex = 0;

        for (int i = 0; i < nJobs; i++) {
            int start = lastIndex;
            boolean allocated = false;
            do {
                if (jobs[i] <= remaining[lastIndex]) {
                    allocation[i] = lastIndex;
                    remaining[lastIndex] -= jobs[i];
                    allocated = true;
                    break;
                }
                lastIndex = (lastIndex + 1) % nBlocks;
            } while (lastIndex != start);

            if (allocated) lastIndex = (lastIndex + 1) % nBlocks;
        }

        System.out.println("\nNext Fit Allocation:");
        System.out.println("Job\tSize\tBlockAssigned");
        int allocatedMemory = 0;
        for (int i = 0; i < nJobs; i++) {
            if (allocation[i] != -1) allocatedMemory += jobs[i];
            System.out.printf("%d\t%d\t%s\n", i, jobs[i], (allocation[i] == -1 ? "Not Allocated" : allocation[i]));
        }

        System.out.println("\nRemaining block sizes:");
        for (int j = 0; j < nBlocks; j++)
            System.out.printf("Block %d: %d\n", j, remaining[j]);

        int totalMemory = Arrays.stream(blocks).sum();
        double efficiency = (double) allocatedMemory / totalMemory * 100;

        System.out.println("\nTotal Memory = " + totalMemory);
        System.out.println("Allocated Memory = " + allocatedMemory);
        System.out.println("Efficiency = " + efficiency);

        sc.close();
    }
}
