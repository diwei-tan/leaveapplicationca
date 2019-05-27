package sg.edu.nus.leaveapplication.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

	@Entity
	public class PublicHoliday {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private long id;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate date;
		private String description;
		public PublicHoliday() {
			super();
			// TODO Auto-generated constructor stub
		}
		public PublicHoliday(LocalDate date, String description) {
			super();
			this.date = date;
			this.description = description;
		}
		
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public LocalDate getDate() {
			return date;
		}
		public void setDate(LocalDate date) {
			this.date = date;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		@Override
		public String toString() {
			return "PublicHoliday [date=" + date + ", description=" + description + "]";
		}
		
}
