
package com.tka.library;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "author")
    private String author;

    @Column(name = "price")
    private double price;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "quantity")
    private int quantity;

    public Book() {
    }

    public Book(int bookId, String bookName, String author,
                double price, String publisher, int quantity) {

        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
        this.publisher = publisher;
        this.quantity = quantity;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book [bookId=" + bookId
                + ", bookName=" + bookName
                + ", author=" + author
                + ", price=" + price
                + ", publisher=" + publisher
                + ", quantity=" + quantity + "]";
    }
}
