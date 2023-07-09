import { Client } from "./Client"

export enum LawsuitType{
    CIVIL = "Civil",
    COMMERCIAL = "Commercial",
    CRIMINAL = "Criminal",
    FAMILY = "Family",
    JUVENILE = "Juvenile",
    TAX = "Tax",
}

export interface Lawsuit{
    id: number
    description:string
    type: LawsuitType
    state: string
    court_date: string
    nb_attorneys: number
    client: Client
}