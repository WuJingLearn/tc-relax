package org.javaboy.relax.draw.core.lottery;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author:majin.wj 抽奖工具,
 */
public class LotteryUtil<T> {

    private List<Continuous> continuousList;
    private int max;


    public LotteryUtil(Map<T, Long> data) {
        continuousList = new ArrayList<>(data.size());

        int min;
        for (Map.Entry<T, Long> entry : data.entrySet()) {
            // 起点为上一个终点，终点为加上新的比率
            min = max;
            max += entry.getValue();
            continuousList.add(new Continuous(entry.getKey(), min, max));
        }

    }

    public T lottery() {
        // 生成一个0 - 10000的随机数
        double v = ThreadLocalRandom.current().nextDouble(max);
        for (Continuous continuous : continuousList) {
            if(continuous.between(v)){
                return continuous.getItem();
            }
        }
        return null;
    }


     class Continuous {
        @Getter
        T item;
        int min;
        int max;

        public Continuous(T item, int min, int max) {
            this.item = item;
            this.min = min;
            this.max = max;
        }

        public boolean between(double value) {
            return min <= value && value < max;
        }


    }

    public static void main(String[] args) {
        //nextDouble(10)方法生成的随机数是在0.0（包含）和10.0（不包含）之间的双精度浮点数。因此，最大值是小于10.0的。
        double v = ThreadLocalRandom.current().nextDouble(10);
        System.out.println(v);
    }

}
