import java.io.*;
import java.util.*;


public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < newvalue.length-1; i++)
					{
						sb.append(newvalue[i]).append("\n");
						//System.out.println(newvalue[i]);
					}
					sb.append(newvalue[newvalue.length-1]);
					System.out.println(sb);
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		int length = value.length;
		for (int i = length -1; i>0; i--){
			for (int j=0; j<length-1; j++){
				if (value[j] > value[j+1]){
					swap(value, j, j+1);
				}
			}
		}
		return (value);
	}

	public static void swap(int[] value, int pos, int next_pos){
		int temp = value[pos];
		value[pos] = value[next_pos];
		value[next_pos] = temp;
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		int length = value.length;
		for (int i=1; i<length; i++){
			int j = i-1;
			int item = value[i];
			while (j>=0 && value[j] > item){
				value[j+1] = value[j];
				j --;
			}
			value[j+1] = item;
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		int start = value.length/2 -1;
		for (int i = start; i >= 0; i--) {
			buildHeap(value, value.length, i);
		}

		for (int size = value.length-1; size>0; size--){
			swap(value, 0, size);
			buildHeap(value, size, 0);
		}

		return value;
	}

	private static void buildHeap(int[] value, int size, int i)
	{
		int parent = i;
		int left = 2 * i + 1;
		int right = 2 * i + 2;

		if (left < size && value[left] > value[parent]){
			parent = left;
		}

		if (right < size && value[right] > value[parent]){
			parent = right;
		}

		if (parent != i){
			swap(value, i, parent);
			buildHeap(value, size, parent);
		}

	}


	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		mergeSort(value, 0, value.length-1);
		return (value);
	}

	private static void mergeSort(int[] value, int p, int r){
		if (p<r){
			int mid = (p+r)/2;
			mergeSort(value, p, mid);
			mergeSort(value, mid+1, r);
			merge(value, p, mid, r);
		}
	}


	private static void merge(int[] value, int p, int q, int r) {
		int start = p;
		int mid = q + 1;
		int index = start;
		int[] tmp = new int[value.length];

		while (start <= q && mid <= r) {
			if (value[start] < value[mid]) {
				tmp[index] = value[start];
				index ++;
				start ++;
			} else {
				tmp[index] = value[mid];
				index ++;
				mid ++;
			}
		}

		while (start <= q){
			tmp[index] = value[start];
			index ++;
			start ++;
		}

		while (mid <= r){
			tmp[index] = value[mid];
			index ++;
			mid ++;
		}

		if (r + 1 - p >= 0) System.arraycopy(tmp, p, value, p, r + 1 - p);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		splitQuickSort(value, 0, value.length-1);
		return value;
	}

	private static int partition(int[] value, int begin, int end){
		int pivot = value[end];
		int i = begin-1;
		for (int j=begin; j<=end-1; j++){
			if (value[j] < pivot){
				i++;
				swap(value, i, j);
			}
		}
		swap(value, i+1, end);
		return i+1;
	}

	private static void splitQuickSort(int[] value, int begin, int end){
		if (begin < end){
			int q = partition(value, begin, end);
			splitQuickSort(value, begin, q-1);
			splitQuickSort(value, q+1, end);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{

		//split into negative & positive numbers and sort + merge
		ArrayList<Integer> positive = new ArrayList<>();
		ArrayList<Integer> negative = new ArrayList<>();

		for (int j : value) {
			if (j < 0) {
				negative.add(j);
			} else {
				positive.add(j);
			}
		}

		//step 1. sort for neg
		if (!negative.isEmpty()){
			//change into positive
			changeNeg(negative);
			int max = findMaxNum(negative);
			for (int place = 1; place <= max; place = place*10){
				RadixSortByDigit(negative, place);
			}
			//step 2. flip the array list
			Collections.reverse(negative);
			//step 3. add the negative again, sorted negative array!
			changeNeg(negative);
		}

		//step 1. sort for pos
		if (!positive.isEmpty()) {
			int max = findMaxNum(positive);
			for (int place = 1; place <= max; place = place*10){
				RadixSortByDigit(positive, place);
			}
		}

		//step 4: if not empty, add pos first, and then add all neg
		ArrayList<Integer> finalValue = new ArrayList<>();

		if (!negative.isEmpty()){
			finalValue.addAll(negative);
		}

		if (!positive.isEmpty()){
			finalValue.addAll(positive);
		}

		//step 5: change arraylist into array
		int[] finalArr = new int[finalValue.size()];
		for (int i =0 ; i<finalValue.size(); i++){
			finalArr[i] = finalValue.get(i);
		}

		return finalArr;

	}

	private static int findMaxNum(ArrayList<Integer> value){
		//find the maximum number, and calculate its digit
		int max = 0;
		for (int j : value) {
			if (max < j) {
				max = j;
			}
		}
		return max;
	}

	private static void RadixSortByDigit(ArrayList<Integer> value, int exp){
		//count sort
		int[] output = new int[value.size()];
		int[] count = new int[10];
		Arrays.fill(count, 0);

		for (int i=0; i<value.size(); i++){
			int digit = (value.get(i)/exp) % 10;
			count[digit] ++;
		}

		for (int i=1; i<10; i++){
			count[i] += count[i-1];
		}

		for (int i=value.size()-1; i>=0; i--){
			int digit = (value.get(i)/exp) % 10;
			output[count[digit]-1] = value.get(i);
			count[digit]--;
		}

		for(int i=0; i<value.size(); i++){
			value.set(i, output[i]);
		}

	}

	private static void changeNeg(ArrayList<Integer> negValues){
			//neg --> pos or pos --> neg
			for (int i=0; i<negValues.size(); i++) {
				negValues.set(i, -negValues.get(i));
			}
	}

}


