import { Lawsuit } from "./Lawsuit"

export enum ClientType{
    PHYSICAL = "Physical Person",
    JURIDICAL = "Juridical Person"
}

export interface Client{
    id: number
    name : string
    phone_number:string
    city:string
    date_of_birth:string
    type: ClientType
    nb_lawsuits:number
    lawsuits?: Lawsuit[]
}

