import java.awt.*;

public interface BoardTheme
{
    enum ShapeType
    {
        SQUARE,
        CIRCLE
    }

    Color getBoardColor();

    Color getMancalaColor();

    Color getPitColor();

    ShapeType getPitShape();

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
        public ShapeType getPitShape()
        {
            return ShapeType.CIRCLE;
        }
    };

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
        public ShapeType getPitShape()
        {
            return ShapeType.SQUARE;
        }
    };
}
