import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TracksMap {

	private final int width;
	private final int height;
	private final char[][] board;
	private List<CartState> cartStates;

	private TracksMap(int width, int height) {
		this.board = new char[height][width];
		this.width = width;
		this.height = height;
		this.cartStates = new ArrayList<>();
	}

	public char get(int x, int y) {
		return this.board[y][x];
	}

	public char get(XY xy) {
		return this.board[xy.getY()][xy.getX()];
	}

	public List<CartState> getCartStates() {
		return cartStates;
	}

	public Set<XY> getCollisions() {
		return cartStates().collect(Collectors.groupingBy(CartState::getCurrentPosition)).entrySet().stream()
				.filter(entry -> entry.getValue().size() > 1).map(Entry::getKey).collect(Collectors.toSet());
	}

	private void set(int x, int y, char c) {
		this.board[y][x] = c;
	}

	public void tick() {
		List<CartState> nextCartStates = cartStates().map(cartState -> cartState.nextState(this))
				.collect(Collectors.toList());
		this.cartStates = nextCartStates;
	}

	private void extractCartStates() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				char c = get(x, y);
				switch (c) {
				case '>':
					set(x, y, '-');
					this.cartStates.add(new CartState(x, y, CartOrientation.RIGHT, CartTurn.LEFT));
					break;
				case 'v':
					set(x, y, '|');
					this.cartStates.add(new CartState(x, y, CartOrientation.DOWN, CartTurn.LEFT));
					break;
				case '<':
					set(x, y, '-');
					this.cartStates.add(new CartState(x, y, CartOrientation.LEFT, CartTurn.LEFT));
					break;
				case '^':
					set(x, y, '|');
					this.cartStates.add(new CartState(x, y, CartOrientation.UP, CartTurn.LEFT));
					break;
				case ' ':
				case '|':
				case '-':
				case '/':
				case '\\':
				case '+':
					break;
				default:
					throw new IllegalArgumentException();
				}
			}
		}
	}

	private Stream<CartState> cartStates() {
		return this.cartStates
				//
				.stream()
				//
				.sorted(Comparator
						//
						.<CartState>comparingInt(cs -> cs.getCurrentPosition().getY())
						//
						.thenComparingInt(cs -> cs.getCurrentPosition().getY()));
	}

	@Override
	public String toString() {
		final char[][] brd = new char[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				brd[y][x] = board[y][x];
			}
		}

		cartStates().forEach(cartState -> {
			XY currentPosition = cartState.getCurrentPosition();
			brd[currentPosition.getY()][currentPosition.getX()] = cartState.getCurrentOrientation().getSymbol();

		});

		final StringBuilder sb = new StringBuilder();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				sb.append(brd[y][x]);
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public static TracksMap parse(List<String> input) {

		final int height = input.size();
		final int width = input.stream().mapToInt(String::length).max().orElseThrow(IllegalArgumentException::new);
		input.stream().mapToInt(String::length).filter(length -> length != width).findFirst()
				.ifPresent(l -> new IllegalArgumentException());
		TracksMap tracksMap = new TracksMap(width, height);

		for (int y = 0; y < height; y++) {
			char[] line = input.get(y).toCharArray();
			for (int x = 0; x < width; x++) {
				char c = line[x];
				tracksMap.set(x, y, c);
			}
		}

		tracksMap.extractCartStates();
		return tracksMap;
	}
}
