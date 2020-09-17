package org.ray.flamingo.library;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.ray.flamingo.barn.Node;
import org.ray.flamingo.index.Clue;

@Entity
@lombok.Getter
@org.hibernate.annotations.Immutable
public class BookLink implements Clue {
	
	@Embeddable
	@SuppressWarnings("serial")
    public static class Id implements Serializable {
		
		@Column(name = "DEPOT_ID")
		protected Long depotId;
		
		@Column(name = "BOOK_ID", length = 25)
		protected String bookId;

        public Id() {}

        public Id(Long depotId, String bookId) {
            this.depotId = depotId;
            this.bookId = bookId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof Id) {
                Id that = (Id) o;
                return this.depotId.equals(that.depotId)
                    && this.bookId.equals(that.bookId);
            }
            return false;
        }

        public int hashCode() {
            return depotId.hashCode() + bookId.hashCode();
        }
    }
	 
	@EmbeddedId
	protected Id id = new Id();
	
	@ManyToOne
	@JoinColumn(name = "DEPOT_ID", insertable = false, updatable = false)
	protected Node depot;
	
	@ManyToOne
	@JoinColumn(name = "BOOK_ID", insertable = false, updatable = false)
	protected Book book;
	
	protected BookLink() {}
	
	public BookLink(Node depot, Book book) {
	    this.id.depotId = depot.getId();
	    this.id.bookId  = book.getIsbn();
	}

	@Override
	public String toString() {
		return "BookLink [node=" + depot + ", book=" + book + "]";
	}
	
	//AS CLUE

	@Override
	public String getContent() {
		return toString();
	}

}
