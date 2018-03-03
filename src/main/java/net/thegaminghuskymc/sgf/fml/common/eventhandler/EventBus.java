package net.thegaminghuskymc.sgf.fml.common.eventhandler;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import net.thegaminghuskymc.sgf.fml.common.FMLLog;
import net.thegaminghuskymc.sgf.fml.common.Loader;
import net.thegaminghuskymc.sgf.fml.common.ModContainer;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EventBus implements IEventExceptionHandler
{
    private static int maxID = 0;

    private ConcurrentHashMap<Object, ArrayList<IEventListener>> listeners = new ConcurrentHashMap<Object, ArrayList<IEventListener>>();
    private Map<Object,ModContainer> listenerOwners = new MapMaker().weakKeys().weakValues().makeMap();
    private final int busID = maxID++;
    private IEventExceptionHandler exceptionHandler;

    public EventBus()
    {
        ListenerList.resize(busID + 1);
        exceptionHandler = this;
    }

    public EventBus(@Nonnull IEventExceptionHandler handler)
    {
        this();
        Preconditions.checkNotNull(handler, "EventBus exception handler can not be null");
        exceptionHandler = handler;
    }

    public void register(Object target)
    {
        if (listeners.containsKey(target))
        {
            return;
        }

        ModContainer activeModContainer = Loader.instance().activeModContainer();
        if (activeModContainer == null)
        {
            FMLLog.log.error("Unable to determine registrant mod for {}. This is a critical error and should be impossible", target, new Throwable());
            activeModContainer = Loader.instance().getMinecraftModContainer();
        }
        listenerOwners.put(target, activeModContainer);
        boolean isStatic = target.getClass() == Class.class;
        @SuppressWarnings("unchecked")
        Set<? extends Class<?>> supers = isStatic ? Sets.newHashSet((Class<?>)target) : TypeToken.of(target.getClass()).getTypes().rawTypes();
        for (Method method : (isStatic ? (Class<?>)target : target.getClass()).getMethods())
        {
            if (isStatic && !Modifier.isStatic(method.getModifiers()))
                continue;
            else if (!isStatic && Modifier.isStatic(method.getModifiers()))
                continue;

            for (Class<?> cls : supers)
            {
                try
                {
                    Method real = cls.getDeclaredMethod(method.getName(), method.getParameterTypes());
                    if (real.isAnnotationPresent(SubscribeEvent.class))
                    {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length != 1)
                        {
                            throw new IllegalArgumentException(
                                "Method " + method + " has @SubscribeEvent annotation, but requires " + parameterTypes.length +
                                " arguments.  Event handler methods must require a single argument."
                            );
                        }

                        Class<?> eventType = parameterTypes[0];

                        if (!Event.class.isAssignableFrom(eventType))
                        {
                            throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation, but takes a argument that is not an Event " + eventType);
                        }

                        register(eventType, target, real, activeModContainer);
                        break;
                    }
                }
                catch (NoSuchMethodException e)
                {
                    ; // Eat the error, this is not unexpected
                }
            }
        }
    }

    private void register(Class<?> eventType, Object target, Method method, final ModContainer owner)
    {
        try
        {
            Constructor<?> ctr = eventType.getConstructor();
            ctr.setAccessible(true);
            Event event = (Event)ctr.newInstance();
            final ASMEventHandler asm = new ASMEventHandler(target, method, owner, IGenericEvent.class.isAssignableFrom(eventType));

            IEventListener listener = asm;
            if (IContextSetter.class.isAssignableFrom(eventType))
            {
                listener = event1 -> {
                    ModContainer old = Loader.instance().activeModContainer();
                    Loader.instance().setActiveModContainer(owner);
                    ((IContextSetter) event1).setModContainer(owner);
                    asm.invoke(event1);
                    Loader.instance().setActiveModContainer(old);
                };
            }

            event.getListenerList().register(busID, asm.getPriority(), listener);

            ArrayList<IEventListener> others = listeners.computeIfAbsent(target, k -> new ArrayList<>());
            others.add(listener);
        }
        catch (Exception e)
        {
            FMLLog.log.error("Error registering event handler: {} {} {}", owner, eventType, method, e);
        }
    }

    public void unregister(Object object)
    {
        ArrayList<IEventListener> list = listeners.remove(object);
        if(list == null)
            return;
        for (IEventListener listener : list)
        {
            ListenerList.unregisterAll(busID, listener);
        }
    }

    public boolean post(Event event)
    {
        IEventListener[] listeners = event.getListenerList().getListeners(busID);
        int index = 0;
        try
        {
            for (; index < listeners.length; index++)
            {
                listeners[index].invoke(event);
            }
        }
        catch (Throwable throwable)
        {
            exceptionHandler.handleException(this, event, listeners, index, throwable);
            Throwables.throwIfUnchecked(throwable);
            throw new RuntimeException(throwable);
        }
        return (event.isCancelable() ? event.isCanceled() : false);
    }

    @Override
    public void handleException(EventBus bus, Event event, IEventListener[] listeners, int index, Throwable throwable)
    {
        FMLLog.log.error("Exception caught during firing event {}:", event, throwable);
        FMLLog.log.error("Index: {} Listeners:", index);
        for (int x = 0; x < listeners.length; x++)
        {
            FMLLog.log.error("{}: {}", x, listeners[x]);
        }
    }
}