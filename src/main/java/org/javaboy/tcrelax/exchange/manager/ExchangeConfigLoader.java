package org.javaboy.tcrelax.exchange.manager;

import org.javaboy.tcrelax.exchange.config.ExchangeActivityConfig;

/**
 * @author:majin.wj
 */
public interface ExchangeConfigLoader {

     void initExchangeConfig();
     ExchangeActivityConfig getExchangeConfig(String scene);

    
}
