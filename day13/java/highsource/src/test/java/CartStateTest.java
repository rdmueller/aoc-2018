import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class CartStateTest {

	@Test
	public void nextStateStraightRight() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList(">-"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		CartState a = currentCartState.nextState(tracksMap);
		CartState b = new CartState(1, 0, CartOrientation.RIGHT, CartTurn.LEFT);
		assertThat(a)
				.isEqualTo(b);
	}

	@Test
	public void nextStateStraightDown() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("v", "|"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		assertThat(currentCartState.nextState(tracksMap))
				.isEqualTo(new CartState(0, 1, CartOrientation.DOWN, CartTurn.LEFT));
	}

	@Test
	public void nextStateStraightLeft() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("-<"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		assertThat(currentCartState.nextState(tracksMap))
				.isEqualTo(new CartState(0, 0, CartOrientation.LEFT, CartTurn.LEFT));
	}

	@Test
	public void nextStateStraightUp() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("|", "^"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		assertThat(currentCartState.nextState(tracksMap))
				.isEqualTo(new CartState(0, 0, CartOrientation.UP, CartTurn.LEFT));
	}

	//////////////////////////////////////////////////////

	@Test
	public void nextStateCurveRightUp() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList(">/"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		assertThat(currentCartState.nextState(tracksMap))
				.isEqualTo(new CartState(1, 0, CartOrientation.UP, CartTurn.LEFT));
	}

	@Test
	public void nextStateCurveRightDown() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList(">\\"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		assertThat(currentCartState.nextState(tracksMap))
				.isEqualTo(new CartState(1, 0, CartOrientation.DOWN, CartTurn.LEFT));
	}

	@Test
	public void nextStateCurveDownRight() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("v", "\\"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		assertThat(currentCartState.nextState(tracksMap))
				.isEqualTo(new CartState(0, 1, CartOrientation.RIGHT, CartTurn.LEFT));
	}

	@Test
	public void nextStateCurveDownLeft() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("v", "/"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		assertThat(currentCartState.nextState(tracksMap))
				.isEqualTo(new CartState(0, 1, CartOrientation.LEFT, CartTurn.LEFT));
	}

	@Test
	public void nextStateCurveLeftUp() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("\\<"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		assertThat(currentCartState.nextState(tracksMap))
				.isEqualTo(new CartState(0, 0, CartOrientation.UP, CartTurn.LEFT));
	}

	@Test
	public void nextStateCurveLeftDown() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("/<"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		assertThat(currentCartState.nextState(tracksMap))
				.isEqualTo(new CartState(0, 0, CartOrientation.DOWN, CartTurn.LEFT));
	}

	@Test
	public void nextStateCurveUpLeft() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("\\", "^"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		assertThat(currentCartState.nextState(tracksMap))
				.isEqualTo(new CartState(0, 0, CartOrientation.LEFT, CartTurn.LEFT));
	}

	@Test
	public void nextStateCurveUp() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("/", "^"));

		CartState currentCartState = tracksMap.getCartStates().get(0);
		assertThat(currentCartState.nextState(tracksMap))
				.isEqualTo(new CartState(0, 0, CartOrientation.RIGHT, CartTurn.LEFT));
	}

	//////////////////////////////////////////////////////

	@Test
	public void nextStateRightCrossing() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("-+"));

		assertThat(new CartState(0, 0, CartOrientation.RIGHT, CartTurn.LEFT).nextState(tracksMap)).isEqualTo(new CartState(1, 0, CartOrientation.UP, CartTurn.STRAIGHT));
		assertThat(new CartState(0, 0, CartOrientation.RIGHT, CartTurn.STRAIGHT).nextState(tracksMap)).isEqualTo(new CartState(1, 0, CartOrientation.RIGHT, CartTurn.RIGHT));
		assertThat(new CartState(0, 0, CartOrientation.RIGHT, CartTurn.RIGHT).nextState(tracksMap)).isEqualTo(new CartState(1, 0, CartOrientation.DOWN, CartTurn.LEFT));
	}
	
	@Test
	public void nextStateDownCrossing() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("|","+"));
		assertThat(new CartState(0, 0, CartOrientation.DOWN, CartTurn.LEFT).nextState(tracksMap)).isEqualTo(new CartState(0, 1, CartOrientation.RIGHT, CartTurn.STRAIGHT));
		assertThat(new CartState(0, 0, CartOrientation.DOWN, CartTurn.STRAIGHT).nextState(tracksMap)).isEqualTo(new CartState(0, 1, CartOrientation.DOWN, CartTurn.RIGHT));
		assertThat(new CartState(0, 0, CartOrientation.DOWN, CartTurn.RIGHT).nextState(tracksMap)).isEqualTo(new CartState(0, 1, CartOrientation.LEFT, CartTurn.LEFT));
	}
	
	@Test
	public void nextStateLeftCrossing() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("+-"));
		assertThat(new CartState(1, 0, CartOrientation.LEFT, CartTurn.LEFT).nextState(tracksMap)).isEqualTo(new CartState(0, 0, CartOrientation.DOWN, CartTurn.STRAIGHT));
		assertThat(new CartState(1, 0, CartOrientation.LEFT, CartTurn.STRAIGHT).nextState(tracksMap)).isEqualTo(new CartState(0, 0, CartOrientation.LEFT, CartTurn.RIGHT));
		assertThat(new CartState(1, 0, CartOrientation.LEFT, CartTurn.RIGHT).nextState(tracksMap)).isEqualTo(new CartState(0, 0, CartOrientation.UP, CartTurn.LEFT));
	}
	
	@Test
	public void nextStateUpCrossing() {
		TracksMap tracksMap = TracksMap.parse(Arrays.asList("+","|"));
		assertThat(new CartState(0, 1, CartOrientation.UP, CartTurn.LEFT).nextState(tracksMap)).isEqualTo(new CartState(0, 0, CartOrientation.LEFT, CartTurn.STRAIGHT));
		assertThat(new CartState(0, 1, CartOrientation.UP, CartTurn.STRAIGHT).nextState(tracksMap)).isEqualTo(new CartState(0, 0, CartOrientation.UP, CartTurn.RIGHT));
		assertThat(new CartState(0, 1, CartOrientation.UP, CartTurn.RIGHT).nextState(tracksMap)).isEqualTo(new CartState(0, 0, CartOrientation.RIGHT, CartTurn.LEFT));
	}
	
}
