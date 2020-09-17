package org.ray.flamingo.library;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@lombok.Getter
@org.hibernate.annotations.Immutable
public class AuthorLink {
	
	//INNER(ID) CLASS
	
	@Embeddable
	@SuppressWarnings("serial")
    public static class Id implements Serializable {
		
		@Column(name = "BOOK_ID", length = 25)
		protected String bookId;
		
		@Column(name = "AUTHOR_ID")
		protected Long authorId;

        public Id() {}

        public Id(String bookId, Long authorId) {
            this.bookId   = bookId;
            this.authorId = authorId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof Id) {
                Id that = (Id) o;
                return this.bookId.equals(that.bookId)
                    && this.authorId.equals(that.authorId);
            }
            return false;
        }

        public int hashCode() {
            return bookId.hashCode() + authorId.hashCode();
        }
    }
	
	//CLASS ITSELF
	 
	@EmbeddedId
	protected Id id = new Id();
	
	@ManyToOne
	@JoinColumn(name = "BOOK_ID", insertable = false, updatable = false)
	protected Book book;
	
	@ManyToOne
	@JoinColumn(name = "AUTHOR_ID", insertable = false, updatable = false)
	protected Author author;
	
	protected AuthorLink() {}
	
	public AuthorLink(Book book, Author author) {
	    this.id.bookId   = book.getIsbn();
	    this.id.authorId = author.getId();
	}

	@Override
	public String toString() {
		return "BookLink [book=" + book + ", author=" + author + "]";
	}

}
