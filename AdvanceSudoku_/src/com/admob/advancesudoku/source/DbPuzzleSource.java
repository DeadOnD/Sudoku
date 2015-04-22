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

import com.admob.advancesudoku.db.PuzzleInfo;
import com.admob.advancesudoku.db.SudokuDatabase;
import com.admob.advancesudoku.model.Difficulty;
import com.admob.advancesudoku.model.Puzzle;
import com.admob.advancesudoku.transfer.PuzzleDecoder;

class DbPuzzleSource implements PuzzleSource {
	private final SudokuDatabase db;
	private final long folderId;

	private int numberOfPuzzles = -1;

	public DbPuzzleSource(SudokuDatabase db, long folderId) {
		this.db = db;
		this.folderId = folderId;
	}

	@Override
	public String getSourceId() {
		return PuzzleSourceIds.forDbFolder(folderId);
	}

	@Override
	public PuzzleHolder load(int number) throws PuzzleIOException {
		PuzzleInfo puzzleInfo = db.loadPuzzle(folderId, number);
		if (puzzleInfo == null)
			throw new PuzzleIOException("Puzzle " + number + " not found in folder " + folderId);

		String name = puzzleInfo.getName();
		Puzzle puzzle = createPuzzle(puzzleInfo);
		Difficulty difficulty = puzzleInfo.getDifficulty();

		return new PuzzleHolder(this, number, name, puzzle, difficulty);
	}

	@Override
	public int numberOfPuzzles() {
		if (numberOfPuzzles == -1)
			numberOfPuzzles = db.getNumberOfPuzzles(folderId);

		return numberOfPuzzles;
	}

	@Override
	public void close() {
		db.close();
	}

	private Puzzle createPuzzle(PuzzleInfo puzzleInfo) {
		return PuzzleDecoder.decode(puzzleInfo.getClues() + "|" + puzzleInfo.getAreas() + "|"
				+ puzzleInfo.getExtraRegions());
	}
}
