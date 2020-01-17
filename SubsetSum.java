import java.util.*;

/*
 * The algorithm determines if it is possible to obtain a sum equivalent to a given number in any subset of the vector limited by a given index
 * 
 * Example:
 * vector = [12, 35, 3, 62, 5, 100, 76]
 * limitIndex = 4 (then the query will only consider [12, 35, 3, 62, 5])
 * target = 6
 * answer: True, 2 + 4 = 6
 * 
 * @problem: https://www.geeksforgeeks.org/subset-sum-problem-dp-25/
 * @author Alisson Diego D.
 */

public class SubsetSum {
	
	public static void main(String[] args) {		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the number of elements of the vector: ");
		int vectLength = sc.nextInt();	
		int[] vect = new int[vectLength];
		System.out.println("Enter the elements of the vector: ");
		for(int i=0; i<vectLength; i++) {
			vect[i] = sc.nextInt();
		}
		String newQuery;		
		
		do{
			System.out.printf("Enter the limit index of the query (max %d): ", vectLength-1);
			int limitIndex = sc.nextInt();			
			System.out.print("Enter the target sum: ");
			int target = sc.nextInt();
			if(target == 0) {
				System.out.println("It is possible to sum 0 choosing an empty subset of the vector");
			}
			else {
				String result = possibleSum(limitIndex, target, vect);
				if( result != null) {
					System.out.printf("It is possible to sum %d with %s\n", target, result); 
				}
				else {
					System.out.printf("It is not possible to sum %d\n", target); 				
				}	
			}			
			sc.nextLine();
			System.out.println("New query (s/n)? ");
			newQuery = sc.nextLine();			
		}while(!newQuery.toLowerCase().matches("n"));
		
		sc.close();		
	}
	
	public static String possibleSum(int limitIndex, int target, int[] vect) {		
		Map<Integer, String> sums = new HashMap<>(); // HashMap that will contain possible sums of subsets of the list
		sums.put(0, "0");
		sums.put(vect[0], String.format("%d", vect[0]));
		if(sums.containsKey(target)) {
			return sums.get(target);
		}			
		
		for (int i=1; i <= limitIndex; i++) {
			List<Integer> keys = new ArrayList<Integer>(sums.keySet()); // Array with the keys of the Hashmap "sums"
			for (int key: keys) {				
				int newSum = key + vect[i];
				if(newSum <= target) {
					if(!sums.containsKey(newSum)) {
						sums.put(newSum, sums.get(key) + " + " + vect[i]);
					}									
					if(newSum == target) {						
						return sums.get(target);
					}
				}				
			}			
		}
		return null;
	}

}

