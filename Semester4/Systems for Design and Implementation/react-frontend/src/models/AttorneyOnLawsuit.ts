import { Attorney } from "./Attorney";
import { Lawsuit } from "./Lawsuit";

export enum AttorenyRole{
    PRIMARY = "Primary",
    SECONDARY = "Secondary",
}

export enum WorkType{
    DOCS = "Documents",
    STATEMENT = "Statement Preparation",
    EVIDENCE = "Evidence Collection",
}

export interface AttorneyOnLawsuit{
    id:number,
    attorney: Attorney,
    lawsuit: Lawsuit,
    att_role: AttorenyRole,
    work_type: WorkType,
    description: string,
}