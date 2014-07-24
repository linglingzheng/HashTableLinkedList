package WebTrackerAlgorithm;

import java.io.*;
import java.util.*;

public class WebTracker {

	// Reads a text file and identify user IDs that have more than 500
	// activities within any given 10 minutes
	//

	public static void main(String[] arg) throws IOException {

		// Declare a linked list and a hash table

		LinkedList<IntNode> list = new LinkedList<IntNode>();
		Hashtable<Integer, Integer> tracker = new Hashtable<Integer, Integer>();

		// Open the file
		String fileName = "data.txt";
		
		File file = new File(fileName);
		Scanner sc = new Scanner(new FileInputStream(file));

		// Read File Line By Line
		
		if (sc.hasNext()) {

			// The first line is the header, skip it
			
			sc.nextLine();

			while (sc.hasNextLine()) {

				// Read the next line and split it by tab, and generate three
				// integer variables corresponding to user id, time stamp and
				// activity count.
				
				String str = sc.nextLine();
				String[] parts = str.split("	");

				int userID = Integer.parseInt(parts[0]);
				int timeStamp = toMins(parts[1]);
				int actCnt = Integer.parseInt(parts[2]);

				// Declare a node and store the three variables in the single node
				
				IntNode entry = new IntNode(userID, timeStamp, actCnt, null);

				// Add the node to the end of the linked list
				
				list.addLast(entry);

				int sumitem = 0;
				int subtractitem = 0;

				// Update the hash table based on the linked list; hash table
				// key is the user id, and its value is the activity count.
				// first, keep adding up count (value) for the same user (key).
				
				if (!(tracker.containsKey(list.getLast().user))) {

					tracker.put(list.getLast().user, list.getLast().count);

				} else {
					sumitem = tracker.get(list.getLast().user)
							+ list.getLast().count;
					tracker.put(list.getLast().user, sumitem);
				}

				// When the time window is less than 10min, keep adding count
				// Otherwise, keep removing the first node of the list until
				// the time window < 10; in the meantime, updating hash table
				// by subtracting the count.
				
				while (list.getLast().time - list.getFirst().time > 10) {

					subtractitem = tracker.get(list.getFirst().user)
							- list.getFirst().count;
					tracker.put(list.getFirst().user, subtractitem);
					list.removeFirst();
				}
                
				// Output the user id if his count is more than 500.
				
				if (tracker.get(list.getLast().user) > 500) {
					System.out
							.println("User ID: "
									+ list.getLast().user
									+ " has more than 500 activities within 10 minutes");
				     
					PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
					writer.println("User ID: "
							+ list.getLast().user
							+ " has more than 500 activities within 10 minutes");
					writer.close();
				
				}

			}
		}
		sc.close();

	}

	/**
	 * @param s
	 *            H:m time stamp, i.e. [Hour in day (0-23) or (0-12)]:[Minute in
	 *            hour (0-59)]
	 * @return total minutes after 00:00am or 00:00pm
	 */
	private static int toMins(String s) {

		String[] parts = s.split(":");
		int hour = 0;
		int min = 0;

		if (parts[1].contains("a")) {
			String[] parts2 = parts[1].split("a");
			min = Integer.parseInt(parts2[0]);
			hour = Integer.parseInt(parts[0]);

		} else if (parts[1].contains("p")) {
			String[] parts2 = parts[1].split("p");
			min = Integer.parseInt(parts2[0]);
			hour = Integer.parseInt(parts[0]);

			if (hour <= 12) {
				hour = hour + 12;
			} else {
				hour = hour + 0;
			}
		}

		int hoursInMins = hour * 60;
		return hoursInMins + min;
	}

}
