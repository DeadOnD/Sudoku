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

package com.admob.advancesudoku;

import java.util.EnumSet;

import com.admob.advancesudoku.model.PuzzleType;

public enum AreaColorPolicy {
	NEVER(EnumSet.noneOf(PuzzleType.class)), //
	STANDARD_SQUIGGLY(EnumSet.of(PuzzleType.STANDARD, PuzzleType.SQUIGGLY)), //
	// name does not include PERCENT but cannot be changed because it is used in existing user preferences
	STANDARD_X_HYPER_SQUIGGLY(EnumSet.of(PuzzleType.STANDARD, PuzzleType.STANDARD_X,
			PuzzleType.STANDARD_HYPER, PuzzleType.STANDARD_PERCENT, PuzzleType.SQUIGGLY)), //
	// all except color sudoku
	ALWAYS(EnumSet.of(PuzzleType.STANDARD, PuzzleType.STANDARD_X, PuzzleType.STANDARD_HYPER,
			PuzzleType.STANDARD_PERCENT, PuzzleType.SQUIGGLY, PuzzleType.SQUIGGLY_X,
			PuzzleType.SQUIGGLY_HYPER, PuzzleType.SQUIGGLY_PERCENT));

	private final EnumSet<PuzzleType> puzzleTypes;

	AreaColorPolicy(EnumSet<PuzzleType> puzzleTypes) {
		this.puzzleTypes = puzzleTypes;
	}

	public boolean matches(PuzzleType puzzleType) {
		return puzzleTypes.contains(puzzleType);
	}
}
