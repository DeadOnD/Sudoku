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

import android.os.Parcel;
import android.os.Parcelable;

import com.admob.advancesudoku.history.Command;
import com.admob.advancesudoku.model.Position;
import com.admob.advancesudoku.model.ValueSet;

public class SetValuesCommand extends AbstractCommand {
	private final Position position;
	private final ValueSet values;
	private ValueSet originalValues;

	public SetValuesCommand(Position position, ValueSet values) {
		this.position = position;
		this.values = values;
	}

	private SetValuesCommand(Position position, ValueSet values, ValueSet originalValues) {
		this.position = position;
		this.values = values;
		this.originalValues = originalValues;
	}

	@Override
	public void execute(AndokuContext context) {
		originalValues = context.getPuzzle().getValues(position.row, position.col);
		redo(context);
	}

	@Override
	public void undo(AndokuContext context) {
		context.getPuzzle().setValues(position.row, position.col, originalValues);
	}

	@Override
	public void redo(AndokuContext context) {
		context.getPuzzle().setValues(position.row, position.col, values);
	}

	@Override
	public Command<AndokuContext> mergeDown(Command<AndokuContext> last) {
		if (!(last instanceof SetValuesCommand))
			return null;

		SetValuesCommand other = (SetValuesCommand) last;
		if (!position.equals(other.position))
			return null;

		return new SetValuesCommand(position, values, other.originalValues);
	}

	@Override
	public boolean isEffective() {
		return !values.equals(originalValues);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(position.row);
		dest.writeInt(position.col);
		dest.writeInt(values.toInt());
		if (originalValues != null)
			dest.writeInt(originalValues.toInt());
	}

	public static final Parcelable.Creator<SetValuesCommand> CREATOR = new Parcelable.Creator<SetValuesCommand>() {
		@Override
		public SetValuesCommand createFromParcel(Parcel in) {
			int row = in.readInt();
			int col = in.readInt();
			Position position = new Position(row, col);
			ValueSet values = new ValueSet(in.readInt());
			ValueSet originalValues = in.dataAvail() > 0 ? new ValueSet(in.readInt()) : null;
			return new SetValuesCommand(position, values, originalValues);
		}

		@Override
		public SetValuesCommand[] newArray(int size) {
			return new SetValuesCommand[size];
		}
	};
}
