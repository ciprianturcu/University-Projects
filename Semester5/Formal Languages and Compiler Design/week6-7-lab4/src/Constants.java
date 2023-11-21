import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Constants {
    public static final ArrayList<Character> IDENTIFIER_ALLOWED_CHARACTERS = new ArrayList<>(List.of(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_'
    ));
    public static final ArrayList<Character> INTCONST_ALLOWED_CHARACTERS = new ArrayList<>(List.of(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    ));
}
