package com.tg.library.model;

public record BookDTO(Integer id, String title, String alternateTitle, Integer authorId, Integer publisherId,
                      Integer genreId, Integer sectionId, String mainCharacter, Integer seriesId, Integer topicId) {

    public static class Builder {

        private Long id;
        private String title;
        private String alternateTitle;
        private Integer authorId;
        private Integer publisherId;
        private Integer genreId;
        private Integer sectionId;
        private String mainCharacter;
        private Integer seriesId;
        private Integer topicId;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder alternateTitle(String alternateTitle) {
            this.alternateTitle = alternateTitle;
            return this;
        }

        public Builder authorId(Integer authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder publisherId(Integer publisherId) {
            this.publisherId = publisherId;
            return this;
        }

        public Builder genreId(Integer genreId) {
            this.genreId = genreId;
            return this;
        }

        public Builder sectionId(Integer sectionId) {
            this.sectionId = sectionId;
            return this;
        }

        public Builder mainCharacter(String mainCharacter) {
            this.mainCharacter = mainCharacter;
            return this;
        }

        public Builder seriesId(Integer seriesId) {
            this.seriesId = seriesId;
            return this;
        }

        public Builder topicId(Integer topicId) {
            this.topicId = topicId;
            return this;
        }

        public BookDTO build() {
            return new BookDTO(id, title, alternateTitle, authorId, publisherId, genreId, sectionId, mainCharacter,
                    seriesId, topicId);
        }
    }


}
   
