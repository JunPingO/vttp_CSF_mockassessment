package vttp2022.assessment.csf.orderbackend.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;

import static vttp2022.assessment.csf.orderbackend.repositories.Queries.*;

@Service
public class OrderService {

	@Autowired
	private PricingService priceSvc;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// POST /api/order
	// Create a new order by inserting into orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public void createOrder(Order order) {

		StringBuilder toppingsString = new StringBuilder();
		for (int i =0; i < order.getToppings().size(); i++){
			toppingsString.append(order.getToppings().get(i)+",");
		}

		System.out.println(order.getSize().toString() + order.getName() + order.getEmail() + order.getSize() +
		order.isThickCrust() + order.getSauce() + ">>>>" + toppingsString.toString() + ">>>	>>>" + order.getComments());

		jdbcTemplate.update(SQL_INSERT_ORDER, 
			order.getName(), order.getEmail(), order.getSize(), 
			order.isThickCrust(), order.getSauce(), toppingsString.toString(), order.getComments());

    // "insert into orders(name, email, pizza_size, thick_crust, sauce, toppings, comments) values(?, ?, ?, ?, ?, ?, ?)";
	
	}

	// GET /api/order/<email>/all
	// Get a list of orders for email from orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public List<OrderSummary> getOrdersByEmail(String email) {
		// Use priceSvc to calculate the total cost of an order
		SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_RETRIEVE_ORDER_BY_EMAIL, email);

		List<OrderSummary> orderSummaryList = new LinkedList<>();

		while(rs.next()){
			Order order = Order.createFromDoc(rs);
			OrderSummary orderSummary = new OrderSummary();
			Float orderAmount = 0f;
			if (order.isThickCrust()){
				orderAmount += priceSvc.thickCrust();
			} else {
				orderAmount += priceSvc.thinCrust();
			}
			for (String topping : order.getToppings()){
				orderAmount += priceSvc.topping(topping);
			}
			orderAmount += priceSvc.sauce(order.getSauce()) + priceSvc.size(order.getSize());
			orderSummary.setAmount(orderAmount);
			orderSummary.setName(order.getName());
			orderSummary.setEmail(order.getEmail());
			orderSummary.setOrderId(rs.getInt("order_id"));

			orderSummaryList.add(orderSummary);
		}
		// System.out.println("from orderSvc>>>>" + orderSummaryList.get(0).toString());

		return orderSummaryList;

	}
}
