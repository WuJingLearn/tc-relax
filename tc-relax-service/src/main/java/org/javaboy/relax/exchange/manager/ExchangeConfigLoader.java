package org.javaboy.relax.exchange.manager;

import org.javaboy.relax.exchange.config.ExchangeActivityConfig;

/**
 * @author:majin.wj
 */
public interface ExchangeConfigLoader {

     void initExchangeConfig();
     ExchangeActivityConfig getExchangeConfig(String scene);

    
}
