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


package com.admob.advancesudoku.db;

public class GameStatistics {
	public final int numGamesSolved;
	public final long sumTime;
	public final long minTime;

	public GameStatistics(int numGamesSolved, long sumTime, long minTime) {
		this.numGamesSolved = numGamesSolved;
		this.sumTime = sumTime;
		this.minTime = minTime;
	}

	public long getAverageTime() {
		return numGamesSolved == 0 ? 0 : sumTime / numGamesSolved;
	}
}
