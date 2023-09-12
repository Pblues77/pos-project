package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Employee implements java.io.Serializable{
	private int idEmployee;
	private String nameEmployee;
	private String username;
	private String password;
	private LocalDate dateOfBirth;
	private String gender;
	private LocalDate dateStartWork;
	private String position;
	private double workHours;
	private double hourlyRate;

	
	public Employee(int idEmployee, String nameEmployee,String position, String username, String password, LocalDate dateOfBirth,
			String gender, LocalDate dateStartWork, double workHours, double hourlyRate) {
		super();
		this.idEmployee = idEmployee;
		this.nameEmployee = nameEmployee;
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.dateStartWork = dateStartWork;
		this.position = position;
		this.workHours = workHours;
		this.hourlyRate = hourlyRate;
	}

	public Employee(int idEmployee, String nameEmployee, String position, String username, String password,
			LocalDate dateOfBirth, String gender) {
		super();
		this.idEmployee = idEmployee;
		this.nameEmployee = nameEmployee;
		this.position = position;
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.dateStartWork = LocalDate.now();
		workHours = 0;
		hourlyRate = 0;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(int idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getNameEmployee() {
		return nameEmployee;
	}

	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateStartWork() {
		return dateStartWork;
	}

	public void setDateStartWork(LocalDate dateStartWork) {
		this.dateStartWork = dateStartWork;
	}

	public double getWorkHours() {
		return workHours;
	}

	public void setWorkHours(double workHours) {
		this.workHours = workHours;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public static void main(String[] args) {
		LocalDate date = LocalDate.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = date.format(myFormatObj);
		System.out.println(formattedDate);
	}

}
