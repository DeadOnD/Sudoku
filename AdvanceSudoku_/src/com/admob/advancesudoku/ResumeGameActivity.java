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

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.admob.advancesudoku.db.PuzzleId;
import com.admob.advancesudoku.db.SudokuDatabase;
import com.admob.advancesudoku.model.PuzzleType;
import com.admob.advancesudoku.source.PuzzleSourceIds;

public class ResumeGameActivity extends ListActivity {
	private static final String TAG = ResumeGameActivity.class.getName();

	private SudokuDatabase db;

	private long baseTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Constants.LOG_V)
			Log.v(TAG, "onCreate(" + savedInstanceState + ")");

		super.onCreate(savedInstanceState);

		setContentView(R.layout.resume_game);

		Util.setupAdNetwork(this);

		db = new SudokuDatabase(this);

		Cursor cursor = db.findGamesInProgress();
		startManagingCursor(cursor);

		String[] from = { SudokuDatabase.COL_TYPE, SudokuDatabase.COL_SOURCE,
				SudokuDatabase.COL_TYPE, SudokuDatabase.COL_TIMER, SudokuDatabase.COL_MODIFIED_DATE };
		int[] to = { R.id.save_game_icon, R.id.save_game_difficulty, R.id.save_game_title,
				R.id.save_game_timer, R.id.save_game_modified };
		SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.save_game_list_item,
				cursor, from, to);
		listAdapter.setViewBinder(new SaveGameViewBinder(getResources()));
		setListAdapter(listAdapter);

		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onResumeGameItem(id);
			}
		});

		View backButton = findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		if (Constants.LOG_V)
			Log.v(TAG, "onResume()");

		super.onResume();

		baseTime = System.currentTimeMillis();

		final boolean hasSavedGames = getListAdapter().getCount() != 0;
		if (!hasSavedGames)
			finish();
	}

	@Override
	protected void onDestroy() {
		if (Constants.LOG_V)
			Log.v(TAG, "onDestroy()");

		super.onDestroy();

		if (db != null) {
			db.close();
		}
	}

	void onResumeGameItem(long rowId) {
		if (Constants.LOG_V)
			Log.v(TAG, "onResumeGameItem(" + rowId + ")");

		PuzzleId puzzleId = db.puzzleIdByRowId(rowId);

		Intent intent = new Intent(this, SudokuActivity.class);
		intent.putExtra(Constants.EXTRA_PUZZLE_SOURCE_ID, puzzleId.puzzleSourceId);
		intent.putExtra(Constants.EXTRA_PUZZLE_NUMBER, puzzleId.number);
		intent.putExtra(Constants.EXTRA_START_PUZZLE, true);
		startActivity(intent);
	}

	private final class SaveGameViewBinder implements SimpleCursorAdapter.ViewBinder {
		private static final int IDX_SOURCE = SudokuDatabase.IDX_GAME_SOURCE;
		private static final int IDX_NUMBER = SudokuDatabase.IDX_GAME_NUMBER;
		private static final int IDX_TYPE = SudokuDatabase.IDX_GAME_TYPE;
		private static final int IDX_TIMER = SudokuDatabase.IDX_GAME_TIMER;
		private static final int IDX_DATE_MODIFIED = SudokuDatabase.IDX_GAME_MODIFIED_DATE;

		public SaveGameViewBinder(Resources resources) {
		}

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if (view instanceof ImageView) {
				assert columnIndex == IDX_TYPE;
				PuzzleType puzzleType = PuzzleType.forOrdinal(cursor.getInt(IDX_TYPE));
				Drawable drawable = Util.getPuzzleIcon(getResources(), puzzleType);
				((ImageView) view).setImageDrawable(drawable);
				return true;
			}

			if (!(view instanceof TextView))
				return false;

			TextView textView = (TextView) view;

			switch (columnIndex) {
				case IDX_TYPE:
					PuzzleType puzzleType = PuzzleType.forOrdinal(cursor.getInt(columnIndex));
					String name = Util.getPuzzleName(getResources(), puzzleType);
					textView.setText(name);
					return true;

				case IDX_TIMER:
					String time = DateUtil.formatTime(cursor.getLong(columnIndex));
					textView.setText(time);
					return true;

				case IDX_DATE_MODIFIED:
					String age = DateUtil.formatTimeSpan(getResources(), baseTime,
							cursor.getLong(columnIndex));
					textView.setText(age);
					return true;

				case IDX_SOURCE:
					String difficultyAndNumber = parseDifficultyAndNumber(cursor.getString(IDX_SOURCE),
							cursor.getInt(IDX_NUMBER));
					textView.setText(difficultyAndNumber);
					return true;
			}

			return false;
		}

		private String parseDifficultyAndNumber(String sourceId, int number) {
			if (PuzzleSourceIds.isDbSource(sourceId)) {
				String folderName = Util.getFolderName(db, sourceId);
				return folderName + " #" + (number + 1);
			}
			else {
				String[] difficulties = getResources().getStringArray(R.array.difficulties);
				int difficulty = sourceId.charAt(sourceId.length() - 1) - '0' - 1;
				return difficulties[difficulty] + " #" + (number + 1);
			}
		}
	}
}
