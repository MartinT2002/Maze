package MazeApp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private static boolean isReallyHeadless() {
        if (GraphicsEnvironment.isHeadless()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Test
    @DisabledIf("isReallyHeadless")
    void main() {
        App app;
        app = new App();
        String[] sA = {};
        app.main(sA);
        app.ViewIndex();
        app.CreateNew();

        assertNotNull(app.mazeData);

        app = null;
    }

}