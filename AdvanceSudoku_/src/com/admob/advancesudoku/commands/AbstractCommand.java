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

import com.admob.advancesudoku.history.Command;
import com.admob.advancesudoku.model.AndokuPuzzle;
import com.admob.advancesudoku.model.ValueSet;

public abstract class AbstractCommand implements Command<AndokuContext> {
	protected AbstractCommand() {
	}

	@Override
	public boolean isEffective() {
		return true;
	}

	@Override
	public Command<AndokuContext> mergeDown(Command<AndokuContext> last) {
		return null;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	protected ValueSet[][] saveValues(AndokuPuzzle puzzle) {
		final int size = puzzle.getSize();
		ValueSet[][] result = new ValueSet[size][size];

		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
				result[row][col] = puzzle.getValues(row, col);

		return result;
	}

	protected void restoreValues(AndokuPuzzle puzzle, ValueSet[][] originalValues) {
		final int size = puzzle.getSize();

		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
				puzzle.setValues(row, col, originalValues[row][col]);
	}
}
