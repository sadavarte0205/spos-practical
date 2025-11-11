import java.util.*;

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter Time Quantum: ");
        int tq = sc.nextInt();

        int[] pid = new int[n];       
        int[] AT = new int[n];        
        int[] BT = new int[n];        
        int[] RT = new int[n];        
        int[] CT = new int[n];        
        int[] TAT = new int[n];       
        int[] WT = new int[n];        
        boolean[] isCompleted = new boolean[n];

        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.print("Enter Arrival Time for P" + pid[i] + ": ");
            AT[i] = sc.nextInt();
            System.out.print("Enter Burst Time for P" + pid[i] + ": ");
            BT[i] = sc.nextInt();
            RT[i] = BT[i]; 
        }

        int time = 0, completed = 0;
        double totalTAT = 0, totalWT = 0;
        Queue<Integer> q = new LinkedList<>();

        boolean[] addedToQueue = new boolean[n];

        while (completed < n) {
            for (int i = 0; i < n; i++) {
                if (AT[i] <= time && !addedToQueue[i] && RT[i] > 0) {
                    q.add(i);
                    addedToQueue[i] = true;
                }
            }

            if (q.isEmpty()) {
                time++; 
                continue;
            }

            int i = q.poll();

            if (RT[i] > tq) {
                time += tq;
                RT[i] -= tq;
            } else {
                time += RT[i];
                RT[i] = 0;
                CT[i] = time;
                isCompleted[i] = true;
                completed++;
            }
            
            for (int j = 0; j < n; j++) {
                if (AT[j] <= time && !addedToQueue[j] && RT[j] > 0) {
                    q.add(j);
                    addedToQueue[j] = true;
                }
            }

            if (RT[i] > 0) {
                q.add(i);
            }
        }
       
        for (int i = 0; i < n; i++) {
            TAT[i] = CT[i] - AT[i];
            WT[i] = TAT[i] - BT[i];
            totalTAT += TAT[i];
            totalWT += WT[i];
        }
        
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + pid[i] + "\t" + AT[i] + "\t" + BT[i] + "\t" +
                    CT[i] + "\t" + TAT[i] + "\t" + WT[i]);
        }
        System.out.printf("\nAverage Turnaround Time: %.2f", totalTAT / n);
        System.out.printf("\nAverage Waiting Time: %.2f", totalWT / n);
        sc.close();
    }
}
