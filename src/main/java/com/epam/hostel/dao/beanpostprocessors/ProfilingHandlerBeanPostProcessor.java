package com.epam.hostel.dao.beanpostprocessors;

import com.epam.hostel.dao.annotations.Profiling;
import com.epam.hostel.dao.profilingcontroller.ProfilingController;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {
    private static Logger logger = Logger.getLogger(ProfilingHandlerBeanPostProcessor.class);
    private Map<String, Class> map = new HashMap<>();
    private ProfilingController controller = new ProfilingController();

    /*public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }*/

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                if (controller.isEnabled()) {
                    logger.info("***Profiling***");
                    long before = System.nanoTime();
                    Object retVal = method.invoke(bean, args);
                    long after = System.nanoTime();
                    long result = after - before;
                    logger.info("***result = " + result + " ***");
                    return retVal;
                } else {
                    return method.invoke(bean, args);
                }
            });
        }
        return bean;
    }
}
