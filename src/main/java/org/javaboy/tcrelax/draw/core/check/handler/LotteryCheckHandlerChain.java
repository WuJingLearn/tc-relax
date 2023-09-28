package org.javaboy.tcrelax.draw.core.check.handler;


import org.javaboy.tcrelax.draw.core.context.LotteryContext;
import org.javaboy.tcrelax.draw.core.request.DrawRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author:majin.wj
 */
@Component
public class LotteryCheckHandlerChain implements InitializingBean {


    @Autowired
    private ApplicationContext context;


    private List<LotteryCheckHandler> checkHandlerChain;


    public void check(DrawRequest request, LotteryContext context) {
        for (LotteryCheckHandler handler : checkHandlerChain) {
            if (handler.supportOption(context.getScene())) {
                handler.check(request, context);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.checkHandlerChain = context.getBeansOfType(LotteryCheckHandler.class).values().stream().sorted(Comparator.comparingInt(handler -> {
            Order o = handler.getClass().getAnnotation(Order.class);
            if (o != null) {
                return o.value();
            }
            return Integer.MAX_VALUE;
        })).collect(Collectors.toList());
    }
}
