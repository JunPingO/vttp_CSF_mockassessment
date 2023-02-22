import { Component, OnInit } from '@angular/core';
import { Form, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PizzaOrder } from '../models';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  pizzaSize = SIZES[0]

  form!: FormGroup

  order!: PizzaOrder

  email!: any

  constructor(private fb:FormBuilder, private router : Router, private pizzaService:PizzaService) {}

  ngOnInit(): void {
    
    this.form = this.createForm();
    console.info(this.form.value.email)
    this.email = this.form.value.email
    
  }

  private createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('', [ Validators.required]),
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      size: this.fb.control<string>(''),
      base: this.fb.control<string>('', [Validators.required]),
      sauce: this.fb.control<string>('', [Validators.required]),
      toppings: this.fb.array([], [Validators.required]),
      comments: this.fb.control<string>('')
    })
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
    // this.order.size! = this.pizzaSize
    // console.info(this.order.size)
  }

  onCheckboxChange(e: any) {
    const toppings = this.form.get('toppings') as FormArray;
    if (e.target.checked) {
      toppings.push(new FormControl(e.target.value));
    } else {
      let i = 0;
      toppings.controls.forEach((topping) => {
        if (topping.value == e.target.value) {
          toppings.removeAt(i);
          return;
        }
        i++;
      });
    }
  }

  listOrders(){
    let email = this.form.get('email')?.value
    console.info(email)
    this.router.navigate(['/orders', email]);
  }

  submitForm(){
    // console.info(this.form.value)
    this.order = this.form.value
    this.order.size = this.pizzaSize
    console.info(this.order)
    this.pizzaService.createOrder(this.order)
    this.router.navigate(['/orders', this.order.email]);
  }
  
}
