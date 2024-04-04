package trax.aero.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the NOTE_PAD database table.
 * 
 */
@Entity
@Table(name="NOTE_PAD")
@NamedQuery(name="NotePad.findAll", query="SELECT n FROM NotePad n")
public class NotePad implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NotePadPK id;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="NOTES_CATEGORY")
	private String notesCategory;

	@Lob
	@Column(name="NOTES_TEXT")
	private String notesText;

	@Column(name="NOTES_TITTLE")
	private String notesTittle;

	@Column(name="PRINT_NOTES")
	private String printNotes;

	public NotePad() {
	}

	public NotePadPK getId() {
		return this.id;
	}

	public void setId(NotePadPK id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getNotesCategory() {
		return this.notesCategory;
	}

	public void setNotesCategory(String notesCategory) {
		this.notesCategory = notesCategory;
	}

	public String getNotesText() {
		return this.notesText;
	}

	public void setNotesText(String notesText) {
		this.notesText = notesText;
	}

	public String getNotesTittle() {
		return this.notesTittle;
	}

	public void setNotesTittle(String notesTittle) {
		this.notesTittle = notesTittle;
	}

	public String getPrintNotes() {
		return this.printNotes;
	}

	public void setPrintNotes(String printNotes) {
		this.printNotes = printNotes;
	}

}