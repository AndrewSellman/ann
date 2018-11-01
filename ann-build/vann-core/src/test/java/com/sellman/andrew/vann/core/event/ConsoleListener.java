package com.sellman.andrew.vann.core.event;

public class ConsoleListener implements BatchIndexChangeListener, BatchErrorChangeListener, EpochIndexChangeListener, EpochErrorChangeListener, MatrixChangeListener, MatrixPollListener,
		NetworkInputListener, NetworkOutputListener, ResetBatchErrorListener, ValidationErrorChangeListener, ColumnVectorChangeListener, ColumnVectorPollListener, RowVectorChangeListener,
		RowVectorPollListener, LearningRateChangeListener, MomentumChangeListener {

	@Override
	public void onEvent(BatchErrorChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(ColumnVectorPollEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(ColumnVectorChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(RowVectorPollEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(RowVectorChangeEvent event) {
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
	public void onEvent(EpochIndexChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(BatchIndexChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(LearningRateChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	@Override
	public void onEvent(MomentumChangeEvent event) {
		onEvent(Event.class.cast(event));
	}

	private void onEvent(Event event) {
		System.out.println(event);
	}

}
