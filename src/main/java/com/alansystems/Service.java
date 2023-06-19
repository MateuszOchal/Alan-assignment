package com.alansystems;

import java.util.Map;

public class Service {

    public Map<Integer, ServiceMessage> updateCompanyBalance(ProductWrapper productWrapper, Map<Integer, ServiceMessage> productInventory, int balance) {
        Product product = productWrapper.getProduct();
        ServiceMessage serviceMessage = productInventory.getOrDefault(product.getId(), ServiceMessage.beforeFirst(balance));
        ServiceMessage newServiceMessage = serviceMessage.calculateStateOfInventory(productWrapper.getType(), product, balance, productInventory);
        productInventory.putIfAbsent(product.getId(), newServiceMessage);
        productInventory.replace(product.getId(), serviceMessage, newServiceMessage);
        return productInventory;
    }
}
