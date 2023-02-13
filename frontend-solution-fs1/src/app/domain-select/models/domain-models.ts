export interface Domain {
    id: string;
    title: string;
    summary: string;
    tags: string[];
    additionals: AdditionalSection[]; 
}

export interface AdditionalSection {
    title: string,
    content: string
}