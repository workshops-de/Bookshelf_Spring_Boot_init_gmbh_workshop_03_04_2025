package de.workshops.bookshelf.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookSearchRequest(@NotBlank String title, @Size(min = 3) String author) {
}
