package ru.protei.proxy;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TimingDynamicInvocationHandler implements InvocationHandler {
    private final static Logger log = Logger.getLogger(TimingDynamicInvocationHandler.class);

    private final Map<String, Method> methods = new HashMap<String, Method>();

    private Object target;

    public TimingDynamicInvocationHandler(Object target) {
        this.target = target;

        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.info("Invoked method: " + method.getName());
        long startTime = System.nanoTime();

        Object result = method.invoke(target, args);

        long time = System.nanoTime() - startTime;
        log.info("Executing " + method.getName() + " finished in " + time + " ns");

        return result;
    }
}
