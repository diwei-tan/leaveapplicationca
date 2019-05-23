package sg.edu.nus.leaveapplication.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "publicholiday")
	public class PublicHoliday {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private long id;
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
