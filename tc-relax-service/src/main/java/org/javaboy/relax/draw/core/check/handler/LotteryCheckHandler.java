package org.javaboy.relax.draw.core.check.handler;


import org.javaboy.relax.draw.core.context.LotteryContext;
import org.javaboy.relax.draw.core.request.DrawRequest;

/**
 * @author:majin.wj
 * LotteryIdempotentCheckHandler
 */
public interface LotteryCheckHandler {

    public boolean check(DrawRequest request, LotteryContext context);

    boolean supportOption(String scene);

}
