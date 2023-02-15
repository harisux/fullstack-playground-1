export interface BackendDiscovery {
    applications: ApplicationsWrap;
}

export interface ApplicationsWrap {
    application: Application[];
}

export interface Application {
    name: string;
    instance: Instance[]; 
}

export interface Instance {
    homePageUrl: string;
    app: string;
    status: string;
    metadata: Metadata;
}

export interface Metadata {
    problemDomainId: string;
    summary: string;
    tags: string;
    detailsList: string;
}