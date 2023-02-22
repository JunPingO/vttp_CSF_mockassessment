// Add your models here if you have any
export interface PizzaOrder {
    name: string
    email: string
    size: string
    base: string
    sauce: string
    toppings: string[]
    comments: string
}

export interface OrderSummary{
    orderId: number
    name: string
    email: string
    amount: number
}
