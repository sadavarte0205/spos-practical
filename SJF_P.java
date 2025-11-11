import java.util.*;

public class SJF_P {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int pid[] = new int[n];
        int at[] = new int[n];
        int bt[] = new int[n];
        int rt[] = new int[n]; 
        int ct[] = new int[n];
        int tat[] = new int[n];
        int wt[] = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for P" + (i+1) + ": ");
            at[i] = sc.nextInt();
            System.out.print("Enter burst time for P" + (i+1) + ": ");
            bt[i] = sc.nextInt();
            pid[i] = i + 1;
            rt[i] = bt[i];
        }

        int completed = 0, time = 0, minIndex = -1;
        double totalTAT = 0, totalWT = 0;
        boolean check = false;

        while (completed != n) {
            minIndex = -1;
            int minRT = Integer.MAX_VALUE;
            
            for (int i = 0; i < n; i++) {
                if (at[i] <= time && rt[i] > 0 && rt[i] < minRT) {
                    minRT = rt[i];
                    minIndex = i;
                    check = true;
                }
            }

            if (!check) {
                time++;
                continue;
            }

            rt[minIndex]--; 
            
            if (rt[minIndex] == 0) {
                completed++;
                ct[minIndex] = time + 1;
                tat[minIndex] = ct[minIndex] - at[minIndex];
                wt[minIndex] = tat[minIndex] - bt[minIndex];
                totalTAT += tat[minIndex];
                totalWT += wt[minIndex];
            }

            time++;
            check = false;
        }
        
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println(pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }
        System.out.printf("\nAverage Turnaround Time: %.2f", totalTAT / n);
        System.out.printf("\nAverage Waiting Time: %.2f", totalWT / n);

        sc.close();
    }
}
