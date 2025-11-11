import java.util.*;

public class Prior_NP {
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
        int[] PR = new int[n];      
        boolean[] done = new boolean[n]; 
        
        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.print("Enter Arrival Time for P" + pid[i] + ": ");
            AT[i] = sc.nextInt();
            System.out.print("Enter Burst Time for P" + pid[i] + ": ");
            BT[i] = sc.nextInt();
            System.out.print("Enter Priority for P" + pid[i] + " : ");
            PR[i] = sc.nextInt();
        }

        int currentTime = 0, completed = 0;
        double totalTAT = 0, totalWT = 0;
        while (completed < n) {
            int idx = -1;
            int highestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!done[i] && AT[i] <= currentTime && PR[i] < highestPriority) {
                    highestPriority = PR[i];
                    idx = i;
                }
            }

            if (idx != -1) {
                CT[idx] = currentTime + BT[idx];
                TAT[idx] = CT[idx] - AT[idx];
                WT[idx] = TAT[idx] - BT[idx];
                done[idx] = true;
                currentTime = CT[idx];
                totalTAT += TAT[idx];
                totalWT += WT[idx];
                completed++;
            } else {
                currentTime++; 
            }
        }
        
        System.out.println("\nPID\tAT\tBT\tPR\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + pid[i] + "\t" + AT[i] + "\t" + BT[i] + "\t" + PR[i] +
                               "\t" + CT[i] + "\t" + TAT[i] + "\t" + WT[i]);
        }
        System.out.printf("\nAverage Turnaround Time: %.2f", totalTAT / n);
        System.out.printf("\nAverage Waiting Time: %.2f", totalWT / n);
        sc.close();
    }
}
