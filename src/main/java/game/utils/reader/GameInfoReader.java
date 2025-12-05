package game.utils.reader;

import java.io.IOException;

public interface GameInfoReader {

    GameInfoReadResponse readGameInfo(String [] selectGhosts, int lives);
}
