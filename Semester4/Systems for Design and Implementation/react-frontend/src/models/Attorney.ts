export enum SpecializationType{
    CIVIL = "Civil",
    COMMERCIAL = "Commercial",
    CRIMINAL = "Criminal",
    FAMILY = "Family",
    JUVENILE = "Juvenile",
    TAX = "Tax",
}

export enum ExperienceType{
    JUNIOR = "Junior",
    MID = "Mid",
    SENIOR = "Senior",
}

export interface Attorney{
    id:number,
    name:string,
    specialization:SpecializationType,
    date_of_birth:string,
    experience:ExperienceType,
    city: string
    fee: number
    nb_lawsuits:number
}