/*
 * Advance Sudoku - a sudoku puzzle game for Android.
 * Copyright 2015 Joachim Thilo
 *
 * This file is part of Andoku.
 *
 * Advance Sudoku is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Advance Sudoku is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Advance Sudoku .  If not, see <http://www.gnu.org/licenses/>.
 */


package com.admob.advancesudoku.im;

import android.os.Bundle;

import com.admob.advancesudoku.model.Position;

public class AutomaticInputMethod implements InputMethod {
	private static final String APP_STATE_ACTIVE_INPUT_METHOD = "automaticInputMethod";
	private static final String IM_UNDECIDED = "undecided";
	private static final String IM_CELL_THEN_VALUES = "cellThenValues";
	private static final String IM_VALUES_THEN_CELL = "valuesThenCell";

	private final InputMethodTarget target;
	private final CellThenValuesInputMethod cellThenValues;
	private final ValuesThenCellInputMethod valuesThenCell;
	private InputMethod activeInputMethod = null;
	private Position lastMarkedPosition;

	public AutomaticInputMethod(InputMethodTarget target) {
		this.target = target;
		cellThenValues = new CellThenValuesInputMethod(target);
		valuesThenCell = new ValuesThenCellInputMethod(target);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (activeInputMethod == null) {
			outState.putString(APP_STATE_ACTIVE_INPUT_METHOD, IM_UNDECIDED);
		}
		else if (activeInputMethod == cellThenValues) {
			outState.putString(APP_STATE_ACTIVE_INPUT_METHOD, IM_CELL_THEN_VALUES);
			cellThenValues.onSaveInstanceState(outState);
		}
		else {
			outState.putString(APP_STATE_ACTIVE_INPUT_METHOD, IM_VALUES_THEN_CELL);
			valuesThenCell.onSaveInstanceState(outState);
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		String inputMethod = savedInstanceState.getString(APP_STATE_ACTIVE_INPUT_METHOD);
		if (inputMethod == null || inputMethod.equals(IM_UNDECIDED)) {
			setUndecided();
		}
		else if (inputMethod.equals(IM_CELL_THEN_VALUES)) {
			activeInputMethod = cellThenValues;
			cellThenValues.onRestoreInstanceState(savedInstanceState);
		}
		else {
			activeInputMethod = valuesThenCell;
			valuesThenCell.onRestoreInstanceState(savedInstanceState);
		}
	}

	@Override
	public void reset() {
		cellThenValues.reset();
		valuesThenCell.reset();
		setUndecided();
	}

	@Override
	public void onMoveMark(int dy, int dx) {
		ifUndecidedUseCellThenValues();

		activeInputMethod.onMoveMark(dy, dx);
	}

	@Override
	public void onKeypad(int digit) {
		ifUndecidedUseValuesThenCells();

		activeInputMethod.onKeypad(digit);

		if (activeInputMethod == valuesThenCell && valuesThenCell.isValuesEmpty()) {
			setUndecided();
		}
	}

	@Override
	public void onClear() {
		if (activeInputMethod != null)
			activeInputMethod.onClear();

		if (activeInputMethod == valuesThenCell)
			setUndecided();
	}

	@Override
	public void onInvert() {
		if (target.getMarkedPosition() == null)
			ifUndecidedUseValuesThenCells();
		else
			ifUndecidedUseCellThenValues();

		activeInputMethod.onInvert();

		if (activeInputMethod == valuesThenCell && valuesThenCell.isValuesEmpty()) {
			setUndecided();
		}
	}

	@Override
	public void onSweep() {
		if (target.getMarkedPosition() != null) {
			lastMarkedPosition = target.getMarkedPosition();
		}

		if (activeInputMethod != null) {
			activeInputMethod.onSweep();
		}
	}

	@Override
	public void onTap(Position position, boolean editable) {
		if ((activeInputMethod == null || activeInputMethod == cellThenValues)
				&& (position == null || position.equals(lastMarkedPosition))) {
			setUndecided();
		}
		else {
			ifUndecidedUseCellThenValues();

			activeInputMethod.onTap(position, editable);
		}
	}

	@Override
	public void onValuesChanged() {
		if (activeInputMethod != null)
			activeInputMethod.onValuesChanged();
	}

	private void setUndecided() {
		activeInputMethod = null;
		target.setMarkedPosition(null);
		target.highlightDigit(null);
		lastMarkedPosition = null;
	}

	private void ifUndecidedUseValuesThenCells() {
		if (activeInputMethod == null) {
			activeInputMethod = valuesThenCell;
		}
	}

	private void ifUndecidedUseCellThenValues() {
		if (activeInputMethod == null) {
			activeInputMethod = cellThenValues;
		}
	}
}
