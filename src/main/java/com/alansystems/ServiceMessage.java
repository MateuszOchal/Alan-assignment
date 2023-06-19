package com.alansystems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.decimal4j.util.DoubleRounder;

import java.math.RoundingMode;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceMessage {
    private int quantity;
    private double value;
    private double ppi;
    private double balance;
    private double profit;

    public ServiceMessage(int balance) {
        this.balance = balance;
    }

    public static ServiceMessage beforeFirst(int balance) {
        return new ServiceMessage(balance);
    }

    public ServiceMessage calculateStateOfInventory(String type, Product product, int balance, Map<Integer, ServiceMessage> map) {

        if (type.equals("PURCHASE")) {
            return new ServiceMessage(
                    quantity = product.getQuantity() + this.quantity,
                    value = this.value + product.getValue(),
                    ppi = DoubleRounder.round(this.value / this.quantity, 5, RoundingMode.HALF_EVEN),
                    balance - product.getValue(),
                    profit = 0
            );
        } else return new ServiceMessage(
                quantity = this.quantity - product.getQuantity(),
                value = this.quantity * ppi,
                ppi = (this.value / this.quantity),
                balance + product.getValue(),
                profit = DoubleRounder.round(product.getValue() - (product.getQuantity() * ppi), 2, RoundingMode.CEILING)
        );
    }
}
