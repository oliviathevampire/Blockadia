package team.hdt.blockadia.game_engine.common.util.interfaces;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({TYPE_USE})
public @interface Nullable {
    // marker annotation with no members
}