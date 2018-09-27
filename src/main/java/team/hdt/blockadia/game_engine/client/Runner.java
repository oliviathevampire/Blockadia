package team.hdt.blockadia.game_engine.client;

import team.hdt.blockadia.game_engine.core.util.Game;
import team.hdt.blockadia.game_engine.core.util.Input;
import team.hdt.blockadia.game_engine.core.util.Window;

public class Runner {
    private Runner() {
    }

    public static void main(String[] args) {
        try {
            new Runner().run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() {
        try {
            init();
            loop();
            destroy();
        } finally {
            terminate();
        }
    }

    private void init() {
//        Configurator.init("config.json");
        Window.init();
        Input.init();
        Game.init();
    }

    private void loop() {
        while (!Window.shouldClose()) {
            Window.setTitle();
            Window.beforeRender();
            if (Window.was_resize) {
                Window.was_resize = false;
                Window.setupAspectRatio();
            }
            Game.before_render();
            Game.render();
            Game.after_render();
            Window.afterRender();
        }
    }

    private void destroy() {
        Game.destroy();
        Window.destroy();
    }

    private void terminate() {
        Window.terminate();
    }
}