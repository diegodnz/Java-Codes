import java.util.*;

public class SubsetSum {
	
	public static void main(String[] args) {		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the number of elements of the vector: ");
		int vectLength = sc.nextInt();	
		int[] vect = new int[vectLength];
		for(int i=0; i<vectLength; i++) {
			vect[i] = i+1;
		}
		String newQuery;
		System.out.printf("Vector created ([1, ..., %d]\n", vectLength);	
		
		do {
			System.out.print("Enter the limit index of the query: ");
			int limitIndex = sc.nextInt();
			System.out.printf("The query will be done on subvect [1, ..., %d]\n", limitIndex+1);
			System.out.print("Enter the target sum: ");
			int target = sc.nextInt();
			String result = possibleSum(limitIndex, target, vect);
			if( result != null) {
				System.out.printf("It is possible to sum %d with %s", target, result); 					
			}
			else {
				System.out.printf("It is not possible to sum %d", target); 				
			}	
			sc.hasNextLine();
			System.out.println("New query (s/n)? ");
			newQuery = sc.nextLine();
		} while(newQuery.toLowerCase() != "n");
		
		sc.close();		
	}
	
	public static String possibleSum(int limitIndex, int target, int[] vect) {		
		Map<Integer, String> sums = new HashMap<>(); // HashMap that will contain possible sums of subsets of the list
		sums.put(0, "0");
		sums.put(vect[0], "vect[0]");
		if(sums.containsKey(target)) {
			return sums.get(target);
		}			
		
		for (int i=1; i <= limitIndex; i++) {
			List<Integer> keys = new ArrayList<Integer>(sums.keySet()); // Array with the keys of the Hashmap "sums"
			for (int key: keys) {				
				int newSum = key + vect[i];
				if(newSum <= target) {
					sums.put(newSum, sums.get(key) + "+ " + vect[i]);				
					if(newSum == target) {
						return sums.get(target);
					}
				}				
			}			
		}
		return null;
	}

}

