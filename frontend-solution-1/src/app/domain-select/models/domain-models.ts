export interface Domain {
    id: number;
    title: string;
    summary: string;
    tags: string[];
    additionals: AdditionalSection[]; 
}

export interface AdditionalSection {
    title: string,
    content: string
}