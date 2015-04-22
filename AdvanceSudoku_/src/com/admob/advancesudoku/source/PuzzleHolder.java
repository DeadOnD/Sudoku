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


package com.admob.advancesudoku.source;

import com.admob.advancesudoku.db.PuzzleId;
import com.admob.advancesudoku.model.Difficulty;
import com.admob.advancesudoku.model.Puzzle;

public class PuzzleHolder {
	private final PuzzleSource source;
	private final int number;

	private final String name;
	private final Puzzle puzzle;
	private final Difficulty difficulty;

	public PuzzleHolder(PuzzleSource source, int number, String name, Puzzle puzzle,
			Difficulty difficulty) throws PuzzleIOException {
		if (source == null)
			throw new IllegalArgumentException();
		if (puzzle == null)
			throw new IllegalArgumentException();
		if (difficulty == null)
			throw new IllegalArgumentException();

		this.source = source;
		this.number = number;
		this.name = name;
		this.puzzle = puzzle;
		this.difficulty = difficulty;
	}

	public PuzzleSource getSource() {
		return source;
	}

	public int getNumber() {
		return number;
	}

	public PuzzleId getPuzzleId() {
		return new PuzzleId(source.getSourceId(), number);
	}

	public String getName() {
		return name;
	}

	public Puzzle getPuzzle() {
		return puzzle;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}
}
