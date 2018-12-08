import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Node {

	private final List<Node> childNodes;
	private final List<Integer> metadataEntries;

	public int sumMetadata() {
		return metadataEntries.stream().mapToInt(Integer::intValue).sum()
				+ childNodes.stream().mapToInt(Node::sumMetadata).sum();
	}
}