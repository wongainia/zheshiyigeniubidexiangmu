package me.caosh.condition.interfaces.web;

import me.caosh.condition.application.order.ConditionOrderCommandService;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.time.TimeOrder;
import me.caosh.condition.infrastructure.repository.ConditionOrderRepository;
import me.caosh.condition.infrastructure.repository.impl.ConditionOrderIdGenerator;
import me.caosh.condition.interfaces.assembler.TimeOrderCommandAssembler;
import me.caosh.condition.interfaces.command.TimeOrderCreateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by caosh on 2017/8/9.
 */
@RestController
@RequestMapping("/time")
public class TimeOrderController {

    private final ConditionOrderIdGenerator idGenerator;
    private final ConditionOrderCommandService conditionOrderCommandService;
    private final ConditionOrderRepository conditionOrderRepository;

    @Autowired
    public TimeOrderController(ConditionOrderIdGenerator idGenerator,
                               ConditionOrderCommandService conditionOrderCommandService,
                               ConditionOrderRepository conditionOrderRepository) {
        this.idGenerator = idGenerator;
        this.conditionOrderCommandService = conditionOrderCommandService;
        this.conditionOrderRepository = conditionOrderRepository;
    }

    @RequestMapping("/create")
    public Long create(@Valid TimeOrderCreateCommand command) {
        Long orderId = idGenerator.nextId();
        TradeCustomerIdentity customerIdentity = new TradeCustomerIdentity(303348, "010000061086");
        TimeOrder timeOrder = TimeOrderCommandAssembler.assemble(orderId, customerIdentity, command);
        conditionOrderCommandService.save(timeOrder);
        return orderId;
    }
/*
    @RequestMapping("/update")
    public Long update(@Valid PriceOrderUpdateCommand command) {
        Long orderId = command.getOrderId();
        Optional<ConditionOrder> conditionOrderOptional = conditionOrderRepository.findOne(orderId);
        if (!conditionOrderOptional.isPresent()) {
            return -1L;
        }
        ConditionOrder conditionOrder = conditionOrderOptional.get();
        if (conditionOrder.getOrderState() != OrderState.ACTIVE && conditionOrder.getOrderState() != OrderState.PAUSED) {
            return -2L;
        }
        PriceOrder oldPriceOrder = (PriceOrder) conditionOrder;
        PriceOrder newPriceOlder = PriceOrderCommandAssembler.mergePriceOrder(oldPriceOrder, command);
        conditionOrderCommandService.update(newPriceOlder);
        return orderId;
    }*/
}
