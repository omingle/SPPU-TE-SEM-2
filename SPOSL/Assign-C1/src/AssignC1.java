import java.util.*;

class FirstComeFirstServe {
	private int CT[], TAT[], WT[];
	private double avgTAT = 0, avgWT = 0;
	private String ganttChart = "|";
	
	public FirstComeFirstServe(int n, int PID[], int AT[], int BT[]) {
		CT = new int[n];
		TAT = new int[n];
		WT = new int[n];
		
		int i, j;
		int gcount = 0;
		
		for(i=0; i<CT.length; i++) {
			if((i==0) || (AT[i] > CT[i-1])){
				CT[i] = AT[i] + BT[i];
				
				for(j=gcount; j<AT[i]; j++) {
					gcount++;
				}
			}
			else {
				CT[i] = CT[i-1] + BT[i];
			}
			
			TAT[i] = CT[i] - AT[i];
			WT[i] = TAT[i] - BT[i];
			avgTAT = avgTAT + TAT[i];
			avgWT = avgWT + WT[i];
			
			ganttChart += " P" + PID[i] + " (" + gcount + "-";
			for(j=gcount; j<CT[i]; j++) {
				gcount++;
			}
			ganttChart +=  gcount + ") |";
		}
		
		avgTAT = avgTAT/TAT.length;
		avgWT = avgWT/WT.length;
	}
	
	public void printTableFCFS(int n, int PID[], int AT[], int BT[]) {
		int i;
		System.out.println("==========================================");
		System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
		System.out.println("------------------------------------------");
		for(i=0; i<n; i++) {
			System.out.println(PID[i] + "\t" + AT[i] + "\t" + BT[i] + "\t"+ CT[i] + "\t" + TAT[i] + "\t" + WT[i]);
		}
		System.out.println("==========================================");
		System.out.printf("Average Turnaround Time : %.2f\n", getAvgTatFCFS());
		System.out.printf("Average Waiting Time    : %.2f\n", getAvgWtFCFS());
		System.out.println("==========================================");
	}
	
	public String getGanttFCFS() {
		return ganttChart;
	}
	
	public double getAvgTatFCFS() {
		return avgTAT;
	}
	
	public double getAvgWtFCFS() {
		return avgWT;
	}
}

class ShortestJobFirst {
	private int CT[], TAT[], WT[];
	private double avgTAT = 0, avgWT = 0;
	private String ganttChart = "|";
	
	public ShortestJobFirst(int n, int PID[], int AT[], int BT[]) {
		CT = new int[n];
		TAT = new int[n];
		WT = new int[n];
		int BT2[] = new int[n];
		
		int i;
		
		for(i=0; i<n; i++)
			BT2[i]  = BT[i];
		
		int min, curPos, prePos = n;
		int numProCmplt = 0, step = 0;
		int proCmpltd[] = new int[n];
				
		while(numProCmplt < n) {
			min = Integer.MAX_VALUE;
			curPos = n;
						
			for(i=0; i<n; i++) {
				if((AT[i]<=step) && (proCmpltd[i] == 0) && (BT2[i] < min)) {
					min = BT2[i];
					curPos = i;
				}
				if(AT[i] > step) {
					break;
				}
			}
			
			if(curPos == n) {
				step++;
			}
			else {
				BT2[curPos]--;
				
				if(curPos != prePos) {
					ganttChart += " P" + PID[curPos] + " (" + step + "-";	
				}
				step++;
				
				if(curPos != prePos) {
					ganttChart +=  step + ") |";	
				}
				else {
					ganttChart = ganttChart.replace("-"+(step-1), "-"+step);
				}
				
				if(BT2[curPos] == 0) {
					proCmpltd[curPos] = 1;
					CT[curPos] = step;
					numProCmplt++;
				}				
			}
			
			prePos = curPos;
		}
		
		for(i=0; i<n; i++) {
			TAT[i] = CT[i] - AT[i];
			WT[i] = TAT[i] - BT[i];
			avgTAT = avgTAT + TAT[i];
			avgWT = avgWT + WT[i];
		}
		
		avgTAT = avgTAT/TAT.length;
		avgWT = avgWT/WT.length;
	}
	
	public void printTableSJF(int n, int PID[], int AT[], int BT[]) {
		int i;
		System.out.println("==========================================");
		System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
		System.out.println("------------------------------------------");
		for(i=0; i<n; i++) {
			System.out.println(PID[i] + "\t" + AT[i] + "\t" + BT[i] + "\t"+ CT[i] + "\t" + TAT[i] + "\t" + WT[i]);
		}
		System.out.println("==========================================");
		System.out.printf("Average Turnaround Time : %.2f\n", getAvgTatSJF());
		System.out.printf("Average Waiting Time    : %.2f\n", getAvgWtSJF());
		System.out.println("==========================================");
	}
	
	public String getGanttSJF() {
		return ganttChart;
	}
	
	public double getAvgTatSJF() {
		return avgTAT;
	}
	
	public double getAvgWtSJF() {
		return avgWT;
	}
}

class PriorityNP {
	private int CT[], TAT[], WT[];
	private double avgTAT = 0, avgWT = 0;
	private String ganttChart = "|";
	
	public PriorityNP(int n, int PID[], int AT[], int BT[], int priority[]) {
		CT = new int[n];
		TAT = new int[n];
		WT = new int[n];
		
		int i, j, temp;
		int gcount = 0;
		
		int maxPrio, curPos;
		int numProCmplt = 0, step = 0;
		int proCmpltd[] = new int[n];
		
		while(numProCmplt < n) {
			maxPrio = Integer.MAX_VALUE;
			curPos = n;

			for(i=0; i<n; i++) {
				if((AT[i]<=step) && (proCmpltd[i] == 0) && (priority[i] < maxPrio)) {
					maxPrio = priority[i];
					curPos = i;
				}
				if(AT[i] > step) {
					break;
				}
			}
			
			if(curPos == n) {
				step++;
			}
			else {
				ganttChart += " P" + PID[curPos] + " (" + step + "-";
				for(i=0; i<BT[curPos];i++) {
					step++;
				}
				ganttChart +=  step + ") |";
				
				proCmpltd[curPos] = 1;
				CT[curPos] = step;
				numProCmplt++;
			}
		}
		
		for(i=0; i<n; i++) {
			TAT[i] = CT[i] - AT[i];
			WT[i] = TAT[i] - BT[i];
			avgTAT = avgTAT + TAT[i];
			avgWT = avgWT + WT[i];
		}
				
		avgTAT = avgTAT/TAT.length;
		avgWT = avgWT/WT.length;
	}
	
	public void printTablePriority(int n, int PID[], int AT[], int BT[], int priority[]) {
		int i;
		System.out.println("==========================================================");
		System.out.println("PID\tAT\tBT\tPriority\tCT\tTAT\tWT");
		System.out.println("----------------------------------------------------------");
		for(i=0; i<n; i++) {
			System.out.println(PID[i] + "\t" + AT[i] + "\t" + BT[i] + "\t" + priority[i] + "\t\t"+ CT[i] + "\t" + TAT[i] + "\t" + WT[i]);
		}
		System.out.println("==========================================================");
		System.out.printf("Average Turnaround Time : %.2f\n", getAvgTatPriority());
		System.out.printf("Average Waiting Time    : %.2f\n", getAvgWtPriority());
		System.out.println("==========================================================");
	}
	
	public String getGanttPriority() {
		return ganttChart;
	}
	
	public double getAvgTatPriority() {
		return avgTAT;
	}
	
	public double getAvgWtPriority() {
		return avgWT;
	}
}

class RoundRobin {
	private int CT[], TAT[], WT[];
	private double avgTAT = 0, avgWT = 0;
	private String ganttChart = "|";
	private Deque<Integer> readyQueue;
	
	public RoundRobin(int n, int PID[], int AT[], int BT[], int tq) {
		CT = new int[n];
		TAT = new int[n];
		WT = new int[n];
		int BT2[] = new int[n];
		int proCmpltd[] = new int[n];
		
		int i;
		
		for(i=0; i<n; i++)
			BT2[i]  = BT[i];
				
		readyQueue = new ArrayDeque<Integer>();
		int step = 0, prevStep = -1;
		
		i=0;
		while(AT[i] > step) {
			step++;
		}
		
		fillReadyQueue(n, step, prevStep, AT, proCmpltd);
		
		while(!readyQueue.isEmpty()) {

			i = readyQueue.peek();
 
			ganttChart += " P" + PID[i] + " (" + step + "-";
			if(BT2[i]>tq) {
				prevStep = step;
				step += tq;
				BT2[i] -= tq;
				
				ganttChart +=  step + ") |";
				
				fillReadyQueue(n, step, prevStep, AT, proCmpltd);
				
				readyQueue.remove();
				readyQueue.add(i);
			}
			else {
				prevStep = step;
				step += BT2[i];
				
				ganttChart +=  step + ") |";
				
				BT2[i] = 0;	
				CT[i] = step;
				proCmpltd[i] = 1;
				
				fillReadyQueue(n, step, prevStep, AT, proCmpltd);
				readyQueue.remove();
			}
			
			if(readyQueue.isEmpty()) {
				while((i<n-1) && (AT[i+1] > step)) {
					step++;
					fillReadyQueue(n, step, prevStep, AT, proCmpltd);
				}
			}
		}
		
		for(i=0; i<n; i++) {
			TAT[i] = CT[i] - AT[i];
			WT[i] = TAT[i] - BT[i];
			avgTAT = avgTAT + TAT[i];
			avgWT = avgWT + WT[i];
		}
		
		avgTAT = avgTAT/TAT.length;
		avgWT = avgWT/WT.length;
	}
	
	void fillReadyQueue(int n, int step, int prevStep, int AT[], int proCmpltd[]) {
		int i;
		for(i=0; i<n; i++) {
			if(AT[i] <= step && ((AT[i] > prevStep) || (readyQueue.isEmpty()) && proCmpltd[i] == 0)) {
				readyQueue.add(i);
			}
		}
	}
	
	public void printTableRR(int n, int PID[], int AT[], int BT[], int tq) {
		int i;
		System.out.println("Time Quantum : " + tq);
		System.out.println("==========================================");
		System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
		System.out.println("------------------------------------------");
		for(i=0; i<n; i++) {
			System.out.println(PID[i] + "\t" + AT[i] + "\t" + BT[i] + "\t"+ CT[i] + "\t" + TAT[i] + "\t" + WT[i]);
		}
		System.out.println("==========================================");
		System.out.printf("Average Turnaround Time : %.2f\n", getAvgTatRR());
		System.out.printf("Average Waiting Time    : %.2f\n", getAvgWtRR());
		System.out.println("==========================================");
	}
	
	public String getGanttRR() {
		return ganttChart;
	}
	
	public double getAvgTatRR() {
		return avgTAT;
	}
	
	public double getAvgWtRR() {
		return avgWT;
	}
}

public class AssignC1 {

	public static void main(String[] args) {
		int numProcess;
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter the Number of Processes : ");
		numProcess = scan.nextInt();
		
		int i;
		int PID[] = new int[numProcess];
		int AT[] = new int[numProcess];
		int BT[] = new int[numProcess];
		int priority[] = new int[numProcess];
		int timeQuantum = 0;
		
		System.out.println();
		
		System.out.println("Enter the Arrival Time of Processes : ");
		for(i=0; i<numProcess; i++) {
			System.out.print("Process P" + (i+1) + " : ");
			AT[i] = scan.nextInt();
			PID[i] = i+1;
		}
		
		System.out.println();
		
		System.out.println("Enter the Burst Time of Processes : ");
		for(i=0; i<numProcess; i++) {
			System.out.print("Process P" + (i+1) + " : ");
			BT[i] = scan.nextInt();
		}
		
		System.out.println();
		
		System.out.println("Enter the Priorities of Processes : ");
		for(i=0; i<numProcess; i++) {
			System.out.print("Process P" + (i+1) + " : ");
			priority[i] = scan.nextInt();
		}
		
		System.out.print("\nEnter the Time Quantum : ");
		timeQuantum = scan.nextInt();
		
		
		// sorting processes as per their arrival time (bubble sort)
		int j, temp;
		for(i=0; i<numProcess-1; i++) {
			for(j=0; j<numProcess-i-1; j++) {
				if(AT[j] > AT[j+1]) {
					temp = AT[j]; 
					AT[j] = AT[j+1];
					AT[j+1] = temp;
					
					temp = BT[j]; 
					BT[j] = BT[j+1];
					BT[j+1] = temp;
					
					temp = PID[j]; 
					PID[j] = PID[j+1];
					PID[j+1] = temp;
					
					temp = priority[j]; 
					priority[j] = priority[j+1];
					priority[j+1] = temp;
				}
			}
		}
		
		FirstComeFirstServe fcfs = new FirstComeFirstServe(numProcess, PID, AT, BT);
		ShortestJobFirst sjf = new ShortestJobFirst(numProcess, PID, AT, BT);
		PriorityNP pnp = new PriorityNP(numProcess, PID, AT, BT, priority);
		RoundRobin rr = new RoundRobin(numProcess, PID, AT, BT, timeQuantum);
		
		System.out.println("\n\n\n================== FCFS ==================");
		fcfs.printTableFCFS(numProcess, PID, AT, BT);
		System.out.print("\nGantt Chart : " + fcfs.getGanttFCFS());
		
		System.out.println("\n\n\n=========== SJF (Pre-emptive) ============");
		sjf.printTableSJF(numProcess, PID, AT, BT);
		System.out.print("\nGantt Chart : " + sjf.getGanttSJF());

		System.out.println("\n\n\n============== Priority (Non Pre-emptive) ================");
		pnp.printTablePriority(numProcess, PID, AT, BT, priority);
		System.out.print("\nGantt Chart : " + pnp.getGanttPriority());
		
		System.out.println("\n\n\n=============== Round Robin ==============");
		rr.printTableRR(numProcess, PID, AT, BT, timeQuantum);
		System.out.print("\nGantt Chart : " + rr.getGanttRR());
		
		System.out.println("\n\n\n\n============================================================================");
		System.out.println("===== Comparision of All Process Scheduling Algorithms For Given Data ======");
		System.out.println("============================================================================");
		System.out.println("Algorithms		Average Turnaround Time		Average Waiting Time");
		System.out.println("----------------------------------------------------------------------------");
		System.out.printf("FCFS		:	%.2f				%.2f\n", fcfs.getAvgTatFCFS(), fcfs.getAvgWtFCFS());
		System.out.printf("SJF		:	%.2f				%.2f\n", sjf.getAvgTatSJF(), sjf.getAvgWtSJF());
		System.out.printf("Priority	:	%.2f				%.2f\n", pnp.getAvgTatPriority(), pnp.getAvgWtPriority());
		System.out.printf("RR		:	%.2f				%.2f\n", rr.getAvgTatRR(), rr.getAvgWtRR());
		System.out.println("============================================================================");
	}

}
