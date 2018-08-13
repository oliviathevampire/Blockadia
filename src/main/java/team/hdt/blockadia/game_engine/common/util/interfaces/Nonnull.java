package team.hdt.blockadia.game_engine.common.util.interfaces;

import java.lang.annotation.*;

/**
 * If put on a parameter, lombok will insert a null-check at the start of the method / constructor's body, throwing a
 * {@code NullPointerException} with the parameter's name as message. If put on a field, any generated method assigning
 * a value to this field will also produce these nullchecks.
 * <p>
 * Note that any annotation named {@code NonNull} with any casing and any package will result in nullchecks produced for
 * generated methods (and the annotation will be copied to the getter return type and any parameters of generated methods),
 * but <em>only</em> this annotation, if present on a parameter, will result in a null check inserted into your otherwise
 * handwritten method.
 * <p>
 * WARNING: If the java community ever does decide on supporting a single {@code @NonNull} annotation (for example via JSR305), then
 * this annotation will <strong>be deleted</strong> from the lombok package. If the need to update an import statement scares
 * you, you should use your own annotation named {@code @NonNull} instead of this one.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Nonnull {
}
