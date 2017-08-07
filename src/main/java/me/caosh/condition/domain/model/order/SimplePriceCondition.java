package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/2.
 */
public class SimplePriceCondition implements Condition {
    private final CompareCondition compareCondition;
    private final BigDecimal targetPrice;

    public SimplePriceCondition(CompareCondition compareCondition, BigDecimal targetPrice) {
        this.compareCondition = compareCondition;
        this.targetPrice = targetPrice;
    }

    public CompareCondition getCompareCondition() {
        return compareCondition;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimplePriceCondition)) return false;

        SimplePriceCondition that = (SimplePriceCondition) o;

        if (compareCondition != that.compareCondition) return false;
        return !(targetPrice != null ? !targetPrice.equals(that.targetPrice) : that.targetPrice != null);

    }

    @Override
    public int hashCode() {
        int result = compareCondition != null ? compareCondition.hashCode() : 0;
        result = 31 * result + (targetPrice != null ? targetPrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("priceDirection", compareCondition)
                .add("targetPrice", targetPrice)
                .toString();
    }

    public boolean isSatisfiedBy(BigDecimal price) {
        if (compareCondition == CompareCondition.GREATER_THAN_OR_EQUALS) {
            return price.compareTo(targetPrice) >= 0;
        } else {
            Preconditions.checkArgument(compareCondition == CompareCondition.LESS_THAN_OR_EQUALS);
            return price.compareTo(targetPrice) <= 0;
        }
    }
}
