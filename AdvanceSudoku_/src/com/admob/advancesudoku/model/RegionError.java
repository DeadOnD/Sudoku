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


package com.admob.advancesudoku.model;

public class RegionError {
	public final Position p1;
	public final Position p2;

	public RegionError(Position p1, Position p2) {
		boolean swap = p1.compareTo(p2) > 0;
		this.p1 = swap ? p2 : p1;
		this.p2 = swap ? p1 : p2;
	}

	@Override
	public int hashCode() {
		return p1.hashCode() * 9901 + p2.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof RegionError))
			return false;

		RegionError other = (RegionError) obj;
		return p1.equals(other.p1) && p2.equals(other.p2);
	}

	@Override
	public String toString() {
		return p1 + "-" + p2;
	}
}
