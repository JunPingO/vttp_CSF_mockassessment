// Implement the methods in PizzaService for Task 3
// Add appropriate parameter and return type 

import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";

@Injectable()
export class PizzaService {

  constructor(private http: HttpClient) { }

  // POST /api/order
  // Add any required parameters or return type
  createOrder(/* add any required parameters */ order: any): Promise<any> { 
      console.info(order)
      const headers = new HttpHeaders().set('Content-Type','application/json');
      return firstValueFrom(
        this.http.post<any>(`/api/order/${"hello"}`, order, {headers: headers})
      )
  }

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(/* add any required parameters */ email: string) { 
    console.info("from pizzaservice", email)
    const headers = new HttpHeaders().set('Content-Type','application/json');
    return firstValueFrom(
      this.http.get<any>(`/api/order/${email}/all`, {headers: headers})
    )
  }

}
