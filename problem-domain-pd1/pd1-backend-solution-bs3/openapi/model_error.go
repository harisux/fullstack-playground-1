/*
 * Sakila Films CRUD API
 *
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * API version: 1.0
 * Generated by: OpenAPI Generator (https://openapi-generator.tech)
 */

package openapi




type Error struct {

	Title string `json:"title,omitempty"`

	Message string `json:"message,omitempty"`
}

// AssertErrorRequired checks if the required fields are not zero-ed
func AssertErrorRequired(obj Error) error {
	return nil
}

// AssertErrorConstraints checks if the values respects the defined constraints
func AssertErrorConstraints(obj Error) error {
	return nil
}