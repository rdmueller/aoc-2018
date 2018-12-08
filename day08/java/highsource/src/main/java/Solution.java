import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {
			for (String line; (line = reader.readLine()) != null;) {
				final List<Integer> data = Arrays.asList(line.split(" ")).stream().map(Integer::parseInt)
						.collect(Collectors.toList());

				final Node root;
				{
					// Read data
					final Stack<Integer> dataToRead = new Stack<Integer>();
					final List<Integer> reverseData = new ArrayList<>(data);
					Collections.reverse(reverseData);
					dataToRead.addAll(reverseData);
					root = read(dataToRead);
					if (!dataToRead.isEmpty()) {
						throw new IllegalStateException("Stack must be empty after reading.");
					}
				}

				// Part 1
				System.out.println("Sum of all metadata entries: " + root.sumOfMetadataEntries());

				// Part 2
				System.out.println("Value of the root node: " + root.value());

			}
		}
	}

	private static Node read(Stack<Integer> data) {

		int quantityOfChildNodes = data.pop();
		int quantityOfMetadataEntries = data.pop();

		final List<Node> childNodes = new ArrayList<>(quantityOfChildNodes);
		for (int index = 0; index < quantityOfChildNodes; index++) {
			childNodes.add(read(data));
		}
		final List<Integer> metadataEntries = new ArrayList<>();
		for (int index = 0; index < quantityOfMetadataEntries; index++) {
			metadataEntries.add(data.pop());
		}
		return new Node(childNodes, metadataEntries);
	}
}