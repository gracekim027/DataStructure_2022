import java.io.*;
import java.util.LinkedList;

public class Matching{

	private static Hashtable table;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			try {
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;
				command(input);
			} catch (IOException e) {
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input) throws Exception {
		if (input.isEmpty()) {
			return;
		}

		char option = input.charAt(0);
		String search = input.substring(2);

		switch (option) {
			case '<':
				table = readWrite(search);
				break;
			case '@':
				printSlot(table, Integer.parseInt(search));
				break;
			case '?':
				searchPattern(table, search);
				break;
		}
		}

	private static Hashtable readWrite(String search) throws IOException {

		//make hashtable with file strings
		Hashtable newTable = new Hashtable(100);
		BufferedReader bf = new BufferedReader(new FileReader(search));
		String line;
		int lineIndex = 0;
		while((line = bf.readLine()) != null){
			lineIndex++;
			for(int i = 0 ; i < line.length() - 5 ; i++){
				String sub = line.substring(i, i+6);
				Pair pair = new Pair(lineIndex, i+1);
				StringPair item = new StringPair(sub, pair);
				newTable.insert(item);
			}
		}
		return newTable;
	}

	private static void printSlot(Hashtable table, int search){
		//get all substrings that are in the AVLTree of hashtable

		//how many nodes one slot has
		/*for (int i=0; i<100; i++){
			System.out.println(table.getValues(i));
		}*/

		table.printSlot(search);
	}

	private static void searchPattern(Hashtable table, String search){
		boolean exists = true;
		boolean print = false;
		int length = search.length();
		LinkedList<String> substrings = new LinkedList();

		//when pattern is length 6
		if (length == 6){
			int hashnum = hash(search);
			AVLNode node = table.searchSlot(hashnum).searchString(search);
			if (node == null) {
				//does not exist
				exists = false;
			} else{
				LinkedList<Pair> pairs = node.getList();
				StringBuilder allIndex = new StringBuilder();
				for (Pair pair : pairs){
					allIndex.append(pair.toString()).append(" ");
					//allIndex.append("(").append(pair.getKey()).append(", ").append(pair.getValue()).append(") ");
				}
				System.out.println(allIndex.substring(0, allIndex.length()-1));
			}
		}else if (length > 6){

			//when pattern is longer than 6
			//making array of substrings
			for(int i = 0 ; i < length - 5 ; i++) {
				String sub = search.substring(i, i + 6);
				substrings.add(sub);
			}

            //step 2: with the hashtable, see if the first & last substrings exist
			String first = substrings.getFirst();
			int first_hash = hash(first);

			String last = substrings.getLast();
			int last_hash = hash(last);

			AVLNode first_node = table.searchSlot(first_hash).searchString(first);
			AVLNode last_node = table.searchSlot(last_hash).searchString(last);

			if (first_node != null && last_node != null){
				LinkedList<Pair> first_list = first_node.getList();
				LinkedList<Pair> last_list = last_node.getList();

				//the search range should be limited to where the first and last have same line
				StringBuilder sb = new StringBuilder();
				for (int i=0; i<first_list.size(); i++){
					for (int j=0; j<last_list.size(); j++){
						if (first_list.get(i).getKey() == last_list.get(j).getKey()){
							//same line now
							int start_index = first_list.get(i).getValue();
							//see if the rest of the substrings have pair
							exists=true;
							for (int k=1; k<substrings.size()-1; k++){
								Pair index = new Pair(first_list.get(i).getKey(), start_index + k);
								if (!exists(substrings.get(k), index, table)) {
									exists = false;
								}
							}
							int diff = last_list.get(j).getValue() - first_list.get(i).getValue();
							if (exists && (diff == length - 6)){
								sb.append(first_list.get(i).toString()).append(" ");
							}
						}
					}
				}
				if(exists || !sb.toString().isEmpty()){
					System.out.println(sb.substring(0, sb.length() - 1));
					print = true;
				}
			}else{
				//one of the strings for front or back does not exist
				exists = false;
			}

		}
		if (!exists && !print){
			System.out.println("(0, 0)");
		}
	}

	public static boolean exists(String substring, Pair supposed_index, Hashtable table){
		boolean exists= false;
		int hash_val = hash(substring);
		AVLTree tree = table.searchSlot(hash_val);
		AVLNode node = tree.searchString(substring);

		if (node == null){
			exists = false;
		}else{
			for (Pair nextPair : node.getList()) {
				if (nextPair.equals(supposed_index)) {
					exists = true;
					break;
				}
			}
		}
		return exists;
	}

	public static int hash(String substring){
		//copy of hash function, add all char and mod100
		int length = substring.length();
		int all = 0;
		for (int i=0; i<length; i++){
			all += substring.charAt(i);
		}
		return all % 100;
	}

}



