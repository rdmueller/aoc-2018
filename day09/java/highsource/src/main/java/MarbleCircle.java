public class MarbleCircle {

	private static final int SPECIAL_MARBLE_VALUE_DIVISOR = 23;

	private Slot firstSlot;
	private Slot currentSlot;

	private class Slot {
		private int value;
		private Slot previous;
		private Slot next;
	}

	public MarbleCircle() {
		final Slot slot = new Slot();
		slot.value = 0;
		slot.next = slot;
		slot.previous = slot;
		firstSlot = slot;
		currentSlot = slot;
	}

	public long place(int marble) {
		if (marble % SPECIAL_MARBLE_VALUE_DIVISOR == 0) {

			final Slot marble_7 = this.currentSlot.previous.previous.previous.previous.previous.previous.previous;

			final Slot previousSlot = marble_7.previous;
			final Slot nextSlot = marble_7.next;

			previousSlot.next = nextSlot;
			nextSlot.previous = previousSlot;
			this.currentSlot = nextSlot;

			return marble + marble_7.value;
		} else {

			final Slot newSlot = new Slot();
			newSlot.value = marble;

			final Slot previousSlot = this.currentSlot.next;
			final Slot nextSlot = previousSlot.next;

			previousSlot.next = newSlot;
			nextSlot.previous = newSlot;
			newSlot.previous = previousSlot;
			newSlot.next = nextSlot;

			currentSlot = newSlot;

			return 0;
		}
	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		Slot slot = firstSlot;

		do {
			sb.append(' ');
			if (slot == currentSlot) {
				sb.append('(');
			}
			sb.append(slot.value);
			if (slot == currentSlot) {
				sb.append(')');
			}
			slot = slot.next;

		} while (slot != firstSlot);
		return sb.toString();
	}
}
