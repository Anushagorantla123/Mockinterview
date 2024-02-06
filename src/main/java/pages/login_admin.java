package pages;

import org.openqa.selenium.By;

import Base.Browser;

public class login_admin extends Browser{
	
	public By username = By.xpath("//input[@name='username']");
	public By password = By.xpath("//input[@name='password']");
	public By submit_btn = By.xpath("//button[text()='Sign in']");
	
	public void usernameenter(String name) {
		enterText(username,name);
	}
	
	public void passwordenter(String password1) {
		enterText(password, password1);
	}
	
	public void submit() {
		clickElement(submit_btn);
	}

}
