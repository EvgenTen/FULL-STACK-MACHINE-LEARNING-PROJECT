import { LocalDate } from "@js-joda/core";

export  interface Car {
 id: number;
 manufacturer: string;
 model: string;
 year: number;
 month: number;
 distance: number;
 hand: number;
 imageUrl: string;
 carCode: string;
 price: number;
 date: LocalDate;
}
 
