package com.tg.library.model;

import lombok.experimental.SuperBuilder;

public record AuthorDTO(Long id, String firstName, String middleName, String lastName, String monastery, String title) {

//    public static Integer liczba;
//
//    private class TestTomka {
//
//    }
//
//    public static TestTomka testTomka;

    public static class Builder {

        private Long id;
        private String firstName;
        private String middleName;
        private String lastName;
        private String monastery;
        private String title;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder monastery(String monastery) {
            this.monastery = monastery;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public AuthorDTO build() {
            return new AuthorDTO(firstName, middleName, lastName, monastery, title);
        }
    }

}