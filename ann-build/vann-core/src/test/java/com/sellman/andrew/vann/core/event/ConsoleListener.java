package com.sellman.andrew.vann.core.event;

public class ConsoleListener implements BatchChangeListener, BatchErrorChangeListener, EpochChangeListener, EpochErrorChangeListener, MatrixChangeListener, MatrixPollListener,
		NetworkInputListener, NetworkOutputListener, ResetBatchErrorListener, ValidationErrorChangeListener, VectorChangeListener, VectorPollListener {

	@Override
	public void onEvent(BatchErrorChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(VectorPollEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(VectorChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(ValidationErrorChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(ResetBatchErrorEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(NetworkOutputEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(NetworkInputEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(MatrixPollEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(MatrixChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(EpochErrorChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(EpochChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(BatchChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	private void onEvent(Event event) {
		System.out.println(event);
	}

}
