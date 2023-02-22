package vttp2022.assessment.csf.orderbackend.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class Order {

	private Integer orderId;
	private String name;
	private String email;
	private Integer size;
	private String sauce;
	private Boolean thickCrust;
	private List<String> toppings = new LinkedList<>();
	private String comments;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setSize(Integer size) { this.size = size; }
	public Integer getSize() { return this.size; }

	public void setSauce(String sauce) { this.sauce = sauce; }
	public String getSauce() { return this.sauce; }

	public void setThickCrust(Boolean thickCrust) { this.thickCrust = thickCrust; }
	public Boolean isThickCrust() { return this.thickCrust; }

	public void setToppings(List<String> toppings) { this.toppings = toppings; }
	public List<String> getToppings() { return this.toppings; }
	public void addTopping(String topping) { this.toppings.add(topping); }

	public void setComments(String comments) { this.comments = comments; }
	public String getComments() { return this.comments; }

	public static Order create(JsonObject jsonObj) {
        Order order = new Order();

        //build the order
        order.setName(jsonObj.getString("name"));
		order.setEmail(jsonObj.getString("email"));
		order.setSauce(jsonObj.getString("sauce"));
		order.setComments(jsonObj.getString("comments"));

		//convert string of size to integer
		String sizeString = jsonObj.getString("size");
		String[] sizeArray = new String[]{"Personal - 6 inches","Regular - 9 inches","Large - 12 inches","Extra Large - 15 inches"};
		for (int j = 0; j < 4; j++){
			if (sizeString.equals(sizeArray[j])){
				order.setSize(j);
				break;
			}
		}

		//convert string of base to boolean
		String crustString = jsonObj.getString("base");
		if (crustString.equals("thick")){
			order.setThickCrust(true);
		} else {
			order.setThickCrust(false);
		}

		List<String> toppings = new LinkedList<>();
		JsonArray jsonTopping = jsonObj.getJsonArray("toppings");
		// System.out.println(jsonTopping.getString(0));
		for (int i =0; i < jsonTopping.size(); i++){
			toppings.add(jsonTopping.getString(i));
		}
		order.setToppings(toppings);

        return order;
    }

	public static Order createFromDoc (SqlRowSet rs) {
		Order order = new Order();
		order.setName(rs.getString("name"));
		order.setEmail(rs.getString("email"));
		order.setSauce(rs.getString("sauce"));
		order.setSize(rs.getInt("pizza_size"));
		order.setThickCrust(rs.getBoolean("thick_crust"));

		List<String> toppings = new ArrayList<String>(Arrays
			.asList(rs.getString("toppings").split(",")));
		order.setToppings(toppings);

		return order;
	}
}
