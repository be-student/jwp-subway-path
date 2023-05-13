package subway.domain.line;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import subway.exception.line.LineNameException;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("노선 이름은")
class LineNameTest {

    @ParameterizedTest(name = "입력값: {0}")
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void 공백이_아니어야_한다(final String input) {
        assertThatCode(() -> new LineName(input))
            .isInstanceOf(LineNameException.class)
            .hasMessage("노선 이름이 공백입니다. 글자를 입력해주세요");
    }

    @Test
    void 최대_글자를_초과하면_안된다() {
        final String input = "a".repeat(256);

        assertThatCode(() -> new LineName(input))
            .isInstanceOf(LineNameException.class)
            .hasMessage("노선 이름이 255글자를 초과했습니다");
    }
}
