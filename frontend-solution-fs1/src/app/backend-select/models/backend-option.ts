export interface BackendOption {
    baseUrl: string;
    id: string;
    title: string; 
    problemDomainId: string;
    summary: string;
    tags: string[];
    source: string;
    details: Detail[]; 
}

export interface Detail {
    title: string,
    values: string[]
}