package com.ssm.entity;

public class Users {
    private Integer id;

    private String phone;

    private String email;

    private String password;

    public Users(Integer id, String phone, String email, String password) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public Users() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

	@Override
	public String toString() {
		return "Users [id=" + id + ", phone=" + phone + ", email=" + email + ", password=" + password + "]";
	}
    
}