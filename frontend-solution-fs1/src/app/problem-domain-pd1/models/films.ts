export interface Film {
    filmId: number;
    title: string;
    description: string;
    releaseYear: number;
    language: Language;
    originalLanguage: Language;
    rentalDuration: number;
    rentalRate: number;
    length: number;
    replacementCost: number;
    rating: string;
    specialFeatures: string;
}

export interface Language {
    name: string;
}