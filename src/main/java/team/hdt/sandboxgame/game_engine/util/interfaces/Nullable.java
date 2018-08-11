package team.hdt.sandboxgame.game_engine.util.interfaces;


import java.lang.annotation.*;
import static java.lang.annotation.ElementType.TYPE_USE;
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ TYPE_USE })
public @interface Nullable {
    // marker annotation with no members
}