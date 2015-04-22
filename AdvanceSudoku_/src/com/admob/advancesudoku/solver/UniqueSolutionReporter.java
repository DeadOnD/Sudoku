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


package com.admob.advancesudoku.solver;

import com.admob.advancesudoku.model.Puzzle;

/**
 * Puzzle reporter that can be used to determine if a puzzle has a unique solution. Also stores that
 * solution.
 */
public class UniqueSolutionReporter implements PuzzleReporter {
	private Puzzle solution;
	private int solutions = 0;

	@Override
	public boolean report(Puzzle solution) {
		this.solution = new Puzzle(solution);
		return ++solutions == 1;
	}

	public Puzzle getSolution() {
		return solution;
	}

	public boolean hasSolution() {
		return solutions > 0;
	}

	public boolean hasUniqueSolution() {
		return solutions == 1;
	}

	public boolean hasMultipleSolutions() {
		return solutions > 1;
	}
}
