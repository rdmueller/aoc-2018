import java.util.Objects;

public class Worker {
	
	private static final int OVERHEAD = 60;
	
	private Character processingStep = null;
	private int busyUntil = -1;
	
	public void startProcessingStep(Character step, int time) {
		Objects.requireNonNull(step, "step must not be null");
		
		if (!isAvailable()) {
			throw new IllegalStateException();
		}
		
		processingStep = step;
		busyUntil = time + stepDuration(step);
	}
	
	public Character tick(int time) {
		if (time >= busyUntil) {
			final Character processedStep = processingStep;
			this.processingStep = null;
			this.busyUntil = -1;
			return processedStep;
		}
		else {
			return null;
		}
	}

	public boolean isAvailable() {
		return processingStep == null;
	}
	

	private static int stepDuration(Character step) {
		return step.charValue() - 'A' + 1 + OVERHEAD;
	}
	
	public Character getProcessingStep() {
		return processingStep == null ? '.' : processingStep;
	}

}
