package me.caosh.condition.domain.model.order.plan;

/**
 * Created by caosh on 2017/8/2.
 */
public interface TradePlan {
    @Deprecated
    TradeNumber getTradeNumber();

    void accept(TradePlanVisitor visitor);
}
