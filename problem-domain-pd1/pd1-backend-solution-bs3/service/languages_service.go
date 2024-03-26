package service

import (
	"context"
	"pd1-backend-solution-bs3/openapi"
	"pd1-backend-solution-bs3/repo"
)

type LanguagesAPIService struct{}

func NewLanguagesAPIService() openapi.LanguagesAPIServicer {
	return &LanguagesAPIService{}
}

// GetLanguages - Gets a list existing languages
func (s *LanguagesAPIService) GetLanguages(ctx context.Context) (openapi.ImplResponse, error) {
	langList, err := repo.GetLanguages()
	if err != nil {
		return openapi.Response(500, openapi.Error{Title: "Error", Message: "Internal Error"}), nil
	}
	return openapi.Response(200, langList), nil
}
