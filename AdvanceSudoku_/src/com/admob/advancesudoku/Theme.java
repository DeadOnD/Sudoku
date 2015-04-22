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

import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.admob.advancesudoku.model.PuzzleType;

interface Theme {
	char getSymbol(int value);

	int[] getPuzzlePadding();

	Drawable getBackground();

	int getPuzzleBackgroundColor();

	int getNameTextColor();
	int getDifficultyTextColor();
	int getSourceTextColor();
	int getTimerTextColor();

	Paint getGridPaint();
	Paint getRegionBorderPaint();
	Paint getExtraRegionPaint(PuzzleType puzzleType, int extraRegionCode);
	Paint getValuePaint();
	Paint getDigitPaint();
	Paint getCluePaint(boolean preview);
	Paint getErrorPaint();
	Paint getMarkedPositionPaint();
	Paint getMarkedPositionCluePaint();
	Paint getOuterBorderPaint();

	float getOuterBorderRadius();

	boolean isDrawAreaColors(PuzzleType puzzleType);
	int getAreaColor(int colorNumber, int numberOfColors);

	HighlightDigitsPolicy getHighlightDigitsPolicy();
	int getHighlightedCellColorSingleDigit();
	int getHighlightedCellColorMultipleDigits();

	Drawable getCongratsDrawable();
	Drawable getPausedDrawable();
}
