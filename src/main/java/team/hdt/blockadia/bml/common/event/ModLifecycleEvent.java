package team.hdt.blockadia.bml.common.event;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModContainer;

/**
 * Parent type to all ModLifecycle events. This is based on Forge EventBus. They fire through the
 * ModContainer's eventbus instance.
 */
public class ModLifecycleEvent extends Event {

    private final ModContainer container;

    public ModLifecycleEvent(ModContainer container) {
        this.container = container;
    }

    public final String description() {
       String cn = getClass().getName();
       return cn.substring(cn.lastIndexOf('.')+4,cn.length()-5);
    }

}