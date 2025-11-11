import java.util.*;

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] pid = new int[n];
        int[] AT = new int[n];
        int[] BT = new int[n];
        int[] CT = new int[n];
        int[] TAT = new int[n];
        int[] WT = new int[n];

        // Input process details
        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.print("Enter Arrival Time for P" + pid[i] + ": ");
            AT[i] = sc.nextInt();
            System.out.print("Enter Burst Time for P" + pid[i] + ": ");
            BT[i] = sc.nextInt();
        }

        // Sort by Arrival Time
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (AT[j] > AT[j + 1]) {
                    int temp = AT[j]; AT[j] = AT[j + 1]; AT[j + 1] = temp;
                    temp = BT[j]; BT[j] = BT[j + 1]; BT[j + 1] = temp;
                    temp = pid[j]; pid[j] = pid[j + 1]; pid[j + 1] = temp;
                }
            }
        }

        // Calculate CT, TAT, WT
        for (int i = 0; i < n; i++) {
            if (i == 0)
                CT[i] = AT[i] + BT[i];
            else {
                if (CT[i - 1] < AT[i]) // CPU idle time
                    CT[i] = AT[i] + BT[i];
                else
                    CT[i] = CT[i - 1] + BT[i];
            }

            TAT[i] = CT[i] - AT[i];
            WT[i] = TAT[i] - BT[i];
        }

        // Output results
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + pid[i] + "\t" + AT[i] + "\t" + BT[i] + "\t" +
                    CT[i] + "\t" + TAT[i] + "\t" + WT[i]);
        }

        // Average times
        double avgTAT = 0, avgWT = 0;
        for (int i = 0; i < n; i++) {
            avgTAT += TAT[i];
            avgWT += WT[i];
        }
        avgTAT /= n;
        avgWT /= n;

        System.out.printf("\nAverage Turnaround Time: %.2f", avgTAT);
        System.out.printf("\nAverage Waiting Time: %.2f", avgWT);

        sc.close();
    }
}
