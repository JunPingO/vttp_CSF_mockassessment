package vttp2022.assessment.csf.orderbackend.repositories;

public class Queries {
    public static final String SQL_INSERT_ORDER = 
    "insert into orders(name, email, pizza_size, thick_crust, sauce, toppings, comments) values(?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_RETRIEVE_ORDER_BY_EMAIL = 
    """
        select order_id, name, email, pizza_size, thick_crust, sauce, toppings 
        from orders
        where email = ?
    """;

}
