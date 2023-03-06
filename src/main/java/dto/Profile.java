package dto;


import enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Profile {
    private String name;
    private String surname;
    private String phone;
    private String password;
    private LocalDate created_date;
    private String status;

    private Role role;

    public Profile(String name, String surname, String phone, String password) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.password = password;
    }

    public Profile(String name, String surname, String phone, LocalDate created_date, String status, Role role) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.created_date = created_date;
        this.status = status;
        this.role = role;
    }
}
