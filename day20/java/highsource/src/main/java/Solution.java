import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import expr.Expr;
import expr.ParseException;
import expr.RouteParser;

public class Solution {

	public static void main(String[] args) throws IOException, ParseException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			for (String line; (line = reader.readLine()) != null;) {
				System.out.println(line);
				
				final RouteParser parser = new RouteParser(new StringReader(line));
				final Expr expr = parser.parseExpr();
				System.out.println(expr);
			}
		}
	}
}