import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { OrderSummary } from '../models';
import { PizzaService } from '../pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements AfterViewInit {

  params$!: Subscription

  orders: OrderSummary[] = []

  constructor(private activatedRoute: ActivatedRoute, private pizzaService:PizzaService) { }
  

  ngAfterViewInit(): void {
    const emailString = this.activatedRoute.snapshot.params['email'];
    console.info("navigated to orders >>>>", emailString)

    this.pizzaService.getOrders(emailString)
      .then(result => {
        for ( var orderSummary of result ){
          // console.info(orderSummary)
          this.orders.push(orderSummary)
        }
        console.info('>>> orders: ', this.orders)
      })
      .catch(error => {
        console.error('>> error: ', error)
      })
      
  }

}
