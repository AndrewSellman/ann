package com.sellman.andrew.vann.core.event;

public class MatrixPollListenerAdapterFactory extends AbstractEventListenerAdapterFactory<MatrixPollEvent, MatrixPollListener, MatrixPollListenerAdapter> {

	public MatrixPollListenerAdapterFactory() {
		super(MatrixPollEvent.class, MatrixPollListener.class, MatrixPollListenerAdapter.class);
	}

	@Override
	protected MatrixPollListenerAdapter create(Listener listener) {
		return new MatrixPollListenerAdapter(getCompatibleListener(listener));
	}

}
