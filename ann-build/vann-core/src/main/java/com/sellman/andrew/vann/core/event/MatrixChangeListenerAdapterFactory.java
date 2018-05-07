package com.sellman.andrew.vann.core.event;

public class MatrixChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<MatrixChangeEvent, MatrixChangeListener, MatrixChangeListenerAdapter> {

	public MatrixChangeListenerAdapterFactory() {
		super(MatrixChangeEvent.class, MatrixChangeListener.class, MatrixChangeListenerAdapter.class);
	}

	@Override
	protected MatrixChangeListenerAdapter create(Listener listener) {
		return new MatrixChangeListenerAdapter(getCompatibleListener(listener));
	}

}
