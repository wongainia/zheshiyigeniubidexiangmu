package me.caosh.condition.infrastructure.rabbitmq.impl;

import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.dto.order.assembler.ConditionOrderDTOAssembler;
import me.caosh.condition.domain.dto.order.converter.ConditionOrderDTOMessageConverter;
import me.caosh.condition.domain.model.order.ConditionOrder;
import me.caosh.condition.infrastructure.rabbitmq.ConditionOrderProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
@Configuration
@ConfigurationProperties(prefix = "me.caosh.condition.conditionOrder")
@Service
public class ConditionOrderProducerImpl implements ConditionOrderProducer {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderProducerImpl.class);

    private String exchangeName;
    private String routingKey;

    private final AmqpAdmin amqpAdmin;

    private final AmqpTemplate amqpTemplate;

    private final MessageConverter messageConverter = new ConditionOrderDTOMessageConverter();

    public ConditionOrderProducerImpl(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    @PostConstruct
    public void init() {
        DirectExchange exchange = new DirectExchange(exchangeName, false, false);
        amqpAdmin.declareExchange(exchange);

        logger.info("=== Condition order producer initialized ===");
    }

    @Override
    public void send(ConditionOrder conditionOrder) {
        ConditionOrderDTO conditionOrderDTO = ConditionOrderDTOAssembler.toDTO(conditionOrder);
        Message message = messageConverter.toMessage(conditionOrderDTO, new MessageProperties());
        amqpTemplate.send(exchangeName, routingKey, message);
        logger.info("Send condition order ==> {}", conditionOrderDTO);
    }
}
