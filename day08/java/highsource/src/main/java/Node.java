import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Node {

	private static final Node NULL = new Node(Collections.emptyList(), Collections.emptyList());

	private final List<Node> childNodes;
	private final List<Integer> metadataEntries;

	public int sumOfMetadataEntries() {
		return metadataEntries.stream().mapToInt(Integer::intValue).sum()
				+ childNodes.stream().mapToInt(Node::sumOfMetadataEntries).sum();
	}

	public int value() {

		if (childNodes.isEmpty()) {
			return sumOfMetadataEntries();
		} else {

			return metadataEntries.stream().
			//
					mapToInt(metadataEntry -> metadataEntry.intValue() - 1).
					//
					filter(childNodeIndex -> childNodeIndex >= 0).
					//
					filter(childNodeIndex -> childNodeIndex < childNodes.size()).
					//
					mapToObj(childNodes::get).
					//
					mapToInt(Node::value).
					//
					sum();
		}
	}
}