package ch.bbw.m450.tictactoe;

import ch.bbw.m450.tictactoe.TicTacToePlayer.Stone;

/**
 * Test-only helper and board fixtures for {@link TicTacToeMainTest}.
 * Turns compact patterns like {@code "XOO OX. XOX"} into a board so tests stay readable.
 * {@code X} = cross, {@code O} = circle, {@code .} = empty; spaces separate rows and are ignored.
 */
final class BoardFixture {

	static final String TOP_ROW_X_WINS = "XXX ... ...";
	static final String MID_ROW_O_WINS = "... OOO ...";
	static final String BOTTOM_ROW_X_WINS = "... ... XXX";
	static final String LEFT_COL_O_WINS = "O.. O.. O..";
	static final String MID_COL_X_WINS = ".X. .X. .X.";
	static final String RIGHT_COL_O_WINS = "..O ..O ..O";
	static final String DIAGONAL_X_WINS = "XOO OX. XOX";
	static final String ANTI_DIAGONAL_O_WINS = "..O .O. O..";
	static final String TOP_ROW_O_WINS = "OOO XX. .X.";
	static final String DRAW_BOARD = "XOX XXO OXO";
	static final String EMPTY_BOARD = "... ... ...";

	private BoardFixture() {
	}

	static Stone[] toBoard(String pattern) {
		var fields = pattern.replace(" ", "");
		if (fields.length() != TicTacToeMain.BOARD_SIZE) {
			throw new IllegalArgumentException("a board needs exactly 9 fields, but got: " + fields);
		}
		var board = new Stone[TicTacToeMain.BOARD_SIZE];
		for (var i = 0; i < board.length; i++) {
			board[i] = switch (fields.charAt(i)) {
				case 'X' -> Stone.CROSS;
				case 'O' -> Stone.CIRCLE;
				case '.' -> null;
				default -> throw new IllegalArgumentException("unexpected field: " + fields.charAt(i));
			};
		}
		return board;
	}
}
