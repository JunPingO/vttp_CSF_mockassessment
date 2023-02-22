package vttp2022.assessment.csf.orderbackend.controllers;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.services.OrderService;

@RestController
@RequestMapping
public class OrderRestController {

    @Autowired
    private OrderService orderSvc;
    
    @PostMapping(path = "/api/order/{hello}")
    public ResponseEntity<String> postOrder(@RequestBody String payload, @PathVariable String hello) {

        List<OrderSummary> orderSummaryList = new LinkedList<>();

        try (JsonReader reader = Json.createReader(new StringReader(payload))) {
            JsonObject jsonObject = reader.readObject();
            orderSvc.createOrder(Order.create(jsonObject));
            orderSummaryList = orderSvc.getOrdersByEmail(Order.create(jsonObject).getEmail());            
            
        } catch (JsonException e) {
            e.printStackTrace();
        }
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (OrderSummary orderSum: orderSummaryList){
            arrBuilder.add(orderSum.toJson());
        }

        return ResponseEntity.ok()
            .body(arrBuilder.build().toString());
    }

    @GetMapping(path = "/api/order/{email}/all")
    public ResponseEntity<String> getOrders(@PathVariable String email) {

        List<OrderSummary> orderSummaryList = new LinkedList<>();
        System.out.print(email);

        try {
            orderSummaryList = orderSvc.getOrdersByEmail(email);            
        } catch (JsonException e) {
            e.printStackTrace();
        }

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (OrderSummary orderSum: orderSummaryList){
            arrBuilder.add(orderSum.toJson());
        }

        return ResponseEntity.ok().body(arrBuilder.build().toString());

    }
}
