package org.project.springweb.repository.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.springweb.dto.book.BookDto;
import org.project.springweb.repository.SpecificationProvider;
import org.project.springweb.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<BookDto> {
    private final List<SpecificationProvider<BookDto>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<BookDto> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException("No specification provider found for key: "
                                + key));

    }
}
