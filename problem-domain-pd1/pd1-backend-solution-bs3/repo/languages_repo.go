package repo

import "pd1-backend-solution-bs3/openapi"

func GetLanguages() (openapi.LanguageList, error) {
	rows, err := db.Query("select language_id, name from language")
	if err != nil {
		return openapi.LanguageList{}, err
	}
	defer rows.Close()

	languages := []openapi.Language{}
	for rows.Next() {
		lang := openapi.Language{}
		if err := rows.Scan(&lang.LanguageId, &lang.Name); err != nil {
			return openapi.LanguageList{}, err
		}
		languages = append(languages, lang)
	}

	return openapi.LanguageList{Data: languages}, nil
}
