package ch.bbw.m450.tictactoe;

import static ch.bbw.m450.tictactoe.BoardFixture.toBoard;
import static ch.bbw.m450.tictactoe.TicTacToeMain.isWin;

import ch.bbw.m450.tictactoe.TicTacToePlayer.Stone;
import ch.bbw.m450.tictactoe.players.GreedyPlayer;
import java.util.stream.Stream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test-suite for the tic-tac-toe engine. Uses AssertJ via the {@link WithAssertions}
 * entry-point so the IDE offers {@code assertThat(...)} completions out of the box.
 * Board patterns live in {@link BoardFixture}, shared player instances in {@link #setUpPlayers()}.
 */
class TicTacToeMainTest implements WithAssertions {

	private GreedyPlayer xPlayer;
	private GreedyPlayer oPlayer;

	@BeforeEach
	void setUpPlayers() {
		xPlayer = new GreedyPlayer();
		oPlayer = new GreedyPlayer();
	}

	@ParameterizedTest(name = "{0} on \"{1}\" -> {2}")
	@MethodSource("winPatterns")
	void winDetection(Stone color, String pattern, boolean expectedToWin) {
		assertThat(isWin(toBoard(pattern), color)).isEqualTo(expectedToWin);
	}

	static Stream<Arguments> winPatterns() {
		return Stream.of(
				// each of the 8 winning lines, checked for the color that actually completes it
				Arguments.of(Stone.CROSS, BoardFixture.TOP_ROW_X_WINS, true),
				Arguments.of(Stone.CIRCLE, BoardFixture.MID_ROW_O_WINS, true),
				Arguments.of(Stone.CROSS, BoardFixture.BOTTOM_ROW_X_WINS, true),
				Arguments.of(Stone.CIRCLE, BoardFixture.LEFT_COL_O_WINS, true),
				Arguments.of(Stone.CROSS, BoardFixture.MID_COL_X_WINS, true),
				Arguments.of(Stone.CIRCLE, BoardFixture.RIGHT_COL_O_WINS, true),
				Arguments.of(Stone.CROSS, BoardFixture.DIAGONAL_X_WINS, true),
				Arguments.of(Stone.CIRCLE, BoardFixture.ANTI_DIAGONAL_O_WINS, true),
				// non-winning constellations
				Arguments.of(Stone.CROSS, BoardFixture.EMPTY_BOARD, false),
				Arguments.of(Stone.CROSS, BoardFixture.DRAW_BOARD, false),
				Arguments.of(Stone.CIRCLE, BoardFixture.DRAW_BOARD, false),
				// a row only wins for its own color
				Arguments.of(Stone.CROSS, BoardFixture.TOP_ROW_O_WINS, false));
	}

	/**
	 * The same as the diagonal case in {@link #winDetection}, written out in the given-when-then
	 * style. Readable, but the parameterized test above usually says the same thing with less
	 * ceremony.
	 */
	@Test
	void givenADiagonal_whenCheckingX_thenItWins() {
		// given
		var boardWithDiagonal = toBoard(BoardFixture.DIAGONAL_X_WINS);
		// when
		var winning = isWin(boardWithDiagonal, Stone.CROSS);
		// then
		assertThat(winning).isTrue();
	}

	@Test
	void twoGreedyPlayersLetTheStartingPlayerWin() {
		// both always fill the top-most-left free field, which lets X complete the 0-4-8 diagonal
		assertThat(TicTacToeMain.play(xPlayer, oPlayer)).isEqualTo(Stone.CROSS);
	}

	@Test
	void theSamePlayerInstanceTwiceIsRejected() {
		assertThatThrownBy(() -> TicTacToeMain.play(xPlayer, xPlayer))
				.isInstanceOf(IllegalArgumentException.class);
	}
}
