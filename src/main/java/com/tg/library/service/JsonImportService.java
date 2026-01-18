package com.tg.library.service;

import com.tg.library.entity.Authors;
import com.tg.library.entity.Books;
import com.tg.library.entity.Genres;
import com.tg.library.entity.Progress;
import com.tg.library.entity.Topics;
import com.tg.library.repository.AuthorsRepository;
import com.tg.library.repository.BooksRepository;
import com.tg.library.repository.GenresRepository;
import com.tg.library.repository.TopicsRepository;
import com.tg.library.service.dto.AuthorImportDto;
import com.tg.library.service.dto.BookImportDto;
import com.tg.library.service.dto.LibraryImportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
public class JsonImportService {

    private final ObjectMapper objectMapper;
    private final BooksRepository booksRepository;
    private final GenresRepository genresRepository;
    private final AuthorsRepository authorsRepository;
    private final TopicsRepository topicsRepository;

    @Autowired
    public JsonImportService(
            ObjectMapper objectMapper,
            BooksRepository booksRepository,
            GenresRepository genresRepository,
            AuthorsRepository authorsRepository,
            TopicsRepository topicsRepository
    ) {
        this.objectMapper = objectMapper;
        this.booksRepository = booksRepository;
        this.genresRepository = genresRepository;
        this.authorsRepository = authorsRepository;
        this.topicsRepository = topicsRepository;
    }

    public int importFromJson(java.nio.file.Path jsonPath) throws Exception {
        LibraryImportDto dto = objectMapper.readValue(jsonPath.toFile(), LibraryImportDto.class);
        if (dto == null || dto.books() == null) return 0;

        int imported = 0;

        for (BookImportDto b : dto.books()) {
            if (b == null) continue;
            if (isBlank(b.title())) continue;

            Books entity = new Books();
            entity.setTitle(b.title().trim());
            entity.setIsbn(blankToNull(b.isbn()));
            entity.setPublicationYear(b.publicationYear());
            entity.setPagesCount(b.pagesCount());
            entity.setNotes(blankToNull(b.notes()));
            //entity.setSeriesId(b.seriesId());
            entity.setMonastery(blankToNull(b.monastery()));

            // progress
            entity.setReadingProgress(parseProgress(b.readingProgress()));

            // genre (po nazwie)
            if (!isBlank(b.genre())) {
                Genres g = genresRepository.findByNameIgnoreCase(b.genre().trim())
                        .orElseGet(() -> {
                            Genres ng = new Genres();
                            ng.setName(b.genre().trim());
                            return genresRepository.save(ng);
                        });
                entity.setGenre(g);
            }

            // authors (po imię+drugie+nazwisko)
            if (b.authors() != null && !b.authors().isEmpty()) {
                java.util.List<Authors> authors = new java.util.ArrayList<>();
                for (AuthorImportDto a : b.authors()) {
                    if (a == null) continue;
                    String fn = safe(a.firstName());
                    String mn = safe(a.middleName());
                    String ln = safe(a.lastName());
                    if (isBlank(fn) && isBlank(ln)) continue;

                    Authors au = authorsRepository
                            .findByFirstNameIgnoreCaseAndMiddleNameIgnoreCaseAndLastNameIgnoreCase(fn, mn, ln)
                            .orElseGet(() -> {
                                Authors na = new Authors();
                                na.setFirstName(fn);
                                na.setMiddleName(mn);
                                na.setLastName(ln);
                                return authorsRepository.save(na);
                            });
                    authors.add(au);
                }
                entity.setAuthors(authors);
            } else {
                entity.setAuthors(java.util.List.of());
            }

            // topics (po nazwie)
            if (b.topics() != null && !b.topics().isEmpty()) {
                java.util.List<Topics> topics = new java.util.ArrayList<>();
                for (String tname : b.topics()) {
                    if (isBlank(tname)) continue;
                    String name = tname.trim();
                    Topics t = topicsRepository.findByNameIgnoreCase(name)
                            .orElseGet(() -> {
                                Topics nt = new Topics();
                                nt.setName(name);
                                return topicsRepository.save(nt);
                            });
                    topics.add(t);
                }
                entity.setTopics(topics);
            } else {
                entity.setTopics(java.util.List.of());
            }

            // Zapis
            booksRepository.save(entity);
            imported++;
        }

        return imported;
    }

    private Progress parseProgress(String s) {
        if (isBlank(s)) return Progress.unread;
        try { return Progress.valueOf(s.trim()); }
        catch (Exception e) { return Progress.unread; }
    }

    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    private static String blankToNull(String s) { return isBlank(s) ? null : s.trim(); }
    private static String safe(String s) { return s == null ? "" : s.trim(); }
}
