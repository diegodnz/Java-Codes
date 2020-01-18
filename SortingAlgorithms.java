import java.util.*;

public class SortingAlgorithms {	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the size of the vector: ");
		int size = sc.nextInt();
		int[] ordered = createVector("ord", size);
		int[] disordered = createVector("disord", size);
		int[] aleatory = createVector("aleatory", size);
		Random gen = new Random();
		String[] algorithms = {"mergesort", "heapsort", "quicksort", "bubblesort"};		
		//Results:	
		for(String algorithm: algorithms) {		
			int[] disorderedClone = disordered.clone();
			int[] aleatoryClone = aleatory.clone();
			System.out.println(algorithm + ":");			
			testTime(ordered, "Ordered", algorithm, gen);
			testTime(disorderedClone, "Disordered", algorithm, gen);
			testTime(aleatoryClone, "Aleatory", algorithm, gen);
			System.out.println();
		}
		sc.close();
	}
	
	public static void testTime(int[] vect, String vectorType, String sort, Random gen){
		long start = System.nanoTime();
		boolean stackOverflow = false;
		if(sort.matches("mergesort")) {
			vect = Mergesort.sort(vect, 0, vect.length-1);			
		}else if(sort.matches("bubblesort")) {
			Bubblesort.sort(vect);			
		}else if(sort.matches("quicksort")) {		
			try {
				Quicksort.sort(vect, 0, vect.length-1, gen);	
			}catch(StackOverflowError error) {
				stackOverflow = true;
			}				
		}else if(sort.matches("heapsort")) {			
			Heap.sort(vect);			
		}
		long end = System.nanoTime();
		double totalTime =(double)(end-start)/1000000;
		if(stackOverflow){
			//System.out.print("(StackOverflow)");
		}
		System.out.printf("%s: %.2fms\n", vectorType, totalTime);
		if(vect.length > 20) {
			//System.out.printf("[%d, %d, %d, ..., %d, %d, %d, ..., %d, %d, %d] %d\n",vect[0], vect[1], vect[2], 
					//vect[vect.length/2+1], vect[vect.length/2+2], vect[vect.length/2+3],
					//vect[vect.length-3], vect[vect.length-2], vect[vect.length-1], vect.length);
		}else {
			//System.out.println(Arrays.toString(vect));
		}
	}
	
	public static int[] createVector(String type, int size) {
		Random generator = new Random();		
		int[] vect = new int[size];
		for(int i=1; i<=size; i++) {
			if(type=="ord") {
				vect[i-1] = i;
			}else if(type=="disord") {
				vect[size-i] = i;
			}else {
				vect[i-1] = generator.nextInt(1000);
			}		
		}
		return vect;		
	}
	
	
	//mergesort
	public static class Mergesort{
		public static int[] merge(int[] vect1, int[] vect2) {
			int finalLength = vect1.length + vect2.length;
			int[] result = new int[finalLength];
			int i=0, j=0, indexRes=0; // i -> vect1, j -> vect2, indexRes -> result
			while(indexRes < finalLength) {
				if(j >= vect2.length || (i < vect1.length && vect1[i] < vect2[j])) {
					result[indexRes] = vect1[i];
					i += 1;			
				}else {
					result[indexRes] = vect2[j];
					j += 1;
				}
				indexRes += 1;
			}
			return result;		
		}
		public static int[] sort(int[] vect, int start, int end) {
			if(end>start) {
				int mid = (end+start)/2;
				int[] leftSide = sort(vect, start, mid);
				int[] rightSide = sort(vect, mid+1, end);
				return merge(leftSide, rightSide);
			}else {			
				return new int[]{vect[start]};
			}
		}
	}
	
	
	//heapsort
	public static class Heap{ 	
		private static void buildHeap(int[] vect) {			
			for(int i = vect.length/2 -1; i >= 0; i--) {			
				maxHeapify(vect, i, vect.length-1);
			}
		}		
		private static void maxHeapify(int[] vect, int index, int limit) { 			
			int max = index;		
			int left = index*2;
			int right = index*2+1;			
			if(left <= limit && vect[left] > vect[max]) {
				max = left;
			}
			if(right <= limit && vect[right] > vect[max]) {
				max = right;
			}
			if(max != index) {
				int num = vect[index];
				vect[index] = vect[max];
				vect[max] = num;
				maxHeapify(vect, max, limit);
			}
		}
		public static void sort(int[] vect) {
			buildHeap(vect);			
			for(int limit = vect.length-1; limit > 0; limit--) {			
				maxHeapify(vect, 0, limit);
				int num = vect[0];
				vect[0] = vect[limit];
				vect[limit] = num;				
			}			
		}
	}
	
	//quicksort
	public static class Quicksort{
		public static int split(int[] vect, int start, int end, Random gen) {		
			int randomPos = gen.nextInt(end+1-start) + start;
			int comparator = vect[randomPos];
			vect[randomPos] = vect[end];
			vect[end] = comparator;		
			int left = start, right = end; 
			boolean turnLeft = true;
			boolean stopAll = false;
			
			while(!stopAll && left < right) {
				if(turnLeft){
					while(vect[left] < comparator) {
						left += 1;
						if(left == right) {
							stopAll = true;
							break;
						}
					}
					if(!stopAll) {
						vect[right] = vect[left];
						right -= 1;					
						turnLeft = false;
					}				
				}else {
					while(vect[right] > comparator){
						right -= 1;
						if(right == left) {
							stopAll = true;
							break;
						}
					}
					if(!stopAll) {
						vect[left] = vect[right];					
						left += 1;
						turnLeft = true;
					}			
				}	
			}		
			vect[right] = comparator;
			return right;
		}
		public static void sort(int[] vect, int start, int end, Random gen) {		
			if(end>start) {
				int mid = split(vect, start, end, gen);			
				sort(vect, start, mid-1, gen);
				sort(vect, mid+1, end, gen);				
			}
		}
	}
	
		
	//bubblesort
	public static class Bubblesort{
		public static void sort(int[] vect) {
			boolean ordained;
			do{
				ordained = true;
				for(int i=0; i<vect.length-1; i++) {
					if(vect[i] > vect[i+1]){
						int num = vect[i+1];
						vect[i+1] = vect[i];
						vect[i] = num;
						ordained = false;
					}
				}
			}while(!ordained);
		}
	}
}
	
