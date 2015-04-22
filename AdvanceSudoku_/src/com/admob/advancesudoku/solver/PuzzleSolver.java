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
 * Can solve sudoku puzzles and notify a puzzle reporter of its solution(s).
 */
public interface PuzzleSolver {
	/**
	 * Solves the specified puzzle and passes solutions to the specified puzzle reporter.
	 * 
	 * @param puzzle puzzle to solve.
	 * @param reporter will be notified of solutions and decides if more solutions should be searched
	 *           for.
	 */
	void solve(Puzzle puzzle, PuzzleReporter reporter);
}
