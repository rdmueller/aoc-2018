import java.io.IOException;
import java.util.stream.Collectors;

public class Solution {

	public static void main(String[] args) throws IOException {
		System.out.println(Lines.resource("input.txt").stream().collect(Collectors.joining("\n")));
	}
}