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

package com.admob.advancesudoku.commands;

import android.os.Parcel;
import android.os.Parcelable;

import com.admob.advancesudoku.TickTimer;
import com.admob.advancesudoku.model.ValueSet;

public class EliminateValuesCommand extends AbstractCommand {
	private static final long TIME_PENALTY_PER_ELIMINATED_VALUE = 1000;

	private ValueSet[][] originalValues;

	public EliminateValuesCommand() {
	}

	private EliminateValuesCommand(ValueSet[][] originalValues) {
		this.originalValues = originalValues;
	}

	@Override
	public void execute(AndokuContext context) {
		originalValues = saveValues(context.getPuzzle());

		int numberValuesEliminated = context.getPuzzle().eliminateValues();

		long penalty = TIME_PENALTY_PER_ELIMINATED_VALUE * numberValuesEliminated;
		TickTimer timer = context.getTimer();
		timer.setTime(timer.getTime() + penalty);
	}

	@Override
	public void undo(AndokuContext context) {
		restoreValues(context.getPuzzle(), originalValues);
	}

	@Override
	public void redo(AndokuContext context) {
		context.getPuzzle().eliminateValues();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		final int size = originalValues.length;
		int[] data = new int[size * size];
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				data[row * size + col] = originalValues[row][col].toInt();
			}
		}
		dest.writeInt(size);
		dest.writeIntArray(data);
	}

	public static final Parcelable.Creator<EliminateValuesCommand> CREATOR = new Parcelable.Creator<EliminateValuesCommand>() {
		@Override
		public EliminateValuesCommand createFromParcel(Parcel in) {
			final int size = in.readInt();
			final int[] data = in.createIntArray();
			ValueSet[][] originalValues = new ValueSet[size][size];
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {
					originalValues[row][col] = new ValueSet(data[row * size + col]);
				}
			}

			return new EliminateValuesCommand(originalValues);
		}

		@Override
		public EliminateValuesCommand[] newArray(int size) {
			return new EliminateValuesCommand[size];
		}
	};
}
