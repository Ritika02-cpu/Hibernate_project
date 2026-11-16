
package com.tka.hospital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {

	@Id
	@Column(name = "patient_id")
	private int patientId;

	@Column(name = "patient_name")
	private String patientName;

	@Column(name = "patient_age")
	private int age;

	@Column(name = "patient_gender")
	private String gender;

	@Column(name = "patient_disease")
	private String disease;

	@Column(name = "doctor_name")
	private String doctorName;

	@Column(name = "patient_fees")
	private double fees;

	public Patient() {

	}

	public Patient(int patientId, String patientName, int age, String gender, String disease, String doctorName,
			double fees) {

		super();
		this.patientId = patientId;
		this.patientName = patientName;
		this.age = age;
		this.gender = gender;
		this.disease = disease;
		this.doctorName = doctorName;
		this.fees = fees;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", patientName=" + patientName + ", age=" + age + ", gender="
				+ gender + ", disease=" + disease + ", doctorName=" + doctorName + ", fees=" + fees + "]";
	}
}
