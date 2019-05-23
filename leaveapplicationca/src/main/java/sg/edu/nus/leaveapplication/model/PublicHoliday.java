package sg.edu.nus.leaveapplication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "publicholiday")
	public class PublicHoliday {
		@Id
		@Column(name="date")
		private String date;
		private String description;
		public PublicHoliday() {
			super();
			// TODO Auto-generated constructor stub
		}
		public PublicHoliday(String date, String description) {
			super();
			this.date = date;
			this.description = description;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
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
