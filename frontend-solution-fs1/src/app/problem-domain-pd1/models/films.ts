export interface FilmsData {
    data: Film[];
    total_count: number;
}

export interface Film {
    film_id: number;
    title: string;
    description: string;
    release_year: number;
    language: Language;
    original_language: Language | null;
    rental_duration: number;
    rental_rate: number;
    length: number;
    replacement_cost: number;
    rating: string;
    special_features: string;
}

export interface Language {
    language_id: number;
    name: string;
}