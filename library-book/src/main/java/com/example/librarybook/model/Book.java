package com.example.librarybook.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Book {

        @Id
        @GeneratedValue
        private int id;

        @NotEmpty
        @Length
        private String title;

        @NotEmpty
        private String author;

        @NotEmpty
        private String genre;

        @NotEmpty
        private String language;

        @NotEmpty
        private String editor;

        private String summary;

        @NotEmpty
        private String releaseDate;

        public Book() {
        }


        public void setId(int id) {
                this.id = id;
        }


        public void setTitle(String title) {
                this.title = title;
        }


        public void setAuthor(String author) {
                this.author = author;
        }


        public void setGenre(String genre) {
                this.genre = genre;
        }


        public void setLanguage(String language) {
                this.language = language;
        }


        public void setEditor(String editor) {
                this.editor = editor;
        }


        public void setSummary(String summary) {
                this.summary = summary;
        }


        public void setReleaseDate(String releaseDate) {
                this.releaseDate = releaseDate;
        }


        @Override
        public String toString() {
                return "Book{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", author='" + author + '\'' +
                        ", genre='" + genre + '\'' +
                        ", language='" + language + '\'' +
                        ", editor='" + editor + '\'' +
                        ", summary='" + summary + '\'' +
                        ", releaseDate='" + releaseDate + '\'' +
                        '}';
        }
}
