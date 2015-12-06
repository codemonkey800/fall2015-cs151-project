import java.awt.*;

/**
 * An interface representing a board theme. The interface two base themes: Theme 1 and Theme 2
 * as constant fields as a part of the interface.
 */
public interface BoardTheme
{
    /**
     * Valid shapes for drawing pits.
     */
    enum ShapeType
    {
        SQUARE,
        CIRCLE
    }

    /**
     * Returns the color of the board.
     *
     * @return The color
     */
    Color getBoardColor();

    /**
     * Returns the color of the mancala.
     *
     * @return The color
     */
    Color getMancalaColor();

    /**
     * Returns the color of the pit.
     *
     * @return The color
     */
    Color getPitColor();

    /**
     * Returns the color of the stone count text.
     *
     * @return The color
     */
    Color getStoneCountColor();

    /**
     * Returns the shape of the pit.
     *
     * @return The shape
     */
    ShapeType getPitShape();

    /**
     * The first base theme.
     */
    BoardTheme THEME_1 = new BoardTheme()
    {
        @Override
        public Color getBoardColor()
        {
            return new Color(0x663300);
        }

        @Override
        public Color getMancalaColor()
        {
            return new Color(0xAD9670);
        }

        @Override
        public Color getPitColor()
        {
            return new Color(0xAD716C);
        }

        @Override
        public Color getStoneCountColor()
        {
            return Color.black;
        }

        @Override
        public ShapeType getPitShape()
        {
            return ShapeType.CIRCLE;
        }
    };

    /**
     * The second base theme.
     */
    BoardTheme THEME_2 = new BoardTheme()
    {
        @Override
        public Color getBoardColor()
        {
            return Color.black;
        }

        @Override
        public Color getMancalaColor()
        {
            return new Color(0x9948FF);
        }

        @Override
        public Color getPitColor()
        {
            return Color.white;
        }

        @Override
        public Color getStoneCountColor()
        {
            return Color.red;
        }

        @Override
        public ShapeType getPitShape()
        {
            return ShapeType.SQUARE;
        }
    };
}
