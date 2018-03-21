package hbec.intellitrade.condorder.domain.orders.price;

import hbec.intellitrade.condorder.domain.orders.DelayConfirmSupportedBuilder;
import hbec.intellitrade.condorder.domain.orders.DeviationCtrlSupportedBuilder;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmBuilder;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlBuilder;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import me.caosh.autoasm.ConvertibleBuilder;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class PriceConditionFacadeBuilder implements ConvertibleBuilder<PriceConditionFacade>,
        DelayConfirmSupportedBuilder,
        DeviationCtrlSupportedBuilder {
    private CompareOperator compareOperator;
    private BigDecimal targetPrice;
    private DelayConfirmBuilder delayConfirm = new DelayConfirmBuilder();
    private DeviationCtrlBuilder deviationCtrl = new DeviationCtrlBuilder();
    private int delayConfirmedCount = 0;

    public void setCompareOperator(CompareOperator compareOperator) {
        this.compareOperator = compareOperator;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    @Override
    public DelayConfirmBuilder getDelayConfirm() {
        return delayConfirm;
    }

    public void setDelayConfirm(DelayConfirmBuilder delayConfirm) {
        this.delayConfirm = delayConfirm;
    }

    @Override
    public DeviationCtrlBuilder getDeviationCtrl() {
        return deviationCtrl;
    }

    public void setDeviationCtrl(DeviationCtrlBuilder deviationCtrl) {
        this.deviationCtrl = deviationCtrl;
    }

    public void setDelayConfirmedCount(int delayConfirmedCount) {
        this.delayConfirmedCount = delayConfirmedCount;
    }

    @Override
    public PriceConditionFacade build() {
        return new PriceConditionFacade(new PriceCondition(compareOperator, targetPrice),
                                        delayConfirm.build(),
                                        deviationCtrl.build(), delayConfirmedCount
        );
    }
}