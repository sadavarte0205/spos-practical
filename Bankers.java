import java.util.*;
public class Bankers {
public static void main(String[] args) 
{
int np, nr, avail[][], max[][], alloc[][], need[][];
Scanner sc = new Scanner(System.in);
System.out.print("Enter number of Processes: ");
np = sc.nextInt();
System.out.print("Enter number of Resources: ");
nr = sc.nextInt();
alloc = new int[np][nr];
avail = new int[1][nr];
max = new int[np][nr];
need = new int[np][nr];
System.out.println("Enter Allocation Matrix:");
for (int i = 0; i < np; i++) {
for (int j = 0; j < nr; j++) {
alloc[i][j] = sc.nextInt();
}
}
System.out.println("Enter Max Matrix:");
for (int i = 0; i < np; i++) {
for (int j = 0; j < nr; j++) {
max[i][j] = sc.nextInt();
}
}
System.out.print("Enter Available Matrix:");
for(int j=0; j<nr; j++){
avail[0][j] = sc.nextInt();
}
for (int i = 0; i < np; i++) {
for (int j = 0; j < nr; j++) {
need[i][j] = max[i][j] - alloc[i][j];
}
}
sc.close();
boolean[] done = new boolean[np];
int[] safeSeq = new int[np];
int count = 0;
while (count < np) {
boolean found = false;
for (int p = 0; p < np; p++) {
if (!done[p]) {
int j;
for (j = 0; j < nr; j++) {
if (need[p][j] > avail[0][j])
break;
}
if (j == nr) {
for (int k = 0; k < nr; k++) {
avail[0][k] += alloc[p][k];
}
safeSeq[count++] = p;
done[p] = true;
found = true;
}
}
}
if (!found) {
System.out.println("System is not in safe state");
return;
} 
}
System.out.println("System is in safe state.");
System.out.print("Safe Sequence: ");
for (int i = 0; i < np; i++) {
System.out.print("P" + safeSeq[i]);
if (i != np - 1)
System.out.print(" -> ");
}
System.out.println();
}
}