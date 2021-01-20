import { Injectable } from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar'; 

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor() { } 
  
  openSnackBar(snackBar:MatSnackBar, message: string, action: string, duration: number) { 
    snackBar.open(message, action, { 
       duration: duration,
       verticalPosition: 'top', 
        horizontalPosition: 'center'
    }); 
  } 
}
