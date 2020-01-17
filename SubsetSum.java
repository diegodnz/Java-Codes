import java.util.*;

public class SubsetSum {
	
	public static void main(String[] args) {		
		Scanner sc = new Scanner(System.in);
		int testCases = sc.nextInt();
		
		for(int test=0; test < testCases; test++) {
			System.out.printf("Caso %d:", test+1);			
			List<Integer> list = new ArrayList<Integer>();
			int nElements = sc.nextInt();				
			int nQuestions = sc.nextInt();
			
			for(int element=0; element < nElements; element++) { // Create list with given elements
				list.add(sc.nextInt());
			}			
			
			for(int quest=0; quest < nQuestions; quest++) {
				int index = sc.nextInt() - 1;
				int element = sc.nextInt();
				if(possibleSum(index, element, list)) {
					System.out.print(" 1"); 					
				}
				else {
					System.out.print(" 0");
					
				}			
			}	
			System.out.println();
			
		}		
		sc.close();		
	}
	
	public static boolean possibleSum(int index, int element, List<Integer> list) {		
		Map<Integer, Boolean> sums = new HashMap<>(); // HashMap that will contain possible sums of subsets of the list
		sums.put(0, true);
		sums.put(list.get(0), true);
		if(sums.containsKey(element)) {
			return true;
		}			
		
		for (int i=1; i <= index; i++) {
			List<Integer> keys = new ArrayList<Integer>(sums.keySet()); // Array with the keys of the Hashmap "sums"
			for (Integer key: keys) {				
				int newSum = key + list.get(i);
				if(newSum <= element) {
					sums.put(newSum, true);				
					if(newSum == element) {
						return true;
					}
				}
				
			}				
		
		}
		return false;
	}
}
