package team.hdt.blockadia.test.game;

import team.hdt.blockadia.test.engine.MouseInput;
import team.hdt.blockadia.test.engine.Window;

public interface IGameLogic {

    void init(Window window) throws Exception;

    void input(Window window, MouseInput mouseInput);

    void update(float interval, MouseInput mouseInput, Window window);

    void render(Window window);

    void cleanup();
}