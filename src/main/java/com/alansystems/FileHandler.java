package com.alansystems;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class FileHandler {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private Service service;

    public void analizeBusinessPerformance() {
        Map<Integer, ServiceMessage> serviceMessageMap = new HashMap<>();
        int balance = 15000000;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/messages.txt"));) {
            for (String newMessageToMap; (newMessageToMap = reader.readLine()) != null; ) {

                System.out.println(newMessageToMap);
                ProductWrapper productWrapper = objectMapper.readValue(newMessageToMap, ProductWrapper.class);
                Product product = productWrapper.getProduct();
                serviceMessageMap = service.updateCompanyBalance(productWrapper, serviceMessageMap, balance);
                if (productWrapper.getType().equals("PURCHASE")) {
                    balance -= productWrapper.getProduct().getValue();
                } else balance += productWrapper.getProduct().getValue();
                printMessage(serviceMessageMap, productWrapper, product.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    public void printMessage(Map<Integer, ServiceMessage> productMap, ProductWrapper productWrapper, Integer id) {
        if (productWrapper.getType().equals("PURCHASE")) {
            System.out.println("Product " + +id + " quantity: " + productMap.get(id).getQuantity() + " value: " + productMap.get(id).getValue() + " ppi: " + productMap.get(id).getPpi() + " balance: " + new BigDecimal(productMap.get(id).getBalance()).toPlainString());
        } else
            System.out.println("Product " + +id + " quantity: " + productMap.get(id).getQuantity() + " value: " + productMap.get(id).getValue() + " ppi: " + productMap.get(id).getPpi() + " balance: " + new BigDecimal(productMap.get(id).getBalance()).toPlainString() + " profit: " + productMap.get(id).getProfit());
    }
}

